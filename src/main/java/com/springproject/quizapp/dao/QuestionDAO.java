package com.springproject.quizapp.dao;

import com.springproject.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


// We have to mention the table name and the type of primary key of that table for JpaRepository
// Since JPA of spring is smart enough to know that category is part of the table, otherwise you have to use Hibernate QL or JPQL
@Repository
public interface QuestionDAO extends JpaRepository<Question, Integer> {

    public List<Question> findQuestionByCategory(String category);

    @Query(value = "SELECT * FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numOfQuest", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numOfQuest);
}
