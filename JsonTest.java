package persistence;


import model.Book;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Tests for Json
public class JsonTest {
    protected void checkBook(String title, String author, String genre, int year, boolean availability, Book book) {
        assertEquals(title, book.getBookTitle());
        assertEquals(author, book.getBookAuthor());
        assertEquals(genre, book.getBookGenre());
        assertEquals(year, book.getPublishedYear());
        assertEquals(availability, book.getBookAvailability());

    }
}
