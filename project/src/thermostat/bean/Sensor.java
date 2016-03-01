package thermostat.bean;

public class Sensor {

	private Values values;
	private double currentUmidity;
	private double currentTemperature;
	
	public Sensor(Values values) {
		super();
		this.values = values;
	}
	
	public double getCurrentUmidity() {
		return currentUmidity;
	}

	public void setCurrentUmidity(String currentUmidity) {
		this.currentUmidity = Double.parseDouble(currentUmidity);
	}

	public double getCurrentTemperature() {
		return currentTemperature;
	}

	public void setCurrentTemperature(String currentTemperature) {
		this.currentTemperature = Double.parseDouble(currentTemperature);
	}

	public boolean isHigher() {
		System.err.println("STO SETTANDO CON QUESTI VALORI " + currentTemperature + " " + values.getTemperature() + " " + values.isToggleButton());
		if(values.isToggleButton()) return !values.isToggleButton(); //se il bottone � su on allora non faccio partire il rel�
		return (currentTemperature > values.getTemperature());
	}
}
