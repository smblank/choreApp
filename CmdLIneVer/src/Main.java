import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.*;

import static java.time.LocalDateTime.parse;

public class Main {
    static Scanner myObj = new Scanner(System.in);
    public static void main (String[] args) throws ParseException, IOException {
        Controller c = new Controller();
        c.importData("appData.txt");

        printRooms(c);
        menu(c);
        /*Functionality to implement:
        Add a room
        Edit a room
        Delete a room
        Add a chore
        Edit a chore
        Delete a chore
        Add chore to room
        Remove chore from room
        Add deep clean chore
        Remove deep clean chore
        */

        c.exportData("appData.txt");
    }

    static void menu(Controller c) {
        Boolean inLoop = true;

        while (inLoop) {
            System.out.println("R - Print Rooms, C - Print chores of select room, D - Print details of select chore, X - Exit");
            String userIn = myObj.nextLine();

            switch (userIn) {
                case "R" -> printRooms(c);
                case "C" -> printChores(c);
                case "D" -> printChore(c);
                case "X" -> inLoop = false;
                default -> System.out.println("Not a valid input, please choose again.");
            }
        }

    }

    static void printRooms(Controller c) {
        List<IRoom> rooms = c.getRooms();
        for (IRoom room: rooms) {
            System.out.println(room.getName());
        }
    }

    static void printChores(Controller c) {
        System.out.println("Which Room?");
        String roomName = myObj.nextLine();
        IRoom room = c.getRoom(roomName);

        if (room == null) {
            System.out.println("Not a valid room");
        }
        else {
            System.out.println(room);
        }
    }

    static void printChore(Controller c) {
        System.out.println("Which Room?");
        String roomName = myObj.nextLine();
        IRoom room = c.getRoom(roomName);

        if (room == null) {
            System.out.println("Not a valid room");
        }

        else {
            System.out.println("Which Chore?");
            String choreName = myObj.nextLine();
            IChore chore = room.getChore(choreName);

            if (chore == null) {
                System.out.println("Not a valid chore");
            }
            else {
                System.out.println(chore.toString());
            }
        }
    }
}
