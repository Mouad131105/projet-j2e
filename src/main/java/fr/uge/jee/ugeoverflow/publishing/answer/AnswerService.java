package fr.uge.jee.ugeoverflow.publishing.answer;

import fr.uge.jee.ugeoverflow.publishing.answer.Answer;
import fr.uge.jee.ugeoverflow.publishing.answer.AnswerRepository;
import org.springframework.stereotype.Service;

import java.util.List;


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

    public Answer findAnswerById(Long idAnswer){
        return this.answerRepository.findAnswerById(idAnswer);
    }
}
