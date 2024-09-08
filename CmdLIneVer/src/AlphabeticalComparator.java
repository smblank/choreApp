import java.util.Comparator;

public class AlphabeticalComparator implements Comparator<IChore> {
    @Override
    public int compare(IChore c1, IChore c2) {
        return c1.getName().compareTo(c2.getName());
    }
}

