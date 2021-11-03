//ID, NAME (4664228, Arnav Singh)
//Put x inside [X] below:
//[X] 	This assignment is entirely my own work and 
// 		I have not seen anyone else's code or design
package comp1010_s2_2021_ass3_for_students;
public class EmptyBallots {

	public Ballot head;
	
	
	/** 5 marks - Pass level
	 * 
	 * Create a list with n empty ballots, with id from 0 to n-1
	 * 
	 * @param n - the number of empty ballots to create in the list
	 */
	public EmptyBallots(int n) {		
		head = null;
	// for loop counting from top as when the list is created the 'first' item is the last in the for loop
		for(int i = n-1; i >= 0; i--) {
			Ballot temp = new Ballot(i, head);
			head = temp;
		}
	}
	
	/** 5 marks - Pass level
	 * 
	 * Remove the first empty ballot from the list
	 * 
	 * @return the removed ballot if there are still empty ballots
	 *         left in the list, null otherwise
	 */
	public Ballot remove() {
		if(head == null) {
			return null;
		}
		
		Ballot current = head;
		if(head.filled==false) {
			head = head.next;
			return current;
		} else {
			Ballot nodeToRemove = current.next;
			while(nodeToRemove!=null) {
				if(current.filled==false) {
					Ballot temp = current;
					current = current.next;
					return temp;
				}
				current = nodeToRemove;
				nodeToRemove = nodeToRemove.next;
			}
		}
		return null;
	}
	
	/** 5 marks - Pass level
	 * 
	 * Check if the ids of the empty ballots in the list are sequential
	 * in an increasing incremental order. For example, if the first empty ballot
	 * id is 13, then it should be followed by ballots with ids 14, 15,
	 * and so on.
	 * 
	 * @return true if the ids in the empty ballots are sequential in 
	 *         increasing incremental order, or if the list is empty, false otherwise
	 */
	public boolean isValid() {
		//
		// TODO - 5 marks
		if(head==null) {
			return true;
		}
		Ballot current = head;
		int count = 1;
		while(current!=null) {
			if(current.next!=null) {
				if(current.next.id==current.id+1) {
					count++;
				}
			}
			current = current.next;
		}
		int idx = idxCounter(head);
		if(count == idx) {
			return true;
		}
		return false;
		
	}
	
	public static int idxCounter(Ballot counter) {
		if(counter == null) {
			return 0;
		}
		Ballot current = counter;
		int count = 0;
		while(current!=null) {
			count++;
			current = current.next;
		}
		return count;
	}
	
}
