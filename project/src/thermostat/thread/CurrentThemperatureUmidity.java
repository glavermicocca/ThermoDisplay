package thermostat.thread;

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.util.Date;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

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
		
		try {
			readFromExecution();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		this.display.timerExec(5000, this);
	}
	
	private void readFromExecution() throws Exception
	{
	    this.labelTemperature.setText(this.sensor.getTemperature());
	    this.labelUmidity.setText(this.sensor.getUmidity());
	    
		//String absolutePath = System.getProperty("user.dir") + "\\src\\thermostat\\resources\\";
		String absolutePathUnix = "/MY_JAVA_CLASSES/thermostat/resources/";
		
		System.err.println(absolutePathUnix);
		
		Process p = Runtime.getRuntime().exec(absolutePathUnix + "dht22_pin26");
	    p.waitFor();

	    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
	    
	    String line = "";
	    while ((line = reader.readLine())!= null) {
	    	System.err.println(line);
	    	
	    	this.sensor.setTemperature(line.split(",")[0]);
	    	this.sensor.setUmidity(line.split(",")[1]);	    	
	    }
	}
	
}