package Unused;
//package Flashcard;
//
//import java.io.BufferedWriter;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.io.UnsupportedEncodingException;
//import java.io.Writer;
//import java.lang.reflect.Type;
//import java.util.List;
//
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonIOException;
//import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.JsonReader;
//
//public class CJson {
//	static void writeFile(List<Card> list, String filename) {
//		try {
//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("src/cards/" + filename + ".json"), "UTF-8"));
//				try {
//					gson.toJson(list, out);
//				} finally {
//					out.flush();
//				    out.close();
//				}
//
//		} catch (JsonIOException e) {
//			// TODO Auto-generated catch block
//			System.out.println("write json file exception");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			System.out.println("write json file exception");
//		}
//	}
//	
//	static void printJson(List<Card> list) {
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//		String str = gson.toJson(list);
//		System.out.println(str);
//	}
//	
//	static List<Card> readFile(String filename) {
//		try {
//			final Type REVIEW_TYPE = new TypeToken<List<Card>>() {}.getType();
//			Gson gson = new Gson();
//			JsonReader reader;
//			reader = new JsonReader(new InputStreamReader(new FileInputStream("src/cards/" + filename + ".json"), "UTF-8"));
//			List<Card> data = gson.fromJson(reader, REVIEW_TYPE); // contains the whole reviews list
//			//printJson(data); // prints to screen some values
//			return data;
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//}
