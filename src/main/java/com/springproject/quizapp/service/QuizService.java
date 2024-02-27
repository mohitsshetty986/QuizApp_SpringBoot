package com.springproject.quizapp.service;

import com.springproject.quizapp.dao.QuestionDAO;
import com.springproject.quizapp.dao.QuizDao;
import com.springproject.quizapp.model.Question;
import com.springproject.quizapp.model.QuestionWrapper;
import com.springproject.quizapp.model.Quiz;
import com.springproject.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDAO questionDAO;

    public ResponseEntity<String> createQuiz(String category, int numOfQuest, String title) {

        try {
            List<Question> questionList = questionDAO.findRandomQuestionsByCategory(category, numOfQuest);

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questionList);

            quizDao.save(quiz);

            return new ResponseEntity<>("Success!! created the quiz.", HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failed to create quiz.", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestionsById(int id) {

        try {
            Optional<Quiz> quiz = quizDao.findById(id);

            List<Question> questionsFromQuiz = quiz.get().getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();
            for (Question q: questionsFromQuiz) {
                QuestionWrapper questionWrapper = new QuestionWrapper();
                questionWrapper.setId(q.getId());
                questionWrapper.setQuestionTitle(q.getQuestionTitle());
                questionWrapper.setOption1(q.getOption1());
                questionWrapper.setOption2(q.getOption2());
                questionWrapper.setOption3(q.getOption3());
                questionWrapper.setOption4(q.getOption4());
                questionsForUser.add(questionWrapper);
            }

            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<Integer> calculateScores(Integer id, List<Response> responses) {
        try {
            Optional<Quiz> quiz = quizDao.findById(id);

            List<Question> questionList = quiz.get().getQuestions();

            System.out.println("questions:" + questionList);
            int score = 0;
            int i = 0;
            for (Response response : responses) {
                if (response.getResponse().equals(questionList.get(i).getRightAnswer())) {
                    score++;
                }
                i++;
            }
            return new ResponseEntity<>(score, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);
    }
}
