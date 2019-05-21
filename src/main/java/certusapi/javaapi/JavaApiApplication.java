package certusapi.javaapi;

import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ini4j.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import certusapi.javaapi.controllers.PingController;

@SpringBootApplication
public class JavaApiApplication {
	public static String ngrok_url;
	public static String cnpj;
	public static void main(String[] args) {
		
    String result = PingController.getRequest("http://localhost:4040/api/tunnels");
    try {

			PingController.getNgrokUrl();
			PingController.getCnpjFromFile();
			PingController.register(cnpj, ngrok_url);

    } catch (Exception e){
      System.out.println(e);
    }
			
		SpringApplication.run(JavaApiApplication.class, args);
	}

}
