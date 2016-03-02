package thermostat.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import thermostat.Mapper;
import thermostat.bean.Sensor;
import thermostat.bean.Values;
import thermostat.gpio.Rele;

public class CurrentThemperatureUmidity extends Thread
{
	private Display display;
	private Label labelTemperature;
	private Label labelUmidity;
	private Sensor sensor;
	private Rele rele;
	private Values values;
	
	private int counter;
	private int lastValue = 0;
	
	public CurrentThemperatureUmidity(Values values, Rele rele, Sensor sensor, Display display, Label labelTemperature, Label labelUmidity) {
		super();
		this.values = values;
		this.rele = rele;
		this.sensor = sensor;
		this.display = display;
		this.labelTemperature = labelTemperature;
		this.labelUmidity = labelUmidity;
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
		    this.labelTemperature.setText(""+this.sensor.getCurrentTemperature());
		    this.labelUmidity.setText(""+this.sensor.getCurrentUmidity());
			
			System.err.println(Mapper.ABSOLUTE_PATH_UNIX);
			
			Process p = Runtime.getRuntime().exec(Mapper.ABSOLUTE_PATH_UNIX + "dht22_pin26");
		    p.waitFor();
	
		    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    
		    String line = "";
		    while ((line = reader.readLine())!= null) {
		    	System.err.println(line);
		    	
		    	this.sensor.setCurrentTemperature(line.split(",")[0]);
		    	this.sensor.setCurrentUmidity(line.split(",")[1]);
		    	
		    	if(this.values.isToggleButton())
		    	{
		    		if(this.sensor.isHigher())
			    	{
		    			if(counter % 2 == 0)
		    			{
		    				rele.set(1);	
		    			}
			    	}
			    	else
			    	{
		    			if(counter % 2 == 0)
		    			{
		    				rele.set(0);
		    			}
			    	}	
		    	}
		    	else
		    	{
		    		rele.set(0);
		    	}
		    	
		    	counter++;
		    }
		}
		catch(Exception ex)
		{
			System.err.println(ex.getMessage());
		}
	}
	
}