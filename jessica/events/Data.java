package jessica.events;

import java.lang.reflect.Method;

public class Data {
	
	public final Object source;
	
	public final Method target;
	
	public final byte prioriry;
	
	public Data(Object source, Method target, byte prioriry) {
		this.source = source;
		this.target = target;
		this.prioriry = prioriry;
	}
	

}
