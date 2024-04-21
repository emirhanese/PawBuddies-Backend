package tr.edu.marmara.petcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PetcareApplication {

    public static void main(String[] args) {
        SpringApplication.run(PetcareApplication.class, args);
    }

}
