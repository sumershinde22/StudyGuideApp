package cs3500.pa02;



import static org.junit.jupiter.api.Assertions.assertTrue;

import cs3500.pa02.FileReader.SrFileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the QuestionData class.
 */
class QuestionDataTest {
  SrFileReader srFileReader;
  ArrayList<QuestionData> questionData;

  @BeforeEach
  public void setup() {
    srFileReader =
        new SrFileReader(Path.of("outputStudyGuides/studyGuideFilenameOrder.sr"));
    questionData = srFileReader.createQuestions();
  }

  /**
   * Test case for the setIsEasy method.
   */
  @Test
  public void testSetIsEasy() {
    QuestionData testQuestion = questionData.get(0);
    testQuestion.setIsEasy(true);
    assertTrue(testQuestion.isEasy);
  }
}