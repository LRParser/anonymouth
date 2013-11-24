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
import edu.drexel.psal.jstylo.generics.Logger;
import edu.drexel.psal.jstylo.generics.ProblemSet;
import edu.drexel.psal.jstylo.service.*;

import org.etherpad_lite_client.*;

public class Application extends Controller {
    
    public static Result index() {
        return ok(views.html.index.render("Stylometry Service"));
    }
    
    public static Result textAnalysis()
    {
        return ok(views.html.textanalysis.render("Stylometry Service"));    	    	
    }
    
    public static Result texts() {
    	return TODO;
    }
    
    
    
    private static String getStylometryAnalysis(String textInput) throws Exception
    {
    	writeTestTextToProblemSet(textInput);
        List<String> results = JStyloService.GetJStyloResult();
        StringBuilder resultInfo = new StringBuilder();
        for(String result : results) {
        	resultInfo.append(result);
        	
        }
        return resultInfo.toString();
    }
    
    private static void writeTestTextToProblemSet(String textInput) throws Exception
    {
    	// Write the test file
        PrintWriter out = new PrintWriter("./jsan_resources/corpora/drexel_1/test/test.txt");
        out.println(textInput);
        out.close();
    }
    
    private static ArrayList<String[]> getAnonymouthAnalysis(String textInput) throws Exception
    {
    	writeTestTextToProblemSet(textInput);	
		StringBuilder sb = new StringBuilder();
		ArrayList<String[]> wordsToRemove = AnonymouthService.calculateAnonymouthSuggestions();
		return wordsToRemove;   
    }
    
    private static String getPadContents()
    {
    	EPLiteClient client = new EPLiteClient("http://localhost:9001", "V7u4gvgxSv99R9yTYz4uJzZkOWnR6lzz");
    	String text = client.getText("Pad1").get("text").toString(); 
    	return text;
    }
    
    private static void appendTextToPad(String appendText)
    {
    	EPLiteClient client = new EPLiteClient("http://localhost:9001", "V7u4gvgxSv99R9yTYz4uJzZkOWnR6lzz");
    	String text = client.getText("test").get("text").toString(); 
    	client.setText("test",text+"Update: "+appendText);    
    	client.sendClientsMessage("test",appendText);
    }
    
    @BodyParser.Of(BodyParser.Json.class)
    public static Result updatePadContents() throws Exception {
      //RequestBody body = request().body();
      //Logger.logln(body.asText());
    	ObjectNode result = Json.newObject();
    	return ok(result);
    }
    
    private static String serializeWordsToRemove(ArrayList<String[]> wordsToRemove)
    {
        StringBuilder resultInfo = new StringBuilder();
        for(String[] toRemove : wordsToRemove) {
            resultInfo.append(toRemove[0]+", ");
        }
        return resultInfo.toString();
    	
    }
    
    public static Result addTexts() throws Exception {

    	final Map<String, String[]> values = request().body().asFormUrlEncoded();
        final String textInput = values.get("textInput1")[0];
              
        String stylometryInfo = getStylometryAnalysis(textInput);
        ArrayList<String[]> wordsToRemove = getAnonymouthAnalysis(textInput);
        StringBuilder resultInfo = new StringBuilder();
        resultInfo.append(stylometryInfo);
        resultInfo.append("<br/>");
        resultInfo.append("Words to remove: ");
        resultInfo.append(serializeWordsToRemove(wordsToRemove));

        
        return ok(resultInfo.toString());
    }
    

    @BodyParser.Of(BodyParser.Json.class)
    public static Result getAnonymouthInfoJSON() throws Exception {
      //RequestBody body = request().body();
      //Logger.logln(body.asText());
      JsonNode json = request().body().asJson();
      ObjectNode result = Json.newObject();
      Logger.logln(json.asText());
      String textInput = json.findPath("text").textValue();
      if(textInput == null) {
        result.put("status", "KO");
        result.put("message", "Missing parameter [text]");
        return badRequest(result);
      } else {
        result.put("status", "OK");
        ArrayList<String[]> wordsToRemove = getAnonymouthAnalysis(textInput);
              
        // Send client words to remove info
        String wordsToRemoveInfo = "JSAN suggests decreasing frequency of these words: "+serializeWordsToRemove(wordsToRemove);
        
        // Update the text pad
        appendTextToPad(wordsToRemoveInfo);
        
        result.put("message", serializeWordsToRemove(wordsToRemove));
        return ok(result);
      }
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
        String resultInfo = getStylometryAnalysis(textInput);
        result.put("message", resultInfo);
        return ok(result);
      }
    }
    
    
}
