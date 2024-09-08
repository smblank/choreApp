import java.util.Comparator;

public class TimeCompleteComparator implements Comparator<IChore> {
    @Override
    public int compare(IChore c1, IChore c2) {
        return c1.timeSinceComplete().compareTo(c2.timeSinceComplete());
    }
}
