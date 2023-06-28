package cs3500.pa02.FileReader;



import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The FilesReaderTest class contains unit tests for the FilesReader class.
 */
class FilesReaderTest {

  @BeforeEach
  public void setup() {
    // nothing to set up
  }

  /**
   * Tests the readFileContent method of the FilesReader class.
   */
  @Test
  public void testReadFileContents() {
    String file1Content =
        String.valueOf(
            FilesReader.readFileContent(Path.of("src/test/resources/studyGuideCreated.md")));
    String file2Content = String.valueOf(
        FilesReader.readFileContent(Path.of("outputStudyGuides/studyGuideCreatedOrder.md")));
    assertEquals(file1Content, file2Content);
  }
}