import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.time.LocalDateTime.parse;


public class Chore implements IChore{
    String name;
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

    public Chore(String name, int effortVal, Duration time, Instant lastComplete, Period frequency) {
        this.name = name;
        this.effortVal = effortVal;
        this.time = time;
        this.lastComplete = lastComplete;
        this.frequency = frequency;
    }

    public Chore(String name, Duration time, Instant lastComplete, Period frequency) {
        this.name = name;
        this.time = time;
        this.lastComplete = lastComplete;
        this.frequency = frequency;
    }

    public Chore(String name, int effortVal, Instant lastComplete, Period frequency) {
        this.name = name;
        this.effortVal = effortVal;
        this.lastComplete = lastComplete;
        this.frequency = frequency;
    }

    public Chore(String name, Instant lastComplete, Period frequency) {
        this.name = name;
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
        str.append(name).append("\n");
        str.append("Time Since Complete: ").append(Controller.readableDuration(timeSinceComplete())).append(" - ");
        str.append("Frequency: ").append(Controller.readablePeriod(frequency)).append("\n");
        str.append(Controller.readableDuration(time)).append(" - ").append("Effort: ").append(effortVal);
        return str.toString();
    }
}
