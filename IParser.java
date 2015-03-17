package jobscrawler;

import java.util.List;

public interface IParser {
	// url : "dice.com" , search = "Java developer"
	List<JobVacancy> DoParse(String url, String search);

	// Check Authenticate before DoParse
	boolean Authenticate(String url, String username, String password);
}
