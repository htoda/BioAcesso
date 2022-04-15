package br.com.bioconnect.BioAcesso.util;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.apache.commons.io.FileUtils;

public class PhotoToBase64 {

	public static void main(String[] args) {
		Optional<String> base64Str = convertPhotoToBase64("/home/hugo/dev/Fotos/Aleatorias/hamilton.jpeg");
		base64Str.ifPresent(ret -> System.out.println(ret));
		
		byte[] fileContentNew = Base64.getDecoder().decode(base64Str.get());
		
		try {
			FileUtils.writeByteArrayToFile(new File("/home/hugo/dev/Fotos/Aleatorias/senna-gerada.jpeg"), fileContentNew);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static Optional<String> convertPhotoToBase64 (String filePath)  {
		byte[] fileContent = null;
		Optional<String> opt = null;
		try {
			fileContent = FileUtils.readFileToByteArray(new File(filePath));
			opt = Optional.of(Base64.getEncoder().encodeToString(fileContent));
		} catch (IOException e) {
			e.printStackTrace();
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return opt;
	} 

}
