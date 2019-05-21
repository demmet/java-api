package certusapi.javaapi.controllers;


import java.io.FileReader;

import org.ini4j.Wini;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import certusapi.javaapi.JavaApiApplication;

@RestController
public class PingController extends ApplicationController {
	
	@GetMapping("/get_ping")
	public String index() {
		System.out.println("[GET] Ping received!");
		return "[GET] Ping received!";
  }

  @PostMapping("/post_ping")
  public String message(@RequestBody String message) {
    System.out.println("[POST] Message received: " + message);
    return "[POST] message received: " + message;
  }
  
  @GetMapping("/register")
	public String create(@RequestParam String cnpj, @RequestParam String url) {
    return register(cnpj, url);
	}
  
  @GetMapping("/ngrok_url")
	public String ngrok_url() {
    return getNgrokUrl();
	}

  @GetMapping("/update_ngrok_url")
	public String updateAndRegisterUrl() {
    String url = getNgrokUrl();
		return register("CNPJRANDOM012", url);
  }
  
  public static String getRequest(String url) {
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(url, String.class);
     
    System.out.println(result);
    return result.toString();
  }


  public static String register(String cnpj, String url) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

    HttpEntity<?> entity = new HttpEntity<>(headers);
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://poc-ngrok-certus.herokuapp.com/company").queryParam("cnpj", cnpj).queryParam("ngrok_url", url);

    HttpEntity<String> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);

    System.out.println(result);
    return result.toString();
  }

  public static String getNgrokUrl() {
    String result = getRequest("http://localhost:4040/api/tunnels");
    try{

      JSONObject obj = new JSONObject(result);
      JSONArray tunnels = obj.getJSONArray("tunnels");
      String url = tunnels.getJSONObject(0).getString("public_url");

      System.out.println(url);
      JavaApiApplication.ngrok_url = url;
      return url;
    } catch (Exception e){
      System.out.println(e);
    }
    return result;
  }

  public static void getCnpjFromFile() {
    try {
      String path = "java-api.ini";
      Wini ini = new Wini(new FileReader(path));
      
      JavaApiApplication.cnpj = ini.get("company", "cnpj");
      System.out.println("cnpj: " + JavaApiApplication.cnpj);
    } catch(Exception e) {
      System.out.println();
    }
    
  }
}