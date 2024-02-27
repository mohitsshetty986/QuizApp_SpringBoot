package com.springproject.quizapp.dao;

import com.springproject.quizapp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDao  extends JpaRepository<Quiz, Integer> {

}
