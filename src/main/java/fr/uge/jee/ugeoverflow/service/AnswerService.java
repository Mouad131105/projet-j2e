package fr.uge.jee.ugeoverflow.service;

import fr.uge.jee.ugeoverflow.entities.Answer;
import fr.uge.jee.ugeoverflow.repository.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnswerService {
    AnswerRepository answerRepository;
    public AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public Answer save(Answer answer) {
        return this.answerRepository.save(answer);
    }

    public List<Answer> findAllByParentQuestionId(Long parentQuestionId){
        return this.answerRepository.findAllByParentQuestionId(parentQuestionId);
    }

    public Optional<Answer> findById(Long idAnswer){
        return this.answerRepository.findById(idAnswer);
    }
}
