package xiaozhou3;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

import xiaozhou3.CandidatesConsumer;


/**
 * This class is used to test class CandidatesComsumer;
 * 
 * @author Sean
 *
 */
public class CandidatesConsumerTest {
	
	@Before
	public void setUp() throws Exception {
	}
	
	
	/**
	 * Test parseQueue() method, check
	 * if result list is null;
	 * if result list has the expected number of puns;
	 * 
	 * cancel the annotation to show the result;
	 */
	@Test(timeout = 1000)
	public void testParseQueue() {
		CandidatesConsumer cc = new CandidatesConsumer("PUN", 100);
		Queue<String> queue = new LinkedList<String>();
		queue.offer("punctual");
		queue.offer("shark");
		queue.offer("punk");
		cc.parseQueue(queue);

		assertNotNull(cc.getResult());
		assertNotEquals(cc.getResult().size(), 0);
		assertEquals(cc.getResult().size(), 2);
	
//		for(String pun:cc.getResult())
//			System.out.println(pun+" ");
	}

}
