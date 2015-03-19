package jobscrawler;


import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Test {

	public static void main(String[] args) throws IOException {
		
		 org.jsoup.Connection.Response res = Jsoup.connect("https://www.dice.com/dashboard/login")
			    .data("email", "jobscrawlerproject@gmail.com")
			    .data("password", "")
			    .method(Method.POST)
			    .execute();

			Document doc = res.parse();
			String sessionId = res.cookie("JSESSIONID");
			
			System.out.println( sessionId);
			
			//Keep logged in
			Map<String, String> cookies = res.cookies();
			
			Document doc2 = Jsoup
				    .connect("https://www.dice.com/jobs?q=java&l=New+York%2C+NY")
				    .cookies(cookies)
				    .get();
			System.out.println(doc2);
	}

}
