package cs3500.pa02.FileWriter;

import java.io.File;

/**
 * The FilesWriter interface represents a writer for files.
 */
public interface FilesWriter {
  /**
   * Writes the content to the specified file.
   *
   * @param file the file to write the content to
   */
  public void write(File file);
}
