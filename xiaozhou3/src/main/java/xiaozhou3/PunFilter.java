package xiaozhou3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.apache.commons.codec.language.Metaphone;
import org.apache.commons.lang.StringUtils;

/**
 * When get a potential pun candidate, this class filters and eliminates
 * unqualified ones and stores the ideal puns in a priority queue; 
 * 
 * Filtered puns queue can be retrieved by method getPuns();
 * 
 * <p>Features:</p>
 * <p>-remove duplicate words; 
 * e.g. POPularity(popularity) and POPlarity(popularity) are both puns of POP,
 * only POPularity(popularity) will be selected;</p>
 * <p>-remove duplicate puns;
 * e.g. DOGS(dogs) and DOGS(docs) are the same, but only DOGS(dogs) is left;</p>
 * <p>-queue is sorted by levenshtein distance;</p>
 * 
 * @author Sean
 *
 */
public class PunFilter {

	private Metaphone mtp = new Metaphone();//metaphone encoding scheme;
	private String target;//target string;
	private String encoded;//encoded string;
	private int maxPuns;//maximum number of puns;
	private char c;//first character of target;
	
	private PriorityQueue<Pun> puns = new PriorityQueue<Pun>();//priority queue is used to compare and sort puns;
	private Map<String, Pun> map;// check if a pun is already existed;


	// Comparator to compare the puns;
	private Comparator<Pun> comparator = new Comparator<Pun>() {
		@Override
		public int compare(Pun o1, Pun o2) {
			return o2.getDistance()-o1.getDistance();
		}
	};
	
	
	/**
	 * Construct a PunFilter;
	 * 
	 * @param target target string
	 * @param maxPuns maximum number of puns
	 */
	public PunFilter(String target, int maxPuns) {
		this.maxPuns = maxPuns;
		this.target = target.toLowerCase();
		this.encoded = mtp.encode(target);
		this.c = this.target.charAt(0);
		this.puns = new PriorityQueue<Pun>(maxPuns, comparator);
		this.map = new HashMap<String, Pun>();
	}
	
	
	/**
	 * get a sorted queue of puns;
	 * this method is used to pass the filtered result;
	 * @return a pun queue;
	 */
	public PriorityQueue<Pun> getPuns() {
		return puns;
	}
	
	
	/**
	 * Here add some restrictions to figure out whether candidate is a pun; 
	 * If is, the most similar pun is selected;
	 * e.g. POPularity(popularity) and POPlarity(popularity) are both puns of POP,
	 * only POPularity(popularity) will be selected;
	 * 
	 * @param candidate candidate word;
	 * @return an array of int[], 
	 * while arr[0] is start postion of pun in the candidate word,
	 * arr[1] is end positon, arr[2] is the levenshtein distance to target word;
	 */
	public int[] findPun(String candidate) {
		int start = -1;
		int end = -1;
		int minDis = Integer.MAX_VALUE;

		for(int i=0; i<candidate.length(); ++i) {
			//find the index matching the first character in target;
			if(candidate.charAt(i) != c) continue;
			
			//when found the index, iterating on several following characters
			//to see if it match the phonetic value;
			for(int j=i; j<i+target.length() && j<candidate.length(); ++j) {
				
				String substring = candidate.substring(i, j+1);
				String tmp = mtp.encode(substring);
				//skip if phonetic value doesn't match
				if(tmp.length() != encoded.length())
					continue;
				//if matched, levenshtein distance is used to compare two strings;
				if(tmp.equals(encoded)) {
					int dis = StringUtils.getLevenshteinDistance(substring, target);
					if(dis < minDis) {
						start = i;
						end = j+1;
						minDis = dis;
					}
				}
			}
		}
		int[] arr = {start, end, minDis};
		return arr;
	}
	

	/**
	 * This method stored pun in priority queue and hashmap;
	 * 
	 * @param candidate potential candidate pun;
	 * @param punInfo an array of int[], 
	 * while arr[0] is start postion of pun in the candidate word,
	 * arr[1] is end positon, arr[2] is the levenshtein distance to target word;
	 */
	public void storePun(String candidate, int[] punInfo) {
		if(punInfo[0] == -1) return;
		//build new Pun;
		String s = candidate.substring(0, punInfo[0])+
				target.toUpperCase()+
				candidate.substring(punInfo[1]);
		Pun pun = new Pun(candidate, s, punInfo[2]);
		
		//if punWord already existed, only the closest pun will be left;
		//e.g. DOGS(dogs) and DOGS(docs) are the same, but only DOGS(dogs) is left;
		if(map.containsKey(s)) {
			if(punInfo[2] < map.get(s).getDistance()) {
				if(puns.contains(map.get(s)))
					puns.remove(map.get(s));
				puns.offer(pun);
				map.put(s, pun);
			}
			return;
		}
		//if pun list exceed maximum numbers, pop out the least similar pun;
		if(puns.size() >= maxPuns) {
			if(punInfo[2] >= puns.peek().getDistance()) return;
			puns.poll();
		}
		puns.offer(pun);
		map.put(s, pun);
	}

}
