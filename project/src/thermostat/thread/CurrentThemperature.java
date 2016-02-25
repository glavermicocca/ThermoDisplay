package thermostat.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class CurrentThemperature  extends Thread
{
	private Display display;
	private Label label;
	private Date dateTime;
		
	public CurrentThemperature(Display display, Label label) {
		super();
		this.display = display;
		this.label = label;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		this.dateTime = new Date(System.currentTimeMillis());
		this.label.setText(new SimpleDateFormat("hh:mm.ss").format(dateTime));
		this.display.timerExec(1000, this);
	}
	
}