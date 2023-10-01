package jessica.value;

import java.awt.Color;

public class ValueColor extends Value{
	private String name;
	private int value;
	
	public ValueColor(String name, int value) {
		super(name, value);
		this.name = name;
		this.value = value;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	public Integer getValue() {
		return this.value;
	}
}
