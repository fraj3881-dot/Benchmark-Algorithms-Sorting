package com.mycompany.librarysorting;

/**
 * Book.java
 * Represents one book in the library.
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private int year;      // publication year (the sorting key)
    private int pages;

    public Book(int id, String title, String author, int year, int pages) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.pages = pages;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public int getPages() { return pages; }

    @Override
    public String toString() {
        return String.format("#%d %-28s %-22s %d  (%d pages)", id, title, author, year, pages);
    }
}
