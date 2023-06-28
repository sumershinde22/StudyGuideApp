# StudyGuideApp
Tool to use for 

[Spaced repetition](https://en.wikipedia.org/wiki/Spaced_repetition) is a learning technique that involves reviewing information at increasing intervals over time. Rather than cramming all your studying into one session, spaced repetition distributes learning and review sessions strategically to optimize long-term memory retention. 

The concept of spaced repetition revolves around the spacing effect, which suggests that information is better retained when it is revisited at specific intervals. When we encounter new information, our brains create memories that are initially fragile and prone to forgetting. However, by reviewing the material at appropriate intervals, spaced repetition strengthens the memory traces and reinforces the neural connections associated with the learned content. This process enhances long-term retention and recall.

Incorporating spaced repetition into a studying routine offers several advantages. Firstly, it helps combat the forgetting curve, a phenomenon that shows how memory declines over time without reinforcement. By strategically spacing out your review sessions, spaced repetition helps counteract this natural forgetting process, enabling you to retain information for longer periods. Additionally, spaced repetition promotes efficient learning by reducing the time required to review and revise material. It allows you to focus on the topics that need more reinforcement while spending less time on information that is already well-remembered. This targeted approach to studying can save you time and improve overall comprehension.

In this project, which is an extension of the MarkdownSummarizer project, I create a console app, which allows for the user to have an interactive study guide session. The following is the normal flow of the study guide:
1. The user provides a path to a `.sr` file that contains a set of questions and their associated metadata. We’ll call this the “question bank”. 
2. The user indicates how many questions they would like to be presented in this study session. If the total number of questions in the question bank is smaller than the total number of questions the user wants to practice, they will be quizzed on all the questions in the file. 
3. The app selects a set of questions from the question bank file for this study session.  The app will first show all questions labeled as **hard** in random order.  Then, questions currently labeled as easy will be shown in random order. In other words, the hard questions will randomly be shown first followed by a random selection of easy questions.  
4. Once the questions are selected, each question is shown to the user.  The user will be able to mark each question as hard or easy, meaning that it was hard to think of the answer (or the user couldn’t actually think of it) or thinking of the answer was easy.  The model should be updated to reflect choices made by the user. 
5. At the end of the study session, the app should show the user some stats including:
    1. the total number of questions answered for that session, 
    2. the total number of questions that changed from easy to hard, 
    3. the total number of questions that changed from hard to easy, 
    4. the updated total number of hard questions in the question bank, and
    5. the updated total number of easy questions in the question bank.

