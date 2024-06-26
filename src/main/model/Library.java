package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// Represents a library having a collection of books
public class Library implements Writable {
    private String name;
    private List<Book> books;

    // EFFECTS: constructs library with a name and empty list of books
    public Library(String name) {
        this.name = name;
        books = new ArrayList<>();
    }

    public String getName() {

        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds book to this library
    public void addBook(Book book) {

        books.add(book);

    }

    // MODIFIES: this
    // EFFECTS: removes book library
    public void removeBook(Book book) {

        books.remove(book);

    }

    // EFFECTS: returns an unmodifiable list of books in this library
    public List<Book> getBooks() {

        return Collections.unmodifiableList(books);
    }

    // EFFECTS: returns number of books in this library
    public int numBooks() {

        return books.size();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("books", booksToJson());
        return json;
    }

    // EFFECTS: returns books in this library as a JSON array
    private JSONArray booksToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Book b : books) {
            jsonArray.put(b.toJson());
        }

        return jsonArray;
    }


}
