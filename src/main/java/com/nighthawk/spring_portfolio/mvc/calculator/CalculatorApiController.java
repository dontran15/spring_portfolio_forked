package com.nighthawk.spring_portfolio.mvc.calculator;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

// Calculator api, endpoint: /api/calculator/
@RestController
@RequestMapping("/api/calculator")
public class CalculatorApiController {
    @GetMapping("/calculate")
    public ResponseEntity<JsonNode> calculate(@RequestParam("expression") String expression)
            throws JsonMappingException, JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();

        try {
            Calculator calculatedExpression = new Calculator(expression);
            // Turn Year Object into JSON
            JsonNode json = mapper.readTree(calculatedExpression.toString());
            return new ResponseEntity<>(json, HttpStatus.OK);
        } catch (RuntimeException e) {
            JsonNode json = mapper.readTree(e.getMessage());
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            JsonNode json = mapper.readTree("{ \"error\": \"Internal Error/Parsing Error, check your expression\" }");
            return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
        }
    }
}
