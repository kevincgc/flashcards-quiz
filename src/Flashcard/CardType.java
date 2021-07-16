package Flashcard;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public enum CardType {
	@SerializedName("verb") VERB, 
	@SerializedName("adjective") ADJECTIVE, 
	@SerializedName("noun") NOUN, 
	@SerializedName("adverb") ADVERB, 
	@SerializedName("expression") EXPRESSION,
	@SerializedName("error") ERROR;
	
	public String toString() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}
}
