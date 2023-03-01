package fr.uge.jee.ugeoverflow.controller;

import fr.uge.jee.ugeoverflow.entities.Question;
import fr.uge.jee.ugeoverflow.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class QuestionController {
    QuestionRepository questionRepository;

    @GetMapping("/questions")
    public String questions(Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<Question> questions = questionRepository.findAll(PageRequest.of(page, 10));
        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "questions";

    }

    @PostMapping("/search")
    public String searchQuestion(Model model,
                                 @RequestParam(name = "page", defaultValue = "0") int page,
                                 @RequestParam(name = "keyword", defaultValue = "") String keyword){
        Page<Question> questions = questionRepository.findByTopicContains(keyword,PageRequest.of(page, 10));
        model.addAttribute("listQuestions", questions.getContent());
        model.addAttribute("pages", new int[questions.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "questions";
    }
}
