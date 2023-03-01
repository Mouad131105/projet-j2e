package fr.uge.jee.ugeoverflow;
import com.github.javafaker.Faker;
import fr.uge.jee.ugeoverflow.entities.Question;
import fr.uge.jee.ugeoverflow.entities.Tag;
import fr.uge.jee.ugeoverflow.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Set;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(QuestionRepository questionRepository){
		return args -> {
			Faker faker = new Faker();
			for (int i = 0 ;i<50;i++){
				Question question = new Question();
				question.setUserName(faker.name().firstName());
				question.setTopic(faker.lorem().words(7).toString());
				question.setTags(Set.of(Tag.JAVA));
				questionRepository.save(question);
			}
		};
	}
}