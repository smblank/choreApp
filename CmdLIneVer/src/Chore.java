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
    public void editChore() {

    }

    @Override
    public Duration timeSinceComplete() {
        Instant currTime = Instant.now();
        Duration diff = Duration.between(lastComplete, currTime);
        return diff;
    }

    @Override
    public String toString() {
        return name + "\n" + effortVal + "\n" + time + "\n" + frequency + "\n" + lastComplete;
    }
}
