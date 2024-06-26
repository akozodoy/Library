package persistence;

import model.Book;
import model.Library;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Tests for JsonWriter
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Library li = new Library("My library");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLibrary() {
        try {
            Library li = new Library("My library");
            JsonWriter writer = new JsonWriter("./data/testWriterLibrary.json");
            writer.open();
            writer.write(li);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterLibrary.json");
            li = reader.read();
            assertEquals("My library", li.getName());
            assertEquals(0, li.numBooks());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralLibrary() {
        try {
            Library li = new Library("My library");
            li.addBook(new Book("Harry Potter and the Sorcerer's Stone", "Joanne Rowling", "Fantasy", 2000,
                    true));
            li.addBook(new Book("Jane Eyre", "Charlotte Bronte", "Novel", 1847, true));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralLibrary.json");
            writer.open();
            writer.write(li);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralLibrary.json");
            li = reader.read();
            assertEquals("My library", li.getName());
            List<Book> books = li.getBooks();
            assertEquals(2, books.size());
            checkBook("Harry Potter and the Sorcerer's Stone", "Joanne Rowling", "Fantasy", 2000,
                    true, books.get(0));
            checkBook("Jane Eyre", "Charlotte Bronte", "Novel", 1847, true, books.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
