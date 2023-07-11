package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

    // Methods taken from JSONReader class in
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

public class ComicBinTest {
    ComicBin testComicBin;
    Comic comic1;
    Comic comic2;
    Comic comic3;
    Comic comic4;
    Comic comic5;

    @BeforeEach
    public void setup() {
        testComicBin = new ComicBin("Duncan's library");
        comic1 =  new Comic("Batman", 1,"DC Comics");
        comic2 =  new Comic("Superman", 2,"DC Comics");
        comic3 =  new Comic("Avengers", 15,"Marvel");
        comic4 =  new Comic("Flash", 21,"DC Comics");
        comic5 =  new Comic("Thor", 4,"Marvel");
    }

    @Test
    void testComicBin() {
        assertEquals(0, testComicBin.length());
    }

    @Test
    void testGetName() {
        assertEquals("Duncan's library", testComicBin.getName());
    }

    @Test
    void testAddIncident() {
        testComicBin.addComic(comic1);
        assertEquals(1, testComicBin.length());
        testComicBin.addComic(comic2);
        testComicBin.addComic(comic3);
        testComicBin.addComic(comic4);
        assertEquals(4, testComicBin.length());
        testComicBin.addComic(comic5);
        assertEquals(5, testComicBin.length());
    }

    @Test
    void testFindComic() {
        assertNull(testComicBin.findComic(comic1.getName(), comic1.getIssue(), comic1.getBrand()));
        testComicBin.addComic(comic1);
        assertEquals(comic1, testComicBin.findComic(comic1.getName(), comic1.getIssue(), comic1.getBrand()));
        assertNull(testComicBin.findComic("Doesn't Exist", comic1.getIssue(), comic1.getBrand()));
        assertNull(testComicBin.findComic(comic1.getName(), comic1.getIssue(), "Doesn't Exist"));
        assertNull(testComicBin.findComic(comic1.getName(), 9000, comic1.getBrand()));
        assertNull(testComicBin.findComic("DNE", comic1.getIssue(), "DNE"));
        testComicBin.addComic(comic2);
        testComicBin.addComic(comic3);
        assertNull(testComicBin.findComic(comic4.getName(), comic4.getIssue(), comic4.getBrand()));
        assertEquals(comic2, testComicBin.findComic(comic2.getName(), comic2.getIssue(), comic2.getBrand()));
        assertEquals(comic3, testComicBin.findComic(comic3.getName(), comic3.getIssue(), comic3.getBrand()));
        testComicBin.addComic(comic4);
        assertEquals(comic4, testComicBin.findComic(comic4.getName(), comic4.getIssue(), comic4.getBrand()));
        assertNull(testComicBin.findComic(comic5.getName(), comic5.getIssue(), comic5.getBrand()));
        assertNull(testComicBin.findComic(comic5.getName(), comic5.getIssue(), comic5.getBrand()));

    }

    @Test
    void testGetComicBin() {
        testComicBin.addComic(comic1);
        assertEquals(testComicBin.getComicBin(), testComicBin.getComicBin());
    }

    @Test
    void testRemoveComic() {
        testComicBin.addComic(comic1);
        assertEquals(1, testComicBin.length());
        testComicBin.removeComic(1);
        assertEquals(0,testComicBin.length());
        testComicBin.addComic(comic2);
        testComicBin.addComic(comic3);
        testComicBin.addComic(comic4);
        assertEquals(3, testComicBin.length());
        testComicBin.removeComic(3);
        assertEquals(2, testComicBin.length());
        testComicBin.addComic(comic5);
        assertEquals(3, testComicBin.length());
    }

    @Test
    void testLength() {
        assertEquals(0, testComicBin.length());
        testComicBin.addComic(comic1);
        testComicBin.addComic(comic2);
        assertNotEquals(1, testComicBin.length());
        assertEquals(2, testComicBin.length());
        testComicBin.addComic(comic3);
        testComicBin.addComic(comic4);
        testComicBin.addComic(comic5);
        assertNotEquals(3, testComicBin.length());
        assertNotEquals(4, testComicBin.length());
        assertEquals(5, testComicBin.length());
        testComicBin.removeComic(4);
        assertNotEquals(5, testComicBin.length());
        assertEquals(4, testComicBin.length());
        testComicBin.removeComic(1);
        testComicBin.removeComic(2);
        assertNotEquals(4, testComicBin.length());
        assertEquals(2, testComicBin.length());
    }


}
