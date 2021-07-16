package Flashcard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class CardDeck {
	private List<Card> cards;
	private List<Integer> indices;
	private int i;
	private String filename, filepath;
	
	public CardDeck(String filename) {
		this.filename = filename;
		this.filepath = "src/cards/" + filename + ".json";
		indices = new ArrayList<Integer>();
		readFile();
	}
	
	public int getIndex() {
		return i;
	}
	
	public Card getCard() {
		return cards.get(indices.get(i));
	}
	
	public Card getCard(int jumpTo) {
		i = jumpTo;
		return getCard();
	}
	
	public Card nextCard() {
		if (i == cards.size() - 1) {
			i = 0;
		} else {
			i++;
		}
		return getCard();
	}
		
	public Card prevCard() {
		if (i == 0) {
			i = cards.size() - 1;
		} else {
			i--;
		}
		return getCard();
	}
	
	public int getSize() {
		return cards.size();
	}
	
	public void addCard(Card c) {
		indices.add(indices.size());
		cards.add(c);
	}
	
	public void removeCard(Card c) {
		indices.remove(indices.indexOf(Collections.max(indices)));
		cards.remove(c);
	}
	
	public void updateCard(Card c) {
		cards.add(indices.get(i), c);
		cards.remove(indices.get(i) + 1);
	}
	
	public void shuffle() {
		Collections.shuffle(indices);
		i = 0;
	}

	public void writeFile() {
		Writer out = null;
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath), "UTF-8"));
			gson.toJson(cards, out);
			out.flush();
		    out.close();
		} catch (JsonIOException e) {
			System.out.println("write json file exception");
		} catch (IOException e) {
			System.out.println("write json file exception");
		}
	}
	
	public void readFile() {
		try {
			File file = new File(filepath); 
			if (!file.exists()) {
				cards = new ArrayList<Card>();
				Card newCard = new Card();
				cards.add(newCard);
				indices.add(0);
			} else {
				final Type LIST_CARD_TYPE = new TypeToken<List<Card>>(){}.getType();
				Gson gson = new Gson();
				JsonReader reader;
				reader = new JsonReader(new InputStreamReader(new FileInputStream(filepath), "UTF-8"));
				cards = gson.fromJson(reader, LIST_CARD_TYPE);
				if (cards.size() == 0) {
					Card newCard = new Card();
					cards.add(newCard);
				}
				for (int i = 0; i < cards.size(); i++) {
					indices.add(i);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(cards);
	}
}
