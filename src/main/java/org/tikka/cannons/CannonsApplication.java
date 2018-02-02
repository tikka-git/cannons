package org.tikka.cannons;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class CannonsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CannonsApplication.class, args);
	}

	@RequestMapping(value = "/cannons" , method = RequestMethod.GET)
	public String getCanon(){
		return "the cannon";
	}
}
