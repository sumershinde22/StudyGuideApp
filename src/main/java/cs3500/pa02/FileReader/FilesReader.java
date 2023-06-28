package cs3500.pa02.FileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * This FilesReader class reads the files of children classes
 */
public class FilesReader {
  /**
   * Reads the contents of a file
   *
   * @param filePath Path of the requested file
   * @return An ArrayList of strings which are each line
   */
  public static ArrayList<String> readFileContent(Path filePath) {
    ArrayList<String> contents = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath.toFile()))) {
      String line;
      while ((line = br.readLine()) != null) {
        contents.add(line);
      }
    } catch (IOException e) {
      System.err.println("Error reading file: " + filePath);
      e.printStackTrace();
      System.exit(1);
    }
    return contents;
  }
}
