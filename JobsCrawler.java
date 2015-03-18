package jobscrawler;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JobsCrawler {

	public static void main(String[] args) throws IOException {
		
		DiceParser dice = new DiceParser();
		if (dice.Authenticate(DiceParser.URL_LOGIN, DiceParser.USERNAME, DiceParser.PASSWORD )) {
			dice.DoParse(DiceParser.URL_PARSE, "java developer");	
		}
	}

}
