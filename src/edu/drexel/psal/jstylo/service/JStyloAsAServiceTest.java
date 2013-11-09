package edu.drexel.psal.jstylo.service;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.drexel.psal.jstylo.analyzers.WekaAnalyzer;
import edu.drexel.psal.jstylo.generics.ProblemSet;
import edu.drexel.psal.jstylo.GUI.*;

public class JStyloAsAServiceTest {

	@Test
	public void test() throws Exception {
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
		
		System.out.println("Analysis finished, result size was: "+main.results.size());
		for(String result : main.results) {
			System.out.println(result);
		}

		// to export to ARFF do: main.analysisSaveResultsJButton.doClick();
	}

}
