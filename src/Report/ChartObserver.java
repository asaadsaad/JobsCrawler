package Report;
import java.util.*;

public interface ChartObserver {
	

	String update(Map<String, Integer> locationDic,
			Map<String, Integer> salaryDic, Map<String, Integer> titleDic);

}