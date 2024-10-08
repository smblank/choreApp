import java.util.List;

public interface IRoom {
    void addChore(IChore newChore);

    void removeChore(IChore chore);

    List<IChore> getChores();

    IChore getChore(String choreName);

    String getName();

    void editName(String newName);

    /*Each chore adds to total number of days (frequency of chore) and number
        on the slider is total number of days - days since completed > 0*/
    int getRoomState();
    int getPerfectRoom();
    String toString();
}
