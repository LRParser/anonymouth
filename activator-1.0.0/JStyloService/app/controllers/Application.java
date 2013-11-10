package controllers;

import play.mvc.Controller;
import play.data.*;
import play.data.Form.*;
import play.mvc.Result;
import java.util.Map;
import java.util.List;

import edu.drexel.psal.jstylo.service.*;

public class Application extends Controller {
    
    public static Result index() {
        return ok(views.html.index.render("Hello Play Framework"));
    }
    
    public static Result texts() {
    	return TODO;
    }
    
    public static Result addTexts() throws Exception {

    	final Map<String, String[]> values = request().body().asFormUrlEncoded();
        final String textInput = values.get("textInput1")[0];
        // TODO consider input(s) in call into JStylo service
        List<String> results = JStyloService.GetJStyloResult(null);
        StringBuilder resultInfo = new StringBuilder();
        for(String result : results) {
        	resultInfo.append(result);
        	
        }
        
        return ok(resultInfo.toString());
    }
    
}
