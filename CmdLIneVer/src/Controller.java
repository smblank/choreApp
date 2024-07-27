import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class Controller {
    List<IRoom> rooms;

    public Controller() {
        rooms = new ArrayList<IRoom>();
    }

    public void addRoom(IRoom newRoom) {
        rooms.add(newRoom);
    }

    public void deleteRoom(IRoom room) {
        rooms.remove(room);
    }

    public void deleteRoom(String roomName) {
        rooms.removeIf(room -> room.getName() == roomName);
    }

    public IRoom getRoom(String roomName) {
        IRoom targetRoom = null;
        for (IRoom room : rooms) {
            if (Objects.equals(room.getName(), roomName)) {
                targetRoom = room;
            }
        }
        return targetRoom;
    }

    public List<IRoom> getRooms() {
        return rooms;
    }

    public Instant dateToInstant(String date) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat();
        Date d = df.parse(date);

        return d.toInstant();
    }

    public String instantToDate(Instant inst) {
        ZonedDateTime date = inst.atZone(ZoneId.systemDefault());
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }

    public String readableDuration(Duration dur) {
        StringBuilder str = new StringBuilder();
        if (dur.toHours() > 0) {
            str.append(dur.toHours()).append(" hour");
            if(dur.toHours() > 1) {
                str.append("s");
            }
        }
        else if (dur.toMinutes() > 0) {
            str.append(dur.toMinutes()).append(" minute");
            if (dur.toMinutes() > 1) {
                str.append("s");
            }
        }
        else {
            str.append(dur.toSeconds()).append(" second");
            if (dur.toSeconds() > 1) {
                str.append("s");
            }
        }

        return str.toString();
    }

    public String readablePeriod(Period per) {
        StringBuilder str = new StringBuilder();
        if (per.getYears() > 0) {
            str.append(per.getYears()).append(" year");
            if (per.getYears() > 1) {
                str.append("s");
            }
        }
        else if (per.getMonths() > 0) {
            str.append(per.getMonths()).append(" month");
            if (per.getMonths() > 1) {
                str.append("s");
            }
        }
        else {
            if (per.getDays() % 7 == 0) {
                str.append(per.getDays() / 7).append(" week");
                if (per.getDays() / 7 > 1) {
                    str.append("s");
                }
            }
            else {
                str.append(per.getDays()).append(" day");
                if (per.getDays() > 1) {
                    str.append("s");
                }
            }
        }

        return str.toString();
    }
}
