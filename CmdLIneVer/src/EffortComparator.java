import java.util.Comparator;

public class EffortComparator implements Comparator<IChore> {

    @Override
    public int compare(IChore c1, IChore c2) {
        return Integer.compare(c1.getEffort(), c2.getEffort());
    }
}
