package edu.drexel.psal.anonymouth.service;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.drexel.psal.jstylo.generics.ProblemSet;

public class AnonymouthServiceTest {

	@Test
	public void TestAnonymouthService() throws Exception {
		AnonymouthService.calculateAnonymouthSuggestions();
	}

}
