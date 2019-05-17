package certusapi.javaapi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import certusapi.javaapi.controllers.PingController;

@SpringBootApplication
public class JavaApiApplication {
	public static String ngrok_url;
	public static void main(String[] args) {
		
    String result = PingController.getRequest("http://localhost:4040/api/tunnels");
    try{

      JSONObject obj = new JSONObject(result);
      JSONArray tunnels = obj.getJSONArray("tunnels");
      String url = tunnels.getJSONObject(0).getString("public_url");

			System.out.println(url);
			ngrok_url = url;
      
    } catch (Exception e){
      System.out.println(e);
    }
			
		SpringApplication.run(JavaApiApplication.class, args);
	}

}
