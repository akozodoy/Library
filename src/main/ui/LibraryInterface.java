package ui;

import model.Book;
import model.Library;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.EventLog;
import model.Event;

import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;


public class LibraryInterface extends JFrame implements ActionListener, Runnable {
    private final JButton countButton = new JButton();
    private final JButton loadButton = new JButton();
    private final JButton saveButton  = new JButton();
    private final JButton printButton  = new JButton();
    private final JButton addButton  = new JButton();
    private final JButton deleteButton  = new JButton();
    private final JButton checkoutButton  = new JButton();
    private final JButton returnButton  = new JButton();
    private final JButton authorButton  = new JButton();
    private final JButton genreButton  = new JButton();
    private JFrame frame;
    private static final String JSON_STORE = "./data/library.json";
    private Library library = new Library("My library");
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private EventLog el = EventLog.getInstance();

    public LibraryInterface()  {
        frame = new JFrame("Library");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        actionForClose();
        setBackground();
        frame.pack();
        frame.setLayout(new FlowLayout());
        JLabel l = new JLabel("My library");
        frame.add(l);
        frame.add(makeLoadButton());
        frame.add(makeSaveButton());
        frame.add(makePrintButton());
        frame.add(makeAddButton());
        frame.add(makeDeleteButton());
        frame.add(makeCheckoutButton());
        frame.add(makeReturnButton());
        frame.add(makePrintAuthorBooksButton());
        frame.add(makePrintGenreBooksButton());
        frame.add(makePrintNumberOfBooksButton());
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    private void actionForClose() {
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLog(el);
                System.exit(0);
            }
        });
    }

    private void setBackground() {
        try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("background.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printLog(EventLog el) {
        for (Event next : el) {
            System.out.print(next + "\n");
        }
    }

    private JButton makePrintNumberOfBooksButton() {
        countButton.setText("Number of book in this library");
        countButton.setBounds(40, 40, 100, 30);
        countButton.addActionListener(this);
        return countButton;
    }

    private JButton makePrintGenreBooksButton() {
        genreButton.setText("Print One Genre Books");
        genreButton.setBounds(400, 150, 100, 30);
        genreButton.addActionListener(this);
        return genreButton;
    }

    private JButton makePrintAuthorBooksButton() {
        authorButton.setText("Print Authors Books");
        authorButton.setBounds(40, 260, 100, 30);
        authorButton.addActionListener(this);
        return authorButton;
    }

    private JButton makeReturnButton() {
        returnButton.setText("Return a Book");
        returnButton.setBounds(40, 370, 100, 30);
        returnButton.addActionListener(this);
        return returnButton;
    }

    private JButton makeCheckoutButton() {
        checkoutButton.setText("Checkout a Book");
        checkoutButton.setBounds(40, 480, 100, 30);
        checkoutButton.addActionListener(this);
        return checkoutButton;
    }

    private JButton makeDeleteButton() {
        deleteButton.setText("Delete a Book");
        deleteButton.setBounds(40, 590, 100, 30);
        deleteButton.addActionListener(this);
        return deleteButton;
    }

    private JButton makeAddButton() {
        addButton.setText("Add a Book");
        addButton.setBounds(40, 700, 100, 30);
        addButton.addActionListener(this);
        return addButton;
    }

    private JButton makePrintButton() {
        printButton.setText("Print the Library");
        printButton.setBounds(40, 810, 100, 30);
        printButton.addActionListener(this);
        return printButton;
    }

    private JButton makeSaveButton() {
        saveButton.setText("Save the Library");
        saveButton.setBounds(40, 920, 100, 30);
        saveButton.addActionListener(this);
        return saveButton;
    }

    private JButton makeLoadButton() {
        loadButton.setText("Load the Library");
        loadButton.setBounds(40, 1030, 100, 30);
        loadButton.addActionListener(this);
        return loadButton;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Load the Library")) {
            loadLibrary();
        } else if (e.getActionCommand().equals("Save the Library")) {
            saveLibrary();
        } else if (e.getActionCommand().equals("Print the Library")) {
            printLibrary();
        } else if (e.getActionCommand().equals("Add a Book")) {
            addBook();
        } else if (e.getActionCommand().equals("Delete a Book")) {
            deleteBook();
        } else if (e.getActionCommand().equals("Checkout a Book")) {
            checkoutBook();
        } else if (e.getActionCommand().equals("Return a Book")) {
            returnBook();
        } else if (e.getActionCommand().equals("Print One Genre Books")) {
            returnOneGenreBooks();
        } else if (e.getActionCommand().equals("Print Authors Books")) {
            returnOneAuthorBooks();
        } else if (e.getActionCommand().equals("Number of book in this library")) {
            printNumberOfBooks();
        }

    }

    @Override
    public void run() {
        frame.pack();
        frame.setVisible(true);
    }

    public void loadLibrary() {
        try {
            library = jsonReader.read();
            Event event = new Event("Library was loaded");
            el.logEvent(event);
            JOptionPane.showMessageDialog(null,
                    "Loaded " + library.getName() + " from " + JSON_STORE,
                    "Loaded",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE,
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveLibrary() {
        try {
            jsonWriter.open();
            jsonWriter.write(library);
            jsonWriter.close();
            Event event = new Event("Library was saved");
            el.logEvent(event);
            JOptionPane.showMessageDialog(null,
                    "Saved " + library.getName() + " to " + JSON_STORE,
                    "Saved",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null,"Unable to write to file: " + JSON_STORE,
                    "Saved",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void printLibrary() {
        StringBuilder printedLibrary = new StringBuilder("");
        for (Book book: library.getBooks()) {
            printedLibrary.append(printBookToScreen(book));
        }
        JOptionPane.showMessageDialog(null,
                printedLibrary.toString(),
                "Library",
                JOptionPane.INFORMATION_MESSAGE);
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

        return line;
    }

    //MODIFIES: this
    //EFFECTS: adds new book in the library if it does not yet exist
    public void addBook() {
        String name = JOptionPane.showInputDialog("Title");
        if ((name != null) && (name.length() > 0)) {
            String author = JOptionPane.showInputDialog("Author");
            if ((author != null) && (author.length() > 0)) {
                String genre = JOptionPane.showInputDialog("Genre");
                if ((genre != null) && (genre.length() > 0)) {
                    String yearString = JOptionPane.showInputDialog("Year");
                    if ((yearString != null) && (yearString.length() > 0)) {
                        int year = Integer.parseInt(yearString);
                        Book book = new Book(name, author, genre, year, true);
                        if (checkIfTheBookDoesNotExist(name, author, genre, year)) {
                            library.addBook(book);
                            addedBook();
                        } else {
                            notAddedBook();
                        }
                    }
                }
            }

        }
    }

    //creates event of adding a book and shows user that the book was added
    private void addedBook() {
        Event event = new Event("Book was Added");
        el.logEvent(event);
        JOptionPane.showMessageDialog(null,"Book was added",
                "Added", JOptionPane.INFORMATION_MESSAGE);
    }

    //creates event when the book was not added and shows user that the book was not added
    private void notAddedBook() {
        Event event = new Event("Book was not added");
        el.logEvent(event);
        JOptionPane.showMessageDialog(null,"Book already exists in the library",
                "Error", JOptionPane.ERROR_MESSAGE);

    }

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
    public void deleteBook() {
        String name = JOptionPane.showInputDialog("Title");
        if ((name != null) && (name.length() > 0)) {
            String author = JOptionPane.showInputDialog("Author");
            if ((author != null) && (author.length() > 0)) {
                String genre = JOptionPane.showInputDialog("Genre");
                if ((genre != null) && (genre.length() > 0)) {
                    String yearString = JOptionPane.showInputDialog("Year");
                    if ((yearString != null) && (yearString.length() > 0)) {
                        int year = Integer.parseInt(yearString);
                        if (checkIfTheBookDoesExist(name, author, genre, year)) {
                            library.removeBook(getNeededBook(name, author, genre, year));
                            Event event = new Event("Book was Deleted");
                            el.logEvent(event);
                            deletedBook();
                        } else {
                            Event event = new Event("Error: Book does not exist in the library");
                            el.logEvent(event);
                            notDeletedBook();
                        }
                    }
                }
            }
        }
    }

    private void deletedBook() {
        JOptionPane.showMessageDialog(null,"Book was deleted",
                "Deleted", JOptionPane.INFORMATION_MESSAGE);
    }

    private void notDeletedBook() {
        JOptionPane.showMessageDialog(null,
                "Book does not exist in the library",
                "Error", JOptionPane.INFORMATION_MESSAGE);

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
    public void checkoutBook() {
        String name = JOptionPane.showInputDialog("Title");
        if ((name != null) && (name.length() > 0)) {
            String author = JOptionPane.showInputDialog("Author");
            if ((author != null) && (author.length() > 0)) {
                String genre = JOptionPane.showInputDialog("Genre");
                if ((genre != null) && (genre.length() > 0)) {
                    String yearString = JOptionPane.showInputDialog("Year");
                    if ((yearString != null) && (yearString.length() > 0)) {
                        int year = Integer.parseInt(yearString);
                        if (checkBookExistAndAvailable(name, author, genre, year)) {
                            getNeededBook(name, author, genre, year).takeBook();
                            Event event = new Event("Book was checked out");
                            el.logEvent(event);
                            checkedOutBook();
                        } else {
                            Event event = new Event("Error: Book does not exist or was already checked out");
                            el.logEvent(event);
                            notCheckedOutBook();
                        }
                    }
                }
            }
        }
    }

    private void checkedOutBook() {
        JOptionPane.showMessageDialog(null,"Book was checked out",
                "Checked out", JOptionPane.INFORMATION_MESSAGE);
    }

    private void notCheckedOutBook() {
        JOptionPane.showMessageDialog(null,
                "Book does not exist or was already checked out",
                "Error", JOptionPane.ERROR_MESSAGE);

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
    public void returnBook() {
        String name = JOptionPane.showInputDialog("Title");
        if ((name != null) && (name.length() > 0)) {
            String author = JOptionPane.showInputDialog("Author");
            if ((author != null) && (author.length() > 0)) {
                String genre = JOptionPane.showInputDialog("Genre");
                if ((genre != null) && (genre.length() > 0)) {
                    String yearString = JOptionPane.showInputDialog("Year");
                    if ((yearString != null) && (yearString.length() > 0)) {
                        int year = Integer.parseInt(yearString);
                        if (checkBookExistAndNotAvailable(name, author, genre, year)) {
                            getNeededBook(name, author, genre, year).returnBook();
                            Event event = new Event("Book was returned");
                            el.logEvent(event);
                            returnedBook();
                        } else {
                            Event event = new Event("Error: Book does not exist or was not checked out");
                            el.logEvent(event);
                            notReturnedBook();
                        }
                    }
                }
            }
        }
    }

    private void returnedBook() {
        JOptionPane.showMessageDialog(null,"Book was returned",
                "Checked out", JOptionPane.INFORMATION_MESSAGE);
    }

    private void notReturnedBook() {
        JOptionPane.showMessageDialog(null,"Book does not exist or was not checked out",
                "Error", JOptionPane.ERROR_MESSAGE);

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


    //REQUIRES: at least one book with this genre exists in the library
    //EFFECTS: prints all books to the screen with one given genre
    public void returnOneGenreBooks() {
        StringBuilder sameGenreBooks = new StringBuilder("");
        String genre = "";
        genre = JOptionPane.showInputDialog("Enter genre");
        if ((genre != null) && (genre.length() > 0)) {
            for (Book book: library.getBooks()) {
                if (book.getBookGenre().equals(genre)) {
                    sameGenreBooks.append(printBookToScreen(book));
                }
            }
            JOptionPane.showMessageDialog(null, sameGenreBooks.toString(),
                    genre + " Genre Books",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void returnOneAuthorBooks() {
        StringBuilder sameAuthorBooks = new StringBuilder("");
        String author = "";
        author = JOptionPane.showInputDialog("Enter author");
        if ((author != null) && (author.length() > 0)) {
            for (Book book: library.getBooks()) {
                if (book.getBookAuthor().equals(author)) {
                    sameAuthorBooks.append(printBookToScreen(book));
                }
            }
            JOptionPane.showMessageDialog(null, sameAuthorBooks.toString(),
                    "Books of " + author,
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    //EFFECTS: prints the number of book in the library
    public void printNumberOfBooks() {
        int n = library.getBooks().size();
        if (n == 0) {
            JOptionPane.showMessageDialog(null, "There are no books in the library",
                    "Library size ",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (n == 1) {
            JOptionPane.showMessageDialog(null, "There is one book in the library",
                    "Library size ",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "There are " + n + " books in the library",
                    "Library size ",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // EFFECTS: Schedules the application to be run at the correct time in the event queue.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new LibraryInterface());
    }

}
