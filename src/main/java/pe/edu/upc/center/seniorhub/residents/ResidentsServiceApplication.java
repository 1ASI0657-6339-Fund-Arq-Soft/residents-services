package pe.edu.upc.center.seniorhub.residents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ResidentsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResidentsServiceApplication.class, args);
    }

}