import java.util.Comparator;

public class PercentageComparator implements Comparator<IChore> {
    @Override
    public int compare(IChore c1, IChore c2) {
        return Integer.compare(c1.getPercentage(), c2.getPercentage());
    }
}
