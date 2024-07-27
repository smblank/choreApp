import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static java.time.LocalDateTime.parse;

public class Main {
    public static void main (String[] args) throws ParseException {
        Controller c = new Controller();

        //Bedroom
        IRoom bedRoom = new Room("Bedroom");
        //Vacuum
        Period frequency = Period.ofWeeks(2);
        Duration time = Duration.ofHours(1);
        Calendar cal = new GregorianCalendar(2024, Calendar.JULY, 22);
        Date date = cal.getTime();
        IChore vacuum = new Chore ("Vacuum Floor", 8, time, date.toInstant(), frequency);
        //System.out.println(vacuum.toString());
        System.out.println(c.instantToDate(vacuum.getLastComplete()));
        System.out.println(c.readableDuration(vacuum.getTime()));
        System.out.println(c.readablePeriod(vacuum.getFrequency()));

        frequency = Period.ofMonths(4);
        time = Duration.ofHours(2);
        cal = new GregorianCalendar(2024, Calendar.FEBRUARY, 3);
        date = cal.getTime();
        IChore deepVacuum = new Chore("Vacuum Floor" , 10, time, date.toInstant(), frequency);
        vacuum.addDeepClean(deepVacuum);
        //System.out.println(vacuum.getDeepClean().toString());

        //Trash
        frequency = Period.ofWeeks(1);
        time = Duration.ofMinutes(15);
        IChore trash = new Chore("Take out Trash", 2, time, date.toInstant(), frequency);
        //System.out.println(trash.toString());

        bedRoom.addChore(trash);
        bedRoom.addChore(vacuum);
        //System.out.println(bedRoom.toString());
    }
}
