package cs3500.pa02;


import static org.junit.jupiter.api.Assertions.assertEquals;

import cs3500.pa02.FileReader.SrFileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the QuestionBank class.
 */
class QuestionBankTest {
  SrFileReader srFileReader;
  ArrayList<QuestionData> questionData;

  @BeforeEach
  public void setup() {
    srFileReader =
        new SrFileReader(Path.of("outputStudyGuides/studyGuideFilenameOrder.sr"));
    questionData = srFileReader.createQuestions();
  }

  /**
   * Test case for the generatePracticeSessionSet method.
   */
  @Test
  public void testGeneratePracticeSessionSet() {
    QuestionBank questionBank = new QuestionBank(questionData);
    ArrayList<QuestionData> selectedQuestions = questionBank.generatePracticeSessionSet(3);
    assertEquals(3, selectedQuestions.size());
  }
}