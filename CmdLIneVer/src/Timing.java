public class Timing {
    int seconds;
    int minutes;
    int hours;
    int days;
    int totSecs;
    public Timing(int time) {
        totSecs = time;

        minutes = time / 60;
        seconds = minutes % 60;
        hours = minutes / 60;
        minutes = minutes % 60;

        days = hours / 24;
        hours = hours % 24;
    }

    @Override
    public String toString() {
        String str = "Days: " + String.valueOf(days) +
                "\nHours: " + String.valueOf(hours) +
                "\nMinutes: " + String.valueOf(minutes) +
                "\nSeconds: " + String.valueOf(seconds);

       return str;
    }
}
