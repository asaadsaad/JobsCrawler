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

public class CyberCodersParser implements IParser {
	public static final String CYBER_SITE = "http://www.cybercoders.com";
	public static final String URL_PARSE = "http://www.cybercoders.com/search/";
	public static final String URL_LOGIN = "https://www.cybercoders.com/login?returnURL=/";
	public static final String USERNAME = "jobscrawlerproject@gmail.com";
	public static final String PASSWORD = "project2015";
	private Response res;
	private Map<String, String> cookies;

	@SuppressWarnings("deprecation")
	@Override
	public List<JobVacancy> DoParse(String url, String search, String location) {
		List<JobVacancy> vacancies = new ArrayList<JobVacancy>();

		// PARSING
		String encodedUrl = url + "?searchterms=" + URLEncoder.encode(search)
				+ "&searchlocation=" + URLEncoder.encode(location);
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
		System.out.println("==== Get Vacancy data for CyberCoders.com ====");

		for (JobVacancy vacancy : vacancies) {
			System.out.println("Vacancy " + vacancies.indexOf(vacancy));

			System.out.println("Title = " + vacancy.getTitle());
			System.out.println("Url = " + vacancy.getUrl());
			System.out.println("Location = " + vacancy.getLocation());
			System.out.println("Company = " + vacancy.getCompany());
			System.out.println("EmpType = " + vacancy.getEmployType());
			System.out.println("Salary = " + vacancy.getSalary());
			System.out.println("PostedTime = " + vacancy.getPostedTime());

			// Navigate to each vacancy
			Document doc = this.NavigateTo(vacancy.getUrl());

			if (doc != null) {
				// Description
				vacancy.setDescription(this.GetDescription(doc));

				// Contact Info
				vacancy.setContactInfo(this.GetContactInfo(doc));

				// Email
				vacancy.setContactInfo(this.GetEmail(doc));

				// PositionId (Job ID)
				vacancy.setPositionId(this.GetPositionId(doc));

				// Skillsets
				vacancy.setSkillSets(this.GetSkillSets(doc));

			}
			System.out.println("=======================");
		}
	}

	private String GetEmail(Document doc) {
		String email = null;

		String href = doc.select("div.recruiter-apply-content").get(0).child(1)
				.select("p a").get(1).attr("href");
		email = href.replaceAll("mailto:", "");
		int pos = email.indexOf("?");
		email = email.substring(0, pos);

		System.out.println("Email = " + email);
		return email;
	}

	private String GetPositionId(Document doc) {
		String posId = doc.select("div.job-id").text()
				.replaceAll("Job ID: ", "");
		System.out.println("PositionId = " + posId);
		return posId;
	}

	private String GetSkillSets(Document doc) {
		String skillSets = doc.select("span.skill-name").text();
		System.out.println("Skillset = " + skillSets);
		return skillSets;
	}

	private String GetContactInfo(Document doc) {
		String contact = null;

		String recruiterLink = CyberCodersParser.CYBER_SITE
				+ doc.select("div.recruiter-apply-content").get(0).child(1)
						.select("p a").get(0).attr("href");
		String recruiterName = doc.select("div.recruiter-apply-content").get(0)
				.child(1).select("p a").get(0).text();
		contact = "Recruiter: " + recruiterName + ", ";
		contact += "Link : " + recruiterLink;

		System.out.println("Contact = " + contact);
		return contact;
	}

	private String GetDescription(Document doc) {
		String description = doc.select("div.job-details").text();
		System.out.println("Description = " + description);
		return description;
	}

	private List<JobVacancy> SaveQueryResults(Document doc) {
		List<JobVacancy> vacancies = new ArrayList<JobVacancy>();

		Elements elems = doc.select("div.job-listing-item");

		for (Element elem : elems) {
			String title = elem.select("div.job-title a").get(0).text();
			String url = CyberCodersParser.CYBER_SITE
					+ elem.select("div.job-title a").get(0).attr("href");
			String location = elem.select("div.location").get(0).text();
			// they hide company name here -> contact recruiter
			String company = elem.select("div.details > div.one-liner").get(0)
					.text();
			String empType = elem.select("div.wage > span").get(0).text();
			String salary = elem.select("div.wage").text()
					.replaceFirst(empType, "");
			String postedTime = elem.select("div.posted").text()
					.replaceAll("Posted ", "");

			vacancies.add(new JobVacancy(title, url, location, company,
					empType, salary, postedTime));
		}

		return vacancies;
	}

	@Override
	public boolean Authenticate(String url, String username, String password) {

		try {
			res = Jsoup.connect(url).timeout(Global.CONNECTION_TIMEOUT)
					.data("email", username).data("password", password)
					.method(Method.POST).execute();

			this.cookies = res.cookies();

			System.out.println(cookies);

			return (res == null ? false : true);

		} catch (IOException e) {
			System.out.println("Login error");
			// e.printStackTrace();
			return false;
		}
	}

}
