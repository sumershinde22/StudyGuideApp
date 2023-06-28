package cs3500.pa02.FileWriter;

import cs3500.pa02.MdFile;
import cs3500.pa02.MdFileCollection;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * The SrFileWriter class implements the FilesWriter interface to write content to a file in the
 * ".sr" format.
 */
public class SrFileWriter implements FilesWriter {
  private final MdFileCollection mdCollectionFiles;
  private static final String OUTPUT_FILE_EXISTS_MESSAGE = "File updated successfully.";

  /**
   * Constructs a SrFileWriter object.
   *
   * @param mdFiles the collection of MdFiles to write to the file
   */
  public SrFileWriter(MdFileCollection mdFiles) {
    this.mdCollectionFiles = mdFiles;
  }

  /**
   * Writes the content to the specified file in the ".sr" format.
   *
   * @param file the file to write the content to
   */
  @Override
  public void write(File file) {

    try {
      if (file.exists()) {
        file.delete();
      }
      file.createNewFile();
      try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
        for (MdFile mdFile : mdCollectionFiles.mdFiles) {
          if (!mdFile.filePath.toString().equals(file.toPath().toString())) {
            ArrayList<String> fileQuestions = mdFile.fileQuestions;
            for (String question : fileQuestions) {
              writer.println(question + ":::hard");
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
