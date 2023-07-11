package persistence;

import model.Comic;
import model.ComicBin;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

    // Methods taken from JSONReaderTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ComicBin cb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyComicBin() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyComicBin.json");
        try {
            ComicBin cb = reader.read();
            assertEquals("My comic collection", cb.getName());
            assertEquals(0, cb.length());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralComicBin() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralComicBin.json");
        try {
            ComicBin cb = reader.read();
            assertEquals("My comic collection", cb.getName());
            List<Comic> library = cb.getComicBin();
            assertEquals(2, library.size());
            checkComic("Batman",1, "DC Comics", library.get(0));
            checkComic("Iron Man", 1, "Marvel", library.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
