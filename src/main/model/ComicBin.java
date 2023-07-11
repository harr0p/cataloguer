package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

// Represents a comicBin having comics inside
public class ComicBin {

    private String name;
    private ArrayList<Comic> comicBin;

    //EFFECTS: Creates an empty ArrayList
    public ComicBin(String name) {
        this.name = name;
        comicBin = new ArrayList<>();
    }

    //REQUIRES: A comic to add
    //MODIFIERS: Modifies this
    //EFFECTS: Adds comic to the ArrayList
    public void addComic(Comic comic) {
        comicBin.add(comic);
        EventLog.getInstance().logEvent(new Event("Comic indexed."));
    }

    //REQUIRES: Requires >= 1 Comic in the list
    //EFFECTS: Returns the comic if found otherwise returns null
    public Comic findComic(String name, int issue, String brand) {
        for (Comic c : comicBin) {
            if (c.getName().equals(name) && c.getIssue() == issue && c.getBrand().equals(brand)) {
                EventLog.getInstance().logEvent(new Event("Comic found."));
                return c;
            }
        }
        EventLog.getInstance().logEvent(new Event("Comic was not found."));
        return null;
    }

    //EFFECTS: Returns the list
    public ArrayList<Comic> getComicBin() {
        return comicBin;
    }

    //REQUIRES: >= 1 comic in the list
    //MODIFIERS: Removes a comic from the list
    //EFFECTS: Changes the size of the arrayList
    public void removeComic(int index) {
        EventLog.getInstance().logEvent(new Event("Comic removed."));
        comicBin.remove(--index);
    }

    //EFFECTS: Returns the size of the list
    public int length() {
        return comicBin.size();
    }

    public String getName() {
        return name;
    }

    public void removeLog() {
        EventLog.getInstance().logEvent(new Event("Comic removed."));
    }

    // Methods taken from Workroom class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

   /* @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        JSONArray jsonArray = new JSONArray();
        for (Comic c : comicBin) {
            jsonArray.put(c.toJson());
        }
        json.put("comicBin", jsonArray);
        return json;
    }*/
}
