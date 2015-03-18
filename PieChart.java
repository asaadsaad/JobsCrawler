import java.util.Map;
import java.io.*;

public class PieChart implements ChartObserver{
	String fileName="src/pieChart.html";
	String beginMark="//data beginning";
	String endMark="//data ending";
	
	@Override
	public void update(Map<String, Integer> dictionary) {
		StringBuilder sb=new StringBuilder();
		String format="['Onions', 1],";
		for(Map.Entry<String, Integer> set :dictionary.entrySet())
		{
			sb.append(String.format("['%s', %d],",set.getKey(),set.getValue()));
		}
		
		String result=sb.toString();
		result=result.substring(0, result.length()-1)+"\n"; //remove last comma
		
		String html=read(result);
		write(html);
		
	}
	
	public String read(String replace)
	{
		StringBuilder strb=new StringBuilder();
		boolean replacing=false;
		
        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
        	String absolutPath=new File(fileName).getAbsolutePath();
            FileReader fileReader = 
                new FileReader( absolutPath);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                if(!replacing)
                {
                	strb.append(line+"\n");
                	if(line.equals(beginMark))
                	{
                		replacing=true;
                		strb.append(replace);
                	}               	
                }
                else
                {
                	if(line.equals(endMark))
                	{
                		replacing=false;
                		strb.append(line+"\n");
                	}    
                }
            }    

            // Always close files.
            bufferedReader.close();            
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");                
        }
        catch(IOException ex) {
            System.out.println(
                "Error reading file '" 
                + fileName + "'");                   
            // Or we could just do this: 
            // ex.printStackTrace();
        }
        
        return strb.toString();
	}
	
	public void write(String content)
	{
		
        try {
            // Assume default encoding.
            FileWriter fileWriter =
                new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter =
                new BufferedWriter(fileWriter);

            // Note that write() does not automatically
            // append a newline character.
            bufferedWriter.write(content);
          

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }
	}

}
