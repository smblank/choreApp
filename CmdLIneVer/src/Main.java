import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.*;

import static java.time.LocalDateTime.parse;

public class Main {
    static Scanner myObj = new Scanner(System.in);
    public static void main (String[] args) throws ParseException, IOException {
        Controller c = new Controller();
        c.importData("C:\\Users\\Sarah\\Desktop\\Work\\choreApp\\CmdLIneVer\\appData.json");

        printRooms(c);
        menu(c);
        /*Functionality to implement:
        Edit a room
        Edit a chore
        Add deep clean chore
        Remove deep clean chore
        */

        c.exportData("C:\\Users\\Sarah\\Desktop\\Work\\choreApp\\CmdLIneVer\\appData.json");
    }

    static void menu(Controller c) throws ParseException {
        boolean inLoop = true;

        while (inLoop) {
            System.out.println("R - Print Rooms");
            System.out.println("C - Print chores of select room");
            System.out.println("D - Print details of select chore");
            System.out.println("AR - Add room");
            System.out.println("DR - Delete select room");
            System.out.println("AC - Add chore to select room");
            System.out.println("DC - Delete chore from select room");
            System.out.println("X - Exit");
            String userIn = myObj.nextLine();

            switch (userIn) {
                case "R" -> printRooms(c);
                case "C" -> printChores(c);
                case "D" -> printChore(c);
                case "AR" -> addRoom(c);
                case "DR" -> deleteRoom(c);
                case "ER" -> menu(c);
                case "AC" -> addChore(c);
                case "DC" -> deleteChore(c);
                case "EC" -> menu(c);
                case "ADC" -> menu(c);
                case "RDC" -> menu(c);
                case "X" -> inLoop = false;
                default -> System.out.println("Not a valid input, please choose again.");
            }
        }

    }

    static void printRooms(Controller c) {
        StringBuilder str = new StringBuilder();
        List<IRoom> rooms = c.getRooms();
        if (rooms.size() > 0) {
            for (IRoom room: rooms) {
                str.append(room.getName());
                str.append(", ");
            }
            System.out.println(str.toString());
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

    static void addRoom(Controller c) {
        System.out.println("Enter name of new room:");
        String newName = myObj.nextLine();

        IRoom newRoom = new Room (newName);
        c.addRoom(newRoom);
    }

    static void deleteRoom(Controller c) {
        System.out.println("Which room should be deleted");
        String roomName = myObj.nextLine();
        IRoom room = c.getRoom(roomName);
        if (room == null) {
            System.out.println("Not a valid room name");
        }
        else {
            c.deleteRoom(room);
        }
    }

    static void addChore(Controller c) throws ParseException {
        System.out.println("Which room will the chore be added to?");
        String roomName = myObj.nextLine();
        IRoom room = c.getRoom(roomName);
        if (room == null) {
            System.out.println("Not a valid room name");
        }
        else {
            System.out.println("Enter name of new chore:");
            String choreName = myObj.nextLine();

            System.out.println("Would you like to enter an effort value?");
            String userIn = myObj.nextLine();
            int effort = -1;
            if (Objects.equals(userIn, "Y")) {
                System.out.println("Enter effort value of chore (1 - 10):");
                effort = Integer.parseInt(myObj.nextLine());
                while (effort > 10 || effort < 1) {
                    System.out.println("Please enter a value between 1 & 10:");
                    effort = Integer.parseInt(myObj.nextLine());
                }
            }

            System.out.println("Would you like to enter a time to complete chore?");
            userIn = myObj.nextLine();
            Duration time = null;
            if (Objects.equals(userIn, "Y")) {
                System.out.println("Enter time to complete the chore (# minutes/hours):");
                int numTime = myObj.nextInt();
                String strTime = myObj.nextLine();
                switch (strTime) {
                    case " day":
                    case " days":
                        time = Duration.ofDays(numTime);
                        break;
                    case " hour":
                    case " hours":
                        time = Duration.ofHours(numTime);
                        break;
                    case " minute":
                    case " minutes":
                        time = Duration.ofMinutes(numTime);
                        break;
                    case " second":
                    case " seconds":
                        time = Duration.ofSeconds(numTime);
                    default:
                        System.out.println("Not a valid time unit");
                        return;
                }
            }

            System.out.println("Enter the date chore was last completed (dd-mm-yyyy):");
            String date = myObj.nextLine();
            Instant lastComp = c.dateToInstant(date);

            System.out.println("Enter how often the chore should be completed (# days/weeks):");
            int numFreq = myObj.nextInt();
            String strFreq = myObj.nextLine();
            Period freq;
            switch (strFreq) {
                case " year", " years" -> freq = Period.ofYears(numFreq);
                case " month", " months" -> freq = Period.ofMonths(numFreq);
                case " week", " weeks" -> freq = Period.ofWeeks(numFreq);
                case " day", " days" -> freq = Period.ofDays(numFreq);
                default -> {
                    System.out.println("Not a valid unit of time");
                    return;
                }
            }

            IChore newChore = new Chore(choreName, effort, time, lastComp, freq);
            room.addChore(newChore);
        }
    }

    static void deleteChore(Controller c) {
        System.out.println("Which room should have a chore deleted");
        String roomName = myObj.nextLine();
        IRoom room = c.getRoom(roomName);
        if (room == null) {
            System.out.println("Not a valid room name");
        }
        else {
            System.out.println("Which chore should be deleted");
            String choreName = myObj.nextLine();
            IChore chore = room.getChore(choreName);
            if (chore == null) {
                System.out.println("Not a valid chore name");
            }
            else {
                room.removeChore(chore);
            }
        }
    }
}
