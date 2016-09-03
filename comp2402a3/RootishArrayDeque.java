package comp2402a3;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

 /**
 * This class implements the List/Deque interface using a collection of arrays of
 * sizes 1, 2, 3, 4, and so on.  The main advantages of this over an 
 * implementation like ArrayList is that there is never more than O(sqrt(size())
 * space being used to store anything other than the List elements themselves.
 *
 * Insertions and removals take O( 1 + min{i, size()-i} ) amortized time.
 * 
 * This provides a space-efficient implementation of Deque.  
 * The total space used beyond what is required to store elements is O(sqrt(n)) 
 * @param <T> the type of objects stored in this list
 */
public class RootishArrayDeque<T> extends RootishArrayStack<T> {
	 protected RootishArrayStack<T> front;
 	 protected RootishArrayStack<T> back;
 

	@Override
	public T get(int i) {
		 if(i < front.size()) return front.get(i);
    
    //else return the value at the back array starting at its first indice
   		 else return back.get(i-front.size());
	}


	@Override
	public T set(int i, T x) {
		//if object at position i is less than size of front array size, add the the object starting at the back of the array
		// which shifts all the objects after it +1
		if (i < front.size()) 
			front.add(i,x);
    
		//else do the same at the back array starting fron the beginning of it
		else back.add(i-front.size(),x);
		reBalance();

		return x;
	}
	
	@Override
	public void add(int i, T x) {
		//if object at position i is less than size of front array size, add the the object starting at the back of the array
		// which shifts all the objects after it +1
		if (i < front.size()) 
			front.add(i,x);
    
		//else do the same at the back array starting fron the beginning of it
		else 
			back.add(i-front.size(),x);
		reBalance();
	}
	
	@Override
	public T remove(int i) {
		T x;
    	// if object at position i is less than the front size, remove the index from the front
    	if(i < front.size()) 
    		x = front.remove(i);
    	// if object at position i is greater than the front size, remove the index from the back
    	else 
    		x = back.remove(i-front.size());
    	reBalance(); //rebalance if the front and back sizes are different by 2
    
    return x;
	}

	protected void reBalance() {
    
    	//if the back size is bigger than the front by 2, add an element to the front
    	if (front.size() == back.size() + 2)
      		back.add(0, front.remove(front.size()-1));
      
    	else if (back.size() == front.size() + 2) 
      		front.add(front.size(), back.remove(0));
      
    }
    //Without overriding toString the deck prints blank
	public String toString(){
		return (front.toString() + back.toString());
	}
	public RootishArrayDeque(Class<T> t) {
		super(t);
		front = new RootishArrayStack<T>(t);
    	back  = new RootishArrayStack<T>(t);  
	}
	

	public static void main(String[] args){
		RootishArrayDeque<String> deck = new RootishArrayDeque<String>(String.class);
		deck.add(0,"a");
		deck.add(0,"b");
		System.out.println(deck);


	}
}	



