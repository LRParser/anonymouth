package edu.drexel.psal.jstylo.service;

import java.util.List;

import org.junit.Test;

import edu.drexel.psal.jstylo.service.JStyloService;

public class JStyloAsAServiceTest {

	@Test
	public void test() throws Exception {
		List<String> results = JStyloService.GetJStyloResult();
		for(String result : results) {
			System.out.println(result);
		}

		// to export to ARFF do: main.analysisSaveResultsJButton.doClick();
	}

}
