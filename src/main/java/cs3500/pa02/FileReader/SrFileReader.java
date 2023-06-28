package cs3500.pa02.FileReader;

import cs3500.pa02.QuestionData;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The SrFileReader class reads and processes .sr files to create instances of QuestionData.
 * It extends FilesReader.
 */
public class SrFileReader extends FilesReader {
  private final Path srFilePath;

  /**
   * Constructs a SrFileReader object.
   */
  public SrFileReader(Path srFilePath) {
    this.srFilePath = srFilePath;
  }

  /**
   * Reads the contents of the .sr file, processes each line, and creates a collection of
   * QuestionData objects.
   *
   * @return an ArrayList of QuestionData objects representing the questions read from the .sr file
   */
  public ArrayList<QuestionData> createQuestions() {
    ArrayList<QuestionData> questionDataCollection = new ArrayList<>();
    ArrayList<String> srLines = readFileContent(srFilePath);
    for (String line : srLines) {
      String[] parts = line.split(":::");
      String question = parts[0];
      String answer = parts[1];
      String difficulty = parts[2];
      boolean isEasy = false;
      if (Objects.equals(difficulty, "hard")) {
        isEasy = false;
      } else if (Objects.equals(difficulty, "easy")) {
        isEasy = true;
      }
      QuestionData questionData = new QuestionData(question, answer, isEasy);
      questionDataCollection.add(questionData);
    }
    return questionDataCollection;
  }
}
