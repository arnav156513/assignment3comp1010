package comp1010_s2_2021_ass3_for_students;

import java.util.*;

/*
 * PLEASE DO NOT MODIFY THIS CLASS, IT IS NOT PART OF YOUR SUBMISSION
 * 
 * The Timestamp class is used to represent the timestamp on each ballot,
 * i.e. the time when the ballot was used to cast a vote. 
 * 
 * To keep things simple, we represent time using a single integer
 * (similar how to UNIX epoch time works) and, if generated randomly,
 * no two Timestamp objects will have the same time. 
 * 
 * You are encouraged to understand how the timestamps are generated
 * (by reading the constructors below), but this is not a requirement 
 * for the assignment, as in, you should never have to create new
 * Timestamp objects.
 * 
 * However, parts of the assignment requires you to compare the timestamps
 * on different ballots, and for this, you need to use the  compareTo
 * method.
 * 
 */


public class Timestamp {

	// The random number generator seed is set to 1 for
	// testing purposes (this ensures we always have the
	// same sequence of 'random' integers)
	private static Random rgen = new Random(1);
	
	// currentTime is the static variable shared by all Timestamp 
	// objects. You can think of this as the internal clock that 
	// gets incremented every time we create a new Timestamp object
	// (more info below)
	private static int currentTime = 0;

	// The variable used to hold the time
	private int time;
	
	/**
	 * Timestamp Constructor
	 * 
	 * Whenever we create a new Timestamp object, we increment
	 * the static variable currentTime and then assign its value
	 * to the variable time. 
	 * 
	 * Hence, the first time we create a Timestamp object, currentTime 
	 * will be incremented to a random number x between 1 and 20 
	 * (inclusive). The second Timestamp object will have the time
	 * equal to x + a random number between 1 and 20 (inclusive). 
	 *
	 * This ensures that no two Timestamps objects will have the same
	 * time. 
	 * 
	 * You can think of currentTime as a (real-life) clock, and that
	 * whenever you want to make a new timestamp, you look at the clock
	 * to record the current time (of course, the clock magically
	 * moves by 1 to 20 time units every time you look at it). 
	 */
	public Timestamp() {
		currentTime += rgen.nextInt(20)+1;
		this.time = currentTime;
	}
	
	/**
	 * The parameterised constructor is only used for testing. You should
	 * not use this for your submission.
	 * 
	 * @param time - the time for the timestamp
	 */
	public Timestamp(int time) {
		if (time < 0)
			this.time = 0;
		this.time = time;
	}
	
	
	/**
	 * Compares current Timestamp object with another. 
	 * 
	 * @param other - a Timestamp object to compare to
	 * @return  1 if this Timestamp has a higher time value,
	 *          0 if both Timestamps have the same value,
	 *         -1 otherwise
	 */
	public int compareTo(Timestamp other) {
		if (this.time < other.time)
			return -1;
		else if(this.time > other.time)
			return 1;
		else
			return 0;
	}
	
	/**
	 * Method used to reset currentTime (so that each JUnit test
	 * can start with currentTime = 0)
	 */
	public static void reset() {
		currentTime = 0;
	}
	
	/**
	 * Getter for the time
	 * 
	 * @return the time
	 */
	public int getTime() {
		return time;
	}
}
