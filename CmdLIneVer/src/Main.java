import java.io.IOException;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.time.Period;
import java.util.*;


public class Main {
    static Scanner myObj = new Scanner(System.in);
    public static void main (String[] args) throws ParseException, IOException {
        Controller c = new Controller();
        c.importData("C:\\Users\\Sarah\\Desktop\\Work\\choreApp\\CmdLIneVer\\appData.json");

        menu(c);

        c.exportData("C:\\Users\\Sarah\\Desktop\\Work\\choreApp\\CmdLIneVer\\appData.json");
    }

    //Menu functions
    static void menu(Controller c) throws ParseException {
        boolean inLoop = true;
        //H - Room list, R - Select Room, C - Select Chore
        char menuType = 'H';
        IRoom room = null;
        IChore chore = null;

        while (inLoop) {
            switch (menuType) {
                case 'H' -> {
                    printRooms(c);
                    System.out.println("A - Add a new room");
                    System.out.println("D - Delete select room");
                    System.out.println("E - Edit select room");
                    System.out.println("M - Move into select room");
                    System.out.println("L - Print all chores in alphabetical order");
                    System.out.println("T - Print all chores in descending order of time since completed");
                    System.out.println("F - Print all chores in ascending order of frequency");
                    System.out.println("R - Print all chores in ascending order of effort");
                    System.out.println("C - Print all chores in ascending order of time to complete");
                    System.out.println("P - Print all chores in descending order of how recently completed");
                    System.out.println("G - Generate recommended list of chores");
                    System.out.println("X - Exit");
                    String userIn = myObj.nextLine();

                    switch (userIn) {
                        case "A" -> addRoom(c);
                        case "D" -> deleteRoom(c);
                        case "E" -> editRoom(c);
                        case "M" -> {
                            room = moveRoom(c);
                            menuType = 'R';
                        }
                        case "L" -> printAlphabetical(c);
                        case "T" -> printByTimeComplete(c);
                        case "F" -> printByFrequency(c);
                        case "R" -> printByEffort(c);
                        case "C" -> printByTime(c);
                        case "P" -> printByPercentage(c);
                        case "G" -> generateRecommended(c);
                        case "X" -> inLoop = false;
                        default -> System.out.println("Not a valid input, please choose again.");
                    }
                }
                case 'R' -> {
                    printChores(room);
                    System.out.println("A - Add a new chore");
                    System.out.println("D - Delete select chore");
                    System.out.println("S - Select a chore");
                    System.out.println("H - Return to room list");
                    System.out.println("X - Exit");
                    String userIn = myObj.nextLine();

                    switch (userIn) {
                        case "A" -> addChore(c, room);
                        case "D" -> deleteChore(room);
                        case "S" -> {
                            chore = chooseChore(room);
                            menuType = 'C';
                        }
                        case "H" -> {
                            room = null;
                            menuType = 'H';
                        }
                        case "X" -> inLoop = false;
                        default -> System.out.println("Not a valid input, please choose again.");
                    }
                }
                case 'C' -> {
                    printChore(chore);
                    System.out.println("A - Add a deep clean version of chore");
                    System.out.println("D - Delete deep clean version of chore");
                    System.out.println("E - Edit chore");
                    System.out.println("C - Complete chore now");
                    System.out.println("R - Return to chore list");
                    System.out.println("H - Return to room list");
                    System.out.println("X - Exit");
                    String userIn = myObj.nextLine();

                    switch (userIn) {
                        case "A" -> addDeepClean(c, chore, room);
                        case "D" -> chore.deleteDeepClean();
                        case "E" -> editChore(c, chore);
                        case "C" -> chore.completeNow();
                        case "R" -> {
                            chore = null;
                            menuType = 'R';
                        }
                        case "H" -> {
                            room = null;
                            menuType = 'H';
                        }
                        case "X" -> inLoop = false;
                        default -> System.out.println("Not a valid input, please choose again.");
                    }
                }
            }
        }
    }

    static IRoom moveRoom(Controller c) {
        System.out.println("Which room do you want?");
        String roomName = myObj.nextLine();
        IRoom room = c.getRoom(roomName);
        while (room == null) {
            System.out.println("That room doesn't exist. Please enter the name of existing room.");
            roomName = myObj.nextLine();
            room = c.getRoom(roomName);
        }
        return room;
    }

    static IChore chooseChore(IRoom room) {
        System.out.println("Which chore do you want?");
        String choreName = myObj.nextLine();
        IChore chore = room.getChore(choreName);
        while (chore == null) {
            System.out.println("That chore doesn't exist. Please enter the name of existing chore.");
            choreName = myObj.nextLine();
            chore = room.getChore(choreName);
        }
        return chore;
    }

    //Printing functions
    static void printRooms(Controller c) {
        StringBuilder str = new StringBuilder();
        List<IRoom> rooms = c.getRooms();
        if (rooms.size() > 0) {
            for (IRoom room: rooms) {
                str.append(room.getName()).append(": ").append(room.getRoomState()).append("%");
                str.append(", ");
            }
            System.out.println(str);
        }
    }

    static void printChores(IRoom room) {
        StringBuilder str = new StringBuilder();
        List<IChore> chores = room.getChores();
        if (chores.size() > 0) {
            for (IChore chore: chores) {
                if (chore.isOneTime()) {
                    str.append(chore.getName()).append(", ");
                }
                else {
                    str.append(chore.getName()).append(": ").append(chore.getPercentage()).append("%, ");
                }
            }
            System.out.println(str);
        }
    }

    static void printChore(IChore chore) {
        System.out.println(chore.toString());
    }

    static List<IChore> addAllChores(Controller c) {
        List<IChore> orderedChores = new ArrayList<>();
        List<IRoom> rooms = c.getRooms();
        for (IRoom room: rooms) {
            List<IChore> chores = room.getChores();
            orderedChores.addAll(chores);
        }

        return orderedChores;
    }

    static void printAlphabetical(Controller c) {
        List<IChore> orderedChores = addAllChores(c);
        orderedChores.sort(new AlphabeticalComparator());

        for (IChore chore: orderedChores) {
            System.out.println(chore.getRoom().getName() + " - " + chore.getName() + ": " + chore.getPercentage() + "%");
        }
    }

    static void printByTimeComplete(Controller c) {
        List<IChore> orderedChores = addAllChores(c);
        orderedChores.sort(Collections.reverseOrder(new TimeCompleteComparator()));

        for (IChore chore: orderedChores) {
            System.out.println(chore.getRoom().getName() + " - " + chore.getName() + ": " + chore.timeSinceComplete());
        }
    }

    static void printByFrequency(Controller c) {
        List<IChore> orderedChores = addAllChores(c);
        orderedChores.sort(new FrequencyComparator());

        for (IChore chore: orderedChores) {
            System.out.println(chore.getRoom().getName() + " - " + chore.getName() + ": " + chore.getFrequency());
        }
    }

    static void printByEffort(Controller c) {
        List<IChore> orderedChores = addAllChores(c);
        orderedChores.sort(new EffortComparator());

        for (IChore chore: orderedChores) {
            System.out.println(chore.getRoom().getName() + " - " + chore.getName() + ": " + chore.getEffort());
        }
    }

    static void printByTime(Controller c) {
        List<IChore> orderedChores = addAllChores(c);
        orderedChores.sort(new TimeComparator());

        for (IChore chore: orderedChores) {
            System.out.println(chore.getRoom().getName() + " - " + chore.getName() + ": " + chore.getTime());
        }
    }

    static void printByPercentage(Controller c) {
        List<IChore> orderedChores = addAllChores(c);
        orderedChores.sort(new PercentageComparator());

        for (IChore chore: orderedChores) {
            System.out.println(chore.getRoom().getName() + " - " + chore.getName() + ": " + chore.getPercentage() + "%");
        }
    }

    static void generateRecommended(Controller c) {
        String totTimeUnits = "", factor = "", roomName = "";
        int totTime = 0, numChores = 0, effort = 0;
        System.out.println("Would you like to enter a total time for chores?");
        String userIn = myObj.nextLine();
        if (userIn.equalsIgnoreCase("y")) {
            System.out.println("Enter the time you have for chores:");
            totTime = myObj.nextInt();
            totTimeUnits = myObj.nextLine();

            if (totTimeUnits.equalsIgnoreCase("hours") || totTimeUnits.equalsIgnoreCase("hour")); {
                totTime = totTime * 60;
            }
        }
        else {
            System.out.println("Would you like to enter a maximum number of chores");
            userIn = myObj.nextLine();
            if (userIn.equalsIgnoreCase("y")) {
                System.out.println("Enter the maximum number of chores you'd like to do:");
                numChores = myObj.nextInt();
                myObj.nextLine();
            }
        }
        System.out.println("Would you like to enter a maximum effort value?");
        userIn = myObj.nextLine();
        if (userIn.equalsIgnoreCase("y")) {
            System.out.println("Enter the maximum effort value you're willing to do (1-10)");
            effort = myObj.nextInt();
            myObj.nextLine();
        }
        System.out.println("Is there a factor you'd like to prioritize in choosing chores?");
        userIn = myObj.nextLine();
        if (userIn.equalsIgnoreCase("y")) {
            System.out.println("Enter a factor you'd like to prioritize (Time, Effort, Room, Time since last completed");
            factor = myObj.nextLine();

            if (factor.equalsIgnoreCase("room")) {
                System.out.println("Which room do you want to prioritize?");
                roomName = myObj.nextLine();
            }
        }
        List<IChore> recChores = c.getChoreList(totTime, factor, roomName, numChores, effort);

        for (IChore chore: recChores) {
            System.out.println(chore.getRoom().getName() + " - " + chore.getName());
            System.out.println(chore.getPercentage() + "% " + chore.getTime() + " " + chore.getEffort());
        }
    }

    //Room functions
    static void addRoom(Controller c) {
        System.out.println("Enter name of new room:");
        String newName = myObj.nextLine();

        IRoom newRoom = new Room (newName);
        c.addRoom(newRoom);
    }

    static void editRoom(Controller c) {
        IRoom room = moveRoom(c);
        System.out.println("Enter new room name");
        String roomName = myObj.nextLine();
        room.editName(roomName);
    }

    static void deleteRoom(Controller c) {
        IRoom room = moveRoom(c);
        c.deleteRoom(room);
    }


    //Chore functions
    static void addChore(Controller c, IRoom room) throws ParseException {
        IChore newChore = createChore(c, room);
        room.addChore(newChore);
    }

    static void editChore(Controller c, IChore chore) throws ParseException {
        System.out.println("What aspect of the chore would you like to edit? (Name, Effort, Time, Last Complete Date, Frequency, Room");
        String userIn = myObj.nextLine();

        switch (userIn) {
            case "name":
            case "Name":
                System.out.println("Enter the new name");
                String name = myObj.nextLine();
                chore.editName(name);
                break;
            case "effort":
            case "Effort":
                System.out.println("Enter the new effort value");
                int effort = Integer.parseInt(myObj.nextLine());
                chore.editEffort(effort);
                break;
            case "time":
            case "Time":
                System.out.println("Enter the new time to complete (# minutes/hours");
                Duration time;
                int numTime = myObj.nextInt();
                String strTime = myObj.nextLine();
                switch (strTime) {
                    case " day", " days" -> time = Duration.ofDays(numTime);
                    case " hour", " hours" -> time = Duration.ofHours(numTime);
                    case " minute", " minutes" -> time = Duration.ofMinutes(numTime);
                    case " second", " seconds" -> time = Duration.ofSeconds(numTime);
                    default -> {
                        System.out.println("Not a valid time unit");
                        return;
                    }
                }
                chore.editTime(time);
                break;
            case "last complete date":
            case "Last Complete Date":
                System.out.println("Enter the new last complete date");
                String date = myObj.nextLine();
                chore.editLastComplete(c.dateToInstant(date));
                break;
            case "frequency":
            case "Frequency":
                if (!chore.isOneTime()) {
                    System.out.println("Would you like to make this chore a one-time frequency?");
                    userIn = myObj.nextLine();
                    if (Objects.equals(userIn, "Y") || Objects.equals(userIn, "y")) {
                        chore.editFrequency(null);
                        break;
                    }
                }
                System.out.println("Enter the new frequency (# days/weeks):");
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
                chore.editFrequency(freq);
                break;
            case "room":
            case "Room":
                System.out.println("Which room would you like to move the chore to?");
                userIn = myObj.nextLine();
                IRoom newRoom = c.getRoom(userIn);
                while (newRoom == null) {
                    System.out.println("That room doesn't exist. Please enter the name of existing room.");
                    userIn = myObj.nextLine();
                    newRoom = c.getRoom(userIn);
                }
                chore.editRoom(newRoom);
                break;
            default:
                System.out.println("Not a valid option");
        }
    }

    static void deleteChore(IRoom room) {
        IChore chore = chooseChore(room);
        room.removeChore(chore);
    }

    static void addDeepClean(Controller c, IChore chore, IRoom room) throws ParseException {
        System.out.println("Creating chore, name will be overridden");
        IChore newChore = createChore(c, room);
        String newChoreName = "Deep Clean " + chore.getName();
        newChore.editName(newChoreName);
        chore.addDeepClean(newChore);
    }

    //Helpers
    static IChore createChore(Controller c, IRoom room) throws ParseException {
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
            time = createDuration();
        }

        System.out.println("Enter the date chore was last completed (dd-mm-yyyy):");
        String date = myObj.nextLine();
        Instant lastComp = c.dateToInstant(date);

        Period freq = null;
        System.out.println("Is this a one-time chore?");
        userIn = myObj.nextLine();
        if (Objects.equals(userIn, "n") || Objects.equals(userIn, "N")) {
            freq = createPeriod();
        }

        return new Chore(choreName, effort, time, lastComp, freq, room);
    }

    static Duration createDuration() {
        System.out.println("Enter time to complete the chore (# minutes/hours):");
        int numTime = myObj.nextInt();
        String strTime = myObj.nextLine();

        while (true) {
            switch (strTime) {
                case " day", " days" -> {
                    return Duration.ofDays(numTime);
                }
                case " hour", " hours" -> {
                    return Duration.ofHours(numTime);
                }
                case " minute", " minutes" -> {
                    return Duration.ofMinutes(numTime);
                }
                case " second", " seconds" -> {
                    return Duration.ofSeconds(numTime);
                }
                default -> System.out.println("Not a valid time unit");
            }
        }
    }

    static Period createPeriod() {
        System.out.println("Enter how often the chore should be completed (# days/weeks):");
        int numFreq = myObj.nextInt();
        String strFreq = myObj.nextLine();

        while (true) {
            switch (strFreq) {
                case " year", " years" -> {
                    return Period.ofYears(numFreq);
                }
                case " month", " months" -> {
                    return Period.ofMonths(numFreq);
                }
                case " week", " weeks" -> {
                    return Period.ofWeeks(numFreq);
                }
                case " day", " days" -> {
                    return Period.ofDays(numFreq);
                }
                default -> System.out.println("Not a valid unit of time");
            }
        }
    }
}
