package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a comic having a name, issue, and brand of comic
public class Comic implements Writable {

    private String name;                   // name of the comic
    private int issue;                     // issue number
    private String brand;                  // brand of comic


    /*
     * REQUIRES: Comic has a non-zero length
     * EFFECTS: name on account is set to comicName; Issue is a
     *          positive integer. Brand name is set to comicBrand
     */
    public Comic(String comicName, int comicIssue, String comicBrand) {
        this.issue = comicIssue;
        this.name = comicName;
        this.brand = comicBrand;

    }

    //EFFECTS: returns name
    public String getName() {
        return name;
    }

    //EFFECTS: returns issue
    public int getIssue() {
        return issue;
    }

    //EFFECTS: returns brand
    public String getBrand() {
        return brand;
    }

    // Method taken from Thingy class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("issue", issue);
        json.put("brand", brand);
        return json;
    }

}

