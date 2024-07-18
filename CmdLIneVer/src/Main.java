import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main (String[] args) throws ParseException {
        String testString = "Vacuumn";
        //Scale of 1 - 10
        int effort = 10;
        //June 10, 2024
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date lastComplete = ft.parse("2024-06-10");
        //1 hour
        Timing time = new Timing(60 * 60);
        //Every 2 weeks
        int freq = 2 * 7 * 24 * 60 * 60;
        Timing frequency = new Timing(freq);

        IChore test = new Chore (testString, effort, time, lastComplete, frequency);
        System.out.println(test.toString());
    }
}
