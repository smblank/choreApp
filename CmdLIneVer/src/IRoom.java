import java.util.List;

public interface IRoom {
    void addChore(IChore newChore);
    List<IChore> getChores();
    String toString();
}
