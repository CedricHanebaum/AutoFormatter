package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Formatter {

	public static File config = new File("org.eclipse.jdt.core.prefs");

	public static void main(String[] args) {
		File f = new File("src/");
		System.out.println(f.exists());
		Formatter.processDirectory(f);
	}

	public static void processDirectory(File dir) {
		if (dir.isDirectory()) {
			for (File fileEntry : dir.listFiles()) {
				if (fileEntry.isDirectory()) {
					Formatter.processDirectory(fileEntry);
				} else {
					Formatter.processFile(fileEntry);
				}
			}
		} else {
			System.out.println("Not a directory!");
		}
	}

	public static void processFile(File f) {
		String command = "eclipse -vm java -application org.eclipse.jdt.core.JavaCodeFormatter -verbose -config "
				+ config.getPath() + " " + f.getPath();
		try {
			Process pr = Runtime.getRuntime().exec(command);
			BufferedReader in = new BufferedReader(new InputStreamReader(pr.getErrorStream()));

			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(">> " + line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
