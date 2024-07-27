import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import static java.time.LocalDateTime.parse;

public class Main {
    public static void main (String[] args) throws ParseException {
//        String testString = "Vacuumn";
//        //Scale of 1 - 10
//        int effort = 10;
//        //June 10, 2024
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
//        //Date lastComplete = ft.parse("2024-06-10");
//        LocalDateTime lastComplete = parse("2024-06-10T19:34:50.63");
//        //1 hour
//        Timing time = new Timing(60 * 60);
//        //Every 2 weeks
//        int freq = 2 * 7 * 24 * 60 * 60;
//        Timing frequency = new Timing(freq);

        IChore test = new Chore ();
        System.out.println(test.toString());

        IRoom room = new Room("Test");
        room.addChore(test);
    }
}
