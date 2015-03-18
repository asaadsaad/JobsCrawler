import java.util.*;

public class client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PieChart pie=new PieChart();
		Map<String, Integer> m=new HashMap<String, Integer>();
		m.put("JavaDeveloper", 5);
		m.put("SDE", 3);
		m.put("SeniorDeveloper", 3);	
		
		pie.update(m);

	}

}
