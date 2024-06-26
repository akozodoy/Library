package persistence;


import model.Book;
import model.Library;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Represents a reader that reads libraryInfo from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads libraryInfo from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Library read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLibraryInfo(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses library from JSON object and returns it
    private Library parseLibraryInfo(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Library li = new Library(name);
        addBooks(li, jsonObject);
        return li;
    }

    // MODIFIES: li
    // EFFECTS: parses books from JSON object and adds them to library
    private void addBooks(Library li, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("books");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(li, nextBook);
        }
    }

    // MODIFIES: li
    // EFFECTS: parses book from JSON object and adds it to library
    private void addBook(Library li, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String author = jsonObject.getString("author");
        String genre = jsonObject.getString("genre");
        Integer year = jsonObject.getInt("year");
        Boolean availability = jsonObject.getBoolean("availability");
        Book book = new Book(name, author, genre, year, availability);
        li.addBook(book);
    }




}
