package thermostat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import thermostat.gpio.Rele;
import thermostat.thread.CurrentDate;
import thermostat.thread.CurrentThemperatureUmidity;
import thermostat.thread.CurrentTime;

public class thermostat {
	//private static final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	//private static Table table;
	private static boolean toggleOnOff = false;
	private static int temperature = 18;

	/**
	 * Launch the application.
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {

		File file = new File("err.txt");
		FileOutputStream fos = new FileOutputStream(file);
		PrintStream ps = new PrintStream(fos);
		System.setErr(ps);

		System.err.println("This goes to err.txt");

		
		Display display = Display.getDefault();
		Shell shell = new Shell(display, SWT.NO_TRIM);
		shell.setMinimumSize(new Point(320, 240));
		shell.setLocation(0, 0);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_RED));
		shell.setSize(320, 240);
		
		Image imageFlame = new Image(display, thermostat.class.getResourceAsStream("resources/flame.png"));
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setAlignment(SWT.CENTER);
		Image image = new Image(display, thermostat.class.getResourceAsStream("resources/humidity.png"));
		lblNewLabel.setImage(image);
		lblNewLabel.setBounds(94, 4, 33, 50);
		
		Label label = new Label(shell, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		Image imageThermometer = new Image(display, thermostat.class.getResourceAsStream("resources/thermometer.png"));
		label.setImage(imageThermometer);
		label.setBounds(3, 4, 20, 50);
		
		Label lblCurrentTemperature = new Label(shell, SWT.NONE);
		lblCurrentTemperature.setFont(SWTResourceManager.getFont("Segoe UI", 26, SWT.NORMAL));
		lblCurrentTemperature.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCurrentTemperature.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblCurrentTemperature.setBounds(26, 4, 65, 50);
		
		Label lblCurrentUmidity = new Label(shell, SWT.NONE);
		lblCurrentUmidity.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblCurrentUmidity.setFont(SWTResourceManager.getFont("Segoe UI", 26, SWT.NORMAL));
		lblCurrentUmidity.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCurrentUmidity.setBounds(130, 4, 91, 50);
		
		Label lblCurrentTime = new Label(shell, SWT.NONE);
		lblCurrentTime.setAlignment(SWT.CENTER);
		lblCurrentTime.setText("-");
		lblCurrentTime.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblCurrentTime.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		lblCurrentTime.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCurrentTime.setBounds(225, 29, 93, 25);
		
		Label lblCurrentDate = new Label(shell, SWT.NONE);
		lblCurrentDate.setAlignment(SWT.CENTER);
		lblCurrentDate.setText("-");
		lblCurrentDate.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblCurrentDate.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		lblCurrentDate.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblCurrentDate.setBounds(225, 4, 93, 25);
		
		Composite composite = new Composite(shell, SWT.BORDER);
		composite.setBounds(3, 60, 146, 174);
		
		Label lblTemperature = new Label(composite, SWT.NONE);
		lblTemperature.setAlignment(SWT.CENTER);
		lblTemperature.setBounds(0, 54, 146, 47);
		lblTemperature.setText("18\u00B0");
		lblTemperature.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblTemperature.setFont(SWTResourceManager.getFont("Segoe UI", 26, SWT.NORMAL));
		lblTemperature.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		Button btnMinus = new Button(composite, SWT.NONE);
		btnMinus.setFont(SWTResourceManager.getFont("Segoe UI", 30, SWT.BOLD));
		btnMinus.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				temperature--;
				lblTemperature.setText(temperature + "°");
			}
		});
		btnMinus.setBounds(0, 1, 73, 47);
		btnMinus.setText("-");
		
		Button btnPlus = new Button(composite, SWT.NONE);
		btnPlus.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				temperature++;
				lblTemperature.setText(temperature + "°");
			}
		});
		btnPlus.setText("+");
		btnPlus.setFont(SWTResourceManager.getFont("Segoe UI", 30, SWT.BOLD));
		btnPlus.setBounds(79, 1, 67, 47);
		
		Button btnOnoff = new Button(composite, SWT.NONE);
		btnOnoff.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		btnOnoff.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(toggleOnOff == true)
				{
					toggleOnOff = false;
					btnOnoff.setText("ON");
					Rele.set(0);
				}
				else
				{
					btnOnoff.setText("");
					toggleOnOff = true;
					Rele.set(1);
				}
			}
		});
		btnOnoff.addPaintListener(new PaintListener() {
			@Override
			    public void paintControl(PaintEvent arg0) {
					if(toggleOnOff)
					{
						arg0.gc.drawImage(imageFlame, 0, 0);
					}
			    }
			});
		btnOnoff.setText("ON");
		btnOnoff.setFont(SWTResourceManager.getFont("Segoe UI", 20, SWT.BOLD));
		btnOnoff.setBounds(0, 103, 146, 71);
		
		List list = new List(shell, SWT.NONE);
		list.setEnabled(false);
		list.setItems(new String[] {"A", "B", "C", "D"});
		list.setBounds(155, 60, 65, 174);

		Runnable dateThread = new CurrentDate(display, lblCurrentDate);
		Runnable timeThread = new CurrentTime(display, lblCurrentTime);
		display.timerExec(1000, dateThread);
		display.timerExec(1000, timeThread);
		
		Runnable temperatureUmidityThread = new CurrentThemperatureUmidity(display, lblCurrentTemperature, lblCurrentUmidity);
		display.timerExec(5000, temperatureUmidityThread);
		
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
