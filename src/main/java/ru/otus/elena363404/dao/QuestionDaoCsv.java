package ru.otus.elena363404.dao;

import ru.otus.elena363404.domain.Answer;
import ru.otus.elena363404.domain.Option;
import ru.otus.elena363404.domain.Question;
import ru.otus.elena363404.exception.QuestionReadingException;
import java.io.InputStream;
import java.util.*;

public class QuestionDaoCsv implements QuestionDao {

  private final String quizPath;

  public QuestionDaoCsv(String quizPath) {
    this.quizPath = quizPath;
  }

  public List<Question> getAllQuestions() throws QuestionReadingException {

    List<Question> listQuestion = new ArrayList<>();

    try (InputStream is = getClass().getClassLoader().getResourceAsStream(quizPath)) {

      Scanner scanner = new Scanner(is);
      scanner.useDelimiter(",|\\n");
      scanner.nextLine();
      while (scanner.hasNext()) {
        int num = scanner.nextInt();
        String question = scanner.next();
        List<String> options = Arrays.asList(scanner.next().split("/"));
        List<Option> listOptions = new ArrayList<>();
        int idOption = 0;
        for (String option: options) {
          idOption = idOption + 1;
          listOptions.add(new Option(idOption, num, option));
        }

        listQuestion.add(new Question(num, question, listOptions, new Answer(num,
          Integer.parseInt(scanner.next()))));
      }
    } catch (Exception err) {
      throw new QuestionReadingException(err.toString());
    }

    return listQuestion;
  }
}
