package com.nighthawk.spring_portfolio.mvc.calendar;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Calendar API
 * Calendar Endpoint: /api/calendar/isLeapYear/2022, Returns:
 * {"year":2020,"isLeapYear":false}
 */
@RestController
@RequestMapping("/api/calendar")
public class CalendarApiController {

  /**
   * GET isLeapYear endpoint
   * ObjectMapper throws exceptions on bad JSON
   * 
   * @throws JsonProcessingException
   * @throws JsonMappingException
   */
  @GetMapping("/isLeapYear/{year}")
  public ResponseEntity<JsonNode> getIsLeapYear(@PathVariable int year)
      throws JsonMappingException, JsonProcessingException {
    // Backend Year Object
    Year year_obj = new Year();
    year_obj.setYear(year); // evaluates Leap Year & firstDayOfYear

    // Turn Year Object into JSON
    ObjectMapper mapper = new ObjectMapper();
    JsonNode json = mapper.readTree(year_obj.isLeapYearToString()); // this requires exception handling

    return ResponseEntity.ok(json); // JSON response, see ExceptionHandlerAdvice for throws
  }

  // add other methods
  @GetMapping("/firstDayOfYear/{year}")
  public ResponseEntity<JsonNode> getFirstDayOfYear(@PathVariable int year)
      throws JsonMappingException, JsonProcessingException {
    // Backend Year Object
    Year year_obj = new Year();
    year_obj.setYear(year); // evaluates Leap Year & firstDayOfYear

    // Turn Year Object into JSON
    ObjectMapper mapper = new ObjectMapper();
    JsonNode json = mapper.readTree(year_obj.firstDayOfYearToString()); // this requires exception handling

    return ResponseEntity.ok(json); // JSON response, see ExceptionHandlerAdvice for throws
  }

  @GetMapping("/dayOfYear/{month}/{day}/{year}")
  public ResponseEntity<JsonNode> getDayOfYear(@PathVariable int month, @PathVariable int day, @PathVariable int year)
      throws JsonMappingException, JsonProcessingException {
    // Backend Year Object
    Year year_obj = new Year();
    year_obj.setDate(month, day, year); // evaluates all the method implementations

    // Turn Year Object into JSON
    ObjectMapper mapper = new ObjectMapper();
    JsonNode json = mapper.readTree(year_obj.dayOfYearToString()); // this requires exception handling

    return ResponseEntity.ok(json); // JSON response, see ExceptionHandlerAdvice for throws
  }

  @GetMapping("/numberOfLeapYears/{year1}/{year2}")
  public ResponseEntity<JsonNode> getNumberOfLeapYears(@PathVariable int year1, @PathVariable int year2)
      throws JsonMappingException, JsonProcessingException {
    // Backend Year Object
    Year year_obj = new Year();

    // Turn Year Object into JSON
    ObjectMapper mapper = new ObjectMapper();
    JsonNode json = mapper.readTree(year_obj.numberOfLeapYearsToString(year1, year2)); // this requires exception
                                                                                       // handling

    return ResponseEntity.ok(json); // JSON response, see ExceptionHandlerAdvice for throws
  }

  @GetMapping("/dayOfWeek/{month}/{day}/{year}")
  public ResponseEntity<JsonNode> getDayOfWeek(@PathVariable int month, @PathVariable int day, @PathVariable int year)
      throws JsonMappingException, JsonProcessingException {
    // Backend Year Object
    Year year_obj = new Year();
    year_obj.setDate(month, day, year);

    // Turn Year Object into JSON
    ObjectMapper mapper = new ObjectMapper();
    JsonNode json = mapper.readTree(year_obj.dayOfWeekToString()); // this requires exception handling

    return ResponseEntity.ok(json); // JSON response, see ExceptionHandlerAdvice for throws
  }

  private JSONObject body; // body for json return
  private HttpStatus status; // body for json return

  // onThisDay wikimedia API call using own api call
  @GetMapping("/onThisDay/{month}/{day}/{type}") // added to end of prefix as endpoint
  public ResponseEntity<JSONObject> onThisDay(@PathVariable int month, @PathVariable int day, @PathVariable String type)
      throws JsonMappingException, JsonProcessingException {
    try { // APIs can fail (ie Internet or Service down)
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create("https://api.wikimedia.org/feed/v1/wikipedia/en/onthisday/" + type + "/" + month + "/" + day))
          .method("GET", HttpRequest.BodyPublishers.noBody())
          .build();

      // RapidAPI request and response
      HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

      // JSONParser extracts text body and parses to JSONObject
      this.body = (JSONObject) new JSONParser().parse(response.body());
      this.status = HttpStatus.OK; // 200 success
    } catch (Exception e) { // capture failure info
      HashMap<String, String> status = new HashMap<>();
      status.put("status", "RapidApi failure: " + e);

      // Setup object for error
      this.body = (JSONObject) status;
      this.status = HttpStatus.INTERNAL_SERVER_ERROR; // 500 error
    }

    // return JSONObject in RESTful style
    return new ResponseEntity<>(this.body, this.status);
  }
}