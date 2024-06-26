# Book tracking application in the library

## Idea description
A book tracking application for library. It lets you
enter information about books in the library and coming 
books. Features of the books includes title, authors name, 
book genre, short descriptions, publication year, 
information about if it is a single book, or it has 
continuation and information about its location in the
library(floor, section and shelf). X could be a book 
and Y could be a list of books with same genre, author 
or list of books with one story divided to different 
books (Like Harry Potter). With application, you can 
find if a particular book was taken by visitor, for what
period it was taken. Also, you can add new books in your
system to show your visitors them and their location in
the library. All books will be arranged by authors or 
genres. One book can have different years publishing, 
so you will be also able to check and add this information. 
Library workers can use this application to monitor 
the presence of books in the library, note that
the book was taken by visitor and for what time it was 
taken. They will be able to check whether the visitor 
returned the book on time, add new books that arrived in
the library. According to the system, they will be able 
to easily explain to visitors where to find the desired
book. Some people need a particular publishing of the 
book because every year some details might be changed 
in books (for example in textbooks). The library worker
will be able to tell if there is a book of the desired 
year. There might be more methods to use this application,
I will come up with them during the developing of the 
project.

I am really interested in this topic  because now 
people read not a lot. They spend more and more time in 
social media. It is easier to read something in Instagram that go to the 
library or book store and take a book.  But if the 
library system would be easier for library workers it
would be easier for visitors.  So, some people can 
become more interested in reading books if the system 
will become easier to use. It is not really cool 
when you spend 50 minutes to go to the library to find 
out that there is no book you wanted at all, or it 
was taken. But with this system the library worker 
will be able to easily check currently book 
availability and book it for you, when you are calling 
them from home.


## User Stories:
- As a user, I want to add a new book in my library
- As a user, I want to delete the book in my library
- As a user, I want to get list of books of a particular author those are exist in the library
- As a user, I want to check out a book
- As a user, I want to return a book to the library, so it will be available again
- As a user, I want to get the number of books in the library
- As a user, I want see all books with a particular genre
- As a user, I want to be able to save my library into file to save changes
- As a user, I want to be able to load my library from file (if I so choose)

JsonReader and JsonWriter was copied from JsonApp Class

# Instructions for Grader:
- You can generate the first required action related to the user story "adding multiple books to a library" 
by clicking the button labelled "Add a Book"
- You can generate the second required action related to the user story "delete multiple books from a library" 
by clicking the button labelled "Delete a Book"
- You can locate my visual component as a menu background of the library app
- You can save the state of my application by clicking "Save the Library"
- You can reload the state of my application by clicking "Load the Library"

# Phase 4: Task 2
- Tue Nov 28 22:33:54 PST 2023
Library was loaded
- Tue Nov 28 22:34:03 PST 2023
Book was Added
- Tue Nov 28 22:34:07 PST 2023
Book was not added
- Tue Nov 28 22:34:14 PST 2023
Book was Deleted
- Tue Nov 28 22:34:19 PST 2023
Error: Book does not exist in the library
- Tue Nov 28 22:34:27 PST 2023
Book was Added
- Tue Nov 28 22:34:31 PST 2023
Book was checked out
- Tue Nov 28 22:34:37 PST 2023
Error: Book does not exist or was already checked out
- Tue Nov 28 22:34:44 PST 2023
Book was returned
- Tue Nov 28 22:34:50 PST 2023
Error: Book does not exist or was not checked out
- Tue Nov 28 22:34:53 PST 2023
Library was saved

# Phase 4: Task 3
In libraryApp and LibraryInterface it might be important to  refactor 
some parts of some methods to another methods since it will be easier to read the code.
I did it in addBook method in LibraryInterface, but the code need more refactoring in other methods.

Also, it might be important to create some new classes to make the code more readable.
It might be important for makeButton methods since they are vry similar to each other 
and if I would make an abstract class for them and use it in the LibraryInterface class
I would not need to write each method with mane lines there and make new buttons easier.