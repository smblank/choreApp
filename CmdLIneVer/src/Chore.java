import java.time.Duration;
import java.time.Instant;
import java.util.Date;


public class Chore implements IChore{
    String name;
    int effortVal;
    Timing time;
    Date lastComplete;
    Timing frequency;
    IChore deepClean;

    public Chore(String name, int effortVal, Timing time, Date lastComplete, Timing frequency) {
        this.name = name;
        this.effortVal = effortVal;
        this.time = time;
        this.lastComplete = lastComplete;
        this.frequency = frequency;
    }

    public Chore(String name, Date lastComplete, Timing frequency) {
        this.name = name;
        this.lastComplete = lastComplete;
        this.frequency = frequency;
    }

    public Chore(String name, Timing time, Date lastComplete, Timing frequency) {
        this.name = name;
        this.time = time;
        this.lastComplete = lastComplete;
        this.frequency = frequency;
    }

    public Chore(String name, int effortVal, Date lastComplete, Timing frequency) {
        this.name = name;
        this.effortVal = effortVal;
        this.lastComplete = lastComplete;
        this.frequency = frequency;
    }

    @Override
    public void editChore() {

    }

    @Override
    public Timing timeSinceComplete() {
        return new Timing(0);
    }

    @Override
    public String toString() {
        return name + "\n" + effortVal + "\n" + time + "\n" + frequency + "\n" + lastComplete;
    }
}
