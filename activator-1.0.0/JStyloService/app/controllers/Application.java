package controllers;

import play.mvc.Controller;
import play.data.*;
import play.data.Form.*;
import play.mvc.Result;
import java.io.*;
import java.util.Map;
import java.util.List;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.BodyParser;                     
import play.libs.Json;
import play.libs.Json.*;                        
import static play.libs.Json.toJson;
import com.fasterxml.jackson.databind.JsonNode;           
import com.fasterxml.jackson.databind.node.ObjectNode;    

import edu.drexel.psal.jstylo.service.*;

public class Application extends Controller {
    
    public static Result index() {
        return ok(views.html.index.render("Stylometry Service"));
    }
    
    public static Result texts() {
    	return TODO;
    }
    
    private static String getStylometryAnalysis(String textInput) throws Exception
    {
    	
    	// Save text input as test file
        PrintWriter out = new PrintWriter("./jsan_resources/corpora/drexel_1/test/test.txt");
        out.println(textInput);
        out.close();

        // TODO consider input(s) in call into JStylo service
        List<String> results = JStyloService.GetJStyloResult(null);
        StringBuilder resultInfo = new StringBuilder();
        for(String result : results) {
        	resultInfo.append(result);
        	
        }
        return resultInfo.toString();
    }
    
    public static Result addTexts() throws Exception {

    	final Map<String, String[]> values = request().body().asFormUrlEncoded();
        final String textInput = values.get("textInput1")[0];
        String resultInfo = getStylometryAnalysis(textInput);
        
        
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
        String resultInfo = getStylometryAnalysis(textInput);
        result.put("message", resultInfo);
        return ok(result);
      }
    }
    
    
}
