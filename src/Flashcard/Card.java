package Flashcard;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Card {
	protected CardType type;
	protected String[] sArr;
	public static int FIELD_COUNT = 10;
	
	public Card(CardType type, String[] sArr) {
		this.type = type;
		this.sArr = new String[FIELD_COUNT];
		for (int i = 0; i < FIELD_COUNT; i++) {
			this.sArr[i] = sArr[i];
		}
	}
	
	public Card() {
		this.type = CardType.VERB;
		sArr = new String[FIELD_COUNT];
		for (int i = 0; i < FIELD_COUNT; i++) {
			this.sArr[i] = "";
		}
	}
	
	public void setType(CardType newType) {
		type = newType;
	}
	
	public CardType getType() {
		return type;
	}
	
	public void setArr(String s, int i) {
		sArr[i] = s;
	}
	
	public String getArr(int i) {
		return sArr[i];
	}
	
	public String toString() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String str = gson.toJson(this);
		return str;
	}
	
	@Override
	public int hashCode() {
		int hash = 7;
		for (int j = 0; j < sArr.length; j++) {
			hash = hash * 31 + sArr.hashCode();
		}
		return hash;
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() != o.getClass()) return false;
        Card c = (Card) o;
        Boolean ans = true;
        for (int i = 0; i < FIELD_COUNT; i++) {
        	ans = ans && sArr[i].equals(c.sArr[i]);
        }
        return ans;
    }

}
