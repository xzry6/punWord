package xiaozhou3;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import xiaozhou3.PunFilter;


/**
 * This class is used to test class PunFilter and its functions;
 * 
 * @author Sean
 *
 */
public class PunFilterTest {

	
	@Before
	public void setUp() throws Exception {
	}
	
	/**
	 * Test findPun() method, check
	 * if punInfo is null;
	 * if punInfo[0] is the start position in candidate matching target word;
	 * if punInfo[1] is the end position in candidate matching target word;
	 * if punInfo[2] is the levenshtein distance between candidate and word;
	 * 
	 * cancel the annotation to show the result;
	 */
	@Test(timeout = 1000)
	public void testFindPun() {
		PunFilter pf = new PunFilter("PUN", 100);
		String candidate = "pond";
		int[] punInfo = pf.findPun(candidate);

		assertNotNull(punInfo);
		assertEquals(punInfo[0], 0);
		assertEquals(punInfo[1], 3);
		assertEquals(punInfo[2], 1);
		
//		System.out.println("start position: "+punInfo[0]);
//		System.out.println("end position: "+punInfo[1]);
//		System.out.println("distance: "+punInfo[2]);
//		System.out.println("related substring: "+candidate.substring(punInfo[0], punInfo[1]));

	}
	
	
	/**
	 * Test storePun() method, check
	 * if PriorityQueue Pun is null;
	 * if Pun word are stored in expected order;
	 * if Queue contains duplicate words;
	 */
	@Test(timeout = 1000)
	public void testStorePun() {
		PunFilter pf = new PunFilter("PUN", 100);
		Set<String> puns = new HashSet<String>();
		Set<String> originals = new HashSet<String>();
		
		String candidate = "pond";
		int[] punInfo = pf.findPun(candidate);
		pf.storePun(candidate, punInfo);
		
		candidate = "punctual";
		punInfo = pf.findPun(candidate);
		pf.storePun(candidate, punInfo);
		
		candidate = "pend";
		punInfo = pf.findPun(candidate);
		pf.storePun(candidate, punInfo);

		assertNotNull(pf.getPuns());
		assertEquals(pf.getPuns().peek().getPunWord(), "PUNd");
		assertEquals(pf.getPuns().peek().getOriWord(), "pond");
		
		while(!pf.getPuns().isEmpty()) {
			Pun pun = pf.getPuns().poll();
			System.out.print(pun.getPunWord()+" ");
			//check if contain duplicate word
			assertTrue(!puns.contains(pun.getPunWord()));
			assertTrue(!originals.contains(pun.getOriWord()));
		}
		System.out.println();
	}

}
