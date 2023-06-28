package cs3500.pa02;

import cs3500.pa02.FileReader.MdFileReader;
import cs3500.pa02.FileWriter.SrFileWriter;
import cs3500.pa02.FileWriter.StudyGuideWriter;
import cs3500.pa02.StudyController.StudyControllerImplementation;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * This is the main driver of this project.
 */
public class Driver {

  private static final String ARGUMENT_EXCEPTION_MESSAGE =
      "Please include all command arguments in the order of root of the notes, ordering flag,"
          + " and output path for the study guide.";

  /**
   * Project entry point
   *
   * @param args - no command line args required
   */
  public static void main(String[] args) throws IOException {
    // Check if 3 arguments are present
    StudyControllerImplementation studyControllerImplementation =
        new StudyControllerImplementation();
    if (args.length == 3) {
      String notesRoot = args[0];
      String orderingFlag = args[1];
      String outputPath = args[2];
      Path notesRootPath = Path.of(notesRoot);
      MdFileWalker mdFileWalker = new MdFileWalker();
      Files.walkFileTree(notesRootPath, mdFileWalker);

      MdFileReader fileReader = new MdFileReader();
      ArrayList<MdFile> mdFiles = fileReader.createMdFiles(mdFileWalker.mdFilePaths);
      MdFileCollection mdFileCollection = new MdFileCollection(mdFiles);
      mdFileCollection.sortMdFiles(orderingFlag);

      StudyGuideWriter studyGuide = new StudyGuideWriter(mdFileCollection);
      studyGuide.write(new File(outputPath));
      String newOutputPath = outputPath.replace(".md", ".sr");
      SrFileWriter srFile = new SrFileWriter(mdFileCollection);
      srFile.write(new File(newOutputPath));
    } else if (args.length == 0) {

      studyControllerImplementation.run();
    } else {
      throw new IllegalArgumentException(ARGUMENT_EXCEPTION_MESSAGE);
    }
  }
}
