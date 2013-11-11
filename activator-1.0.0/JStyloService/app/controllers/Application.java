package controllers;

import play.mvc.Controller;
import play.data.*;
import play.data.Form.*;
import play.mvc.Result;

import java.io.*;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.BodyParser;                     
import play.libs.Json;
import play.libs.Json.*;                        
import static play.libs.Json.toJson;

import com.fasterxml.jackson.databind.JsonNode;           
import com.fasterxml.jackson.databind.node.ObjectNode;    

import edu.drexel.psal.anonymouth.service.AnonymouthService;
import edu.drexel.psal.jstylo.generics.ProblemSet;
import edu.drexel.psal.jstylo.service.*;

public class Application extends Controller {
    
    public static Result index() {
        return ok(views.html.index.render("Stylometry Service"));
    }
    
    public static Result texts() {
    	return TODO;
    }
    
    private static String getStylometryAnalysis() throws Exception
    {
        List<String> results = JStyloService.GetJStyloResult();
        StringBuilder resultInfo = new StringBuilder();
        for(String result : results) {
        	resultInfo.append(result);
        	
        }
        return resultInfo.toString();
    }
    
    private static String getAnonymouthAnalysis() throws Exception
    {
		StringBuilder sb = new StringBuilder();
		ArrayList<String[]> wordsToRemove = AnonymouthService.calculateAnonymouthSuggestions();
		 for(String[] strArr : wordsToRemove) {
		 	sb.append(strArr[0]+", ");
		 }
		return sb.toString();
    
    }
    
    public static Result addTexts() throws Exception {

    	final Map<String, String[]> values = request().body().asFormUrlEncoded();
        final String textInput = values.get("textInput1")[0];
        
    	// Write the test file
        PrintWriter out = new PrintWriter("./jsan_resources/corpora/drexel_1/test/test.txt");
        out.println(textInput);
        out.close();
        
        String stylometryInfo = getStylometryAnalysis();
        String wordsToRemoveInfo = getAnonymouthAnalysis();
        StringBuilder resultInfo = new StringBuilder();
        resultInfo.append(stylometryInfo);
        resultInfo.append("\\n");
        resultInfo.append("Words to remove: \\n");
        resultInfo.append(wordsToRemoveInfo);
        
        return ok(resultInfo.toString());
    }
    
    @BodyParser.Of(BodyParser.Json.class)
    public static Result getStylometryInfoJSON() throws Exception {
      JsonNode json = request().body().asJson();
      ObjectNode result = Json.newObject();
      String textInput = json.findPath("text").textValue();
      if(textInput == null) {
        result.put("status", "KO");
        result.put("message", "Missing parameter [text]");
        return badRequest(result);
      } else {
        result.put("status", "OK");
    	// Write the test file
        PrintWriter out = new PrintWriter("./jsan_resources/corpora/drexel_1/test/test.txt");
        out.println(textInput);
        out.close();
        String resultInfo = getStylometryAnalysis();
        result.put("message", resultInfo);
        return ok(result);
      }
    }
    
    
}
