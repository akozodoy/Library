package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestBook {
    Book b1;
    Book b2;
    Book b3;

    @BeforeEach
    void runBefore() {
        b1 = new Book("Jane Eyre", "Charlotte Bronte", "Novel", 1847,
                true);

        b2 = new Book("Harry Potter and the Sorcerer's Stone", "Joanne Rowling","Fantasy",
                1997, true);

        b3 = new Book("Harry Potter and the Sorcerer's Stone", "Joanne Rowling","Fantasy",
                2000, true);
    }

    @Test

    void testConstructor() {
        assertEquals("Jane Eyre", b1.getBookTitle());
        assertEquals("Charlotte Bronte", b1.getBookAuthor());
        assertEquals("Novel", b1.getBookGenre());
        assertEquals(1847, b1.getPublishedYear());
        assertTrue(b1.getBookAvailability());

    }

    @Test
    void testTakeBookFromTheLibrary() {
       b1.takeBook();
       assertFalse(b1.getBookAvailability());
    }

    @Test
    void testReturnBookToTheLibrary(){
        b1.takeBook();
        b1.returnBook();
        assertTrue(b1.getBookAvailability());
    }
}
