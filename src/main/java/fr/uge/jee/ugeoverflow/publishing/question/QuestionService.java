package fr.uge.jee.ugeoverflow.publishing.question;

import fr.uge.jee.ugeoverflow.publishing.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public Page<Question> findByTag(String tag, PageRequest of){ return questionRepository.findByTagsContaining(Set.of(Tag.valueOf(tag)),of);}
    public Question findQuestionById(Long id) {
        return this.questionRepository.findQuestionById(id);
    }
}