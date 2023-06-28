package cs3500.pa02;

import java.util.Scanner;

/**
 * The PromptViewer class is responsible for displaying prompts and handling user input during the
 * study session.
 */
public class PromptViewer {

  private final Scanner sc;

  /**
   * Constructs a PromptViewer object.
   */
  public PromptViewer() {
    sc = new Scanner(System.in);
  }

  /**
   * Displays a welcome message to the user and prompts for the path to a .sr file.
   *
   * @return the full path to the .sr file entered by the user
   */
  public String welcomeUser() {
    System.out.println("Welcome to the Study Session!");
    System.out.println("Please enter a full path to a .sr file:");
    return sc.nextLine();
  }

  /**
   * Prompts the user for the number of questions they would like to answer in the practice session.
   *
   * @param size the total number of questions available
   * @return the number of questions the user wants to answer
   */
  public int getNumPracticeQuestions(int size) {
    System.out.printf("Please enter how many questions out of %d you would like to answer: ",
        size);
    return sc.nextInt();
  }

  /**
   * Displays a question to the user and prompts for a response.
   *
   * @param question the QuestionData object representing the question
   * @return the user's response (1, 2, 3, or 4)
   */
  public int askQuestion(QuestionData question) {
    System.out.println("\n" + question.question);
    System.out.println("Enter 1 to label as easy and proceed to next question.");
    System.out.println("Enter 2 to label as hard and proceed to next question.");
    System.out.println("Enter 3 to see answer.");
    System.out.println("Enter 4 to quit.");
    return sc.nextInt();
  }

  /**
   * Handles the user's response to a question.
   *
   * @param question the QuestionData object representing the question
   * @param response the user's response
   */
  public void handleQuestionResponse(QuestionData question, int response) {
    if (response == 1) {
      question.setIsEasy(true);
    } else if (response == 2) {
      question.setIsEasy(false);
    } else if (response == 3) {
      System.out.println(question.answer);
      int response2 = askQuestionPart2(sc);
      handleQuestionPart2Response(question, response2);
    } else if (response != 4) {
      throw new IllegalArgumentException("Please only enter numbers between 1-4.");
    }
  }

  private int askQuestionPart2(Scanner sc) {
    System.out.println("Enter 1 to label as easy and proceed to next question.");
    System.out.println("Enter 2 to label as hard and proceed to next question.");
    return sc.nextInt();
  }

  private void handleQuestionPart2Response(QuestionData question, int response) {
    if (response == 1) {
      question.setIsEasy(true);
    } else if (response == 2) {
      question.setIsEasy(false);
    } else {
      throw new IllegalArgumentException("Please only enter numbers between 1-2.");
    }
  }

  /**
   * Displays the statistics for the study session.
   *
   * @param numQuestions the number of questions answered
   * @param easyToHard   the number of questions that changed from easy to hard in the question bank
   * @param hardToEasy   the number of questions that changed from hard to easy in the question bank
   * @param numHard      the number of hard questions in the question bank
   * @param numEasy      the number of easy questions in the question bank
   */
  public void showStats(int numQuestions, int easyToHard, int hardToEasy, int numHard,
                        int numEasy) {
    System.out.println("Thanks for practicing! Here are the statistics for your session:");
    System.out.printf("Number of Questions Answered: %d", numQuestions);
    System.out.printf("\nNumber of Questions that changed from easy to hard in Bank: %d",
        easyToHard);
    System.out.printf("\nNumber of Questions that changed from hard to easy in Bank: %d",
        hardToEasy);
    System.out.printf("\nNumber of Hard Questions in Bank: %d", numHard);
    System.out.printf("\nNumber of Easy Questions in Bank: %d", numEasy);
    System.out.println("\nGoodbye!");
  }

}
