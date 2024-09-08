import java.util.Comparator;

public class TimeComparator implements Comparator<IChore> {

    @Override
    public int compare(IChore c1, IChore c2) {
        if (c1.getTime() == null && c2.getTime() == null) {
            return 0;
        }
        else if (c1.getTime() == null && c2.getTime() != null) {
            return 1;
        }
        else if (c1.getTime() != null && c2.getTime() == null) {
            return -1;
        }
        else {
            return c1.getTime().compareTo(c2.getTime());
        }
    }
}

