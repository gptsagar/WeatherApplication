package net.javaguides.springboot.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.javaguides.springboot.Exceptions.ErrorWhileMakingRequestException;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.model.Employee;
import net.javaguides.springboot.repository.EmployeeRepository;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import utility.Util;

import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {



    @GetMapping("/getWeather")
    @Produces("application/json")
    @Consumes("application/json")
    public StringBuffer getTemperatureOfCity(@RequestParam("cityName") String cityName) throws IOException {
        String OpenWeatherStackApiURL = new Util().getBaseURL() + "&query=" + cityName;
        StringBuffer result = new StringBuffer();
        try {
            HttpGet request = new HttpGet(OpenWeatherStackApiURL);
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(request);
            try (BufferedReader buffer = new BufferedReader(
                    new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))) {
                String readResponse;
                while ((readResponse = buffer.readLine()) != null) {
                    result.append(readResponse);
                }
            }
        }
        catch (ErrorWhileMakingRequestException e){
            new ErrorWhileMakingRequestException("Error Making Request");
        }
        return result;
    }
}
