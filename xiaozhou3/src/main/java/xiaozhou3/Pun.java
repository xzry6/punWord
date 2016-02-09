package xiaozhou3;

/**
 * This is a class store pun information.
 * Variables in this class include a original word (e.g. popular), 
 * a transformed pun word(e.g. POPular) and levenshtein distance 
 * between pun and the target word.
 * 
 * @author Sean
 *
 */
public class Pun {
	
	private String oriWord;
	
	private String punWord;
	
	private int distance;
	
	public Pun(String oriWord, String punWord, int distance) {
		this.oriWord = oriWord;
		this.punWord = punWord;
		this.distance = distance;
	}
	
	public String getOriWord() {
		return oriWord;
	}
	
	public String getPunWord() {
		return punWord;
	}
	
	public int getDistance() {
		return distance;
	}
}
