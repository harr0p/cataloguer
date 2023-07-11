package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonWriter;

import static org.junit.jupiter.api.Assertions.*;

// Class created to test the Comic Class
class ComicTest {
    private Comic testComic;

    @BeforeEach
    void setup() {
        testComic = new Comic("Batman", 52, "DC Comics");
    }

    @Test
    void testConstructor() {
        assertEquals("Batman", testComic.getName());
        assertEquals(52, testComic.getIssue());
        assertEquals("DC Comics",testComic.getBrand());
    }

    @Test
    void testToJson(){
        JSONObject json = new JSONObject();
        json.put("name", testComic.getName());
        json.put("issue", testComic.getIssue());
        json.put("brand", testComic.getBrand());
        assertEquals(json.get("name") , testComic.toJson().get("name"));
        assertEquals(json.get("issue") , testComic.toJson().get("issue"));
        assertEquals(json.get("brand") , testComic.toJson().get("brand"));
    }
}