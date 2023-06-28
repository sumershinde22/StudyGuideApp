package cs3500.pa02.StudyController;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa02.Driver;
import cs3500.pa02.FileWriter.SrFileWriter;
import cs3500.pa02.MdFile;
import cs3500.pa02.MdFileCollection;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class contains test cases for the StudyControllerImplementation class.
 */
class StudyControllerImplementationTest {
  SrFileWriter srFileWriter;

  private static final String OUTPUT_FILE_EXISTS_MESSAGE = "File updated successfully.";

  /**
   * Resets the necessary variables and creates sample MdFiles for testing.
   */
  @BeforeEach
  public void reset() {
    Map<String, ArrayList<String>> importantInfo1 = new LinkedHashMap<>();
    ArrayList<String> alist1 = new ArrayList<>();
    alist1.add("ArrayList is a Java class implemented using the List interface");
    importantInfo1.put("# ArrayList in Java", alist1);
    ArrayList<String> alist2 = new ArrayList<>();
    alist2.add("AbstractList Class and implement only the get() and the size() methods");
    importantInfo1.put("## AbstractList", alist2);
    ArrayList<String> alist3 = new ArrayList<>();
    alist3.add("making a fresh copy of the list");
    importantInfo1.put("## CopyOnWriteArrayList", alist3);
    ArrayList<MdFile> mdFiles = new ArrayList<>();
    ArrayList<String> fileQuestions = new ArrayList<>();
    fileQuestions.add(
        "What is the purpose of the CopyOnWriteArrayList class in Java?:::The CopyOnWriteArrayList "
            + "class in Java is an enhanced version of ArrayList.");
    mdFiles.add(new MdFile(Path.of("testDirectory/ArrayList/ArrayList.md"),
        "ArrayList.md",
        importantInfo1,
        FileTime.from(Instant.parse("2023-05-13T20:08:12Z")),
        FileTime.from(Instant.parse("2023-05-13T20:16:10.966753036Z")), fileQuestions));

    Map<String, ArrayList<String>> importantInfo2 = new LinkedHashMap<>();
    ArrayList<String> blist1 = new ArrayList<>();
    blist1.add("An **array** is a collection of variables of the same type");
    importantInfo2.put("# Java Arrays", blist1);
    ArrayList<String> blist2 = new ArrayList<>();
    blist2.add("General Form: type[] arrayName;");
    blist2.add("only creates a reference");
    importantInfo2.put("## Declaring an Array", blist2);
    ArrayList<String> blist3 = new ArrayList<>();
    blist3.add("General form:  arrayName = new type[numberOfElements];");
    blist3.add("numberOfElements must be a positive Integer.");
    blist3.add("Gotcha: Array size is not modifiable once instantiated.");
    importantInfo2.put("## Creating an Array (Instantiation)", blist3);
    ArrayList<String> fileQuestions2 = new ArrayList<>();
    fileQuestions2.add(
        "What is an array in Java?:::An array in Java is a collection of variables of the same"
            + "type, referred to by a common name.");

    mdFiles.add(new MdFile(Path.of("sampleMdFiles/Arrays/Arrays.md"), "Arrays.md",
        importantInfo2,
        FileTime.from(Instant.parse("2023-05-13T02:52:03Z")),
        FileTime.from(Instant.parse("2023-05-13T02:52:03Z")), fileQuestions2));

    Map<String, ArrayList<String>> importantInfo3 = new LinkedHashMap<>();
    ArrayList<String> clist1 = new ArrayList<>();
    clist1.add("Vectors act like resizable arrays");
    importantInfo3.put("# Vectors", clist1);
    ArrayList<String> clist2 = new ArrayList<>();
    clist2.add("General Form: Vector<type> v = new Vector();");
    clist2.add("type needs to be a valid reference type");
    importantInfo3.put("## Declaring a vector", clist2);
    ArrayList<String> clist3 = new ArrayList<>();
    clist3.add("v.add(object of type);");
    importantInfo3.put("## Adding an element to a vector", clist3);
    ArrayList<String> fileQuestions3 = new ArrayList<>();
    fileQuestions3.add(
        "What are vectors in Java?:::Vectors in Java act like resizable arrays.");

    mdFiles.add(
        new MdFile(Path.of("sampleMdFiles/Vectors/Vectors.md"), "ArrayList.md", importantInfo3,
            FileTime.from(Instant.parse("2023-05-15T17:17:26Z")),
            FileTime.from(Instant.parse("2023-05-15T17:17:26Z")), fileQuestions3));
    MdFileCollection mdFileCollection = new MdFileCollection(mdFiles);
    srFileWriter = new SrFileWriter(mdFileCollection);
    srFileWriter.write(
        new File("src/test/resources/ControllerTestFiles/studyGuideFilenameOrder.sr"));
    writeEasy(new File("src/test/resources/ControllerTestFiles/studyGuideFilenameOrder2.sr"),
        mdFileCollection);
  }

  private void writeEasy(File file, MdFileCollection mdCollectionFiles) {
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
              writer.println(question + ":::easy");
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

  /**
   * Tests the quit early functionality of the StudyControllerImplementation.
   */
  @Test
  public void testQuitEarly() {
    try {
      // Prepare input for the Scanner
      String input = "src/test/resources/ControllerTestFiles/studyGuideFilenameOrder.sr\n3\n2\n4\n";

      // Redirect System.in to the ByteArrayInputStream with the input
      InputStream originalInput = System.in;
      System.setIn(new ByteArrayInputStream(input.getBytes()));

      // Redirect System.out to a ByteArrayOutputStream
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream printStream = new PrintStream(outputStream);
      PrintStream originalPrintStream = System.out;
      System.setOut(printStream);

      // Call the main method
      Driver.main(new String[0]);

      // Restore the original System.in and System.out
      System.setIn(originalInput);
      System.setOut(originalPrintStream);

      // Get the printed output
      String printedOutput = outputStream.toString();

      // Define the expected output
      String welcomeOutput =
          "Welcome to the Study Session!\n"
              + "Please enter a full path to a .sr file:\n"
              + "Please enter how many questions out of 3 you would like to answer: ";
      String question1Output = "What is an array in Java?\n"
          + "Enter 1 to label as easy and proceed to next question.\n"
          + "Enter 2 to label as hard and proceed to next question.\n"
          + "Enter 3 to see answer.\n"
          + "Enter 4 to quit.";

      String question2Output =
          "What is the purpose of the CopyOnWriteArrayList class in Java?\n"
              + "Enter 1 to label as easy and proceed to next question.\n"
              + "Enter 2 to label as hard and proceed to next question.\n"
              + "Enter 3 to see answer.\n"
              + "Enter 4 to quit.";
      String question3Output =
          "What are vectors in Java?\n"
              + "Enter 1 to label as easy and proceed to next question.\n"
              + "Enter 2 to label as hard and proceed to next question.\n"
              + "Enter 3 to see answer.\n"
              + "Enter 4 to quit.";
      String statsOutput =
          "Practice session ended early.\n"
              + "\n"
              + "File updated successfully.\n"
              + "Thanks for practicing! Here are the statistics for your session:\n"
              + "Number of Questions Answered: 1\n"
              + "Number of Questions that changed from easy to hard in Bank: 0\n"
              + "Number of Questions that changed from hard to easy in Bank: 0\n"
              + "Number of Hard Questions in Bank: 3\n"
              + "Number of Easy Questions in Bank: 0\n"
              + "Goodbye!";

      assertTrue(
          printedOutput.contains(welcomeOutput) && (printedOutput.contains(question1Output)
              || printedOutput.contains(question2Output) || printedOutput.contains(question3Output))
              && printedOutput.contains(statsOutput));
    } catch (IOException e) {
      System.err.println("IO Error");
      e.printStackTrace();
      System.exit(1);
    }
  }


  /**
   * Tests changing a question from easy to hard during a study session.
   */
  @Test
  public void testChangeToSetHard() {
    try {
      // Prepare input for the Scanner
      String input =
          "src/test/resources/ControllerTestFiles/studyGuideFilenameOrder2.sr\n3\n2\n4\n";

      // Redirect System.in to the ByteArrayInputStream with the input
      InputStream originalInput = System.in;
      System.setIn(new ByteArrayInputStream(input.getBytes()));

      // Redirect System.out to a ByteArrayOutputStream
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream printStream = new PrintStream(outputStream);
      PrintStream originalPrintStream = System.out;
      System.setOut(printStream);

      // Call the main method
      Driver.main(new String[0]);

      // Restore the original System.in and System.out
      System.setIn(originalInput);
      System.setOut(originalPrintStream);

      // Get the printed output
      String printedOutput = outputStream.toString();

      // Define the expected output
      String welcomeOutput =
          "Welcome to the Study Session!\n"
              + "Please enter a full path to a .sr file:\n"
              + "Please enter how many questions out of 3 you would like to answer: ";
      String question1Output = "What is an array in Java?\n"
          + "Enter 1 to label as easy and proceed to next question.\n"
          + "Enter 2 to label as hard and proceed to next question.\n"
          + "Enter 3 to see answer.\n"
          + "Enter 4 to quit.";

      String question2Output =
          "What is the purpose of the CopyOnWriteArrayList class in Java?\n"
              + "Enter 1 to label as easy and proceed to next question.\n"
              + "Enter 2 to label as hard and proceed to next question.\n"
              + "Enter 3 to see answer.\n"
              + "Enter 4 to quit.";
      String question3Output =
          "What are vectors in Java?\n"
              + "Enter 1 to label as easy and proceed to next question.\n"
              + "Enter 2 to label as hard and proceed to next question.\n"
              + "Enter 3 to see answer.\n"
              + "Enter 4 to quit.";
      String statsOutput =
          "Practice session ended early.\n"
              + "\n"
              + "File updated successfully.\n"
              + "Thanks for practicing! Here are the statistics for your session:\n"
              + "Number of Questions Answered: 1\n"
              + "Number of Questions that changed from easy to hard in Bank: 1\n"
              + "Number of Questions that changed from hard to easy in Bank: 0\n"
              + "Number of Hard Questions in Bank: 1\n"
              + "Number of Easy Questions in Bank: 2\n"
              + "Goodbye!";

      // Assert that the printed output matches the expected output
      assertTrue(
          printedOutput.contains(welcomeOutput) && (printedOutput.contains(question1Output)
              || printedOutput.contains(question2Output) || printedOutput.contains(question3Output))
              && printedOutput.contains(statsOutput));
    } catch (IOException e) {
      System.err.println("IO Error");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Tests changing a question from hard to easy during a study session.
   */
  @Test
  public void testSetEasy() {
    try {
      // Prepare input for the Scanner
      String input = "src/test/resources/ControllerTestFiles/studyGuideFilenameOrder.sr\n3\n1\n4\n";

      // Redirect System.in to the ByteArrayInputStream with the input
      InputStream originalInput = System.in;
      System.setIn(new ByteArrayInputStream(input.getBytes()));

      // Redirect System.out to a ByteArrayOutputStream
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream printStream = new PrintStream(outputStream);
      PrintStream originalPrintStream = System.out;
      System.setOut(printStream);

      // Call the main method
      Driver.main(new String[0]);

      // Restore the original System.in and System.out
      System.setIn(originalInput);
      System.setOut(originalPrintStream);

      // Get the printed output
      String printedOutput = outputStream.toString();

      // Define the expected output
      String welcomeOutput =
          "Welcome to the Study Session!\n"
              + "Please enter a full path to a .sr file:\n"
              + "Please enter how many questions out of 3 you would like to answer: ";
      String question1Output = "What is an array in Java?\n"
          + "Enter 1 to label as easy and proceed to next question.\n"
          + "Enter 2 to label as hard and proceed to next question.\n"
          + "Enter 3 to see answer.\n"
          + "Enter 4 to quit.";

      String question2Output =
          "What is the purpose of the CopyOnWriteArrayList class in Java?\n"
              + "Enter 1 to label as easy and proceed to next question.\n"
              + "Enter 2 to label as hard and proceed to next question.\n"
              + "Enter 3 to see answer.\n"
              + "Enter 4 to quit.";
      String question3Output =
          "What are vectors in Java?\n"
              + "Enter 1 to label as easy and proceed to next question.\n"
              + "Enter 2 to label as hard and proceed to next question.\n"
              + "Enter 3 to see answer.\n"
              + "Enter 4 to quit.";
      String statsOutput =
          "Practice session ended early.\n"
              + "\n"
              + "File updated successfully.\n"
              + "Thanks for practicing! Here are the statistics for your session:\n"
              + "Number of Questions Answered: 1\n"
              + "Number of Questions that changed from easy to hard in Bank: 0\n"
              + "Number of Questions that changed from hard to easy in Bank: 1\n"
              + "Number of Hard Questions in Bank: 2\n"
              + "Number of Easy Questions in Bank: 1\n"
              + "Goodbye!";

      assertTrue(
          printedOutput.contains(welcomeOutput) && (printedOutput.contains(question1Output)
              || printedOutput.contains(question2Output) || printedOutput.contains(question3Output))
              && printedOutput.contains(statsOutput));
    } catch (IOException e) {
      System.err.println("IO Error");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Test case for seeing answer to a question and setting its difficulty to easy.
   */
  @Test
  public void testAnswerSetEasy() {
    try {
      // Prepare input for the Scanner
      String input =
          "src/test/resources/ControllerTestFiles/studyGuideFilenameOrder.sr\n3\n3\n1\n4\n";

      // Redirect System.in to the ByteArrayInputStream with the input
      InputStream originalInput = System.in;
      System.setIn(new ByteArrayInputStream(input.getBytes()));

      // Redirect System.out to a ByteArrayOutputStream
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream printStream = new PrintStream(outputStream);
      PrintStream originalPrintStream = System.out;
      System.setOut(printStream);

      // Call the main method
      Driver.main(new String[0]);

      // Restore the original System.in and System.out
      System.setIn(originalInput);
      System.setOut(originalPrintStream);

      // Get the printed output
      String printedOutput = outputStream.toString();

      // Define the expected output
      String welcomeOutput =
          "Welcome to the Study Session!\n"
              + "Please enter a full path to a .sr file:\n"
              + "Please enter how many questions out of 3 you would like to answer: ";
      String question1Output = "What is an array in Java?\n"
          + "Enter 1 to label as easy and proceed to next question.\n"
          + "Enter 2 to label as hard and proceed to next question.\n"
          + "Enter 3 to see answer.\n"
          + "Enter 4 to quit.";

      String question2Output =
          "What is the purpose of the CopyOnWriteArrayList class in Java?\n"
              + "Enter 1 to label as easy and proceed to next question.\n"
              + "Enter 2 to label as hard and proceed to next question.\n"
              + "Enter 3 to see answer.\n"
              + "Enter 4 to quit.";
      String question3Output =
          "What are vectors in Java?\n"
              + "Enter 1 to label as easy and proceed to next question.\n"
              + "Enter 2 to label as hard and proceed to next question.\n"
              + "Enter 3 to see answer.\n"
              + "Enter 4 to quit.";
      String questionPt2Output = "Enter 1 to label as easy and proceed to next question.\n"
          + "Enter 2 to label as hard and proceed to next question.\n";
      String statsOutput =
          "Practice session ended early.\n"
              + "\n"
              + "File updated successfully.\n"
              + "Thanks for practicing! Here are the statistics for your session:\n"
              + "Number of Questions Answered: 1\n"
              + "Number of Questions that changed from easy to hard in Bank: 0\n"
              + "Number of Questions that changed from hard to easy in Bank: 1\n"
              + "Number of Hard Questions in Bank: 2\n"
              + "Number of Easy Questions in Bank: 1\n"
              + "Goodbye!";
      assertTrue(
          printedOutput.contains(welcomeOutput) && (printedOutput.contains(question1Output)
              || printedOutput.contains(question2Output) || printedOutput.contains(question3Output))
              && printedOutput.contains(statsOutput) && printedOutput.contains(questionPt2Output));
    } catch (IOException e) {
      System.err.println("IO Error");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Test case for seeing answer to a question and setting its difficulty to hard.
   */
  @Test
  public void testGetAnswerSetHard() {
    try {
      // Prepare input for the Scanner
      String input =
          "src/test/resources/ControllerTestFiles/studyGuideFilenameOrder.sr\n3\n3\n2\n4\n";

      // Redirect System.in to the ByteArrayInputStream with the input
      InputStream originalInput = System.in;
      System.setIn(new ByteArrayInputStream(input.getBytes()));

      // Redirect System.out to a ByteArrayOutputStream
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      PrintStream printStream = new PrintStream(outputStream);
      PrintStream originalPrintStream = System.out;
      System.setOut(printStream);

      // Call the main method
      Driver.main(new String[0]);

      // Restore the original System.in and System.out
      System.setIn(originalInput);
      System.setOut(originalPrintStream);

      // Get the printed output
      String printedOutput = outputStream.toString();

      // Define the expected output
      String welcomeOutput =
          "Welcome to the Study Session!\n"
              + "Please enter a full path to a .sr file:\n"
              + "Please enter how many questions out of 3 you would like to answer: ";
      String question1Output = "What is an array in Java?\n"
          + "Enter 1 to label as easy and proceed to next question.\n"
          + "Enter 2 to label as hard and proceed to next question.\n"
          + "Enter 3 to see answer.\n"
          + "Enter 4 to quit.";

      String question2Output =
          "What is the purpose of the CopyOnWriteArrayList class in Java?\n"
              + "Enter 1 to label as easy and proceed to next question.\n"
              + "Enter 2 to label as hard and proceed to next question.\n"
              + "Enter 3 to see answer.\n"
              + "Enter 4 to quit.";
      String question3Output =
          "What are vectors in Java?\n"
              + "Enter 1 to label as easy and proceed to next question.\n"
              + "Enter 2 to label as hard and proceed to next question.\n"
              + "Enter 3 to see answer.\n"
              + "Enter 4 to quit.";
      String questionPt2Output = "Enter 1 to label as easy and proceed to next question.\n"
          + "Enter 2 to label as hard and proceed to next question.\n";
      String statsOutput =
          "Practice session ended early.\n"
              + "\n"
              + "File updated successfully.\n"
              + "Thanks for practicing! Here are the statistics for your session:\n"
              + "Number of Questions Answered: 1\n"
              + "Number of Questions that changed from easy to hard in Bank: 0\n"
              + "Number of Questions that changed from hard to easy in Bank: 0\n"
              + "Number of Hard Questions in Bank: 3\n"
              + "Number of Easy Questions in Bank: 0\n"
              + "Goodbye!";

      assertTrue(
          printedOutput.contains(welcomeOutput) && (printedOutput.contains(question1Output)
              || printedOutput.contains(question2Output) || printedOutput.contains(question3Output))
              && printedOutput.contains(statsOutput) && printedOutput.contains(questionPt2Output));
    } catch (IOException e) {
      System.err.println("IO Error");
      e.printStackTrace();
      System.exit(1);
    }
  }

  /**
   * Test case for providing an invalid argument (out of range) for a question difficulty.
   */
  @Test
  public void testInvalidArgument1() {
    // Prepare input for the Scanner
    String input = "outputStudyGuides/studyGuideFilenameOrder.sr\n3\n3\n2\n5\n";

    // Redirect System.in to the ByteArrayInputStream with the input
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    // Redirect System.out to a ByteArrayOutputStream
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);

    // Call the main method
    assertThrows(IllegalArgumentException.class, () -> Driver.main(new String[0]));
  }

  /**
   * Test case for providing an invalid argument (non-numeric) for a question difficulty.
   */
  @Test
  public void testInvalidArgument() {
    // Prepare input for the Scanner
    String input = "outputStudyGuides/studyGuideFilenameOrder.sr\n3\n3\n3\n2\n";

    // Redirect System.in to the ByteArrayInputStream with the input
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    // Redirect System.out to a ByteArrayOutputStream
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setOut(printStream);

    // Call the main method
    assertThrows(IllegalArgumentException.class, () -> Driver.main(new String[0]));
  }
}