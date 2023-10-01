package jessica.utils;

public final class WaitTimer {
    public long time = System.nanoTime() / 1000000L;

    public boolean hasTimeElapsed(long time, boolean reset) {
        if (time < 150L) {
            if ((double)this.getTime() >= (double)time / 1.63) {
                if (reset) {
                    this.reset();
                }
                return true;
            }
        } else if (this.getTime() >= time) {
            if (reset) {
                this.reset();
            }
            return true;
        }
        return false;
    }

    public long getTime() {
        return System.nanoTime() / 1000000L - this.time;
    }

    public void reset() {
        this.time = System.nanoTime() / 1000000L;
    }
}

