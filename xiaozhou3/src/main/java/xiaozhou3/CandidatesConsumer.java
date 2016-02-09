package xiaozhou3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * This class includes a method consuming candidates queue
 * and producing the real puns in a sorted order;
 * 
 * Result list can be retrieved by method getResult();
 * 
 * Candidates queue is generated from class CandidatesProducer;
 * 
 * @author Sean
 *
 */
public class CandidatesConsumer {

	private String target;//target string;
	private int maxPuns;//maximum number of puns;
	
	private List<String> result = new ArrayList<String>();//result list;
	
	
	/**
	 * Construct a CandidateComsumer using following parameters;
	 * @param target target string
	 * @param maxPuns maximum number of puns
	 */
	public CandidatesConsumer(String target, int maxPuns) {
		this.target = target;
		this.maxPuns = maxPuns;
	}
	
	
	/**
	 * This method fetch the result list;
	 * @return a list of put result
	 */
	public List<String> getResult() {
		return result;
	}
	
	
	/**
	 * this method parse the candidate queue, send them to PunFilter
	 * to filter the candidates. Then a list of puns is generated;
	 * @param candidates a queue of potential pun candidates
	 */
	public void parseQueue(Queue<String> candidates) {
		
		PunFilter pf = new PunFilter(target ,maxPuns);
		while(!candidates.isEmpty()) {
			String candidate = candidates.poll();
			int[] punInfo = pf.findPun(candidate);
			pf.storePun(candidate, punInfo);
		}
		generateList(pf);
	}
	
	
	/**
	 * this method transform priority pun queue to a pun list;
	 * @param pf a PunFilter stores puns;
	 */
	private void generateList(PunFilter pf) {
		result = new LinkedList<String>();
		Queue<Pun> puns = pf.getPuns();
		while(!puns.isEmpty()) {
			Pun pun = puns.poll();
			result.add(0, pun.getPunWord()+"("+pun.getOriWord()+")");
		}
	}
	
}
