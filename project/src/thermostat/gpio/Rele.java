package thermostat.gpio;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import thermostat.Mapper;

public class Rele {
	public static void set(int state)
	{
		try
		{
			System.err.println(Mapper.ABSOLUTE_PATH_UNIX);
			
			Process p = Runtime.getRuntime().exec(Mapper.ABSOLUTE_PATH_UNIX + "rele_pin13 " + state);
		    p.waitFor();
		
		    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    
		    String line = "";
		    while ((line = reader.readLine())!= null) {
		    	System.err.println(line);
		    }
		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}
}

