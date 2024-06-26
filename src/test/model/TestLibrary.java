package model;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestLibrary {
    private Library testLibrary;
    private Book b1;
    private Book b2;
    private Book b3;

    @BeforeEach
    void runBefore() {
        testLibrary = new Library("My library");
        b1 = new Book("Jane Eyre", "Charlotte Bronte", "Novel", 1847, true);
        b2 = new Book("Harry Potter and the Sorcerer's Stone", "Joanne Rowling", "Fantasy", 1997, true);
        b3 = new Book("Harry Potter and the Sorcerer's Stone", "Joanne Rowling", "Fantasy", 2000, true);

    }

    @Test
    void TestConstructor() {
        assertEquals(0, testLibrary.numBooks());

    }

    @Test
    void testAddSingleBook() {
        testLibrary.addBook(b1);
        assertEquals(1, testLibrary.numBooks());
    }

    @Test
    void testAddMultipleBooks() {
        testLibrary.addBook(b1);
        testLibrary.addBook(b2);
        testLibrary.addBook(b3);
        assertEquals(3, testLibrary.numBooks());
    }

    @Test
    void testRemoveSingleBook() {
        testLibrary.addBook(b1);
        testLibrary.addBook(b2);
        testLibrary.addBook(b3);
        testLibrary.removeBook(b1);
        assertEquals(2, testLibrary.numBooks());
    }

    @Test
    void testRemoveMultipleBooks() {
        testLibrary.addBook(b1);
        testLibrary.addBook(b2);
        testLibrary.addBook(b3);
        testLibrary.removeBook(b1);
        testLibrary.removeBook(b2);
        assertEquals(1, testLibrary.numBooks());
        testLibrary.removeBook(b3);
        assertEquals(0, testLibrary.numBooks());
    }

}
