package WebProject.withpet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class WithPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(WithPetApplication.class, args);
	}

}
