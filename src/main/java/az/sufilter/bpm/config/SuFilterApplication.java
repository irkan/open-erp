package az.sufilter.bpm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@Configuration
@EnableScheduling
@ComponentScan(basePackages = "az.sufilter.bpm")
@EntityScan("az.sufilter.bpm.entity")
@EnableJpaRepositories("az.sufilter.bpm.repository")
public class SuFilterApplication {
	public static void main(String[] args) {
		SpringApplication.run(SuFilterApplication.class, args);
	}

}
