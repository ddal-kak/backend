package ddalkak.prize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
public class PrizeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrizeApplication.class, args);
	}

}
