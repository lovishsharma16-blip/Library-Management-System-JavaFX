package com.example.librarymanagementsystemgui;
import java.time.LocalDate;


public class BOOK {
    private int BookID;
    private String Title;
    private String Author;
    private double Price;
    private boolean Issued;
    private String Category;
    private LocalDate issueDate;
    private LocalDate returnDate;

    public BOOK(int bookID, String title, String author, double price, boolean issued, String category) {
        this.BookID = bookID;
        this.Title = title;
        this.Author = author;
        this.Price = price;
        this.Issued = issued;
        this.Category = category;
        this.issueDate = null;
        this.returnDate = null;
    }

    public int getBookID() {
        return BookID;
    }

    public String getTitle() {
        return Title;
    }

    public String getAuthor() {
        return Author;
    }

    public double getPrice() {
        return Price;
    }
    public boolean isIssued() {
        return Issued;
    }
    public void setIssued(boolean issued){
        this.Issued= issued;

    }

    public String getCategory() {
        return Category;
    }
    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}


