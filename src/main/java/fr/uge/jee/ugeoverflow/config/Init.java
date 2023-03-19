package fr.uge.jee.ugeoverflow.config;

import fr.uge.jee.ugeoverflow.publishing.Tag;
import fr.uge.jee.ugeoverflow.publishing.question.Question;
import fr.uge.jee.ugeoverflow.publishing.question.QuestionService;
import fr.uge.jee.ugeoverflow.user.Role;
import fr.uge.jee.ugeoverflow.user.User;
import fr.uge.jee.ugeoverflow.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class Init {
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    private QuestionService questionService;

    @Value("${admin.username}")
    private String username;

    @Value("${admin.password}")
    private String password;

    @Value("${admin.email}")
    private String email;

    public Init(PasswordEncoder passwordEncoder, UserService userService, QuestionService questionService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.questionService = questionService;
    }

    @PostConstruct
    public void init() {
        User admin = new User(
                this.username,
                this.passwordEncoder.encode(this.password),
                this.email,
                Role.ADMIN,
                Collections.emptySet()
        );
        this.userService.save(admin);
        User user1 = new User();
        user1.setUsername("Michel");
        user1.setEmail("michel@gmail.com");
        user1.setPassword(passwordEncoder.encode("Michel123!"));
        user1.setRole(Role.AUTHENTIFIED);
        User user2 = new User();
        user2.setUsername("Dorian");
        user2.setEmail("dorian@gmail.com");
        user2.setPassword(passwordEncoder.encode("Dorian123!"));
        user2.setRole(Role.AUTHENTIFIED);
        User user3 = new User();
        user3.setUsername("Robert");
        user3.setEmail("robert@gmail.com");
        user3.setPassword(passwordEncoder.encode("Robert123!"));
        user3.setRole(Role.AUTHENTIFIED);
        User user4 = new User();
        user4.setUsername("Louise");
        user4.setEmail("louise@gmail.com");
        user4.setPassword(passwordEncoder.encode("Louise123!"));
        user4.setRole(Role.AUTHENTIFIED);
        User user8 = new User();
        user8.setUsername("Arnaud");
        user8.setEmail("arnaud@gmail.com");
        user8.setPassword(passwordEncoder.encode("arnaud"));
        user8.setRole(Role.AUTHENTIFIED);

        List<User> predefinedUsers = Arrays.asList(user1, user2, user3, user4, user8);
        this.userService.saveAll(predefinedUsers);


        List<User> users = userService.getAllUsers();
        Random random = new Random();

        Map<Tag, String[]> topicSuggestions = new HashMap<>();
        topicSuggestions.put(Tag.JAVA, new String[]{"Java Basics", "Java OOP", "Java Collections"});
        topicSuggestions.put(Tag.CONCURRENCY, new String[]{"Multithreading", "Synchronization", "Locks and Semaphores"});
        topicSuggestions.put(Tag.SPRING, new String[]{"Spring Boot", "Spring Security", "Spring Data"});
        topicSuggestions.put(Tag.POSTGRESQL, new String[]{"PostgreSQL Queries", "PostgreSQL Optimization", "PostgreSQL Administration"});

        for (int i = 1; i <= 15; i++) {
            User randomUser = users.get(random.nextInt(users.size()));
            Set<Tag> tags = new HashSet<>();
            tags.add(Tag.values()[random.nextInt(Tag.values().length)]);
            tags.add(Tag.values()[random.nextInt(Tag.values().length)]);

            StringBuilder topicBuilder = new StringBuilder();
            StringBuilder contentBuilder = new StringBuilder();

            for (Tag tag : tags) {
                String[] suggestions = topicSuggestions.get(tag);
                String suggestedTopic = suggestions[random.nextInt(suggestions.length)];
                topicBuilder.append(suggestedTopic).append(" / ");
                contentBuilder.append("Contenu relatif à ").append(suggestedTopic).append(". ");
            }

            String topic = topicBuilder.toString().trim();
            String content = contentBuilder.toString().trim();

            Question question = new Question();
            question.setAuthor(randomUser);
            question.setTopic(topic);
            question.setContent(content);
            question.setTags(tags);

            questionService.save(question);
        }
    }
}
