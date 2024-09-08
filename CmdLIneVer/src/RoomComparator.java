import java.util.Comparator;

public class RoomComparator implements Comparator<IChore> {
    String room;
    public RoomComparator(String roomName) {
        room = roomName;
    }
    @Override
    public int compare(IChore c1, IChore c2) {
        if (c1.getRoom().getName().equals(room) && c2.getRoom().getName().equals(room)) {
            return 0;
        }
        else if (!c1.getRoom().getName().equals(room) && c2.getRoom().getName().equals(room)) {
            return 1;
        }
        else if (c1.getRoom().getName().equals(room) && !c2.getRoom().getName().equals(room)) {
            return -1;
        }
        else {
            return 0;
        }
    }
}
