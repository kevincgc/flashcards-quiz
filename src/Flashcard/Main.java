package Flashcard;

import java.util.Arrays;
import java.util.List;

import com.google.gson.*;

public class Main {

	public static void main(String[] args) {
//		Card c = new Card();
//		List<Card> l = Arrays.asList(c);
//		CJson.writeFile(l, "test");
		Flashcard window = new Flashcard("test1");
		window.frame.setVisible(true);
	}

}
