import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import org.json.*;

public class Controller {
    List<IRoom> rooms;

    public Controller() {
        rooms = new ArrayList<IRoom>();
    }

    //NOTE: This adds extra fields to the json & I'm not sure why
    public void exportData(String filePath) throws IOException {
        JSONObject json = new JSONObject();
        JSONArray roomsArr = new JSONArray();

        for (IRoom room: rooms) {
            JSONArray chores = new JSONArray();
            for (IChore chore: room.getChores()) {
                JSONObject obj = new JSONObject();
                obj.put("name", chore.getName());
                obj.put("effort", chore.getEffort());
                obj.put("time", chore.getTime());
                obj.put("lastComplete", chore.getLastComplete());
                obj.put("frequency", chore.getFrequency());
                chores.put(obj);
            }
            JSONObject roomObj = new JSONObject();
            roomObj.put("name", room.getName());
            roomObj.put("chores", chores);
            roomsArr.put(room);
            json.put("rooms", rooms);
        }

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(json.toString());
        }
    }

    public void importData(String filePath) throws IOException, ParseException {
        String jsonStr = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        JSONObject obj = new JSONObject(jsonStr);

        JSONArray arr = obj.getJSONArray("rooms");
        for (int i = 0; i < arr.length(); ++i) {
            String name = arr.getJSONObject(i).getString("name");
            IRoom room = new Room(name);

            JSONArray chores = arr.getJSONObject(i).getJSONArray("chores");
            for (int j = 0; j < chores.length(); ++j) {
                String chrName = chores.getJSONObject(j).getString("name");
                int effort = chores.getJSONObject(j).getInt("effort");
                Duration time = Duration.parse(chores.getJSONObject(j).getString("time"));
                Instant lastComp = Instant.parse(chores.getJSONObject(j).getString("lastComplete"));
                Period freq = Period.parse(chores.getJSONObject(j).getString("frequency"));

                IChore chore = new Chore(chrName, effort, time, lastComp, freq);
                room.addChore(chore);
            }
            rooms.add(room);
        }
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
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date d = df.parse(date);

        return d.toInstant();
    }

    static String instantToDate(Instant inst) {
        ZonedDateTime date = inst.atZone(ZoneId.systemDefault());
        return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL));
    }

    static String readableDuration(Duration dur) {
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

    static String readablePeriod(Period per) {
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
