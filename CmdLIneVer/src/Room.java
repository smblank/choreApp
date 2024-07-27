import java.util.ArrayList;
import java.util.List;

public class Room implements IRoom {
    String name;
    List<IChore> choreList;

    public Room(String name) {
        this.name = name;
        choreList = new ArrayList<IChore>();
    }

    @Override
    public void addChore(IChore newChore) {
        choreList.add(newChore);
    }

    @Override
    public List<IChore> getChores() {
        return choreList;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getRoomState() {
        int perfectRoom = getPerfectRoom();
        for (IChore ichore : choreList) {
            int roomState = (int) ichore.timeSinceComplete().toDays();
            if (perfectRoom - roomState > 0) {
                perfectRoom -= perfectRoom;
            }
            else {
                perfectRoom -= ichore.getFrequency().getDays();
            }
        }
        return perfectRoom;
    }

    @Override
    public int getPerfectRoom() {
        int roomState = 0;
        for (IChore iChore : choreList) {
            roomState += iChore.getFrequency().getDays();
        }
        return roomState;
    }

    @Override
    public String toString() {
        StringBuilder choreStr = new StringBuilder();
        int i;
        for (i = 0; i < choreList.size() - 1; ++i) {
            choreStr.append(choreList.get(i).getName()).append(", ");
        }
        choreStr.append(choreList.get(i).getName());
        return choreStr.toString();
    }
}
