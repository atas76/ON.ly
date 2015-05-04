package org.sjon.only.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ObjectLoader {
	
	public ObjectLoader(File file) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		
		List<String> rawContent = new ArrayList<String>();
		
		// Read the file contents
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF8"));
		String line = br.readLine();
		// System.out.println(line);
		// br.skip(1); // skip BOM
		while ((line = br.readLine()) != null) {
			rawContent.add(line);
		}
		br.close();
		
		// Analyze each file to tokens
		// Each line is an object
		
		// Parse the file
		
		// Create the objects
	}
}
