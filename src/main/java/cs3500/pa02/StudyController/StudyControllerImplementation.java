package cs3500.pa02.StudyController;

import cs3500.pa02.FileReader.SrFileReader;
import cs3500.pa02.PromptViewer;
import cs3500.pa02.QuestionBank;
import cs3500.pa02.QuestionData;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The StudyControllerImplementation class implements the StudyController interface to handle
 * running the study session.
 */
public class StudyControllerImplementation implements StudyController {
  private final PromptViewer promptViewer;

  /**
   * Constructs a StudyControllerImplementation object.
   */
  public StudyControllerImplementation() {
    promptViewer = new PromptViewer();
  }

  /**
   * Handles running the study session.
   */
  @Override
  public void run() {
    String path = promptViewer.welcomeUser();
    SrFileReader srFileReader = new SrFileReader(Path.of(path));
    ArrayList<QuestionData> questionData = srFileReader.createQuestions();
    QuestionBank questionBank = new QuestionBank(questionData);
    int numRequestedQuestions = promptViewer.getNumPracticeQuestions(questionData.size());
    ArrayList<QuestionData> practiceQuestions =
        questionBank.generatePracticeSessionSet(numRequestedQuestions);
    int numQuestions = 0;
    boolean shouldBreak = false;
    for (QuestionData question : practiceQuestions) {
      int response = promptViewer.askQuestion(question);
      promptViewer.handleQuestionResponse(question, response);
      if (response == 4) {
        shouldBreak = true;
        break;
      }
      numQuestions++;
    }
    if (shouldBreak) {
      System.out.println("Practice session ended early.");
    }
    for (QuestionData question : practiceQuestions) {
      for (QuestionData initialQuestionData : questionData) {
        if (Objects.equals(question.question, initialQuestionData.question)) {
          initialQuestionData.setIsEasy(question.isEasy);
        }
      }
    }
    int[] countArray =
        questionBank.updateSrFile(Path.of(path));
    promptViewer.showStats(numQuestions, countArray[0], countArray[1], questionBank.getNumHard(),
        questionBank.getNumEasy());
  }

}
