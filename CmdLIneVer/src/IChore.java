import java.time.Duration;
import java.time.Instant;
import java.time.Period;

public interface IChore {
    void addDeepClean(IChore deepClean);
    void deleteDeepClean();
    IChore getDeepClean();
    Boolean hasDeepClean();
    String getName();
    void editName(String newName);
    int getEffort();
    void editEffort(int newEffort);
    Duration getTime();
    void editTime(Duration newTime);
    Instant getLastComplete();
    void editLastComplete(Instant newComplete);
    void completeNow();

    int getPercentage();

    boolean isOneTime();

    Period getFrequency();
    void editFrequency(Period newFreq);
    Duration timeSinceComplete();
    String toString();
}
