package jessica.events;

public interface Cancellable {
    boolean isCancelled();

    void setCancelled(boolean var1);
}