package jobscrawler;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class jobscrawler {

	public static void main(String[] args) throws IOException {
		
//		DiceParser dice = new DiceParser();
//		if (dice.Authenticate(DiceParser.URL_LOGIN, DiceParser.USERNAME, DiceParser.PASSWORD )) {
//			dice.DoParse(DiceParser.URL_PARSE, "java developer", "New York");	
//		}
		
		CyberCodersParser cyber = new CyberCodersParser();
		if (cyber.Authenticate(CyberCodersParser.URL_LOGIN, CyberCodersParser.USERNAME, CyberCodersParser.PASSWORD )) {
			cyber.DoParse(CyberCodersParser.URL_PARSE, "java developer", "New York");	
		}
	}

}
