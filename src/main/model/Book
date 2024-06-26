package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a book with its title, author, genre, published year, and availability in the library
public class Book implements Writable {
    private String bookTitle;
    private String bookAuthor;
    private String bookGenre;
    private int publishedYear;
    private boolean bookAvailability;

    // REQUIRES: publishedYear > 0
    // EFFECTS: constructs a book with an associated name,
    // book author, book genre, its location in the library,
    // the year when it was published and true bookAvailability
    public Book(String bookTitle, String bookAuthor, String bookGenre, int publishedYear, boolean bookAvailability) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookGenre = bookGenre;
        this.publishedYear = publishedYear;
        this.bookAvailability = bookAvailability;


    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {

        return bookAuthor;
    }

    public String getBookGenre() {

        return bookGenre;
    }

    public int getPublishedYear() {
        return publishedYear;
    }

    public boolean getBookAvailability() {
        return bookAvailability;
    }

    //EFFECT: makes a book unavailable
    public boolean takeBook() {
        return bookAvailability = false;
    }

    //EFFECT: makes a book available
    public boolean returnBook() {
        return bookAvailability = true;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", bookTitle);
        json.put("author", bookAuthor);
        json.put("genre", bookGenre);
        json.put("year", publishedYear);
        json.put("availability", bookAvailability);
        return json;
    }
}
