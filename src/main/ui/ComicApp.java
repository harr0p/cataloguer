package ui;

import model.Comic;
import model.ComicBin;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;
import javax.swing.JFrame;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

//Comic application
public class ComicApp extends JFrame {

    //private Comic
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/comiclist.json";
    private ComicBin library;
    //ComicBin library = new ComicBin();

    public ComicApp() throws FileNotFoundException {
        library = new ComicBin("Duncan's Collection");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runComic();

    }

    //MODIFIES: this
    //EFFECTS: processes user input
    private void runComic() {
        boolean keepGoing = true;
        String command;
        input = new Scanner(System.in);

        while (keepGoing) {
            entryMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nComeback soon!");
    }


    //EFFECTS: displays menu of options to user
    private void entryMenu() {
        System.out.println("\nWhat would you like to do today?");
        System.out.println("\ti -> index a comic");
        System.out.println("\tr -> remove a comic");
        System.out.println("\tv -> view a comic");
        System.out.println("\tb -> view the entire library");
        System.out.println("\ts -> save the library");
        System.out.println("\tl -> load a library");
        System.out.println("\tq -> quit");
    }

    //MODIFIERS: This
    //EFFECTS: Processes user command
    private void processCommand(String command) {
        if (command.equals("i")) {
            indexMenu();
        } else if (command.equals("r")) {
            removeMenu();
        } else if (command.equals("v")) {
            finderMenu();
        } else if (command.equals("b")) {
            libraryMenu();
        } else if (command.equals("s")) {
            saveComicBin();
        } else if (command.equals("l")) {
            loadComicBin();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    //REQUIRES
    //MODIFIERS: This
    //EFFECTS: Creates the comic
    public void indexMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWhat's the title of comic?");
        String name = scanner.nextLine();
        System.out.println("\nWhat's the issue number of your comic?");
        int issue = Integer.parseInt(scanner.nextLine());
        System.out.println("\nWho made your comic?");
        String brand = scanner.nextLine();
        //Comic comic = new Comic(name, issue, brand);

        library.addComic(new Comic(name, issue, brand));
    }

    //REQUIRES: >=1 Comic in the list
    //EFFECTS: Returns the comic if its found, null if it's not
    private void finderMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWhat comic do you want to find?");
        String name = scanner.nextLine();
        System.out.println("\nWhat's the issue number of your comic?");
        int issue = Integer.parseInt(scanner.nextLine());
        System.out.println("\nWho made your comic?");
        String brand = scanner.nextLine();


        Comic comic = library.findComic(name, issue, brand);
        System.out.println("The comic was found! Here is: " + comic.getName()
                + " " + comic.getIssue() + " " + comic.getBrand());
        //System.out.println(comic);

    }

    //REQUIRES: >=1 Comic in the list
    //EFFECTS: Creates a numbered list of the comics in the list
    private void libraryMenu() {
        int i = 1;
        for (Comic c : library.getComicBin()) {
            System.out.println(i + " " + c.getName() + " " + c.getIssue() + " " + c.getBrand());
            i++;
        }
    }

    //REQUIRES: >=1 Comic in the list
    //MODIFIERS: Removes the comic in the list
    //EFFECTS: Removes comic from the list
    private void removeMenu() {
        Scanner scanner = new Scanner(System.in);

        int i = 1;
        System.out.println("Choose the index you want to remove");
        for (Comic c : library.getComicBin()) {
            System.out.println(i + " " + c.getName() + " " + c.getIssue() + " " + c.getBrand());
            i++;
        }
        int index = Integer.parseInt(scanner.nextLine());
        library.removeComic(index);
    }

    // Methods taken from WorkRoomApp class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: saves the ComicBin to file
    private void saveComicBin() {
        try {
            jsonWriter.open();
            jsonWriter.write(library);
            jsonWriter.close();
            System.out.println("Saved " + library.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Method taken from WorkRoomApp class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // MODIFIES: this
    // EFFECTS: loads ComicBin from file
    private void loadComicBin() {
        try {
            library = jsonReader.read();
            System.out.println("Loaded " + library.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    //--------------------

//    private class ComicMouseListener extends MouseAdapter {
//
//        // EFFECTS: Forward mouse pressed event to the active tool
//        public void mousePressed(MouseEvent e) {
//            handleMousePressed(translateEvent(e));
//        }
//
//        // EFFECTS: Forward mouse released event to the active tool
//        public void mouseReleased(MouseEvent e) {
//            handleMouseReleased(translateEvent(e));
//        }
//
//        // EFFECTS:Forward mouse clicked event to the active tool
//        public void mouseClicked(MouseEvent e) {
//            handleMouseClicked(translateEvent(e));
//        }
//
//        // EFFECTS:Forward mouse dragged event to the active tool
//        public void mouseDragged(MouseEvent e) {
//            handleMouseDragged(translateEvent(e));
//        }
//    }

//    // MODIFIES: this
//    // EFFECTS:  initializes a DrawingMouseListener to be used in the JFrame
//    private void initializeInteraction() {
//        ComicMouseListener cml = new ComicMouseListener();
//        addMouseListener(cml);
//        addMouseMotionListener(cml);
//    }

}

