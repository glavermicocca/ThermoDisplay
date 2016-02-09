package thermostat;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class TestThread extends Thread
{
	private Display display;
	private Label label;
	private int count = 0;
	
	
	public TestThread(Display display, Label label) {
		super();
		this.display = display;
		this.label = label;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		this.label.setText(""+count++);
		this.display.timerExec(1000, this);
	}
	
}
