package cs3500.pa02;

import cs3500.pa02.FileReader.FilesReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

/**
 * The QuestionBank class represents a collection of QuestionData objects.
 * It provides functionality for generating a practice session set, updating an .sr file,
 * and retrieving the number of hard and easy questions in the bank.
 */
public class QuestionBank {
  private final ArrayList<QuestionData> questionData;
  private static final String OUTPUT_FILE_EXISTS_MESSAGE = "\nFile updated successfully.";

  /**
   * Constructs a QuestionBank object with the specified question data.
   *
   * @param questionData the list of QuestionData objects representing the questions in the bank
   */
  public QuestionBank(ArrayList<QuestionData> questionData) {
    this.questionData = questionData;
  }

  /**
   * Generates a practice session set with the specified number of questions.
   * The questions are randomly selected from the bank and sorted from hard to easy.
   *
   * @param numQuestions the number of questions to include in the practice session set
   * @return an ArrayList of QuestionData objects representing the practice session set
   */
  public ArrayList<QuestionData> generatePracticeSessionSet(int numQuestions) {
    ArrayList<QuestionData> sessionQuestionData = new ArrayList<>();
    Collections.shuffle(questionData);
    for (int i = 0; i < numQuestions; i++) {
      QuestionData selectedItem = questionData.get(i);
      sessionQuestionData.add(selectedItem);
    }
    Comparator<QuestionData> comparator =
        Comparator.comparing(questionData -> questionData.isEasy);
    sessionQuestionData.sort(comparator);

    return sessionQuestionData;
  }

  /**
   * Returns the number of hard questions in the question bank.
   *
   * @return the number of hard questions
   */
  public int getNumHard() {
    int numHardQuestions = 0;
    for (QuestionData question : questionData) {
      if (!question.isEasy) {
        numHardQuestions++;
      }
    }
    return numHardQuestions;
  }

  /**
   * Returns the number of easy questions in the question bank.
   *
   * @return the number of easy questions
   */
  public int getNumEasy() {
    int numEasyQuestions = 0;
    for (QuestionData question : questionData) {
      if (question.isEasy) {
        numEasyQuestions++;
      }
    }
    return numEasyQuestions;
  }

  /**
   * Updates the .sr file at the specified path with the current question data in the bank.
   * It also returns an array containing the number of questions that changed from easy to hard
   * and the number of questions that changed from hard to easy.
   *
   * @param path the path to the .sr file to be updated
   * @return an array with the number of easy-to-hard and hard-to-easy question changes
   */
  public int[] updateSrFile(Path path) {
    ArrayList<String> lines = FilesReader.readFileContent(path);
    ArrayList<String> questionDifficulty = new ArrayList<>();
    File fileOfPath = new File(path.toUri());
    for (String line : lines) {
      int length = line.length();
      String lastFourCharacters = line.substring(length - 4);
      questionDifficulty.add(lastFourCharacters);
    }
    int[] outputArray = new int[2];
    int easyToHard = 0;
    int hardToEasy = 0;
    try {
      if (fileOfPath.exists()) {
        fileOfPath.delete();
      }
      fileOfPath.createNewFile();
      try (PrintWriter writer = new PrintWriter(new FileWriter(fileOfPath))) {
        int countIndex = 0;
        for (QuestionData question : questionData) {
          if (Objects.equals(questionDifficulty.get(countIndex), "easy") && !question.isEasy) {
            easyToHard++;
          } else if (Objects.equals(questionDifficulty.get(countIndex), "hard")
              && question.isEasy) {
            hardToEasy++;
          }
          if (question.isEasy) {
            writer.println(question.question + ":::" + question.answer + ":::easy");
          } else {
            writer.println(question.question + ":::" + question.answer + ":::hard");
          }
        }
        System.out.println(OUTPUT_FILE_EXISTS_MESSAGE);
      }
    } catch (IOException e) {
      System.err.println("Problem with file");
      System.exit(1);
    }
    outputArray[0] = easyToHard;
    outputArray[1] = hardToEasy;
    return outputArray;
  }
}
