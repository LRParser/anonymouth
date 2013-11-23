package edu.drexel.psal.anonymouth.service;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import weka.classifiers.Classifier;
import edu.drexel.psal.anonymouth.engine.*;
import edu.drexel.psal.anonymouth.gooie.GUIMain;
import edu.drexel.psal.anonymouth.gooie.ThePresident;
import edu.drexel.psal.jstylo.generics.Logger;
import edu.drexel.psal.jstylo.generics.ProblemSet;

public class AnonymouthService {


	public static ArrayList<String[]> calculateAnonymouthSuggestions() throws Exception
	{
		ThePresident pres = new ThePresident();
	 	ThePresident.num_Tagging_Threads = 1;
		pres.main.documentProcessor.process();
		pres.main.documentProcessor.processing.get();
		Logger.logln("Done service call");
		ArrayList<String[]> wordsToRemove = pres.main.wordSuggestionsDriver.getTopToRemove();
		// TODO: Fully de-couple UI from processing logic
		pres.main.dispose();
		return wordsToRemove;
	}
	
}
