package ui;

import model.Comic;
import model.ComicBin;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLOutput;

// Represents a comicApp's graphic user interface
public class ComicAppGui extends JFrame {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 500;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private ComicBin library;
    private DefaultListModel stringLibrary = new DefaultListModel();
    private static final String JSON_STORE = "./data/comiclist.json";

    //EFFECTS: Formats the GUI
    public ComicAppGui() {
        super("Comic App");
        library = new ComicBin("Duncan's Collection");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initializeLoading();
        initializeGraphics();
        initializeButtons();
        initializeList();
        setVisible(true);
    }

    //REQUIRES: A file to preview
    //EFFECTS: Creates a loading screen before launching app
    private void initializeLoading() {
        JWindow window = new JWindow();
        window.getContentPane().add(
                new JLabel("", new ImageIcon("./data/loading.gif"), SwingConstants.CENTER));
        window.setBounds(500, 200, 500, 246);
        window.setVisible(true);
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        window.setVisible(false);
    }

    //REQUIRES: WIDTH & HEIGHT size
    //EFFECTS: Creates the general size of the app UI
    private void initializeGraphics() {
        setLayout(new BorderLayout());
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    //EFFECTS: Creates the buttons and thus the tools that will be used in my ComicAppGUI
    private void initializeButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2));
        buttonPanel.setPreferredSize(new Dimension(200, 150));
        buttonPanel.add(new JButton(new AddComicAction()));
        buttonPanel.add(new JButton(new RemoveComicAction()));
        buttonPanel.add(new JButton(new ViewComicAction()));
        buttonPanel.add(new JButton(new SaveJsonAction()));
        buttonPanel.add(new JButton(new LoadJsonAction()));
        buttonPanel.add(new JButton(new QuitComicAppAction()));
        add(buttonPanel, BorderLayout.CENTER);

    }

    //REQUIRES: A ComicBin with list of comics
    //MODIFIES: Modifies the comics into a string in order to be uploaded into the default list model
    //EFFECTS: Creates a defaultlistmodel with all of the comics turned into strings
    //         and creates a jlist with the comic strings inside
    public void initializeList() {
        for (Comic c : library.getComicBin()) {
            stringLibrary.addElement(comicToString(c));
        }

        JList jlistLibrary = new JList(stringLibrary);
        jlistLibrary.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        jlistLibrary.setLayoutOrientation(JList.VERTICAL);
        jlistLibrary.setPreferredSize(new Dimension(200,150));
        jlistLibrary.setVisibleRowCount(-1);
        JScrollPane listScroller = new JScrollPane(jlistLibrary);
        listScroller.setPreferredSize(new Dimension(200, 150));
        add(listScroller, BorderLayout.LINE_END);
    }

    //REQUIRES: A comic name, issue, and brand, no duplicates as well
    //EFFECTS: Creates a comic based off the users inputs and adds it to the JList to be previewed
    public class AddComicAction extends AbstractAction {

        AddComicAction() {
            super("Index Comic");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String comicInputName = JOptionPane.showInputDialog(null,
                    "Comic Name?", "Enter Comic Title", JOptionPane.QUESTION_MESSAGE);
            String comicInputIssue = JOptionPane.showInputDialog(null,
                    "Comic Issue", "Enter Comic Issue", JOptionPane.QUESTION_MESSAGE);
            String comicInputBrand = JOptionPane.showInputDialog(null,
                    "Comic Brand", "Enter Comic Brand", JOptionPane.QUESTION_MESSAGE);

            if (comicInputName != null && comicInputIssue != null && comicInputBrand != null) {
                Comic load = new Comic(comicInputName, Integer.parseInt(comicInputIssue), comicInputBrand);
                library.addComic(load);
                stringLibrary.addElement(comicToString(load));
            }
        }
    }

    //REQUIRES: a comic
    //EFFECTS: returns a string representation of the string
    public String comicToString(Comic comic) {
        return (comic.getName() + " " + comic.getIssue() + " " + comic.getBrand());
    }

    //REQUIRES: The comic has to exist
    //MODIFIES: JList, library, default list model
    //EFFECTS: Removes the comic from the library, removes the comic string form from the default list model
    //         which removes the string comic from the JList
    private class RemoveComicAction extends AbstractAction {

        RemoveComicAction() {
            super("Remove Comic");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String comicInputName = JOptionPane.showInputDialog(null,
                    "Comic Name?", "Enter Comic Title", JOptionPane.QUESTION_MESSAGE);
            String comicInputIssue = JOptionPane.showInputDialog(null,
                    "Comic Issue", "Enter Comic Issue", JOptionPane.QUESTION_MESSAGE);
            String comicInputBrand = JOptionPane.showInputDialog(null,
                    "Comic Brand", "Enter Comic Brand", JOptionPane.QUESTION_MESSAGE);

            Comic load = new Comic(comicInputName, Integer.parseInt(comicInputIssue), comicInputBrand);

            if (stringLibrary.contains(comicToString(load))) {
                stringLibrary.removeElement(comicToString(load));
                for (Comic c : library.getComicBin()) {
                    if (c.getBrand().equals(comicInputBrand) && c.getName().equals(comicInputName)
                            && c.getIssue() == Integer.parseInt(comicInputIssue)) {
                        library.getComicBin().remove(c);
                        library.removeLog();
                        break;
                    }
                }
            }
        }
    }

    //REQUIRES: a list of comics
    //MODIFIES: JSon file
    //EFFECTS: Saves the list of comics to a json file
    private class SaveJsonAction extends AbstractAction {

        public SaveJsonAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(library);
                jsonWriter.close();
                System.out.println("Saved " + library.getName() + " to " + JSON_STORE);
            } catch (FileNotFoundException fne) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    //REQUIRES: Json file of comics
    //EFFECTS: Loads up the saved Json file of list of comics to preview in the GUI
    private class LoadJsonAction extends AbstractAction {

        public LoadJsonAction() {
            super("Load");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                library = jsonReader.read();
                System.out.println("Loaded " + library.getName() + " from " + JSON_STORE);
            } catch (IOException io) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
            stringLibrary.clear();
            for (int i = 0; i < library.length(); i++) {
                stringLibrary.addElement(comicToString(library.getComicBin().get(i)));
            }
        }
    }


    //REQUIRES: a comic that exists within the list
    //EFFECTS: provides a pop up confirming whether the comic is in the list
    private class ViewComicAction extends AbstractAction {

        ViewComicAction() {
            super("View Comic");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String comicInputName = JOptionPane.showInputDialog(null,
                    "Comic Name?", "Enter Comic Title", JOptionPane.QUESTION_MESSAGE);
            String comicInputIssue = JOptionPane.showInputDialog(null,
                    "Comic Issue", "Enter Comic Issue", JOptionPane.QUESTION_MESSAGE);
            String comicInputBrand = JOptionPane.showInputDialog(null,
                    "Comic Brand", "Enter Comic Brand", JOptionPane.QUESTION_MESSAGE);

            Comic comic = library.findComic(comicInputName, Integer.parseInt(comicInputIssue), comicInputBrand);
            if (comic != null) {
                JOptionPane.showMessageDialog(new JFrame(), "The comic was found! Here is: " + comic.getName()
                        + " " + comic.getIssue() + " " + comic.getBrand());
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "The comic doesn't exist :(");
            }
        }
    }

    //EFFECTS: Quits the entire app
    private class QuitComicAppAction extends AbstractAction {
        QuitComicAppAction() {
            super("Quit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            printLog(EventLog.getInstance());
            System.exit(0);
        }
    }

    public void printLog(EventLog e) {
        System.out.println();
        for (Event event : e) {
            System.out.println(event.getDescription() + " on " + event.getDate());
        }
    }


}



