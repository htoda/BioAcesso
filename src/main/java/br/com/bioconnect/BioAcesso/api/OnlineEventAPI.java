package br.com.bioconnect.BioAcesso.api;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

import br.com.bioconnect.BioAcesso.configuration.ServerConfig;
import br.com.bioconnect.BioAcesso.model.Device;
import br.com.bioconnect.BioAcesso.model.dto.ActionOnlineDto;
import br.com.bioconnect.BioAcesso.model.dto.ResponseOnlineDto;
import br.com.bioconnect.BioAcesso.model.dto.ResultOnlineDto;
import br.com.bioconnect.BioAcesso.model.dtodevice.NewUserIdentifiedDTO;
import br.com.bioconnect.BioAcesso.service.AuthorizationService;
import br.com.bioconnect.BioAcesso.util.RawImageUtil;

@RestController
public class OnlineEventAPI {
	
	@Autowired 
	private ServerConfig serverConfig;
	
	@Autowired
	private AuthorizationService authService;
	
	// apenas em modo enterprise	
	@PostMapping("/new_biometric_image.fcgi")
	public ResponseEntity<?> newBiometricImage(@RequestBody byte[] requestBody, @RequestParam Map<String, String> allRequestParams) {
		System.out.println("EventAPI.newBiometricImage");
		System.out.println("requestBodyLength = [" + ( requestBody != null ? requestBody.length : 0 ) + "], allRequestParams = [" + allRequestParams + "]");

		//code
		
		int width = Integer.parseInt(allRequestParams.get("width"));
		int height = Integer.parseInt(allRequestParams.get("height"));
		//System.out.println("Largura da imagem: " + width);
		//System.out.println("Altura da imagem: " + height);
		//System.out.println("Tamanho da imagem (bytes): "+ requestBody.length);
		BufferedImage bi = RawImageUtil.toBufferedImg(requestBody, width, height);
		
		try {
			RawImageUtil.SavePNG(requestBody, width, height, "/Users/fabiocardoso/Desktop/libfprint/fabio.png");
			RawImageUtil.SaveJPG(requestBody, width, height, "/Users/fabiocardoso/Desktop/libfprint/fabio.jpg");
			//RawImageUtil.SaveBMP(requestBody, "/Users/fabiocardoso/Desktop/libfprint/fabio.bmp");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<ActionOnlineDto> acoes = new ArrayList<ActionOnlineDto>();
		//acoes.add(new ActionOnlineDto("door", "door=1"));
		//acoes.add(new ActionOnlineDto("door", "door=2"));
		ResultOnlineDto result = new ResultOnlineDto(
					7, //event - Acesso autorizado
					1, //user_id
					"FABIO CARDOSO", //user_name
					false, //user_image - false
					"", //user_image_hash
					1,  //portal_id
					acoes , //actions
					"Acesso liberado" //message
				);		

		ResponseOnlineDto response = new ResponseOnlineDto(result);
		System.out.println(response.toString());
		
		return ResponseEntity.ok(response);
	}
	
	// apenas em modo enterprise
	@PostMapping("/new_biometric_template.fcgi")
	public ResponseEntity<?> newBiometricTemplate(@RequestBody byte[] requestBody, @RequestParam Map<String, String> allRequestParams) {
		System.out.println("EventAPI.newBiometricTemplate");
		System.out.println("requestBodyLength = [" + ( requestBody != null ? requestBody.length : 0 ) + "], allRequestParams = [" + allRequestParams + "]");

		//code
		/*
		File outputFile = new File("/Users/fabiocardoso/Desktop/libfprint/template.txt");
		try {
			Files.write(outputFile.toPath(), requestBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//String testV = new JSONObject(new String(requestBody)).toString();
		//JSONObject testV = new JSONObject(new String(requestBody));
		String testV = new String(requestBody);		
		//System.out.println(testV);
		
		String encodedString = Base64.getEncoder().encodeToString(requestBody);
		
		try (FileOutputStream fos = new FileOutputStream("/Users/fabiocardoso/Desktop/libfprint/template.txt")) {
		      fos.write(requestBody);
		      //fos.close // no need, try-with-resources auto close
		  } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try (FileOutputStream fos = new FileOutputStream("/Users/fabiocardoso/Desktop/libfprint/template.base64.txt")) {
		      fos.write(encodedString.getBytes());
		      //fos.close // no need, try-with-resources auto close
		  } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok().build();
	}

	@PostMapping("/new_card.fcgi")
	public ResponseEntity<?> newCard(@RequestParam Map<String, String> allRequestParams) {
		System.out.println("EventAPI.newCard");
		System.out.println("allRequestParams = [" + allRequestParams + "]");

		//code

		return ResponseEntity.ok().build();
	}

	@PostMapping("/new_user_id_and_password.fcgi")
	public ResponseEntity<?> newUserIdAndPassword(@RequestParam Map<String, String> allRequestParams) {
		System.out.println("EventAPI.newUserIdAndPassword");
		System.out.println("allRequestParams = [" + allRequestParams + "]");

		//code

		return ResponseEntity.ok().build();
	}

	/**
	 * Metodo responsavel por receber as autenticacoes do device e proceder as autorizacoes
	 * Funciona exclusivamente com o modo online - PRO
	 * @param allRequestParams
	 * @return
	 * @throws URISyntaxException 
	 */
	@PostMapping("/new_user_identified.fcgi")
	public ResponseEntity<?> newUserIdentified(HttpServletRequest request, @RequestParam Map<String, String> allRequestParams) throws URISyntaxException {
		System.out.println("IdentificationAPI.newUserIdentified");
		//System.out.println("allRequestParams = [" + allRequestParams + "]");

		String retorno = this.authService.doAuthorization(new NewUserIdentifiedDTO(allRequestParams),new Device(getClientIp(request),serverConfig.getDevicePort()));
		
		return ResponseEntity.ok().body(retorno);
	}
	
    private String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        
        return remoteAddr;
    }

	@GetMapping("/user_get_image.fcgi")
	public ResponseEntity<?> userGetImage(@RequestParam Map<String, String> allRequestParams) {
		System.out.println("EventAPI.userGetImage");
		System.out.println("allRequestParams = [" + allRequestParams + "]");

		//code

		return ResponseEntity.ok().build();
	}

	@PostMapping("/device_is_alive.fcgi")
	public ResponseEntity<?> deviceIsAlive(@RequestParam Map<String, String> allRequestParams) {
		System.out.println("EventAPI.deviceIsAlive");
		System.out.println("allRequestParams = [" + allRequestParams + "]");

		//code

		return ResponseEntity.ok().build();
	}

	@PostMapping("/template_create.fcgi")
	public ResponseEntity<?> templateCreate(@RequestBody byte[] requestBody, @RequestParam Map<String, String> allRequestParams) {
		System.out.println("EventAPI.templateCreate");
		System.out.println("requestBodyLength = [" + ( requestBody != null ? requestBody.length : 0 ) + "], allRequestParams = [" + allRequestParams + "]");

		//code

		return ResponseEntity.ok().build();
	}

	@PostMapping("/card_create.fcgi")
	public ResponseEntity<?> cardCreate(@RequestBody ObjectNode objectNode) {
		System.out.println("EventAPI.cardCreate");
		System.out.println("objectNode = [" + objectNode + "]");

		//code

		return ResponseEntity.ok().build();
	}
	
	
	@PostMapping("/face_create.fcgi")
	public ResponseEntity<?> faceCreate(@RequestBody ObjectNode objectNode) {
		System.out.println("EventAPI.faceCreate");
		System.out.println("objectNode = [" + objectNode + "]");

		//code

		return ResponseEntity.ok().build();
	}

}
