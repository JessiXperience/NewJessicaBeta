package jessica.utils;

public class TimerUtils {
	private long lastMS;
	private long prevMS;
	private long previousTime;
    
    public TimerUtils() {
    	this.prevMS = 0L;
        this.previousTime = -1L;
    }
    
    public boolean check(float milliseconds) {
        return this.getCurrentMS() - this.previousTime >= milliseconds;
    }
    
    public short convert(float perSecond) {
        return (short)(1000.0f / perSecond);
    }
    
    public long get() {
        return this.getCurrentMS() - this.previousTime;
    }
    
    protected long getCurrentMS() {
        return System.nanoTime() / 1000000L;
    }
    
      
      public long getLastMS() {
        return this.lastMS;
      }
      
      public boolean hasReached(long milliseconds) {
        return (getCurrentMS() - this.lastMS >= milliseconds);
      }
      
      public boolean resetIfHasReached(long milliseconds) {
        if (hasReached(milliseconds)) {
          reset();
          return true;
        } 
        return false;
      }
      
      public long getTimeDiff() {
        return getCurrentMS() - this.lastMS;
      }
      
      public void reset() {
        this.lastMS = getCurrentMS();
      }
      
      public void setLastMS(long currentMS) {
        this.lastMS = currentMS;
      }
    }
