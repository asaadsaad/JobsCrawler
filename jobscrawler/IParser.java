package jobscrawler;

import java.util.List;

public interface IParser {
	List<JobVacancy> DoParse(String url, String search, String location);
	boolean Authenticate(String url, String username, String password);
}
