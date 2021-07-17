package Flashcard;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

class Config {
	String filename;
	int showRadio;

	public Config(String filename, int showRadio) {
		super();
		this.filename = filename;
		this.showRadio = showRadio;
	}
}

public class Main {

	static void writeFile(String filename) {
		try {
			Config cfg = new Config(filename, 0);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			Writer out = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream("src/resources/" + "config.json"), "UTF-8"));
			try {
				gson.toJson(cfg, out);
			} finally {
				out.flush();
				out.close();
			}
		} catch (JsonIOException e) {
			System.out.println("write json file exception");
		} catch (IOException e) {
			System.out.println("write json file exception");
		}
	}

	static Config readFile() {
		try {
			final Type REVIEW_TYPE = new TypeToken<Config>() {
			}.getType();
			Gson gson = new Gson();
			JsonReader reader;
			reader = new JsonReader(
					new InputStreamReader(new FileInputStream("src/resources/" + "config.json"), "UTF-8"));
			return gson.fromJson(reader, REVIEW_TYPE);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		System.out.println("error");
		return null;
	}

	public static void main(String[] args) {
//		writeFile("all_verbs");
		Config c = readFile();
		Flashcard window = new Flashcard(c.filename);
		window.frame.setVisible(true);
	}

}
