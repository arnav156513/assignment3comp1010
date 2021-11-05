//ID, NAME (4664228, Arnav Singh)

//Put x inside [X] below:
//[X] 	This assignment is entirely my own work and 
// 		I have not seen anyone else's code or design
package comp1010_s2_2021_ass3_for_students;



public class FilledBallots {

	public Ballot head;
	
	// add attributes as needed
	// needed to implement efficient code 
	// tested for `advanced' parts
	
	/**
	 * Default constructor
	 * 
	 * You may modify this constructor if you need to (e.g.
	 * if you want to initialise extra attributes in the class)
	 */
	public FilledBallots() {
		head = null;
		//
		// add initialisation of added attributes, if needed
		
	}
	

    	/**
	 * 5 marks - P level
	 * 
	 * Add a vote (a ballot) for a candidate to the END of the Filled list.
	 * 
	 * @param candidate - the name of the candidate voted for
         * @param ballots - the list of empty ballots where the official card comes from
         * @param a new time stamp
	 * 
	 */

	
	public void addVote(String candidate, EmptyBallots ballots, Timestamp time) {
		//
		// TODO - 5 marks
		Ballot add = new Ballot(ballots.head.id,null);
		ballots.head = add;
		add.candidate = candidate;
		add.timestamp = time;
		Ballot current = head;
		if(head==null) {
			head = add;
		}
		while(current!=null) {
			
			if(current.next==null) {
				current.next = add;
				break;
			}
			current = current.next;
		}
	}
	
	/**
	 * 5 marks, P level
	 * 
	 * Count the number of votes obtained by a candidate in a valid Filled list.
	 * 
	 * @param candidate	- the name of the candidate
	 * @return the number of votes obtained by the candidate
	 */
	public int countVoteFor(String candidate) {
		//
		// TODO - 5 marks
		Ballot current = head;
		int count = 0;
		while(current!=null) {
			if(current.candidate==candidate) {
				count++;
			}
			current = current.next;
		}
		return count;
	}
	
	
	/** 
	 * 5 marks P level
	 * 4 marks Advanced P level for efficiency
	 * 
	 * Count how many ballots we have in the list.
	 * 
	 * @return the number of ballots in the list
	 */
	public int size() {
		
		Ballot current = head;
		int count = 0;
		while(current!=null) {
				count++;
			current = current.next;
		}
		return count;
		
		
		
	}
	
	
	/**
	 * 5 marks
	 * 
	 * Check the integrity of the filled ballot list: 
	 * each ballot is valid and the list is in increasing time stamp order
	 * 
	 * @return true if the current list of ballots is valid,
	 *         false otherwise
	 */
	public boolean isValid(int first, int last, String[] candidates ) {
		//
		// TODO - 5 marks
		if(head==null) {
			return true;
		}
		boolean firstID = false;
		boolean lastID = false;

		int candidateYes = 0;
		int time = 0;
		Ballot current = head;
		
		if(current.next!=null) {
			if(current.timestamp.compareTo(current.next.timestamp) ==-1) {
				time++;
			}
		}
		
		while(current!=null) {
			if(current.next!=null) {
				if(current.next.id == first ) {
					firstID = true;
				}
				
				if(current.timestamp.compareTo(current.next.timestamp) ==-1) {
					time++;
				}
			}
			
			if(current.next==null) {
				if(current.id==first) {
					lastID = true;
				}
			}
	
			for(int i = 0; i < candidates.length; i++)  {
				if(current.candidate == candidates[i]) {
				candidateYes++;
				}
			}	
			
			current = current.next;
		}
		
		if(size() == candidateYes && lastID && firstID && time == size()) {
			return true;
		}
		
		return false;
	}
	

    	/**
	 * 5 marks
	 * 
	 * Insert a ballot to the Filled list such that the list remains sorted
	 * by timestamp.
	 * pre-condition: the Filled list is sorted by timestamp
	 * post-condition: the Filled list is sorted by timestamp
	 * 
	 * You may assume that the ballot to be inserted is valid and that
	 * its next field is initially null (it is not connected to another ballot before insertion).
	 * 
	 * @param b - the ballot to be inserted
	 */

	public void insertBallot(Ballot b) {
		//
		// TODO - 5 marks
		Timestamp bTime = b.timestamp;
		Ballot current = head.next;
		Ballot prev = head;

		
		// first case
		if(head.timestamp.compareTo(bTime)==1) {
			Ballot temp = new Ballot (0, head);
			temp = b;
			temp.next = head;
			
			head = temp;
		}
		
		while(current!=null) {
			// middle case
			if(prev.timestamp.compareTo(bTime)==-1 && current.timestamp.compareTo(bTime)==1 
					|| prev.timestamp.compareTo(bTime)==0 && current.timestamp.compareTo(bTime)==1) {
				b.next = current;
				prev.next = b;
			}
			// end case
			if(current.next==null) {
				if(current.timestamp.compareTo(bTime)==-1) {
					current.next = b;
				}
			}
			// increment
			prev = current;
			current = current.next;
		}
		
		

	}
	
	/**
	 * 5 marks - Pass level
	 * 
	 * Remove from this list all ballots that was added after the 
	 * cutoff time (given as Timestamp t), that is remove
	 * all ballots that has a timestamp strictly greater than t.
	 * 
	 * @param t - the out-of-time limit
	 * @return - true if at least one ballot was removed, false otherwise
	 *           (including if the list was empty)
	 */
	public boolean removeLateBallots(Timestamp t) {
		//
		// TODO - 5 marks	
		// atleast one value, meaning next is satisfied i think
		Ballot current = head.next;
		Ballot prev = head;
		int size = size();
		boolean changed = false;
		
		// checks first value in the list 
		if(head.timestamp.compareTo(t)==1) {
			head = null;
			return true;
		}
		
		while(current!=null) {			
			
			// determines if current timestamp is bigger than inserted timestamp 
			// changes the list so that 
			if(current.timestamp.compareTo(t)==1) {
//				temp.next = current.next;
//				current = temp;
				prev.next = current.next;
				current = prev;
				changed = true;
				
				head.next = head.next.next;
				
				
			}
			
			if(current.next==null) {
				if(current.timestamp.compareTo(t)==1) {
					current = null;
					return true;
				}
			}
			
			// increment
			prev = current;
			current = current.next;
		}
		
		if(changed) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 5 marks - Credit level
	 * 
	 * Remove all blank ballots from the list, that is,
	 * remove all ballots where the candidate field is
	 * not filled. 
	 * 
	 * You can assume that if the candidate field of a
	 * ballot is not empty, then it must have at least
	 * one alpha-numeric character (i.e. it cannot be
	 * just white spaces)
	 * 
	 * @return - true if at least one ballot was removed,
	 * 			 false otherwise (including if the list
	 *           is empty)
	 */
	public boolean removeBlankBallots() {
		//
		// TODO - 5 marks
		
		// empty list
		if(head==null) {
			return false;
		}
		
		// init variables 
		int size = size();
		Ballot current = head.next;
		Ballot prev = head;
		boolean changedStuff = false;
		
		// if the head.next is null and the entire thing is 1 long
		if(head.next==null) {
			if(head.candidate==null || head.candidate.isBlank()) {
				head = null;
				return true;
			}
		}
		
		while(current!=null) {
			if(current.candidate==null || current.candidate.isBlank()) {
				//delete stuff logic 
				
				prev.next = current.next;
				current = prev.next;
				// something has been deleted then true
				changedStuff = true;
			}
			// increment
			prev = current;
			current=current.next;
		}
		
		if(changedStuff) {
			return true;
		}
		
		
//		// init variables 
//		int size = size();
//		Ballot current = head.next;
//		Ballot prev = head;
//		boolean changedStuff = false;
//		
//		for(int i = 0; i < size; i++) {
//			
//			if(current.candidate==null || current.candidate.isBlank()) {
//				current = delNodes(prev,current);
//				changedStuff = true;
//			}
//			
//		}
//		
//		if(changedStuff) {
//			return true;
//		}
		
		return false;
	}
	
	// helper func to remove ballots
	public static Ballot delNodes(Ballot p, Ballot c) {
		p.next = c.next;
		c = p.next;
		return c;
	}
	
	/**
	 * 5 marks - Credit level
	 * 
	 * Remove all ballots from the list with an invalid candidate
	 * name. The list of valid candidate names is given as
	 * an input. 
	 * 
	 * Important: you must NOT modify the input array (String[] candidates)
	 * 
	 * @param candidates - an array containing valid candidate
	 *                     names
	 * @return true if at least one ballot was removed, 
	 *         false otherwise (including if the list
	 *         is empty or null)
	 */
	public boolean removeInvalidBallots(String[] candidates) {
		//
		// TODO - 5 marks
		
		// when head is null & head.next doesnt exist
		if(head==null) {
			return false;
		}
		
		// init var 
		Ballot current = head.next;
		Ballot prev = head;
		int iterator = 0;
		boolean removed = false;
		
		
		while(current!=null) {
			// finds if a candidate is not a candidate in the list of candidates
			for(int i = 0; i < candidates.length; i++) {
				if(current.candidate.equals(candidates[i])) {
					iterator++;
				}
			}
			// when they do equal only one candidate then the difference between iterator and the length of the candidate list is 1
			if(iterator<1) {
				// says it is removed
				removed = true;
				
				// skipping logic
				prev.next = current.next;
				current = prev;
				
				
				
				// resets iterator
				iterator = 0;
				
				// resets to the top of list;
				
			}
			
			// increment the value
			prev = current;
			current = current.next;
		}
		
		
		if(removed) {
			return true;
		}
		
		return false;
	}
	
	

	/**
	 * 5 marks - D level
	 * 
	 * Find the percentage (out of 100) of valid votes obtained by each 
	 * candidate.
	 * 
	 * @param candidates - the list of candidates
	 * @return an array of Double containing the percentage of valid votes
	 *         obtained by the candidates (the percentage of votes
	 *         obtained by candidates[i] should be returned in index i
	 *         of the array)
	 */
	public Double[] findPercentages(String[] candidates) {
		//
		// TODO - 5 marks
		return null;
	}


	/* 5 marks - D level
	 * 
	 * Find the candidate with the majority vote, defined to be at 
	 * least half of the valid votes + 1. You may assume that the 
	 * list contains only valid Ballots. 
	 * 
	 * Return null if no candidate received the majority vote, or if
	 * the list is empty.
	 * 
	 */
	public String findMajority() {
		//
		// TODO - 5 marks
		
		return null;
		
	}
	
	/** (8 marks - HD level)
	 * 
	 * Constructor - creates a new FilledBallots by combining two 
	 *               sorted FilledBallots
	 * 
	 * For this task, you need to write a constructor that creates a new
	 * FilledBallots object by combining two other FilledBallots object,
	 * list1 and list2. You must not make new ballots in the process
	 * (i.e. you should transfer the ballots from both list to the new one).
	 * 
	 * You can assume that list1 and list2 are valid FilledBallots (that is,
	 * all Ballot objects in both lists are valid as defined in the assignment
	 * specification), and that they are sorted according to the Ballots'
	 * timestamps. 
	 * 
	 * The resulting FilledBallot must also be sorted according to the ballots'
	 * timestamp. Please see UnitTest.java for a sample input and the expected
	 * output. 
	 * 
	 * If both list1 and list2 are empty, then construct an empty FilledBallots. 
	 * Do the same if both lists are null. If only one of the lists are empty
	 * or null, then you should return the other.
	 * 
	 * You may assume that list1 and list2 are not going to be used beyond
	 * this constructor (i.e. you can modify list1 and list2 as you please)
	 * 
	 * @param list1 - the first FilledBallots 
	 * @param list2 - the second FilledBallots
	 */
	public FilledBallots(FilledBallots list1, FilledBallots list2) {
		//
		// TODO - 8 marks
		
		
	}
	
	/** (8 marks - HD level)
	 * 
	 * Constructor - creates a new FilledBallots by combining all the sorted
	 *               FilledBallots in an array.
	 *
	 * For this task, you need to write another constructor that combines
	 * FilledBallots objects, but this time all the FilledBallots you need
	 * to combine are stored in an array. Note that as in the previous task,
	 * you must transfer the ballots instead of making their copies.
	 * 
	 * As before, you can assume that list1 and list2 are valid FilledBallots,
	 * and that they are sorted according to the Ballots' timestamps, and
	 * the resulting FilledBallot must also be sorted according to the ballots'
	 * timestamp. 
 	 * 
 	 * If all the FilledBallots in the array are empty, then you need to construct
 	 * an empty FilledBallot as well. Do the same if the input list is null,
 	 * or only contain nulls.
 	 * 
 	 * You may modify any of the FilledBallots object in the array as they will
 	 * not be used beyond this constructor.
	 * 
	 * @param list - the array containing the FilledBallots to be combined
	 */
	public FilledBallots(FilledBallots[] list) {
		//
		// TODO - 8 marks
		
		
		
	}

}
