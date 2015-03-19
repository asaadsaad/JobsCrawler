package jobscrawler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

// 2 special cases
// http://www.dice.com/job/result/rhalfint/01500-129433
// http://www.dice.com/job/result/kforcecx/ITDIA1412073

public class DiceParser implements IParser {
	public static final String URL_PARSE = "https://www.dice.com/jobs";
	public static final String URL_LOGIN = "https://www.dice.com/dashboard/login";
	public static final String USERNAME = "jobscrawlerproject@gmail.com";
	public static final String PASSWORD = "project2015";
	private Response res;
	private Map<String, String> cookies;

	@SuppressWarnings("deprecation")
	@Override
	public List<JobVacancy> DoParse(String url, String search, String location) {
		List<JobVacancy> vacancies = new ArrayList<JobVacancy>();

		// PARSING
		// Keep logged in
		cookies = res.cookies();
		String encodedUrl = url + "?q=" + URLEncoder.encode(search) + "&l=" + URLEncoder.encode(location);
		Document doc;

		doc = this.NavigateTo(encodedUrl);

		// Save queried vacancies with some info
		vacancies = this.SaveQueryResults(doc);

		// Navigate to job url to get full data
		this.GetVacancyData(vacancies);

		return vacancies;
	}

	private Document NavigateTo(String url) {
		try {
			// Navigate
			Document doc = Jsoup.connect(url)
					.timeout(Global.CONNECTION_TIMEOUT).cookies(this.cookies)
					.get();
			return doc;
		} catch (IOException e) {
			System.out.println("Connect error !!!");
			// e.printStackTrace();
			return null;
		}
	}

	private void GetVacancyData(List<JobVacancy> vacancies) {
		System.out.println("==== Get Vacancy data for Dice.com ====");

		for (JobVacancy vacancy : vacancies) {
			System.out.println("Vacancy " + vacancies.indexOf(vacancy));
			System.out.println("Title = " + vacancy.getTitle());
			System.out.println("Url = " + vacancy.getUrl());
			System.out.println("Location = " + vacancy.getLocation());
			System.out.println("Company = " + vacancy.getCompany());
			
			// Navigate to each vacancy
			Document doc = this.NavigateTo(vacancy.getUrl());

			if (doc != null) {
				// PositionId
				vacancy.setPositionId(this.GetPositionId(doc));

				// Description
				vacancy.setDescription(this.GetDescription(doc));
				
				// Employee Type
				vacancy.setEmployType(this.GetEmployeeType(doc));
				
				// Contact Info
				vacancy.setContactInfo(this.GetContactInfo(doc));
				
			}
			System.out.println(" ");
		}
	}

	private String GetContactInfo(Document doc) {
		String contact = null;
		
		if (doc.select("body div.contact_info").size() > 0) {
			contact = doc.select("body div.contact_info dl").get(0).text();
		} else if (doc.select("h3:contains(Posted By)").size() > 0) {
			// https://www.dice.com/jobs/detail/Java-Developer-Source-Allies%2C-Inc.-Des-Moines-IA-50312/10116220/772915?q=java developer&l=Des Moines, IA
			contact = doc.select("h3:contains(Posted By)").get(0).nextElementSibling().text();
			if (doc.select("a#contactTT").size() > 0) {
				contact += doc.select("a#contactTT").get(0).attr("data-original-title"); // Phone
			}
		} else if (doc.select("strong:contains(Contact:)").size() > 0) {
			// https://www.dice.com/jobs/detail/Java-Developer-Visiont-Des-Moines-IA-50309/10397961/897386?q=java%20developer&l=Des%20Moines,%20IA
			// Element parent = doc.select("strong:contains(Contact:)").get(0).parent();
			// TO DO
		} else if (doc.select("td.col_one_b:contains(Phone:)").size() > 0) {
			// http://www.dice.com/job/result/rhalfint/01500-129433
			contact = "Phone: " + doc.select("td.col_one_b:contains(Phone:)").get(0).nextElementSibling().text();
		} else if (doc.select("div.contact_info:contains(Contact Information)").size() > 0) {
			// http://www.dice.com/job/result/10125450/AVA400769
			for (Element e : doc.select("div.contact_info:contains(Contact Information)").get(0).select("dl")) {
				// add name, Phone, fax
				contact += e.text();
			} 
			
			// add siteUrl
			if (doc.select("div#siteUrl").size() > 0) {
				contact += doc.select("div#siteUrl a").get(0).text();
			}
			
			// TO DO : recheck 
		} 

		System.out.println("Contact = " + contact);
		return contact;
	}

	private String GetEmployeeType(Document doc) {
		String empType = null;

		// There are 2 ways Dice show Employee Type
		if (doc.select("body div.iconsiblings").size() > 0) {
			empType = doc.select("body div.iconsiblings").get(0).text(); // Get the first child only !!!
		} else if (doc.select("dt:contains(Employ. Type)").size() > 0) {
			empType = doc.select("dt:contains(Employ. Type)").get(0).nextElementSibling().text();
		}

		System.out.println("EmpType = " + empType);
		return empType;
	}

	private String GetDescription(Document doc) {
		String description = null;
		
		// There are 4 ways Dice show Description (maybe more)
		if (doc.select("body h2.jobdesc").size() > 0) {
			description = doc.select("body div#jobdescSec").get(0).text();
		} else if (doc.select("body div.job_description").size() > 0) {
			description = doc.select("body div.job_description").get(0).text();
		} else if (doc.select("body div.jobDesc div.content").size() > 0) {
			description = doc.select("body div.jobDesc div.content").get(0).text();
		} else if (doc.select("body div.job_desc").size() > 0) {
			description = doc.select("body div.job_desc").get(0).text();
		} 

		System.out.println("Description = " + description);
		return description;
	}

	private String GetPositionId(Document doc) {
		String positionId = null;

		// There are 4 ways Dice show PositionId (maybe more)
		if (doc.select("div.company-header-info").size() > 0) {
			String temp = doc.select("div.company-header-info").get(0)
					.select("div.col-md-12").text();
			int pos = temp.lastIndexOf(" ");
			positionId = temp.substring(pos + 1, temp.length());

		} else if (doc.select("dt:contains(Position ID)").size() > 0) {
			positionId = doc.select("dt:contains(Position ID)").get(0).nextElementSibling().text();
		} else if (doc.select("dt:contains(Job Ref. Code)").size() > 0) {
			positionId = doc.select("dt:contains(Job Ref. Code)").get(0).nextElementSibling().text();
		} else if (doc.select("td.col_one_b:contains(Position ID)").size() > 0) {
			positionId = doc.select("td.col_one_b:contains(Position ID)").get(0).nextElementSibling().text();
		}

		System.out.println("PositionId = " + positionId);
		return positionId;
	}

	private List<JobVacancy> SaveQueryResults(Document doc) {
		List<JobVacancy> vacancies = new ArrayList<JobVacancy>();

		Elements elems = doc.select("div.serp-result-content");

		for (Element elem : elems) {
			String title = elem.select("h3 a").text();
			String url = elem.select("h3 a").attr("href");
			String location = elem.select("ul li.location").get(0).text();
			String company = elem.select("ul li.employer a").get(0).text();

			vacancies.add(new JobVacancy(title, url, location, company));
		}

		return vacancies;
	}

	@Override
	public boolean Authenticate(String url, String username, String password) {

		try {
			res = Jsoup.connect(url).timeout(Global.CONNECTION_TIMEOUT)
					.data("email", username).data("password", password)
					.method(Method.POST).execute();

			String sessionId = res.cookie("JSESSIONID");

			System.out.println(sessionId);

			return (res == null ? false : true);

		} catch (IOException e) {
			System.out.println("Login error");
			// e.printStackTrace();
			return false;
		}
	}

}
