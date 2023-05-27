package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
        He he = new He();
        he.setData("hello");
        System.out.println(he.getData());

		SpringApplication.run(JpashopApplication.class, args);
	}

}
