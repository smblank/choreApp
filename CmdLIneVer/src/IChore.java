import java.time.Duration;

public interface IChore {
    void editChore();
    String toString();
    public Duration timeSinceComplete();
}
