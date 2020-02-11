package edu.fgcu.dataengineering;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import org.sqlite.SQLiteException;
/** This class is used to create a connection to BookStore database. */
public class BookStoreDB {
  Connection conn = null;
  Statement stmt = null;

  /** This method establishes a connection to the database */
  private void connect() {
    try {
      Class.forName("org.sqlite.JDBC");
      conn = DriverManager.getConnection("jdbc:sqlite:src/Data/BookStore.db");
      stmt = conn.createStatement();
     // System.out.println("successfully connected");
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  /** This method closes a connection to the database */
  private void closeDb() {
    try {
      stmt.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void selectAuthors() {
    connect();
    String sql = "SELECT * FROM PRODUCT;";
    ResultSet rs;
    try {
      rs = stmt.executeQuery(sql);
      while (rs.next()) {
        System.out.println();
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * This method is used to insert new author information into the database.
   * Any information that is already in the data base will not be added.
   * @param addAuthors this is an array that holds author objects created using the information from
   *     the JSON file.
   */
  public void insertAuthor(AuthorParser[] addAuthors) {
    connect();
    String preparedStm =
        "INSERT INTO author(author_name, author_email, author_url) VALUES ( ?,?, ?);";
    for (int i = 0; i < addAuthors.length; i++) {
      try {
        PreparedStatement preparedStatement = conn.prepareStatement(preparedStm);
        preparedStatement.setString(1, addAuthors[i].getName());
        preparedStatement.setString(2, addAuthors[i].getEmail());
        preparedStatement.setString(3, addAuthors[i].getUrl());
        preparedStatement.execute();
      } catch (SQLiteException ex) {
        System.out.println("Information already exists in the author table");
        // ex.printStackTrace();
      } catch (SQLException e) {
        System.out.println("");
        // e.printStackTrace();
      }
    }
    closeDb();
  }

  /**  This method is used to insert new book information into the database.
   *   Any information that is already in the data base will not be added.
   * @param addBooks this is an array that holds book objects created using the information from
   *     the JSON file.
   * */

  public void insertBook(Book[] addBooks) {
    connect();
    String preparedStm =
        "INSERT INTO book(isbn, publisher_name, author_name, book_year, book_title, book_price) VALUES ( ?,?, ?,?,?,?);";
    for (int i = 0; i < addBooks.length; i++) {
      try {
        PreparedStatement preparedStatement = conn.prepareStatement(preparedStm);
        preparedStatement.setString(1, addBooks[i].getIsbn());
        preparedStatement.setString(2, addBooks[i].getPublisher());
        preparedStatement.setString(3, addBooks[i].getAuthor());
        preparedStatement.setInt(4, addBooks[i].getBookYear());
        preparedStatement.setString(5, addBooks[i].getTitle());
        preparedStatement.setDouble(6, addBooks[i].getBookPrice());
        preparedStatement.execute();
      } catch (SQLiteException ex) {
        System.out.println("Information already exists in the book table");
        // ex.printStackTrace();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    closeDb();
  }
}
