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

public class DiceParser implements IParser {
	public static final String URL_PARSE = "https://www.dice.com/jobs";
	public static final String URL_LOGIN = "https://www.dice.com/dashboard/login";
	public static final String USERNAME = "jobscrawlerproject@gmail.com";
	public static final String PASSWORD = "project2015";
	private Response res;
	
	@SuppressWarnings("deprecation")
	@Override
	public List<JobVacancy> DoParse(String url, String search) {
		List<JobVacancy> vacancies = new ArrayList<JobVacancy>();
		
		// PARSING
		// Keep logged in
		Map<String, String> cookies = res.cookies();
		String encodedUrl = url + "?q=" + URLEncoder.encode(search);
		Document doc;
		
		try {
			doc = Jsoup
				    .connect(encodedUrl)
				    .cookies(cookies)
				    .get();
			
			System.out.println(doc);
			// Traverse to parse
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vacancies;
	}

	@Override
	public boolean Authenticate(String url, String username, String password) {
		
		try {
			res = Jsoup.connect(url)
				    .data("email", username)
				    .data("password", password)
				    .method(Method.POST)
				    .execute();
			
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
