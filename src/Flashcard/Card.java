package Flashcard;

public class Card {
	public CardType type;
	public String en, jpDict, jpMasu, jpTe, jpSimpNeg, jpParticle;
	
	public Card(CardType type, String en, String jpDict, String jpMasu, String jpTe, String jpSimpNeg) {
		super();
		this.type = type;
		this.en = en;
		this.jpDict = jpDict;
		this.jpMasu = jpMasu;
		this.jpTe = jpTe;
		this.jpSimpNeg = jpSimpNeg;
		this.jpParticle = new String();
	}
	
	public Card(CardType type, String en, String jpDict, String jpMasu, String jpTe, String jpSimpNeg,
			String jpParticle) {
		super();
		this.type = type;
		this.en = en;
		this.jpDict = jpDict;
		this.jpMasu = jpMasu;
		this.jpTe = jpTe;
		this.jpSimpNeg = jpSimpNeg;
		this.jpParticle = jpParticle;
	}

	public Card() {
		super();
		this.type = CardType.VERB;
		this.en = new String();
		this.jpDict = new String();
		this.jpMasu = new String();
		this.jpTe = new String();
		this.jpSimpNeg = new String();
		this.jpParticle = new String();
	}
	
}
