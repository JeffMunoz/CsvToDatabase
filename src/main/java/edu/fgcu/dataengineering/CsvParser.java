package edu.fgcu.dataengineering;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

  private List fileRows = new ArrayList();

  public CsvParser(String infile) throws IOException, CsvValidationException {
    /**
     * CsvParser - Reads csv Files using OpenCSV On load, check if file exsts & then load it into
     * fileRows
     *
     * @param infile the file to be opened with path information
     */
    if (checkFile(infile)) {
      readCsv(infile);
      convert(infile);
    }
  }

  protected void readCsv(String csvinfile) throws IOException, CsvValidationException {
    /**
     * readCsv: Read CSV file and load into our fileRows list
     *
     * @param csvinfile CSV file with path information for loading
     */

    // Open a file & input stream for use with CSVReader (to create a reader object)
    FileInputStream csvStream = new FileInputStream(csvinfile);
    InputStreamReader inputStream = new InputStreamReader(csvStream, StandardCharsets.UTF_8);
    CSVReader reader = new CSVReader(inputStream);

    /* As reader is an Object type this will be too (not String[])
         But making it a String[] allows it to cast later
      Read the file and load each line (split by default ",") into our List
    */
    String[] nextLine;
    while ((nextLine = reader.readNext()) != null) {
      fileRows.add(nextLine);
    }

    // Close the reader
    reader.close();
  }

  protected void writeCsv(String csvoutfile) {
    // place holder for write method (we'll add later with tests)
  }

  protected void printCsv() {
    /** printCsv - Printout the Csv */
    for (Object row : fileRows) {
      /*
      So fileRows will be an Object type (which is fine we are just incrementing though it)
           So after getting each row, we will need to "cast" row to a String array (String[])
      */
      for (String fields : (String[]) row) {

        System.out.print(fields + ", ");
      }
      System.out.println("\b\b\n---------------------");
    }
  }

  private boolean checkFile(String csvfile) {
    /**
     * checkFile - checks to ensure the file exists
     *
     * @return false on file not found, true on found
     */
    if (!Files.exists(Paths.get(csvfile))) {
      System.out.println("File does not exist");
      return false;
      // may change this to throw an exception
    }
    return true;
  }

  public void convert(String convertFile) {
    /**
     * Converts a .csv file to .json. Assumes first line is header with columns
     *
     * @param coverFile this is the CSV file that was passed in to parse
     */
    // Some code obtained from
    // https://stackoverflow.com/questions/9524191/converting-an-csv-file-to-a-json-object-in-java
    try {
      BufferedReader read = new BufferedReader(new FileReader(convertFile));
      // This will create a .json file with the same name as the CSV file and save it in the same
      // directory
      String outputName = convertFile.substring(0, convertFile.lastIndexOf(".")) + ".json";
      BufferedWriter write = new BufferedWriter(new FileWriter(new File(outputName)));

      String line;
      String columns[]; // contains column names
      int num_cols;
      String tokens[];

      // initialize columns
      line = read.readLine();
      columns = line.split(",");
      num_cols = columns.length;

      write.write("["); // begin file as array
      line = read.readLine();

      boolean loopControl = true;
      while (loopControl) {
        tokens = line.split(",");
        if (tokens.length == num_cols) { // if number columns equal to number entries
          write.write("{");

          for (int k = 0; k < num_cols; ++k) { // for each column
            if (tokens[k].matches("^-?[0-9]*\\.?[0-9]*$")) { // if a number
              write.write("" + columns[k] + ": " + tokens[k]);
              if (k < num_cols - 1) write.write(", ");
            } else { // if a string
              write.write("" + columns[k] + ": " + tokens[k] + "");
              if (k < num_cols - 1) write.write(", ");
            }
          }

          if ((line = read.readLine()) != null) { // if not last line
            write.write("},");
            write.newLine();
          } else {
            write.write("}]"); // if last line
            write.newLine();
            loopControl = false;
          }
        } else {
          System.out.println("Could not covert the CSV to JSON");
        }
      }

      write.close();
      read.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
