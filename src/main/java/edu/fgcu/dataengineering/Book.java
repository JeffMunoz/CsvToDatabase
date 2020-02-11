package edu.fgcu.dataengineering;

/** This class is used to create book objects to help maintained information grouped together. */
public class Book {
  private String isbn;
  private String publisher;
  private String author;
  private String title;
  private double bookPrice;
  private int bookYear;

  public Book(
      String isbn,
      String publisherName,
      String authorName,
      String bookTitle,
      double bookPrice,
      int bookYear) {
    this.isbn = isbn;
    this.publisher = publisherName;
    this.author = authorName;
    this.title = bookTitle;
    this.bookPrice = bookPrice;
    this.bookYear = bookYear;
  }

  public Book(String isbn, String publisherName, String authorName, String bookTitle) {
    this.isbn = isbn;
    this.publisher = publisherName;
    this.author = authorName;
    this.title = bookTitle;
  }

  public String getIsbn() {
    if (isbn.equals("")) {
      isbn = null;
    }
    return isbn;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public String getPublisher() {
    if (publisher.equals("")) {
      publisher = null;
    }
    return publisher;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getAuthor() {
    if (author.equals("")) {
      author = null;
    }
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    if (title.equals("")) {
      title = null;
    }
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public double getBookPrice() {
    if (bookPrice <= 0) {
      bookPrice = 0;
    }
    return bookPrice;
  }

  public void setBookPrice(double bookPrice) {
    this.bookPrice = bookPrice;
  }

  public int getBookYear() {
    if (bookYear <= 0) {
      bookYear = 0;
    }
    return bookYear;
  }

  public void setBookYear(int bookYear) {
    this.bookYear = bookYear;
  }
}
