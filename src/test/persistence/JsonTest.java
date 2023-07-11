package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import model.Comic;
import model.ComicBin;

    // Methods taken from JSONTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonTest {
    protected void checkComic(String name, int issue, String brand, Comic comic) {
        assertEquals(name, comic.getName());
        assertEquals(issue, comic.getIssue());
        assertEquals(brand, comic.getBrand());
    }
}
