package jessica.value;

public class ValueNumber extends Value {

	private String name;
	private double value;
	private double min;
	private double max;
	public double increment;

	public ValueNumber(String name, double value, double min, double max, double increment) {
		super(name, value);
		this.name = name;
		this.value = value;
		this.min = min;
		this.max = max;
		this.increment = increment;
		arrayVal.add(this);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}
	
	public double getDoubleValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double setValueMax(double v) {
		return this.max = v;
	}
}
