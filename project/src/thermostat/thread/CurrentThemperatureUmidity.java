package thermostat.thread;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
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
	
	public CurrentThemperatureUmidity(Display display, Label labelTemperature, Label labelUmidity) {
		super();
		this.display = display;
		this.labelTemperature = labelTemperature;
		this.labelUmidity = labelUmidity;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		new Date(System.currentTimeMillis());
		this.labelTemperature.setText();
		this.labelUmidity.setText();
		this.display.timerExec(5000, this);
	}
	
	private void readFromExecution()
	{
		this.sensor = new Sensor();
		
		StringBuilder sb = new StringBuilder();
		
		Process p = Runtime.getRuntime().exec("./dht22_pin26");
	    p.waitFor();

	    BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

	    String line = "";			
	    while ((line = reader.readLine())!= null) {
	    	sb.append(line + "\n");
	    }
	    
		this.sensor.setTemperature(temperature);
		this.sensor.setUmidity(umidity);
	}
	
}