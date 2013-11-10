package edu.drexel.psal.jstylo.service;

import java.util.List;

import edu.drexel.psal.jstylo.GUI.GUIMain;
import edu.drexel.psal.jstylo.GUI.GUIUpdateInterface;
import edu.drexel.psal.jstylo.analyzers.WekaAnalyzer;
import edu.drexel.psal.jstylo.generics.ProblemSet;
import com.jgaap.generics.Document;
public class JStyloService {

	// TODO build problem set XML dynamically (consider inputs)
	public static List<String> GetJStyloResult(List<String> inputTexts) throws Exception
	{
		GUIMain main = new GUIMain();
		main.analysisClassTestDocsJRadioButton.setSelected(true);
		String problemSetPath = "jsan_resources/problem_sets/drexel_1_classifycm.xml";
		main.ps = new ProblemSet(problemSetPath);
		// Select Basic-9 features
		main.cfd = main.presetCFDs.get(3);
		
		// Select Weka SMO
		WekaAnalyzer tmpAnalyzer = new WekaAnalyzer(Class.forName("weka.classifiers.functions.SMO").newInstance());
				
		main.analyzers.add(tmpAnalyzer);
		
		GUIUpdateInterface.updateProblemSet(main);
		main.analysisRunJButton.doClick();
		main.analysisThread.join();

		return main.results;
		
	}
}
