package jobscrawler;

public class ParserFactory {
	public static IParser getParser(String site)
	{
		if(site.toLowerCase().contains("cyber"))
			return new CyberCodersParser();
		else 
			return new DiceParser();
	}
}


