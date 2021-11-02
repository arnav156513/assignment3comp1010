//ID, NAME (4664228, Arnav Singh)

//Put x inside [X] below:
//[X] 	This assignment is entirely my own work and 
// 		I have not seen anyone else's code or design
package comp1010_s2_2021_ass3_for_students;

/*
 * class representing a voting card (ballot)
 * 
 * is also a node in the two kind of linked lists to construct and manipulate
 * 
 * 
 */




public class Ballot {

	public int id; // unique identifier of voting card
	public String candidate; // name of candidate for vote (possibly incorrect/invalid/null)
	public Ballot next;     // reference to next ballot in appropriate Linked List
	public Timestamp timestamp; // unique time stamp (provided at voting time)
	public boolean filled;  // becomes true when vote is casted
	
	/** 
	 * 
	 * @param id 
	 * represents the official identity number of the Ballot
	 * @param next
	 * is the reference to the next Ballot in the relevant list
	 * other parameters need to be set with default values
	 */
	
	public Ballot(int id, Ballot next) {
		// Provided - not to be changed
		candidate = null;
		this.id = id;
		this.next = next;
		this.filled = false;
		this.timestamp = null;
	}
	
	/**
	 * Voting: the voting card is filled
	 * 
	 * @param name of (possibly incorrect) candidate name
	 * @param time stamp of the vote
	 * 
	 * need to fill the ballot: the vote
	 * need to check whether has been already filled (used for voting)
	 * and both name and time stamp are not null 
	 * 
	 * @return - true if ballot was successfully filled, false otherwise
	 */
	public boolean fill(String name, Timestamp time) {
	// checks if the ballot is already filled
		if(filled==true) {
			return false;
		}
	// checks if the name is not null and time is not null then sets values for instance variables & returns true
		if(name != null && time!=null) {
			timestamp = time;
			candidate = name;
			filled = true;
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * You may assume that the input array (String[] candidates) will
	 * not be null or empty.
	 * 
	 * @param first - first valid id
	 * @param last  - last valid id
	 * 
	 * A ballot is valid if it is: filled, time-stamped, 
	 * and have an official id and a vote for one of the candidate listed
	 * 
	 * @return true if the Ballot is valid based on the criteria listed above,
	 *         false otherwise
	 */
	public boolean isValid(int first, int last, String[] candidates) {
		
	// checks if the ballot is filled
		if(filled && timestamp!=null) {
		// checks if the id is within the range of ID's
			if(this.id >= first && this.id <=last) {
			// determines if the candidate selected is within the canidates that can be chosen from
				for(int i = 0; i < candidates.length; i++) {
					if(candidates[i] == this.candidate) {
						return true;
					}
				}
			}
		}
		// if conditons are not met, returns false
		return false;
		
	}
}


