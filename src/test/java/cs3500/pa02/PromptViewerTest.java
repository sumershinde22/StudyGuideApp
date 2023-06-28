package cs3500.pa02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the PromptViewer class.
 */
class PromptViewerTest {
  PromptViewer promptViewer;

  @BeforeEach
  public void setup() {
    promptViewer = new PromptViewer();
  }

  /**
   * Test case for the showStats method.
   */
  @Test
  public void testShowStats() {
    // Redirect System.out to a ByteArrayOutputStream
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    PrintStream originalPrintStream = System.out;
    System.setOut(printStream);

    promptViewer.showStats(0, 0, 0, 0, 0);
    // Restore the original System.out
    System.setOut(originalPrintStream);

    // Get the printed output
    String printedOutput = outputStream.toString();

    // Define the expected output
    String statsOutput =
        "Thanks for practicing! Here are the statistics for your session:\n"
            + "Number of Questions Answered: 0\n"
            + "Number of Questions that changed from easy to hard in Bank: 0\n"
            + "Number of Questions that changed from hard to easy in Bank: 0\n"
            + "Number of Hard Questions in Bank: 0\n"
            + "Number of Easy Questions in Bank: 0\n"
            + "Goodbye!\n";

    // Assert that the printed output matches the expected output
    assertEquals(statsOutput, printedOutput);
  }
}
