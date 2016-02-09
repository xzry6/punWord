package xiaozhou3;

import static org.junit.Assert.*;

import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

import xiaozhou3.CandidatesProducer;


/**
 * This class is used to test class CandidatesProducer;
 * 
 * @author Sean
 *
 */
public class CandidatesProducerTest {

	@Before
	public void setUp() throws Exception {
	}

	
	/**
	 * Test readFrom() method, check
	 * if candidates queue is null;
	 * 
	 * cancel the annotation to show the result;
	 */
	@Test(timeout = 1000)
	public void testReadFrom() {
		Queue<String> queue = CandidatesProducer.readFrom("PUN", "DICTIONARY");
		assertNotNull(queue);
		
//		System.out.println("queue size is: "+queue.size());
//		System.out.print("potential pun candidates: ");
//		while(!queue.isEmpty())
//			System.out.print(queue.poll()+" ");
	}

}
