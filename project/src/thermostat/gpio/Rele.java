package thermostat.gpio;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import thermostat.Mapper;
import thermostat.bean.Sensor;

public class Rele {
	
	public Rele() {
		super();
		this.set(0);
	}

	public void set(int state)
	{		
		//System.err.println("STO SETTANDO CON QUESTI VALORI " + state);
		
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

