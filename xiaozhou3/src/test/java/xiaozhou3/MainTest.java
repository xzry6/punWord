package xiaozhou3;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import xiaozhou3.CandidatesConsumer;
import xiaozhou3.CandidatesProducer;

/**
 * This is the test class testing the main program;
 * 
 * @author Sean
 *
 */
public class MainTest {

	@Before
	public void setUp() throws Exception {
	}
	
	/**
	 * Test the whole program, then check 
	 * if the result list is null;
	 * if there is no puns;
	 * if every pun contains target in the list;
	 * 
	 * cancel the annotation to show the result;
	 */
	@Test(timeout = 1000)
	public void test() {
		String target = "DOG";
		String fileName = "DICTIONARY";
		int maxPuns = 1000;
		CandidatesConsumer cc = new CandidatesConsumer(target, maxPuns);
		
		cc.parseQueue(CandidatesProducer.readFrom(target, fileName));
		
		assertNotNull(cc.getResult());
		assertNotEquals(cc.getResult().size(), 0);

		for(String pun:cc.getResult())
			assertTrue(pun.contains(target));
		
//		print(cc.getResult(), target);
	}
	

	/**
	 * Test the whole program with iteration of several targets, check
	 * if the result list is null;
	 * if every pun contains target in the list;
	 * 
	 * cancel the annotation to show the result;
	 */
	@Test
	public void test2() {
		String fileName = "DICTIONARY";
		List<String> list = readList(fileName);
		int maxPuns = 10;
		
		for(String target:list) {
			target = target.toUpperCase();
			CandidatesConsumer cc = new CandidatesConsumer(target, maxPuns);
			
			cc.parseQueue(CandidatesProducer.readFrom(target, fileName));
			
			assertNotNull(cc.getResult());
			
			for(String pun:cc.getResult()) 
				assertTrue(pun.contains(target));
			
//			print(cc.getResult(), target);
		}
	}
	
	
	/**
	 * read all words with length smaller than 4 to a list;
	 * @param fileName dictionary directory
	 * @return a list of expected words
	 */
	private List<String> readList(String fileName) {
		List<String> list = new ArrayList<String>();
		try {
			File file = new File(fileName);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String word = null;
			while((word = reader.readLine()) != null) {
				if(word.length() > 4) continue;
				list.add(word);
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * print the result
	 */
	private void print(List<String> list, String target) {
		System.out.print(target.toUpperCase()+": ");
		for(String pun:list)
			System.out.print(pun+" ");
		System.out.println();
	}
}
