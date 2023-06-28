package cs3500.pa02.FileWriter;

import cs3500.pa02.MdFile;
import cs3500.pa02.MdFileCollection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

/**
 * A class representing a writer that writes a study guide to a file from a collection of markdown
 * files.
 */
public class StudyGuideWriter implements FilesWriter {
  /**
   * The collection of markdown files.
   */
  private final MdFileCollection mdCollectionFiles;

  private static final String OUTPUT_FILE_EXTENSION = ".md";
  private static final String OUTPUT_FILE_EXISTS_MESSAGE = "File updated successfully.";

  /**
   * Constructs a new StudyGuideWriter with the given collection of markdown files.
   *
   * @param mdFiles the collection of markdown files
   */
  public StudyGuideWriter(MdFileCollection mdFiles) {
    this.mdCollectionFiles = mdFiles;
  }

  /**
   * Writes a study guide to the given file from the collection of markdown files.
   *
   * @param file the file to write the study guide to
   */
  public void write(File file) {
    if (!file.getPath().endsWith(OUTPUT_FILE_EXTENSION)) {
      throw new IllegalArgumentException(
          "Please make sure there is an output file and it ends in " + OUTPUT_FILE_EXTENSION);
    } else {
      try {
        if (file.exists()) {
          file.delete();
        }
        file.createNewFile();
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
          for (MdFile mdFile : mdCollectionFiles.mdFiles) {
            if (!mdFile.filePath.toString().equals(file.toPath().toString())) {
              Map<String, ArrayList<String>> mdImportantText = mdFile.importantText;
              for (Map.Entry<String, ArrayList<String>> entry : mdImportantText.entrySet()) {
                String key = entry.getKey();
                ArrayList<String> values = entry.getValue();
                writer.print(key + "\n");
                for (String value : values) {
                  writer.print("- " + value + "\n");
                }
                writer.print("\n");
              }
            }
          }
          System.out.println(OUTPUT_FILE_EXISTS_MESSAGE);
        }
      } catch (IOException e) {
        System.err.println("Problem with file");
        System.exit(1);
      }
    }
  }
}
