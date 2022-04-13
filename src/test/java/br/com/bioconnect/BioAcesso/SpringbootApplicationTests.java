package br.com.bioconnect.BioAcesso;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringbootApplicationTests {
	
	@Test
	void httpClientExample() throws URISyntaxException {
		System.out.println("Aqui!!!!!!!!!!!!!!!!!!!!!!!!!");
		    // no need for this
		HttpRequest request = HttpRequest.newBuilder()
				  .uri(new URI("https://postman-echo.com/get"))
				  .GET()
				  .build();
		    
	}

}
