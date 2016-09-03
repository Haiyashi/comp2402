package comp2402a2;

import java.util.AbstractList;

/**
 * Treque : an implementation of the List interface 
 * that allows for fast modifications at the head, tail
 * and middle of the list.
 *
 * Modify the methods so that 
 *  -set/get have constant runtime
 *  -add/remove have O(1+min{i, size()-i, |size()/2-i|})
 *              amortized runtime.
 * 
 * @author Nicholas Lewanowicz / MyArrayDeque
 *
 * @param <T> the type of objects stored in this list
 */

public class Treque<T> extends AbstractList<T> {
  
  
  protected MyArrayDeque<T> front;
  protected MyArrayDeque<T> back;
  
  public Treque(Class<T> t) {
	front = new MyArrayDeque<T>(t);
	back = new MyArrayDeque<T>(t);  
  }
  public int size() {
	return front.size() + back.size();
  }
  
  public T get(int i) {

	//If the index exists in 'front' get i from front
	if(i < front.size()) return front.get(i);
	//else if index exists in back get index from back (less mod front)
	else return back.get(i-front.size());
  }
  
  
  public T set(int i, T x) {
	//If index exists in front 'set' i in front
	if (i<front.size()) return front.set(i,x);
	//else if index exists in back set index from back (less mod front)
	else return back.set(i-front.size(),x);
  }
  
  public void add(int i, T x) {
	//If i exists in front 'add' x to i index in front
	if (i < front.size()) front.add(i,x);
	//else i exists in back 'add' x to i index in back (less mod front)
	else back.add(i-front.size(),x);
	  //Must rebalance as this may unbalance our arrays
	  reBalance();
  }
  
  public T remove(int i) {
	//Declare
	T x;
	//If i is in front then remove element i
	if(i < front.size()) x = front.remove(i);
	//else remove from back position i (less mod front)
	else x = back.remove(i-front.size());
	//Must rebalance as this may unbalance our array
	reBalance();
	return x;
  }

  protected void reBalance() {
	//the front and back can have a delta of 0 or 1.
	//If this is 2 you will pop an item front the front/back and add it to the back/front
	if (front.size() == back.size() + 2){
		back.add(0, front.remove(front.size()-1));
	//Or vise versa
	} else if (back.size() == front.size() + 2) {
		front.add(front.size(), back.remove(0));
	} 
  }
  public void clear() {
	//Clear will clear the array
    front.clear();    
    back.clear();
  }
  
  public static void main(String args[]){
    Treque<Integer> myTreque = new Treque<>(Integer.class);
  }
}