package Report;

import java.util.Map;
import java.io.*;

public class PieChart implements ChartObserver {
	String fileName = "src/Report/pieChart.html";
	String beginMarkl = "//location data beginning";
	String endMarkl = "//location data ending";
	String beginMarks = "//salary data beginning";
	String endMarks = "//salary data ending";
	String beginMarkt = "//title data beginning";
	String endMarkt = "//title data ending";

	@Override
	public String update(Map<String, Integer> locationDic,
			Map<String, Integer> salaryDic, Map<String, Integer> titleDic) {

		String locationStr = getStringByDict(locationDic);
		String salary = getStringByDict(salaryDic);
		String title = getStringByDict(titleDic);

		String html = read(locationStr, salary, title);
		write(html);
		return new File(fileName).getAbsolutePath();
	}

	private String getStringByDict(Map<String, Integer> locationDic) {
		StringBuilder sb = new StringBuilder();

		for (Map.Entry<String, Integer> set : locationDic.entrySet()) {
			sb.append(String.format("['%s', %d],", set.getKey(), set.getValue()));
		}
		String result = sb.toString();
		result = result.substring(0, result.length() - 1) + "\n"; // remove last
																	// comma

		return result;
	}

	public String read(String location, String salary, String title) {
		StringBuilder strb = new StringBuilder();
		boolean replacing = false;

		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			String absolutPath = new File(fileName).getAbsolutePath();
			FileReader fileReader = new FileReader(absolutPath);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				if (!replacing) {
					strb.append(line + "\n");
					if (line.equals(beginMarkl)) {
						replacing = true;
						strb.append(location);
					} else if (line.equals(beginMarks)) {
						replacing = true;
						strb.append(salary);
					} else if (line.equals(beginMarkt)) {
						replacing = true;
						strb.append(title);
					}
				} else {
					if (line.equals(endMarkl) || line.equals(endMarks)
							|| line.equals(endMarkt)) {
						replacing = false;
						strb.append(line + "\n");
					}
				}
			}

			// Always close files.
			bufferedReader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}

		return strb.toString();
	}

	public void write(String content) {

		try {
			// Assume default encoding.
			FileWriter fileWriter = new FileWriter(fileName);

			// Always wrap FileWriter in BufferedWriter.
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			// Note that write() does not automatically
			// append a newline character.
			bufferedWriter.write(content);

			// Always close files.
			bufferedWriter.close();
		} catch (IOException ex) {
			System.out.println("Error writing to file '" + fileName + "'");
			// Or we could just do this:
			// ex.printStackTrace();
		}
	}

}
