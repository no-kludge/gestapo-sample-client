package com.apple;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.apple.controller.ClientController;

@SpringBootApplication

public class ApplicationConfiguration implements CommandLineRunner {

	public static void main(String[] args) {
		 SpringApplication.run(ApplicationConfiguration.class);
	}
	
	@Override
    public void run(String... strings) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		ClientController clientController = ctx.getBean("client", ClientController.class);
		
		System.out.println(clientController.lookup());
		
		System.exit(9);
	}

}

