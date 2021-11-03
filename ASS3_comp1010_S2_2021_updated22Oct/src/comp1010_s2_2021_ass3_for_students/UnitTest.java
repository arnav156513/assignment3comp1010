package comp1010_s2_2021_ass3_for_students;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import java.util.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.lang.reflect.Method;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnitTest {

	// these variables are used together with Graded.java to track the number
	// of passed tests and the score awarded
	public static int score = 0;
	public static String result = "";
	public static String currentMethodName = null;
	ArrayList<String> methodsPassed = new ArrayList<String>();
	
	// random seed (seed change should not affect the tests)
	public static int seed = 1;
	// the size of medium and large test cases
	private static final int mLimit = 1000;
	private static final int xLimit = 2_000_000;
	
	// the valid candidates in the ballots
	private static final String[] candidates = {"Captain America", "Iron Man", "Black Panther", "Thanos", "Thor"};

	/* Here is the list of test cases that are used in the above unit tests.
	 * Please note that some were constructed in the setUpBeforeClass() method
	 */

	private static String[] testcase1 = {
		"Captain America", "Thanos", "Black Panther", "Thanos", "Thor",
		"Thor", "Thor", "Iron Man", "Thanos", "Thanos",
		"Thor", "Thanos", "Black Panther", "Thanos", "Black Panther",
		"Thor", "Black Panther", "Black Panther", "Iron Man", "Thor",
		"Iron Man", "Black Panther", "Captain America", "Thor", "Thor"
	};
	private static String[] testcase2 = {
		"Captain America", "Captain America", "Iron Man", "Captain America", "Thanos",
		"Captain America", "Thanos", "Thor", "Thor", "Captain America",
		"Captain America", "Captain America", "Black Panther", "Black Panther", "Captain America",
		"Captain America", "Captain America", "Captain America", "Thor", "Captain America",
		"Iron Man", "Captain America", "Iron Man", "Thanos", "Thanos"
	};
	
	private static String[] testcase3;
	private static String[] testcase4;
	private static String[] testcase5;
	private static String[] testcase6;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Random rgen = new Random(seed);
		// creates testcase3
		testcase3 = new String[mLimit];
		for(int i = 0; i < mLimit; i++) {
			int nextRandom = rgen.nextInt(100);
			if (nextRandom <= 22) testcase3[i] = candidates[0];
			else if (nextRandom <= 26) testcase3[i] = candidates[1];
			else if (nextRandom <= 42) testcase3[i] = candidates[2];
			else if (nextRandom <= 68) testcase3[i] = candidates[3];
			else  testcase3[i] = candidates[4];
		}
		// creates testcase4
		testcase4 = new String[mLimit];
		for(int i = 0; i < mLimit; i++) {
			int nextRandom = rgen.nextInt(100);
			if (nextRandom <= 8) testcase4[i] = candidates[0];
			else if (nextRandom <= 74) testcase4[i] = candidates[1];
			else if (nextRandom <= 79) testcase4[i] = candidates[2];
			else if (nextRandom <= 89) testcase4[i] = candidates[3];
			else  testcase4[i] = candidates[4];
		}
		// creates testcase5
		testcase5 = new String[xLimit];
		for(int i = 0; i < xLimit; i++) {
			int nextRandom = rgen.nextInt(100);
			if (nextRandom <= 17) testcase5[i] = candidates[0];
			else if (nextRandom <= 40) testcase5[i] = candidates[1];
			else if (nextRandom <= 62) testcase5[i] = candidates[2];
			else if (nextRandom <= 74) testcase5[i] = candidates[3];
			else  testcase5[i] = candidates[4];
		}
		
		// creates testcase6
		testcase6 = new String[xLimit];
		for(int i = 0; i < xLimit; i++) {
			int nextRandom = rgen.nextInt(100);
			if (nextRandom <= 6) testcase6[i] = candidates[0];
			else if (nextRandom <= 18) testcase6[i] = candidates[1];
			else if (nextRandom <= 23) testcase6[i] = candidates[2];
			else if (nextRandom <= 84) testcase6[i] = candidates[3];
			else  testcase6[i] = candidates[4];
		}
		
	}

	@BeforeEach
	public void setUp() throws Exception {
		currentMethodName = null;
		Timestamp.reset();
	}


	@Test
	@Order(1)
	@Timeout(1)
	@Graded(description = "Ballots.fill", marks = 5)
	public void testBallotFill() {
		Ballot b = new Ballot(1,null);
		Timestamp t = new Timestamp();

		// Case 1: normal usage
		assertTrue(b.fill(candidates[0],t));
		assertTrue(0 < b.timestamp.getTime());
		assertTrue(b.filled);
		assertEquals(t,b.timestamp);
		assertEquals(candidates[0],b.candidate);
		
		// Case 2: null parameters
		b = new Ballot(1,null);
		assertFalse(b.fill(null,null));
		assertFalse(b.fill(null,t));
		assertFalse(b.fill(candidates[0],null));
		assertFalse(b.filled);
		
		// Case 3: empty string as parameter
		b = new Ballot(1,null);
		assertTrue(b.fill("",t));
		assertEquals("",b.candidate);
		assertTrue(b.filled);
		
		// Case 4: ballot already filled
		assertFalse(b.fill(candidates[0],t));
		assertEquals("",b.candidate);

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(2)
	@Timeout(1)
	@Graded(description = "Ballots.isValid()", marks = 5)
	public void testBallotIsValid() {
		
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			Ballot v, nv;
			
			// Case 1: Invalid candidate
			nv = new Ballot(6,null);
			nv.fill("Batman",new Timestamp());
			assertFalse(nv.isValid(3, 7, candidates.clone()));
			
			// Case 2: Invalid id
			nv = new Ballot(6,null);
			nv.fill(candidates[0],new Timestamp());
			assertFalse(nv.isValid(1, 5, candidates.clone()));
			assertFalse(nv.isValid(7, 20, candidates.clone()));
			
			// Case 2: Valid ballot
			v = new Ballot(7,null);
			v.fill(candidates[1],new Timestamp());
			assertTrue(v.isValid(3, 7, candidates.clone()));
			assertTrue(v.isValid(7, 12, candidates.clone()));
		});
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	
	@Test
	@Order(3)
	@Timeout(1)
	@Graded(description = "EmptyBallots Constructor", marks = 5)
	public void testEmptyBallotsConstructor() {

		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {

			EmptyBallots e = new EmptyBallots(10);
			assertNotNull(e);
			assertNotNull(e.head);
			assertEquals(0,e.head.id);

			Ballot start = e.head;
			for (int i = 0; i < 10; i++) {
				assertEquals(i, start.id);
				start = start.next;
			}
			assertNull(start);
		});
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(4)
	@Timeout(1)
	@Graded(description = "EmptyBallots.remove()", marks = 5)
	public void testEmptyBallotsRemove() {
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(5);
			Ballot b0 = e.head;
			Ballot b3 = e.head.next.next.next;
			Ballot b4 = e.head.next.next.next.next;

			Ballot removed = e.remove();
			assertEquals(b0, removed);
			e.remove();
			e.remove();
			assertEquals(b3, e.remove());
			assertEquals(b4, e.remove());
			assertNull(e.remove());
			assertNull(e.head);

		});
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(5)
	@Timeout(1)
	@Graded(description = "EmptyBallots.isValid()", marks = 5)
	public void testEmptyBallotsIsValid() {
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(10);
			e.remove();
			e.remove();
			e.remove();
			assertTrue(e.isValid());

			e.head.next = e.head.next.next.next;
			assertFalse(e.isValid());
		});
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(6)
	@Timeout(1)
	@Graded(description = "FilledBallots.addVote()", marks = 5)
	public void testFilledBallotsAddVote() {
		
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e;
			FilledBallots f; 
			
			// Case 1: Add one ballot
			e = new EmptyBallots(100);
			f = new FilledBallots();
			assertNull(f.head);
			f.addVote(candidates[0],e,new Timestamp());
			assertNotNull(f.head);
			assertEquals(candidates[0],f.head.candidate);
			
			// Case 2: Add multiple ballots
			e = new EmptyBallots(100);
			f = new FilledBallots();
			assertNull(f.head);
			f.addVote(candidates[0],e,new Timestamp());
			f.addVote(candidates[1],e,new Timestamp());
			f.addVote(candidates[2],e,new Timestamp());
			f.addVote(candidates[0],e,new Timestamp());
			f.addVote(candidates[2],e,new Timestamp());

			assertNotNull(f.head);
			assertEquals(candidates[0],f.head.candidate);
			assertEquals(candidates[1],f.head.next.candidate);
			assertEquals(candidates[2],f.head.next.next.candidate);
			assertEquals(candidates[0],f.head.next.next.next.candidate);
			assertEquals(candidates[2],f.head.next.next.next.next.candidate);
			assertNull(f.head.next.next.next.next.next);
		});
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(7)
	@Timeout(5)
	@Graded(description = "FilledBallots.countVoteFor()", marks = 5)
	public void testFilledBallotsCountVoteFor() {
		
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e;
			FilledBallots f; 
			
			// Case 1: Empty list
			e = new EmptyBallots(100);
			f = new FilledBallots();
			assertEquals(0,f.countVoteFor(candidates[0]));
			assertEquals(0,f.countVoteFor(candidates[1]));
			
			// Case 2: List with only one name
			f.addVote(candidates[0],e,new Timestamp());
			assertEquals(1,f.countVoteFor(candidates[0]));
			assertEquals(0,f.countVoteFor(candidates[1]));
			f.addVote(candidates[0],e,new Timestamp());
			assertEquals(2,f.countVoteFor(candidates[0]));
			
			// Case 3: List with several names 
			e = new EmptyBallots(mLimit);
			f = new FilledBallots();
			for(String s : testcase3) 
				f.addVote(s, e, new Timestamp());
			assertEquals(216,f.countVoteFor(candidates[0]));
			assertEquals(44,f.countVoteFor(candidates[1]));
			assertEquals(146,f.countVoteFor(candidates[2]));
			assertEquals(263,f.countVoteFor(candidates[3]));
			assertEquals(331,f.countVoteFor(candidates[4]));
			assertEquals(0,f.countVoteFor("Spiderman"));
		});
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(8)
	@Timeout(1)
	@Graded(description = "FilledBallots.size()", marks = 5)
	public void testFilledBallotsSize() {
		
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e;
			FilledBallots f; 
			
			// Case 1: Empty list
			e = new EmptyBallots(100);
			f = new FilledBallots();
			assertEquals(0, f.size());
		
			// Case 2: Non-empty list (all added votes are valid)
			e = new EmptyBallots(100);
			f = new FilledBallots();
			for(int i = 0; i < 3; i++) f.addVote(candidates[0], e, new Timestamp());
			f.addVote(candidates[1],e,new Timestamp());
			for(int i = 0; i < 6; i++) f.addVote(candidates[2], e, new Timestamp());
			f.addVote(candidates[3],e,new Timestamp());
			for(int i = 0; i < 2; i++) f.addVote(candidates[1], e, new Timestamp());
			f.addVote(candidates[4],e,new Timestamp());
			for(int i = 0; i < 3; i++) f.addVote(candidates[3], e, new Timestamp());
			f.addVote(candidates[4],e,new Timestamp());
			assertEquals(18, f.size());
		});
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Order(9)
	@Timeout(1)
	@Graded(description = "FilledBallots.isValid()", marks = 5)
	public void testFilledBallotsIsValid() {
//		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e;
			FilledBallots f;

			// Case 1: Empty list
			f = new FilledBallots();
			assertTrue(f.isValid(0, 0, candidates.clone()));

			// Case 2: Valid list
			f = new FilledBallots();
			e = new EmptyBallots(mLimit);
			for(String s : testcase3) {
				f.addVote(s, e, new Timestamp());
			}
			assertTrue(f.isValid(0, mLimit, candidates.clone()));
			
			// Case 3: Invalid list
			f = new FilledBallots();
			e = new EmptyBallots(100);
			f.addVote(candidates[3], e, new Timestamp(5));
			f.addVote(candidates[2], e, new Timestamp(9));
			f.addVote("Spiderman", e, new Timestamp(12));
			assertFalse(f.isValid(0, 100, candidates.clone()));
			
			// Case 4: Invalid lists
			f = new FilledBallots();
			e = new EmptyBallots(100);
			f.addVote(candidates[0], e, new Timestamp(20));
			f.addVote(candidates[0], e, new Timestamp(9));
			f.addVote(candidates[0], e, new Timestamp(14));
			f.addVote(candidates[0], e, new Timestamp(17));
			assertFalse(f.isValid(0, 100, candidates.clone()));
			
			f = new FilledBallots();
			e = new EmptyBallots(100);
			f.addVote(candidates[0], e, new Timestamp(3));
			f.addVote(candidates[0], e, new Timestamp(9));
			f.addVote(candidates[0], e, new Timestamp(12));
			f.addVote(candidates[0], e, new Timestamp(27));
			f.addVote(candidates[0], e, new Timestamp(19));
			assertFalse(f.isValid(0, 100, candidates.clone()));
			

			
//		});

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(10)
	@Timeout(1)
	@Graded(description = "FilledBallots.insertBallot()", marks = 5)
	public void testFilledBallotsInsertBallot() {

		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(100);
			FilledBallots f0 = new FilledBallots();
			FilledBallots f1 = new FilledBallots();
			
			// because the only proper way to create a filled ballot is by calling addVote,
			// here we make a second list (f1) and put some ballots in there, which we will
			// insert later to the main list (f0) (to make it easier to check, we'll also
			// use the last candidate for all the ballots we're going to insert)
			Timestamp t0 = new Timestamp();
			f1.addVote(candidates[candidates.length-1],e,t0);
			Ballot b0 = f1.head;
			Timestamp t1 = new Timestamp();
			f1.addVote(candidates[candidates.length-1],e,t1);
			Ballot b1 = b0.next;
			assertNotNull(b0);
			assertNotNull(b1);

			// now insert random ballots
			Random localgen = new Random(seed);
			for(int i = 0; i < 8; i++) 
				f0.addVote(candidates[localgen.nextInt(candidates.length-1)], e, new Timestamp());


			// create two more ballots to be inserted later
			Timestamp t2 = new Timestamp();
			f1.addVote(candidates[candidates.length-1],e,t2);
			Ballot b2 = b1.next;
			Timestamp t3 = new Timestamp();
			f1.addVote(candidates[candidates.length-1],e,t3);
			Ballot b3 = b2.next;
			assertNotNull(b2);
			assertNotNull(b3);

			// add more random ballots
			for(int i = 0; i < 7; i++) 
				f0.addVote(candidates[localgen.nextInt(candidates.length-1)], e, new Timestamp());

			// and the final two ballots to put at the end
			Timestamp t4 = new Timestamp();
			f1.addVote(candidates[candidates.length-1],e,t4);
			Ballot b4 = b3.next;
			Timestamp t5 = new Timestamp();
			f1.addVote(candidates[candidates.length-1],e,t5);
			Ballot b5 = b4.next;
			assertNotNull(b4);
			assertNotNull(b5);

			// size should be 15 before we insert anything
			assertEquals(15,f0.size());

			// Case 1: inserting in the middle (b2)
			f0.insertBallot(b2);
			assertEquals(16,f0.size());
			
			Ballot current = f0.head;
			for(int i = 0; i < 7; i++)
				current = current.next;
			assertEquals(1,t2.compareTo(current.timestamp));
			current = current.next;
			assertEquals(candidates[candidates.length-1],current.candidate);
			assertEquals(0,t2.compareTo(current.timestamp));
			assertEquals(-1,t2.compareTo(current.next.timestamp));
			assertEquals(b2,current);
			
			// Case 2: inserting in the middle (b3)
			f0.insertBallot(b3);
			assertEquals(17,f0.size());

			current = f0.head;
			for(int i = 0; i < 8; i++)
				current = current.next;
			assertEquals(1,t3.compareTo(current.timestamp));
			current = current.next;
			assertEquals(candidates[candidates.length-1],current.candidate);
			assertEquals(0,t3.compareTo(current.timestamp));
			assertEquals(-1,t3.compareTo(current.next.timestamp));
			assertEquals(b3,current);
			
			// Case 3: inserting at the end (b5)
			f0.insertBallot(b5);
			assertEquals(18,f0.size());
			current = f0.head;
			for(int i = 0; i < 16; i++)
				current = current.next;
			assertEquals(1,t5.compareTo(current.timestamp));
			current = current.next;
			assertEquals(candidates[candidates.length-1],current.candidate);
			assertEquals(0,t5.compareTo(current.timestamp));
			assertNull(current.next);
			assertEquals(b5,current);
		
			// Case 4: inserting at the end (b4)
			f0.insertBallot(b4);
			assertEquals(19,f0.size());
			current = f0.head;
			for(int i = 0; i < 16; i++)
				current = current.next;
			assertEquals(1,t4.compareTo(current.timestamp));
			current = current.next;
			assertEquals(candidates[candidates.length-1],current.candidate);
			assertEquals(0,t4.compareTo(current.timestamp));
			assertEquals(b4,current);
			assertEquals(b5,current.next);
			
			// Case 5: insert at the start (b0 and b1)
			f0.insertBallot(b1);
			assertEquals(20,f0.size());
			current = f0.head;

			assertEquals(b1,current);
			assertEquals(0,t1.compareTo(current.timestamp));
			assertEquals(candidates[candidates.length-1],current.candidate);
			assertNotNull(current.next);
			assertEquals(b1,current);
			
			f0.insertBallot(b0);
			assertEquals(21,f0.size());
			current = f0.head;

			assertEquals(b0,current);
			assertEquals(b1,current.next);
			assertEquals(0,t0.compareTo(current.timestamp));
			assertEquals(-1,t0.compareTo(current.next.timestamp));
			assertEquals(candidates[candidates.length-1],current.candidate);
			assertNotNull(current.next);
		});
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Order(11)
	@Timeout(1)
	@Graded(description = "FilledBallots.removeLateBallots()", marks = 5)

	public void testFilledBallotsRemoveLateBallot() {
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {

			EmptyBallots e = new EmptyBallots(100);
			FilledBallots f;
			
			
			// Case 1: Nothing to remove
			f = new FilledBallots();
			f.addVote(candidates[0], e, new Timestamp());
			Timestamp t = new Timestamp();
			f.removeLateBallots(t);
			assertNotNull(f);
			assertEquals(1,f.size());
			assertEquals(1,t.compareTo(f.head.timestamp));
			
			
			// Case 2: Remove several times
			f = new FilledBallots();
			Timestamp t0 = new Timestamp();
			// insert random ballots
			Random localgen = new Random(seed);
			for(int i = 0; i < 8; i++) 
				f.addVote(candidates[localgen.nextInt(candidates.length)], e, new Timestamp());

			Timestamp t1 = new Timestamp();
			// insert more random ballots
			for(int i = 0; i < 6; i++) 
				f.addVote(candidates[localgen.nextInt(candidates.length)], e, new Timestamp());

			Timestamp t2 = new Timestamp();
			// insert more random ballots
			for(int i = 0; i < 5; i++) 
				f.addVote(candidates[localgen.nextInt(candidates.length)], e, new Timestamp());
			
			f.removeLateBallots(t2);
			assertNotNull(f);
			assertEquals(14,f.size());
			Ballot current = f.head;
			while (current != null) {
				assertEquals(1,t2.compareTo(current.timestamp));
				current = current.next;
			}
			
			f.removeLateBallots(t1);
			assertEquals(8,f.size());
			current = f.head;
			while (current != null) {
				assertEquals(1,t1.compareTo(current.timestamp));
				current = current.next;
			}
			
			f.removeLateBallots(t0);
			assertEquals(0,f.size());
			assertNull(f.head);
			
		});

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Order(12)
	@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
	@Graded(description = "FilledBallots.addVote() (Advanced)", marks = 5)
	public void testFilledBallotsAddVoteAdvanced() {

		// Case 1: Medium size
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(mLimit);
			FilledBallots f = new FilledBallots();
			for(String s : testcase3) {
				f.addVote(s, e, new Timestamp());
			}
			assertNotNull(f);
			Ballot cur = f.head;
			for(int i = 0; i < 200; i++) cur = cur.next;
			assertEquals(candidates[4],cur.candidate);
			assertEquals(candidates[0],cur.next.candidate);
			assertEquals(candidates[4],cur.next.next.candidate);
			for(int i = 0; i < 500; i++) cur = cur.next;
			assertEquals(candidates[3],cur.candidate);
			assertEquals(candidates[3],cur.next.candidate);
			assertEquals(candidates[0],cur.next.next.candidate);
		});

		// Case 2: Large size
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(xLimit);
			FilledBallots f = new FilledBallots();
			for(String s : testcase5) {
				f.addVote(s, e, new Timestamp());
			}
			assertNotNull(f);
			Ballot cur = f.head;
			for(int i = 0; i < 200000; i++) cur = cur.next;
			assertEquals(candidates[2],cur.candidate);
			assertEquals(candidates[2],cur.next.candidate);
			assertEquals(candidates[4],cur.next.next.candidate);
			for(int i = 0; i < 500000; i++) cur = cur.next;
			assertEquals(candidates[0],cur.candidate);
			assertEquals(candidates[3],cur.next.candidate);
			assertEquals(candidates[2],cur.next.next.candidate);
		});

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}
	
	@Test
	@Order(13)
	@Timeout(1)
	@Graded(description = "FilledBallots.size() (Advanced)", marks = 4)
	public void testFilledBallotSizeAdvanced() {
		
		// Case 1: Medium size
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(mLimit);
			FilledBallots f = new FilledBallots();
			for(int i = 0; i < 573; i++)
				f.addVote(testcase3[i], e, new Timestamp());
			assertNotNull(f);
			assertEquals(573,f.size());
		});
	
		// Case 2: Large size
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(xLimit);
			FilledBallots f = new FilledBallots();
			for(int i = 0; i < 956142; i++)
				f.addVote(testcase5[i], e, new Timestamp());
			assertNotNull(f);
			for(int i = 0; i < 1000; i++)
				assertEquals(956142,f.size());
		});

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(14)
	@Timeout(1)
	@Graded(description = "FilledBallots.removeBlankBallots()", marks = 5)
	public void testFilledBallotsRemoveBlankBallots() {

		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			
			EmptyBallots e;
			FilledBallots f;
		
			// Case 1: Empty list
			e = new EmptyBallots(100);
			f = new FilledBallots();
			assertFalse(f.removeBlankBallots());
			assertEquals(0,f.size());
			
			// Case 2: List with no blank ballots
			e = new EmptyBallots(mLimit);
			f = new FilledBallots();
			for(String s : testcase3) 
				f.addVote(s, e, new Timestamp());
			assertFalse(f.removeBlankBallots());
			assertEquals(mLimit,f.size());
			
			// Case 3: List with some blank ballots
			e = new EmptyBallots(100);
			f = new FilledBallots();
			f.addVote("", e, new Timestamp());
			f.addVote("", e, new Timestamp());
			for(int i = 0; i < 2; i++) f.addVote(candidates[1], e, new Timestamp());
			f.addVote("", e, new Timestamp());
			for(int i = 0; i < 4; i++) f.addVote(candidates[2], e, new Timestamp());
			f.addVote("", e, new Timestamp());
			for(int i = 0; i < 3; i++) f.addVote(candidates[1], e, new Timestamp());
			f.addVote("", e, new Timestamp());
			f.addVote("", e, new Timestamp());
			f.addVote("", e, new Timestamp());
			f.addVote("", e, new Timestamp());
			for(int i = 0; i < 7; i++) f.addVote(candidates[3], e, new Timestamp());
			f.addVote("", e, new Timestamp());
			f.addVote("", e, new Timestamp());

			assertTrue(f.removeBlankBallots());
			assertEquals(candidates[1],f.head.candidate);
			assertEquals(candidates[2],f.head.next.next.candidate);
			assertEquals(16,f.size());
			
			// Case 4: List with only blank ballots
			e = new EmptyBallots(100);
			f = new FilledBallots();
			f.addVote("", e, new Timestamp());
			f.addVote("", e, new Timestamp());
			f.addVote("", e, new Timestamp());
			assertTrue(f.removeBlankBallots());
			assertNull(f.head);
			assertEquals(0,f.size());
			
		});
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(15)
	@Timeout(1)
	@Graded(description = "FilledBallots.removeInvalidBallots()", marks = 5)
	public void testFilledBallotsRemoveInvalidBallots() {

		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			
			EmptyBallots e;
			FilledBallots f;
		
			// Case 1: Empty list
			e = new EmptyBallots(100);
			f = new FilledBallots();
			assertFalse(f.removeInvalidBallots(candidates.clone()));
		
			// Case 2: List with only valid names
			e = new EmptyBallots(mLimit);
			f = new FilledBallots();
			for(String s : testcase3) 
				f.addVote(s, e, new Timestamp());
			assertFalse(f.removeInvalidBallots(candidates.clone()));
			assertEquals(mLimit,f.size());
			
			// Case 3: List with invalid candidates
			e = new EmptyBallots(100);
			f = new FilledBallots();
			for(int i = 0; i < 2; i++) f.addVote("Batman", e, new Timestamp());
			for(int i = 0; i < 2; i++) f.addVote("Flash", e, new Timestamp());
			for(int i = 0; i < 4; i++) f.addVote(candidates[1], e, new Timestamp());
			f.addVote("Superman", e, new Timestamp());
			for(int i = 0; i < 1; i++) f.addVote(candidates[2], e, new Timestamp());
			f.addVote("Batman", e, new Timestamp());
			for(int i = 0; i < 2; i++) f.addVote(candidates[1], e, new Timestamp());
			f.addVote("Batman", e, new Timestamp());
			f.addVote("Batman", e, new Timestamp());
			for(int i = 0; i < 4; i++) f.addVote(candidates[3], e, new Timestamp());
			for(int i = 0; i < 7; i++) f.addVote(candidates[1], e, new Timestamp());
			f.addVote("Batman", e, new Timestamp());
			f.addVote("The Joker", e, new Timestamp());
			f.addVote("Batman", e, new Timestamp());

			assertTrue(f.removeInvalidBallots(candidates.clone()));
			assertEquals(18,f.size());
			
			// Case 4: List with only invalid candidates
			e = new EmptyBallots(100);
			f = new FilledBallots();
			f.addVote("Superman", e, new Timestamp());
			f.addVote("Superman", e, new Timestamp());
			f.addVote("Batman", e, new Timestamp());
			f.addVote("Batman", e, new Timestamp());

			assertTrue(f.removeInvalidBallots(candidates.clone()));
			assertEquals(0,f.size());
			
		});
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}


	@Test
	@Order(16)
	@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
	@Graded(description = "FilledBallots.findPercentages()", marks = 5)
	public void testFilledBallotsFindPercentages() {
		
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e;
			FilledBallots f;
			Double output[]; // array to contain the results
			
			// Case 1: All votes going to one candidate
			e = new EmptyBallots(100);
			f = new FilledBallots();
			for(int i = 0; i < 50; i++)
				f.addVote(candidates[0], e, new Timestamp());
			output = f.findPercentages(candidates.clone());
			assertNotNull(output);
			assertEquals(5,output.length);
			assertEquals(100.0,output[0],0.1);
			assertEquals(0.0,output[1],0.1);
			assertEquals(0.0,output[2],0.1);
			assertEquals(0.0,output[3],0.1);
			assertEquals(0.0,output[3],0.1);
	
			// Case 2: Empty list
			e = new EmptyBallots(100);
			f = new FilledBallots();
			output = f.findPercentages(candidates.clone());
			assertNotNull(output);
			assertEquals(5,output.length);
			assertEquals(0.0,output[0],0.1);
			assertEquals(0.0,output[1],0.1);
			assertEquals(0.0,output[2],0.1);
			assertEquals(0.0,output[3],0.1);
			assertEquals(0.0,output[4],0.1);
			
			// Case 3: List containing invalid candidates
			e = new EmptyBallots(100);
			f = new FilledBallots();
			for(int i = 0; i < 2; i++) f.addVote("Batman", e, new Timestamp());
			for(int i = 0; i < 2; i++) f.addVote("Flash", e, new Timestamp());
			for(int i = 0; i < 4; i++) f.addVote(candidates[1], e, new Timestamp());
			f.addVote("Superman", e, new Timestamp());
			for(int i = 0; i < 1; i++) f.addVote(candidates[2], e, new Timestamp());
			f.addVote("Batman", e, new Timestamp());
			for(int i = 0; i < 2; i++) f.addVote(candidates[1], e, new Timestamp());
			f.addVote("Batman", e, new Timestamp());
			f.addVote("Batman", e, new Timestamp());
			for(int i = 0; i < 4; i++) f.addVote(candidates[3], e, new Timestamp());
			for(int i = 0; i < 7; i++) f.addVote(candidates[1], e, new Timestamp());
			f.addVote("Batman", e, new Timestamp());
			f.addVote("The Joker", e, new Timestamp());
			f.addVote("Batman", e, new Timestamp());
			output = f.findPercentages(candidates.clone());
			assertNotNull(output);
			assertEquals(5,output.length);
			assertEquals(0.0,output[0],0.1);
			assertEquals(72.22222,output[1],0.1);
			assertEquals( 5.55556,output[2],0.1);
			assertEquals(22.22222,output[3],0.1);
			assertEquals(0.0,output[4],0.1);

			// Case 4: List with several candidates
			e = new EmptyBallots(mLimit);
			f = new FilledBallots();
			for(String s : testcase3) 
				f.addVote(s, e, new Timestamp());
			output = f.findPercentages(candidates.clone());
			assertNotNull(output);
			assertEquals(5,output.length);
			assertEquals(21.6,output[0],0.01);
			assertEquals( 4.4,output[1],0.01);
			assertEquals(14.6,output[2],0.01);
			assertEquals(26.3,output[3],0.01);
			assertEquals(33.1,output[4],0.01);
		
		});

		// Case 5: Large test case 1
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(xLimit);
			FilledBallots f = new FilledBallots();
			for(String s : testcase5) 
				f.addVote(s, e, new Timestamp());
			Double[] output = f.findPercentages(candidates.clone());
			assertNotNull(output);
			assertEquals(5,output.length);
			assertEquals(18.02200,output[0],0.01);
			assertEquals(22.98895,output[1],0.01);
			assertEquals(22.01880,output[2],0.01);
			assertEquals(12.01085,output[3],0.01);
			assertEquals(24.95940,output[4],0.01);
		});
		
		// Case 6: Large test case 2
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(xLimit);
			FilledBallots f = new FilledBallots();
			for(String s : testcase6) 
				f.addVote(s, e, new Timestamp());
			Double[] output = f.findPercentages(candidates.clone());
			assertNotNull(output);
			assertEquals(5,output.length);
			assertEquals( 7.00420,output[0],0.01);
			assertEquals(11.98825,output[1],0.01);
			assertEquals( 5.00050,output[2],0.01);
			assertEquals(60.97635,output[3],0.01);
			assertEquals(15.03070,output[4],0.01);
		});
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(17)
	@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
	@Graded(description = "FilledBallots.findMajority()", marks = 5)
	public void testFilledBallotsFindMajority() {
		
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(mLimit);
			FilledBallots f = new FilledBallots();
			
			// Case 1: Empty list
			assertEquals(null,f.findMajority());
			
			// Case 2: List with one ballot
			f.addVote(candidates[0], e, new Timestamp());
			assertEquals(candidates[0],f.findMajority());
			
		});
		// Case 3: Medium test case - no majority
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(mLimit);
			FilledBallots f = new FilledBallots();
			for(String s : testcase3) 
				f.addVote(s, e, new Timestamp());
			assertNull(f.findMajority());
		});
		
		// Case 4: Medium test case - majority
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(mLimit);
			FilledBallots f = new FilledBallots();
			for(String s : testcase4) 
				f.addVote(s, e, new Timestamp());
			assertEquals(candidates[1],f.findMajority());
		});
		
		// Case 5: Large test case - no majority
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(xLimit);
			FilledBallots f = new FilledBallots();
			for(String s : testcase5) 
				f.addVote(s, e, new Timestamp());
			assertNull(f.findMajority());
		});
		
		// Case 6: Large test case - majority
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(xLimit);
			FilledBallots f = new FilledBallots();
			for(String s : testcase6) 
				f.addVote(s, e, new Timestamp());
			assertEquals(candidates[3],f.findMajority());
		});	
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(18)
	@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
	@Graded(description = "FilledBallots Constructor from two FilledBallots", marks = 8)
	public void testFilledBallotsConstructorTwoFilledBallots() {
		
		// Case 1: Null tests
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(100);
			FilledBallots f1 = new FilledBallots();

			FilledBallots f1f2 = new FilledBallots(f1,null);
			assertNull(f1f2.head);
			assertEquals(0,f1f2.size());
			
			FilledBallots f2 = new FilledBallots();
			f2.addVote(candidates[1], e, new Timestamp());
			f2.addVote(candidates[2], e, new Timestamp());
			f2.addVote(candidates[3], e, new Timestamp());
			Ballot b1 = f2.head;
			
			f1f2 = new FilledBallots(null,f2);
			assertNotNull(f1f2.head);
			assertEquals(b1,f1f2.head);
			assertEquals(3,f1f2.size());
		});
		
		// Case 2: One empty list
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(100);
			FilledBallots f1 = new FilledBallots();
			FilledBallots f2 = new FilledBallots();

			f1.addVote(candidates[1], e, new Timestamp());
			f1.addVote(candidates[2], e, new Timestamp());
			f1.addVote(candidates[3], e, new Timestamp());
			
			Ballot b1 = f1.head;
			Ballot b2 = b1.next;
			Ballot b3 = b2.next;
			
			FilledBallots f1f2 = new FilledBallots(f1,f2);
			Ballot head = f1f2.head;
			Ballot current = head;
			
			assertEquals(b1,current);
			assertEquals(b2,current.next);
			assertEquals(b3,current.next.next);
			assertNull(current.next.next.next);
			assertEquals(3,f1f2.size());

		});

		// Case 3: Small test case
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(100);
			FilledBallots f1 = new FilledBallots();
			FilledBallots f2 = new FilledBallots();

			f2.addVote(candidates[0], e, new Timestamp());
			f1.addVote(candidates[1], e, new Timestamp());
			f1.addVote(candidates[2], e, new Timestamp());
			f1.addVote(candidates[3], e, new Timestamp());
			
			f1.addVote(candidates[1], e, new Timestamp());
			f1.addVote(candidates[2], e, new Timestamp());
			f2.addVote(candidates[2], e, new Timestamp());
			f1.addVote(candidates[3], e, new Timestamp());
			
			f2.addVote(candidates[0], e, new Timestamp());
			f2.addVote(candidates[3], e, new Timestamp());
			f1.addVote(candidates[4], e, new Timestamp());
			f1.addVote(candidates[4], e, new Timestamp());
			f1.addVote(candidates[2], e, new Timestamp());

			Ballot b1 = f2.head;
			Ballot b2 = f1.head;
			
			FilledBallots f1f2 = new FilledBallots(f1, f2);
			Ballot head = f1f2.head;
			Ballot current = head;
			assertEquals(b1,current);
			assertEquals(b2,current.next);

			assertEquals(candidates[0], current.candidate);
			for (int i = 0; i < 4; i++) current = current.next;
			assertEquals(candidates[1], current.candidate);
			for (int i = 0; i < 3; i++) current = current.next;
			assertEquals(candidates[3], current.candidate);
			current = current.next;
			assertEquals(candidates[0], current.candidate);
			current = current.next.next;
			assertEquals(candidates[4], current.candidate);
			current = current.next;
			assertEquals(candidates[4], current.candidate);
			assertNull(current.next.next);
			assertEquals(b1,f1f2.head);
			assertEquals(13,f1f2.size());
		});

		// Case 4: Large test case
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(xLimit);
			
			// create the two ballots we are going to merge
			FilledBallots f1 = new FilledBallots();
			FilledBallots f2 = new FilledBallots();
			
			// create an array of ballots to store the references of
			// each ballot we're going to put in f1 and f2
			Ballot[] check = new Ballot[xLimit];
			
			// put one vote in f1 and f2 (names taken from first two entries
			// in testcase5, and keep their reference in the array 'check'
			f1.addVote(testcase5[0], e, new Timestamp());
			f2.addVote(testcase5[1], e, new Timestamp());
			Ballot cf1 = f1.head; check[0] = cf1;
			Ballot cf2 = f2.head; check[1] = cf2;

			// then continue putting votes in f1 and f2, and put the references
			// in the array 'check' (put even entries in f1, and odd entries in f2)
			for(int i = 2; i < xLimit; i+=2) {
				f1.addVote(testcase5[i], e, new Timestamp());
				cf1 = cf1.next; check[i] = cf1;
				f2.addVote(testcase5[i+1], e, new Timestamp());
				cf2 = cf2.next; check[i+1] = cf2;
			}

			// now create the merged list and check for correctness
			FilledBallots f1f2 = new FilledBallots(f1, f2);
			Ballot cf = f1f2.head;
			assertEquals(xLimit, f1f2.size());
			for(int i = 0; i < xLimit; i+=2) {
				assertEquals(check[i],cf);
				assertEquals(testcase5[i],cf.candidate);
				assertEquals(check[i+1],cf.next);
				assertEquals(testcase5[i+1],cf.next.candidate);
				cf = cf.next.next;
			}
			assertNull(cf);
		
		});
		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@Test
	@Order(19)
	@Timeout(value = 2000, unit = TimeUnit.MILLISECONDS)
	@Graded(description = "FilledBallots Constructor from array", marks = 8)
	public void testFilledBallotsConsructorFromArray() {
		
		
		// Case 1: Null and empty lists
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			
			EmptyBallots e = new EmptyBallots(100);
			FilledBallots[] list = new FilledBallots[4];
			list[0] = null;
			list[1] = null;
			list[2] = null;
			list[3] = null;
			
			FilledBallots ff = new FilledBallots(list);
			assertNotNull(ff);
			assertNull(ff.head);
			assertEquals(0,ff.size());
			
			list = new FilledBallots[4];
			list[0] = null;
			list[1] = new FilledBallots();
			list[2] = new FilledBallots();
			list[3] = null;

			ff = new FilledBallots(list);
			assertNotNull(ff);
			assertNull(ff.head);
			assertEquals(0,ff.size());
			
			list = new FilledBallots[4];
			list[0] = null;
			list[1] = new FilledBallots();
			list[2] = new FilledBallots();
			list[3] = new FilledBallots();
			list[3].addVote(candidates[0], e, new Timestamp());
			Ballot b1 = list[3].head;
			list[1].addVote(candidates[0], e, new Timestamp());
			Ballot b2 = list[1].head;
			list[3].addVote(candidates[0], e, new Timestamp());
			Ballot b3 = list[3].head.next;
			list[3].addVote(candidates[0], e, new Timestamp());
			Ballot b4 = list[3].head.next.next;

			ff = new FilledBallots(list);
			assertNotNull(ff);
			assertNotNull(ff.head);
			assertEquals(4,ff.size());
			assertEquals(b1,ff.head);
			assertEquals(b2,ff.head.next);
			assertEquals(b3,ff.head.next.next);
			assertEquals(b4,ff.head.next.next.next);
			assertNull(ff.head.next.next.next.next);
		});
		
		// Case 2: Small test case
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			EmptyBallots e = new EmptyBallots(100);
			FilledBallots[] list = new FilledBallots[4];
			list[0] = new FilledBallots();
			list[1] = new FilledBallots();
			list[2] = new FilledBallots();
			list[3] = new FilledBallots();
			
			list[0].addVote(candidates[0], e, new Timestamp());
			list[3].addVote(candidates[2], e, new Timestamp());
			list[1].addVote(candidates[3], e, new Timestamp());
			list[0].addVote(candidates[1], e, new Timestamp());
			list[2].addVote(candidates[2], e, new Timestamp());
			Ballot b1 = list[0].head;
			Ballot b2 = list[3].head;
			Ballot b3 = list[1].head;
			Ballot b4 = list[2].head;

			list[0].addVote(candidates[4], e, new Timestamp());
			list[2].addVote(candidates[0], e, new Timestamp());
			list[1].addVote(candidates[4], e, new Timestamp());
			list[3].addVote(candidates[0], e, new Timestamp());
			list[1].addVote(candidates[2], e, new Timestamp());
			Ballot b5 = list[3].head.next;

			list[0].addVote(candidates[1], e, new Timestamp());
			list[3].addVote(candidates[2], e, new Timestamp());
			list[1].addVote(candidates[4], e, new Timestamp());
			list[0].addVote(candidates[3], e, new Timestamp());
			list[2].addVote(candidates[2], e, new Timestamp());
			Ballot b6 = list[0].head.next.next.next.next;
			
			list[1].addVote(candidates[4], e, new Timestamp());
			list[1].addVote(candidates[2], e, new Timestamp());
			list[2].addVote(candidates[3], e, new Timestamp());
			list[0].addVote(candidates[1], e, new Timestamp());
			list[3].addVote(candidates[1], e, new Timestamp());
			
			FilledBallots ff = new FilledBallots(list);
			assertNotNull(ff);
			assertNotNull(ff.head);
			assertEquals(20,ff.size());
			assertEquals(b1,ff.head);
			assertEquals(b2,ff.head.next);
			assertEquals(b3,ff.head.next.next);
			assertEquals(b4,ff.head.next.next.next.next);
			Ballot current = ff.head;
			for(int i = 0; i < 8; i++) 
				current = current.next;
			assertEquals(b5,current);
			for(int i = 0; i < 5; i++) 
				current = current.next;
			assertEquals(b6,current);
			for(int i = 0; i < 7; i++) 
				current = current.next;
			assertNull(current);
				
		});
		
		// Case 3: Large test case
		assertTimeoutPreemptively(Duration.ofMillis(1000), () -> {
			// similar approach the large test case in previous unit test, but the
			// ballots are randomly distributed among n different lists in an array
			int n = 6;
			EmptyBallots e = new EmptyBallots(xLimit);
			FilledBallots[] list = new FilledBallots[n];
			Ballot[] check = new Ballot[xLimit];
			Ballot[] pointer = new Ballot[n];
			Random localgen = new Random(seed);

			int count = 0;
			for(int i = 0; i < list.length; i++) {
				list[i] = new FilledBallots();
				list[i].addVote(testcase5[count], e, new Timestamp());
				pointer[i] = list[i].head;
				check[count] = list[i].head;
				count++;
			}
			for(int i = 0; i < xLimit-n; i++) {
				int rand = localgen.nextInt(n);
				list[rand].addVote(testcase5[count], e, new Timestamp());
				pointer[rand] = pointer[rand].next;
				check[count] = pointer[rand];
				count++;
			}
			
			
			FilledBallots ff = new FilledBallots(list);
			Ballot cf = ff.head;
			assertEquals(xLimit, ff.size());
			for(int i = 0; i < xLimit; i++) {
				assertEquals(check[i],cf);
				assertEquals(testcase5[i],cf.candidate);
				cf = cf.next;
			}
			assertNull(cf);
			
			
		});

		currentMethodName = new Throwable().getStackTrace()[0].getMethodName();
	}

	@AfterEach
	public void logSuccess() throws NoSuchMethodException, SecurityException {
		if (currentMethodName != null && !methodsPassed.contains(currentMethodName)) {
			methodsPassed.add(currentMethodName);
			Method method = getClass().getMethod(currentMethodName);
			Graded graded = method.getAnnotation(Graded.class);
			score += graded.marks();
			result += graded.description() + " passed. Marks awarded: " + graded.marks() + "\n";
		}
	}

	@AfterAll
	public static void wrapUp() throws IOException {
		if (result.length() != 0) {
			result = result.substring(0, result.length() - 1); // remove the last "\n"
		}
		System.out.println(result);
		System.out.println("\nIndicative mark: " + score + " out of 100");
		System.out.println();
	}
	
	

}
