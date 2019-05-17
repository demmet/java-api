package certusapi.javaapi.controllers;


import org.json.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import certusapi.javaapi.JavaApiApplication;

@RestController
public class PingController extends ApplicationController {
	
	@GetMapping("/ping")
	public String index(@RequestParam String message) {
		return "pong: " + message;
  }
  
	// @GetMapping("/ping")
	// public String index() {
  //   return contests("https://fael-middleware-homolog.herokuapp.com/contests");
  // }

  @GetMapping("/error")
	public String error(@RequestParam String message) {
    return 1/0 + "";
  }

  @GetMapping("/get_tunnels")
  public String tunnels() {
    String result = getRequest("http://localhost:4040/api/tunnels");
    try{

      JSONObject obj = new JSONObject(result);
      JSONArray tunnels = obj.getJSONArray("tunnels");
      String url = tunnels.getJSONObject(0).getString("public_url");

      System.out.println(url);
      return url;
    } catch (Exception e){
      System.out.println(e);
    }
    return result;
  }
  
  @GetMapping("/register")
	public String create(@RequestParam String url) {
    return register("CNPJRANDOM123", url);
	}
  
  public static String getRequest(String url) {
    RestTemplate restTemplate = new RestTemplate();
    String result = restTemplate.getForObject(url, String.class);
     
    System.out.println(result);
    return result.toString();
  }

  @GetMapping("/ngrok_url")
	public String ngrok_url() {
    return JavaApiApplication.ngrok_url;
	}

  public static String register(String cnpj, String url) {
    RestTemplate restTemplate = new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

    HttpEntity<?> entity = new HttpEntity<>(headers);
    UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("https://a2937df9.ngrok.io/company").queryParam("cnpj", cnpj).queryParam("ngrok_url", url);

    HttpEntity<String> result = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, String.class);

    System.out.println(result);
    return result.toString();
  }
}