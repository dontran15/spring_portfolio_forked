package com.nighthawk.spring_portfolio.mvc.lightboard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/lightboard")
public class LightApiController {
    @PostMapping("/")
    public ResponseEntity<JsonNode> getLightBoard(@RequestParam int numRows, @RequestParam int numCols,
            @RequestParam double percentLightsOff)
            throws JsonMappingException, JsonProcessingException {
        // Backend Year Object
        LightBoard lightBoard = new LightBoard(numRows, numCols, percentLightsOff);

        // Turn Year Object into JSON
        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = mapper.readTree(lightBoard.toString()); // this requires exception handling

        return ResponseEntity.ok(json); // JSON response, see ExceptionHandlerAdvice for throws
    }
}
