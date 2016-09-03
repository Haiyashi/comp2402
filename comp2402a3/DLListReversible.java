package comp2402a3;

import java.util.AbstractSequentialList;
import java.util.ListIterator;

/**
 * An implementation of the List interface as a doubly-linked circular list. A
 * dummy node is used to simplify the code. This version has a reverse method.
 * 
 * @param <T>
 *            the type of elements stored in the list
 */
public class DLListReversible<T> extends DLList<T> {
	
	/**
	 * Reverses the order of the items in the list
	 */
	public void reverse() {
		Node temp1 = dummy.next;
		Node temp2 = dummy.prev;
            //I will loop from the end of the data set to the begining
            for (int i = size(); i >= 0; i--) {
                //Switch the Head and tail 
                temp1 = temp2.next;
                temp2.next = temp2.prev;
                temp2.prev = temp1;
                
                //Move to next node
                temp2 = temp2.next; 
            }
	}

	public static void main(String[] args){
		
		DLList<Character> list = new DLListReversible<Character>();
		
		String datasequence = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; i < datasequence.length(); i++){
			list.add(datasequence.charAt(i));
		}

		System.out.println(list);
		
		((DLListReversible)list).reverse();

		System.out.println(list);

	}
}
