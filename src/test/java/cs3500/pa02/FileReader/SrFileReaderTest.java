package cs3500.pa02.FileReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa02.QuestionData;
import java.nio.file.Path;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The SrFileReaderTest class contains unit tests for the SrFileReader class.
 */
class SrFileReaderTest {
  @BeforeEach
  public void setup() {
    // No setup required for this test class
  }

  /**
   * Tests the createQuestions method of the SrFileReader class.
   */
  @Test
  public void testSrFileReader() {
    SrFileReader srFileReader = new SrFileReader(
        Path.of("outputStudyGuides/studyGuideFilenameOrder.sr"));
    QuestionData question1 =
        new QuestionData("What is the purpose of the CopyOnWriteArrayList class in Java?",
            "The CopyOnWriteArrayList class in Java is an enhanced version of ArrayList.",
            false);
    QuestionData question2 = new QuestionData("What is an array in Java?",
        "An array in Java is a collection of variables of the same type, referred to by"
            + " a common name.",
        false);
    QuestionData question3 =
        new QuestionData("What are vectors in Java?", "Vectors in Java act like"
            + "resizable arrays.",
            false);
    ArrayList<QuestionData> questionDataArrayList2 = new ArrayList<>();
    final ArrayList<QuestionData> questionDataArrayList = srFileReader.createQuestions();

    questionDataArrayList2.add(question1);
    questionDataArrayList2.add(question2);
    questionDataArrayList2.add(question3);

    for (int i = 0; i < questionDataArrayList.size(); i++) {
      assertEquals(questionDataArrayList.get(i).question, questionDataArrayList2.get(i).question);
    }
  }
}
