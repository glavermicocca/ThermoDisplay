package thermostat.thread;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.Date;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import thermostat.Mapper;
import thermostat.bean.Sensor;

public class CurrentThemperatureUmidity extends Thread
{
	private Display display;
	private Label labelTemperature;
	private Label labelUmidity;
	private Sensor sensor;
	private int counter;
	
	public CurrentThemperatureUmidity(Display display, Label labelTemperature, Label labelUmidity) {
		super();
		this.display = display;
		this.labelTemperature = labelTemperature;
		this.labelUmidity = labelUmidity;
		this.sensor = new Sensor();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		
		readFromExecution();
		
		this.display.timerExec(5000, this);
	}
	
	private void readFromExecution()
	{
		try
		{
		    this.labelTemperature.setText(this.sensor.getTemperature());
		    this.labelUmidity.setText(this.sensor.getUmidity());
			
			System.err.println(Mapper.ABSOLUTE_PATH_UNIX);
			
			Process p = Runtime.getRuntime().exec(Mapper.ABSOLUTE_PATH_UNIX + "dht22_pin26");
		    p.waitFor();
	
		    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    
		    String line = "";
		    while ((line = reader.readLine())!= null) {
		    	System.err.println(line);
		    	
		    	this.sensor.setTemperature(line.split(",")[0]);
		    	this.sensor.setUmidity(line.split(",")[1]);	    	
		    }
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
	}
	
}