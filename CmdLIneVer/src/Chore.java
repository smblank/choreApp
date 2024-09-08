import java.time.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Chore implements IChore{
    String name;
    IRoom room;
    int effortVal;
    Duration time;
    Instant lastComplete;
    Period frequency;
    IChore deepClean;

    public Chore() {
        name = "Vacuum";
        //Scale of 1 - 10
        effortVal = 10;
        //June 10, 2024
        Calendar cal = new GregorianCalendar(2024, Calendar.JUNE, 10);
        Date date = cal.getTime();
        lastComplete = date.toInstant();
        //1 hour
        time = Duration.ofHours(1);
        //Every 2 weeks
        frequency = Period.ofWeeks(2);
    }

    public Chore(String name, int effortVal, Duration time, Instant lastComplete, Period frequency, IRoom room) {
        this.name = name;
        this.effortVal = effortVal;
        this.time = time;
        this.lastComplete = lastComplete;
        this.frequency = frequency;
        this.room = room;
    }

    public Chore(String name, Duration time, Instant lastComplete, Period frequency) {
        this.name = name;
        this.effortVal = -1;
        this.time = time;
        this.lastComplete = lastComplete;
        this.frequency = frequency;
    }

    public Chore(String name, int effortVal, Instant lastComplete, Period frequency) {
        this.name = name;
        this.effortVal = effortVal;
        this.time = null;
        this.lastComplete = lastComplete;
        this.frequency = frequency;
    }

    public Chore(String name, Instant lastComplete, Period frequency) {
        this.name = name;
        this.effortVal = -1;
        this.time = null;
        this.lastComplete = lastComplete;
        this.frequency = frequency;
    }

    @Override
    public void addDeepClean(IChore deepClean) {
        this.deepClean = deepClean;
    }

    @Override
    public void deleteDeepClean() {
        deepClean = null;
    }

    @Override
    public IChore getDeepClean() {
        return deepClean;
    }

    @Override
    public Boolean hasDeepClean() {
        return deepClean != null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void editName(String newName) {
        name = newName;
    }

    @Override
    public IRoom getRoom() {
        return room;
    }

    @Override
    public void editRoom(IRoom newRoom) {
        room = newRoom;
    }

    @Override
    public int getEffort() {
        return effortVal;
    }

    @Override
    public void editEffort(int newEffort) {
        effortVal = newEffort;
    }

    @Override
    public Duration getTime() {
        return time;
    }

    @Override
    public void editTime(Duration newTime) {
        time = newTime;
    }

    @Override
    public Instant getLastComplete() {
        return lastComplete;
    }

    @Override
    public void editLastComplete(Instant newComplete) {
        lastComplete = newComplete;
    }

    @Override
    public void completeNow() {
        lastComplete = Instant.now();
    }

    @Override
    public int getPercentage() {
        if (frequency == null ) {
            return 0;
        }

        else {
            double freqDays = (frequency.getYears() * 365.0);
            freqDays += frequency.getMonths() * 30.0;
            freqDays += frequency.getDays();

            double days = timeSinceComplete().toDays();

            if (days > freqDays) {
                return 0;
            }
            else {
                return (int) (100 - ((days/freqDays) * 100));
            }
        }
    }

    @Override
    public boolean isOneTime() {
        return frequency == null;
    }

    @Override
    public Period getFrequency() {
        return frequency;
    }

    @Override
    public void editFrequency(Period newFreq) {
        frequency = newFreq;
    }

    @Override
    public Duration timeSinceComplete() {
        Instant currTime = Instant.now();
        return Duration.between(lastComplete, currTime);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(name);
        if (frequency != null) {
            str.append(": ").append(getPercentage()).append("%");
        }
        str.append("\n");
        str.append("Time Since Complete: ").append(Controller.readableDuration(timeSinceComplete()));
        if (frequency != null) {
            str.append(" - ").append("Frequency: ").append(Controller.readablePeriod(frequency));
        }
        str.append("\n");
        if (time != null) {
            str.append(Controller.readableDuration(time));
        }
        if (time != null && effortVal > 0) {
            str.append(" - ").append("Effort: ").append(effortVal);
        }
        else if (effortVal > 0) {
            str.append("Effort: ").append(effortVal);
        }

        if (hasDeepClean()) {
            str.append("\n").append(deepClean.toString());
        }
        return str.toString();
    }
}