package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//From PA01

/**
 * Test the MdFileCollectionTest class
 */
public class MdFileCollectionTest {
  /**
   * Test for an invalid ordering flag as a command line parameter
   */
  @Test
  public void testInvalidOrderingFlag() {
    String mdRootDirectory =
        "/Users/sumershinde/Desktop/Sumer/College/Summer/CS3500/pa01-sumershinde22/sampleMdFiles";
    String orderingFlag = "null";
    String studyGuidePath =
        "/Users/sumershinde/Desktop/Sumer/College/Summer/CS3500/pa01-sumershinde22/sampleMdFiles/"
            + "studyGuide.md";
    String[] mainArray = {mdRootDirectory, orderingFlag, studyGuidePath};
    assertThrows(IllegalArgumentException.class, () -> Driver.main(mainArray));
  }
}
