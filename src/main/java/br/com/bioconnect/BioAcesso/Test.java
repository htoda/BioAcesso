package br.com.bioconnect.BioAcesso;

import org.json.JSONArray;
import org.json.JSONObject;

public class Test {

	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("tableID", 1);
		jsonObject.put("price", 53);
		jsonObject.put("payment", "cash");
		jsonObject.put("quantity", 3);
		
		JSONArray blocoArray = new JSONArray();
		
		//elementoArray 1
		JSONObject elementoArray1 = new JSONObject();
		elementoArray1.put("ID", 1);
		elementoArray1.put("quantity", 1);
		blocoArray.put(elementoArray1); //add to products
		
		jsonObject.put("aqui_entrou_array", blocoArray);
		
		
		System.out.println(jsonObject);
		 
	}

}
