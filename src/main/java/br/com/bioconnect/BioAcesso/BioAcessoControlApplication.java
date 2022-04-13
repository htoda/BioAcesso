package br.com.bioconnect.BioAcesso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan("configuration.properties")
public class BioAcessoControlApplication {

	public static void main(String[] args) {
		
		try {
			SpringApplication.run(BioAcessoControlApplication.class, args);
	    } catch (Throwable e) {
	        if(e.getClass().getName().contains("SilentExitException")) {
	            System.out.println("Spring is restarting the main thread - See spring-boot-devtools");
	        } else {
	        	System.out.println("Application crashed: " + e.getMessage());
	        }
	    }
		
	}

}
