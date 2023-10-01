package jessica.value;

import java.util.ArrayList;

public class Value<T> {
    public static ArrayList<Value> arrayVal = new ArrayList<>();
    public T value;
    public T defaultValue;
	private String name;
	
    public Value(String name) {
    	this.name = name;
    }
    
    public Value(String name, final T value) {
        this.name = name;
        this.value = value;
        this.defaultValue = value;
    }
    
    public T getValue() {
		return value;
	}
    
	public void setValue(T value) {
		this.value = value;
	}
	
	public T getDefaultValue() {
		return defaultValue;
	}
	
	public void setDefaultValue(T defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
