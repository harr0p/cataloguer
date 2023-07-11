package persistence;

import model.Comic;
import model.ComicBin;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

    // Methods taken from JSONWriterTest class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            ComicBin cb = new ComicBin("My collection");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyComicBin() {
        try {
            ComicBin cb = new ComicBin("My comic collection");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyComicBin.json");
            writer.open();
            writer.write(cb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyComicBin.json");
            cb = reader.read();
            assertEquals("My comic collection", cb.getName());
            assertEquals(0, cb.length());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralComicBin() {
        try {
            ComicBin cb = new ComicBin("My collection");
            cb.addComic(new Comic("Batman",1, "DC Comics"));
            cb.addComic(new Comic("Iron Man", 1, "Marvel"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralComicBin.json");
            writer.open();
            writer.write(cb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralComicBin.json");
            cb = reader.read();
            assertEquals("My collection", cb.getName());
            List<Comic> library = cb.getComicBin();
            assertEquals(2, library.size());
            checkComic("Batman",1, "DC Comics", library.get(0));
            checkComic("Iron Man", 1, "Marvel", library.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
