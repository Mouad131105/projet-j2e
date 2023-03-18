package fr.uge.jee.ugeoverflow.publishing.question;

import fr.uge.jee.ugeoverflow.publishing.Tag;
import fr.uge.jee.ugeoverflow.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Question> getQuestionsByUser(User user) {
        return questionRepository.findByAuthor(user);
    }
    public List<Question> getSortedQuestionsByFollowing(User loggedUser) {
        List<User> following = new ArrayList<>(loggedUser.getFollowedUsers());
        System.out.println("Followed user:  " + following.size());
        List<Question> sortedQuestions = new ArrayList<>();

        for (User user : following) {
            sortedQuestions.addAll(getQuestionsByUser(user));
        }
        for (User user : following) {
            for (User followedUser : user.getFollowedUsers()) {
                sortedQuestions.addAll(getQuestionsByUser(followedUser));
            }
        }
        return sortedQuestions;
    }
    public List<Question> getQuestionsByUser(User user, String keyword) {
        return questionRepository.findByAuthorAndTopicContains(user, keyword);
    }
    public List<Question> getSortedQuestionsByFollowing(User loggedUser, String keyword) {
        List<User> following = new ArrayList<>(loggedUser.getFollowedUsers());
        List<Question> sortedQuestions = new ArrayList<>();

        for (User user : following) {
            sortedQuestions.addAll(getQuestionsByUser(user, keyword));
        }
        for (User user : following) {
            for (User followedUser : user.getFollowedUsers()) {
                sortedQuestions.addAll(getQuestionsByUser(followedUser, keyword));
            }
        }
        return sortedQuestions;
    }
    public List<Question> getQuestionsByUser(User user, Tag tag) {
        return questionRepository.findByAuthorAndTagsContaining(user, Set.of(tag));
    }
    public List<Question> getSortedQuestionsByFollowing(User loggedUser, Tag tag) {
        List<User> following = new ArrayList<>(loggedUser.getFollowedUsers());
        List<Question> sortedQuestions = new ArrayList<>();

        for (User user : following) {
            sortedQuestions.addAll(getQuestionsByUser(user, tag));
        }
        for (User user : following) {
            for (User followedUser : user.getFollowedUsers()) {
                sortedQuestions.addAll(getQuestionsByUser(followedUser, tag));
            }
        }
        return sortedQuestions;
    }

    public Question findQuestionById(Long id) {
        return this.questionRepository.findQuestionById(id);
    }
}
