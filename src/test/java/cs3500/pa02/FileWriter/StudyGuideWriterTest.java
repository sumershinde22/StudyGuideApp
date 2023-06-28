package cs3500.pa02.FileWriter;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import org.junit.jupiter.api.Test;

/**
 * Test the StudyGuideWriter class
 */
//From PA01
public class StudyGuideWriterTest {
  /**
   * Tests for correct response to an invalid output path
   */
  @Test
  public void testInvalidOutputPath() {
    String studyGuidePath1 =
        "sampleMdFiles/studyGuide";
    StudyGuideWriter writer = new StudyGuideWriter(null);
    assertThrows(IllegalArgumentException.class, () -> writer.write(
        new File(studyGuidePath1)));
    String studyGuidePath2 =
        "sampleMdFiles/";
    assertThrows(IllegalArgumentException.class, () -> writer.write(
        new File(studyGuidePath2)));
  }
}
