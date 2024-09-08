import java.util.Comparator;

public class FrequencyComparator implements Comparator<IChore> {

    @Override
    public int compare(IChore c1, IChore c2) {
        if (c1.isOneTime() && c2.isOneTime()) {
            return 0;
        }
        else if (c1.isOneTime() && !c2.isOneTime()) {
            return 1;
        }
        else if (!c1.isOneTime() && c2.isOneTime()) {
            return -1;
        }
        else {
            int freq1 = c1.getFrequency().getYears() * 365;
            freq1 += c1.getFrequency().getMonths() * 30;
            freq1 += c1.getFrequency().getDays();

            int freq2 = c2.getFrequency().getYears() * 365;
            freq2 += c2.getFrequency().getMonths() * 30;
            freq2 += c2.getFrequency().getDays();

            return Integer.compare(freq1, freq2);
        }
    }
}
