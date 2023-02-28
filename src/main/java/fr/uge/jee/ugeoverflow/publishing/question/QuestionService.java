package fr.uge.jee.ugeoverflow.publishing.question;

import fr.uge.jee.ugeoverflow.user.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
}
