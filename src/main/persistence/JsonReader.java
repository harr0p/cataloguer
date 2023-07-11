package persistence;

import model.Comic;
import model.ComicBin;
import model.Event;
import model.EventLog;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads comicBin from JSON data stored in file
public class JsonReader {
    private String source;

    // Methods taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ComicBin from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ComicBin read() throws IOException {
        EventLog.getInstance().logEvent(new Event("Loaded Json."));
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseComicBin(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ComicBin from JSON object and returns it
    private ComicBin parseComicBin(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ComicBin cb = new ComicBin(name);
        addComicBins(cb, jsonObject);
        return cb;
    }

    // MODIFIES: cb
    // EFFECTS: parses ComicBin from JSON object and adds them to ComicBin
    private void addComicBins(ComicBin cb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("comicBin");
        for (Object json : jsonArray) {
            JSONObject nextComic = (JSONObject) json;
            addComic(cb, nextComic);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses Comic from JSON object and adds it to ComicBin
    private void addComic(ComicBin cb, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int issue = jsonObject.getInt("issue");
        String brand = jsonObject.getString("brand");
        Comic comic = new Comic(name, issue, brand);
        cb.addComic(comic);
    }


}
