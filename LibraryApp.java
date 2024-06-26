package ui;

import java.util.*;

import model.Book;
import model.Library;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


// Represents the workroom application
public class LibraryApp {
    private static final String JSON_STORE = "./data/library.json";
    private Library library;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    //EFFECTS: runs the library application
    public LibraryApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        library = new Library("My library");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runLibrary();
    }


    // MODIFIES: this
    // EFFECTS: processes user input
    private void runLibrary() {
        boolean keepGoing = true;
        String command = null;
        input = new Scanner(System.in);

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addBook();
        } else if (command.equals("d")) {
            deleteBook();
        } else if (command.equals("c")) {
            checkOutBook();
        } else if (command.equals("r")) {
            returnBook();
        } else if (command.equals("si")) {
            librarySize();
        } else if (command.equals("og")) {
            oneGenreBooks(library);
        } else if (command.equals("oa")) {
            oneAuthorBooks(library);
        } else if (command.equals("p")) {
            printLibraryToScreen(library);
        } else if (command.equals("s")) {
            saveLibrary();
        } else if (command.equals("l")) {
            loadLibrary();
        } else {
            System.out.println("Command not valid...");
        }
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add book");
        System.out.println("\td -> delete book");
        System.out.println("\tc -> check out book");
        System.out.println("\tr -> return book");
        System.out.println("\tsi -> get number of book in the library");
        System.out.println("\tog -> get books with a specified genre");
        System.out.println("\toa -> get books with a specified author");
        System.out.println("\tp -> print library");
        System.out.println("\ts -> save library to file");
        System.out.println("\tl -> load library from file");
        System.out.println("\tq -> quit");
    }

    //MODIFIES: this
    //EFFECTS: adds new book in the library if it does not yet exist
    private void addBook() {
        String name = "";
        String author = "";
        String genre = "";
        int year = 0;
        boolean availability = true;
        System.out.print("Enter book name: ");
        name = input.next();
        System.out.print("Enter book author: ");
        author = input.next();
        System.out.print("Enter book genre: ");
        genre = input.next();
        System.out.print("Enter book year: ");
        year = input.nextInt();
        Book book = new Book(name, author, genre, year, true);
        if (checkIfTheBookDoesNotExist(name, author, genre, year)) {
            library.addBook(book);
            System.out.print("Book was added \n");
        } else {
            System.out.print("Book already exists in the library");
        }
    }

    //EFFECTS: check if library is not empty and if it is not, check if the book with
    //          the given name, author,genre, year does not exist there yet
    private boolean checkIfTheBookDoesNotExist(String name, String author, String genre, int year) {
        if (library.getBooks().isEmpty()) {
            return true;
        } else {
            for (Book book : library.getBooks()) {
                if (book.getBookTitle().equals(name) && book.getBookAuthor().equals(author)
                        && book.getBookGenre().equals(genre)
                        && book.getPublishedYear() == year) {
                    return false;
                }
            }
            return true;
        }
    }

    //MODIFIES: this
    //EFFECTS: delete book from the library if it exists
    private void deleteBook() {
        String name = "";
        String author = "";
        String genre = "";
        int year = 0;
        System.out.print("Enter book name: ");
        name = input.next();
        System.out.print("Enter book author: ");
        author = input.next();
        System.out.print("Enter book genre: ");
        genre = input.next();
        System.out.print("Enter book year: ");
        year = input.nextInt();
        if (checkIfTheBookDoesExist(name, author, genre, year)) {
            library.removeBook(getNeededBook(name, author, genre, year));
            System.out.print("Book was deleted from the library \n");
        } else {
            System.out.print("Book does not exist in the library");
        }
    }

    //EFFECTS: check if library is empty and if not, check if the book with
    //          the given name, author,genre, year exists  there
    private boolean checkIfTheBookDoesExist(String name, String author, String genre, int year) {
        if (library.getBooks().isEmpty()) {
            return false;
        } else {
            for (Book book : library.getBooks()) {
                if (book.getBookTitle().equals(name) && book.getBookAuthor().equals(author)
                        && book.getBookGenre().equals(genre)
                        && book.getPublishedYear() == year) {
                    return true;
                }
            }
            return false;
        }
    }

    //REQUIRED: library is not empty
    //EFFECTS: gets the book we need to do something with
    private Book getNeededBook(String name, String author, String genre, int year) {
        Book neededBook = new Book(name, author, genre, year, true);
        for (Book book : library.getBooks()) {
            if (book.getBookTitle().equals(name) && book.getBookAuthor().equals(author)
                    && book.getBookGenre().equals(genre)
                    && book.getPublishedYear() == year) {
                neededBook = book;
            }
        }
        return neededBook;
    }





    //MODIFIES: this
    //EFFECTS: makes book unavailable if it is available and exist
    private void checkOutBook() {
        String name = "";
        String author = "";
        String genre = "";
        int year = 0;
        System.out.print("Enter book name: ");
        name = input.next();
        System.out.print("Enter book author: ");
        author = input.next();
        System.out.print("Enter book genre: ");
        genre = input.next();
        System.out.print("Enter book year: ");
        year = input.nextInt();
        if (checkBookExistAndAvailable(name, author, genre, year)) {
            getNeededBook(name, author, genre, year).takeBook();
            System.out.print("Book was checked out \n");
        } else {
            System.out.print("Book does not exists or was already checked out");
        }
    }

    //EFFECTS: return true if the book exists and available in the library
    private boolean checkBookExistAndAvailable(String name, String author, String genre, int year) {
        boolean available = false;
        if (library.getBooks().isEmpty()) {
            return false;
        } else {
            for (Book book : library.getBooks()) {
                if (book.getBookTitle().equals(name) && book.getBookAuthor().equals(author)
                        && book.getBookGenre().equals(genre)
                        && book.getPublishedYear() == year && book.getBookAvailability()) {
                    available = true;
                }
            }
            return available;
        }
    }



    //MODIFIES: this
    //EFFECTS: makes book available if it is unavailable and exist
    private void returnBook() {
        String name = "";
        String author = "";
        String genre = "";
        int year = 0;
        System.out.print("Enter book name: ");
        name = input.next();
        System.out.print("Enter book author: ");
        author = input.next();
        System.out.print("Enter book genre: ");
        genre = input.next();
        System.out.print("Enter book year: ");
        year = input.nextInt();
        if (checkBookExistAndNotAvailable(name, author, genre, year)) {
            getNeededBook(name, author, genre, year).returnBook();
            System.out.print("Book was returned \n");
        } else {
            System.out.print("Book does not exists or was not checked out");
        }
    }

    //EFFECTS: return true if the book exists and not available in the library
    private boolean checkBookExistAndNotAvailable(String name, String author, String genre, int year) {
        boolean notAvailable = false;
        if (library.getBooks().isEmpty()) {
            return false;
        } else {
            for (Book book: library.getBooks()) {
                if (book.getBookTitle().equals(name) && book.getBookAuthor().equals(author)
                        && book.getBookGenre().equals(genre)
                        && book.getPublishedYear() == year && (!book.getBookAvailability())) {
                    notAvailable = true;
                }
            }
            return notAvailable;
        }
    }

    //EFFECTS: print Book
    private StringBuilder printBookToScreen(Book book) {
        StringBuilder line = new StringBuilder("");
        line.append("Title: ");
        line.append(book.getBookTitle());
        line.append(" | ");
        line.append("Author: ");
        line.append(book.getBookAuthor());
        line.append(" | ");
        line.append("Genre: ");
        line.append(book.getBookGenre());
        line.append(" | ");
        line.append("Year: ");
        line.append(book.getPublishedYear());
        line.append(" | ");
        line.append("Availability: ");
        line.append(book.getBookAvailability());
        line.append("\n");
        line.append("--------- \n");

        System.out.print(line);
        return line;
    }

    //EFFECTS: prints all books to the screen
    private void printLibraryToScreen(Library library) {
        List<StringBuilder> printedLibrary = new ArrayList<>();
        for (Book book: library.getBooks()) {
            printedLibrary.add(printBookToScreen(book));
        }
    }

    //EFFECTS: prints the number of book in the library
    private void librarySize() {
        int n = library.getBooks().size();
        System.out.println(n);
    }

    //REQUIRES: at least one book with this genre exists in the library
    //EFFECTS: prints all books to the screen with one given genre
    private void oneGenreBooks(Library library) {
        List<StringBuilder> sameGenreBooks = new ArrayList<>();
        String genre = "";
        System.out.print("Enter book genre: ");
        genre = input.next();
        for (Book book: library.getBooks()) {
            if (book.getBookGenre().equals(genre)) {
                System.out.print(sameGenreBooks.add(printBookToScreen(book)));
            }
        }
    }

    //REQUIRES: at least one book of the author exists in the library
    //EFFECTS: prints all books to the screen with one given genre
    private void oneAuthorBooks(Library library) {
        List<StringBuilder> sameAuthorBooks = new ArrayList<>();
        String author = "";
        System.out.print("Enter book author: ");
        author = input.next();
        for (Book book: library.getBooks()) {
            if (book.getBookAuthor().equals(author)) {
                System.out.print(sameAuthorBooks.add(printBookToScreen(book)));
            }
        }
    }

    // EFFECTS: saves the library to file
    private void saveLibrary() {
        try {
            jsonWriter.open();
            jsonWriter.write(library);
            jsonWriter.close();
            System.out.println("Saved " + library.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads library from file
    private void loadLibrary() {
        try {
            library = jsonReader.read();
            System.out.println("Loaded " + library.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


}


