package jessica.value;

public class ValueBoolean extends Value {
	private boolean value;
	private String name;

	public ValueBoolean(String name, boolean state) {
		super(name, state);
		this.name = name;
		this.value = state;
		arrayVal.add(this);
	}
	
	public Boolean getValue() {
		return value;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
