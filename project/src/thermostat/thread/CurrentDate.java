package thermostat.thread;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class CurrentDate extends Thread
{
	private Display display;
	private Label label;
	private Date dateTime;
	
	
	public CurrentDate(Display display, Label label) {
		super();
		this.display = display;
		this.label = label;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		this.dateTime = new Date(System.currentTimeMillis());
		this.label.setText(new SimpleDateFormat("dd.MM.yy").format(dateTime));
		this.display.timerExec(60000, this);
	}
	
}