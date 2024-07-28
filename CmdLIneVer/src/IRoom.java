import java.util.List;

public interface IRoom {
    void addChore(IChore newChore);
    List<IChore> getChores();

    IChore getChore(String choreName);

    String getName();
    /*Each chore adds to total number of days (frequency of chore) and number
    on the slider is total number of days - days since completed > 0*/
    int getRoomState();
    int getPerfectRoom();
    String toString();
}
