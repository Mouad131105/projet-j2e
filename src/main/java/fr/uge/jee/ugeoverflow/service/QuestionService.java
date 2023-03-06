package fr.uge.jee.ugeoverflow.service;

import fr.uge.jee.ugeoverflow.entities.Question;
import fr.uge.jee.ugeoverflow.repository.QuestionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {
    QuestionRepository questionRepository;
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    public Question save(Question question) {
        return this.questionRepository.save(question);
    }
    public List<Question> getAllQuestions(){
        return this.questionRepository.findAll();
    }
    public Page<Question> findAll(int page, int size){
        return questionRepository.findAll(PageRequest.of(page, size));
    }
    public Page<Question> findByTopicContains(String keyword, PageRequest of){
        return questionRepository.findByTopicContains(keyword,of);
    }

    public Question findQuestionById(Long id) {
        return this.questionRepository.findQuestionById(id);
    }
}
