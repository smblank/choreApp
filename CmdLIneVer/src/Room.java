import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Room implements IRoom {
    String name;
    List<IChore> choreList;

    public Room(String name) {
        this.name = name;
        choreList = new ArrayList<>();
    }

    @Override
    public void addChore(IChore newChore) {
        choreList.add(newChore);
    }

    @Override
    public void removeChore(IChore chore) {
        choreList.remove(chore);
    }

    @Override
    public List<IChore> getChores() {
        return choreList;
    }

    @Override
    public IChore getChore(String choreName) {
        IChore targetChore = null;
        for (IChore chore: choreList) {
            if (Objects.equals(chore.getName(), choreName)) {
                targetChore = chore;
            }
        }
        return targetChore;
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
    public int getRoomState() {
        double chorePercentage = 0;
        for (IChore chore: choreList) {
            chorePercentage += chore.getPercentage();
        }
        double totPercentage = 100 * choreList.size();

        return (int) ((totPercentage - chorePercentage) / totPercentage);
    }

    @Override
    public int getPerfectRoom() {
        int roomState = 0;
        for (IChore iChore : choreList) {
            if (!iChore.isOneTime()) {
                int freq = iChore.getFrequency().getYears() * 365;
                freq += iChore.getFrequency().getMonths() * 30;
                freq += iChore.getFrequency().getDays();
                roomState += freq;
            }
        }
        return roomState;
    }

    @Override
    public String toString() {
        StringBuilder choreStr = new StringBuilder();
        int i;
        if (choreList.size() > 0) {
            for (i = 0; i < choreList.size() - 1; ++i) {
                choreStr.append(choreList.get(i).getName()).append(", ");
            }
            choreStr.append(choreList.get(i).getName());
            return choreStr.toString();
        }
        return "";
    }
}
