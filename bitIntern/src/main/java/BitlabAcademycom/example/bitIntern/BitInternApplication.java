package BitlabAcademycom.example.bitIntern;

import BitlabAcademycom.example.bitIntern.dto.CourseDto;
import BitlabAcademycom.example.bitIntern.exception.ErrorMessage;
import BitlabAcademycom.example.bitIntern.exception.GlobalExceptionHandler;
import BitlabAcademycom.example.bitIntern.repository.CourseRepository;
import BitlabAcademycom.example.bitIntern.service.CourseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BitInternApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitInternApplication.class, args);
	}

}