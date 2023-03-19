package fr.uge.jee.ugeoverflow.publishing.answer;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public void deleteAnswerById(Long idAnswer){
        this.answerRepository.deleteAnswerById(idAnswer);
    }
}
