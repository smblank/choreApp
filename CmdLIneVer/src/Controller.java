import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Controller {
    List<IRoom> rooms;

    public Controller() {
        rooms = new ArrayList<>();
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
                if (chore.getEffort() > 0) {
                    obj.put("effort", chore.getEffort());
                }
                if (chore.getTime() != null) {
                    obj.put("time", chore.getTime());
                }
                obj.put("lastComplete", chore.getLastComplete());
                if (!chore.isOneTime()) {
                    obj.put("frequency", chore.getFrequency());
                }
                if (chore.hasDeepClean()) {
                    JSONObject deepClean = new JSONObject();
                    deepClean.put("name", chore.getDeepClean().getName());
                    if (chore.getEffort() > 0) {
                        deepClean.put("effort", chore.getDeepClean().getEffort());
                    }
                    if (chore.getTime() != null) {
                        deepClean.put("time", chore.getDeepClean().getTime());
                    }
                    deepClean.put("lastComplete", chore.getDeepClean().getLastComplete());
                    if (!chore.getDeepClean().isOneTime()) {
                        deepClean.put("frequency", chore.getDeepClean().getFrequency());
                    }
                    obj.put("deep clean", deepClean);
                }
                chores.put(obj);
            }
            JSONObject roomObj = new JSONObject();
            roomObj.put("name", room.getName());
            roomObj.put("chores", chores);
            roomsArr.put(roomObj);
            json.put("rooms", roomsArr);
        }

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(json.toString());
        }
    }

    public void importData(String filePath) throws IOException {
        String jsonStr = Files.readString(Paths.get(filePath));
        JSONObject obj = new JSONObject(jsonStr);

        JSONArray arr = obj.getJSONArray("rooms");
        for (int i = 0; i < arr.length(); ++i) {
            String name = arr.getJSONObject(i).getString("name");
            IRoom room = new Room(name);

            JSONArray chores = arr.getJSONObject(i).getJSONArray("chores");
            for (int j = 0; j < chores.length(); ++j) {
                String chrName = chores.getJSONObject(j).getString("name");
                int effort = -1;
                if (chores.getJSONObject(j).has("effort")) {
                    effort = chores.getJSONObject(j).getInt("effort");
                }
                Duration time = null;
                if (chores.getJSONObject(j).has("time")) {
                    time = Duration.parse(chores.getJSONObject(j).getString("time"));
                }
                Instant lastComp = Instant.parse(chores.getJSONObject(j).getString("lastComplete"));
                Period freq = null;
                if (chores.getJSONObject(j).has("frequency")) {
                    freq = Period.parse(chores.getJSONObject(j).getString("frequency"));
                }

                IChore chore = new Chore(chrName, effort, time, lastComp, freq, room);

                if (chores.getJSONObject(j).has("deepClean")) {
                    JSONObject deep = chores.getJSONObject(j).getJSONObject("deepClean");
                    String deepName = deep.getString("name");
                    int deepEffort = -1;
                    if (deep.has("effort")) {
                        deepEffort = deep.getInt("effort");
                    }
                    Duration deepTime = null;
                    if (deep.has("time")) {
                        deepTime = Duration.parse(deep.getString("time"));
                    }
                    Instant deepLastComp = Instant.parse(deep.getString("lastComplete"));
                    Period deepFreq = null;
                    if (deep.has("frequency")) {
                        Period.parse(deep.getString("frequency"));
                    }
                    IChore deepChore = new Chore(deepName, deepEffort, deepTime, deepLastComp, deepFreq, room);
                    chore.addDeepClean(deepChore);
                }
                room.addChore(chore);
            }
            rooms.add(room);
        }
    }

    public List<IChore> getChoreList (int time, String factor, String roomName, int numChores, int effort) {
        List<IChore> recChores = new ArrayList<>();
        List<IChore> allChores = new ArrayList<>();

        for (IRoom room: rooms) {
            allChores.addAll(room.getChores());
        }



        for (IChore chore: allChores) {
            if (effort != 0) {
                if (chore.getEffort() <= effort) {
                    recChores.add(chore);
                }
            }
        }

        if (recChores.size() > 0) {
            allChores = new ArrayList<>(recChores);
            recChores = new ArrayList<>();
        }


        for (IChore chore: allChores) {
            if (chore.getTime() != null && chore.getEffort() != -1) {
                recChores.add(chore);
            }
        }

        if (time != 0) {
            if (recChores.size() > 0) {
                allChores = new ArrayList<>(recChores);
                recChores = new ArrayList<>();
            }

            allChores.sort(new TimeComparator());
            int totTime = 0;
            for (IChore chore: allChores) {
                if (totTime + chore.getTime().toMinutes() <= time) {
                    totTime += chore.getTime().toMinutes();
                    recChores.add(chore);
                }
                else {
                    break;
                }
            }
        }

        switch (factor.toLowerCase()) {
            case "time":
                //Sort by time, then percentage
                recChores.sort(new TimeComparator().thenComparing(new PercentageComparator()));
                break;
            case "effort":
                //Sort by effort, percentage, time
                recChores.sort(new EffortComparator().thenComparing(new PercentageComparator().thenComparing(new TimeComparator())));
                break;
            case "room":
                //Prioritize one room first
                recChores.sort(new RoomComparator(roomName).thenComparing(new PercentageComparator().thenComparing(new TimeComparator())));
                break;
            default:
                //Sort by percentage, time to complete
                recChores.sort(new PercentageComparator().thenComparing(new TimeComparator()));
        }

        if (numChores != 0) {
            if (numChores < recChores.size()) {
                return recChores.subList(0, numChores);
            }
            else {
                return recChores;
            }
        }
        else {
            return recChores;
        }
    }

    public void addRoom(IRoom newRoom) {
        rooms.add(newRoom);
    }

    public void deleteRoom(IRoom room) {
        rooms.remove(room);
    }

    public void deleteRoom(String roomName) {
        rooms.removeIf(room -> Objects.equals(room.getName(), roomName));
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
