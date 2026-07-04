package com.example.librarymanagementsystemgui;
import java.time.LocalDate;
import java.util.ArrayList;
public class Library {
    private ArrayList<BOOK> books;

    public Library() {
        books = new ArrayList<>();
    }
    public void displaybooks() {
        for (BOOK book : books)
        {

            System.out.println("--------------------------------");
            System.out.println("Book ID   : " + book.getBookID());
            System.out.println("Title     : " + book.getTitle());
            System.out.println("Author    : " + book.getAuthor());
            System.out.println("Price     : ₹" + book.getPrice());
            System.out.println("Issued    : " + book.isIssued());
            System.out.println("Category  : " + book.getCategory());
            System.out.println("--------------------------------");

        }

    }

    public BOOK searchBook(int bookID) {
        for (BOOK book : books) {
            if (book.getBookID() == bookID) {
                return book;
            }
        }
        return null;
    }
    public boolean addBook(BOOK book){
        if(searchBook(book.getBookID()) !=null){
            return false;
        }
        books.add(book);
        return true;

    }

    public void issueBook(int bookID) {
        BOOK book = searchBook(bookID);
        if (book == null) {
            System.out.println("BOOK NOT FOUND");
        } else if (book.isIssued()) {
            System.out.println("BOOK IS ISSUED");
        } else {
            book.setIssued(true);
            book.setIssued(true);
            book.setIssueDate(LocalDate.now());
            book.setReturnDate(null);
            System.out.println("BOOK ISSUED SUCCESSFULLY");

        }
    }

    public void returnBook(int bookID) {
        BOOK book = searchBook(bookID);
        if (book == null) {
            System.out.println("BOOK NOT FOUND");
        } else if (!book.isIssued()) {
            System.out.println("BOOK IS ALREADY AVAILABLE");
        } else {
            book.setIssued(false);
            book.setReturnDate(LocalDate.now());
            System.out.println("BOOK RETURNED SUCCESSFULLY");

        }

    }
    public boolean deleteBook(int bookID) {

        BOOK book = searchBook(bookID);

        if (book == null) {
            System.out.println("BOOK NOT FOUND");
            return false;
        } else {
            books.remove(book);
            System.out.println("BOOK DELETED SUCCESSFULLY");
            return true;
        }
    }
    public int totalBooks(){
        return books.size();
    }
    public ArrayList<BOOK> getBooks() {
        return books;
    }


}


