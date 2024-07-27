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

    //TODO: Doesn't get chore names for list
    @Override
    public String toString() {
        StringBuilder choreStr = new StringBuilder();
        for (IChore iChore : choreList) {
            choreStr.append(iChore).append(", ");
        }
        return choreStr.toString();
    }
}
