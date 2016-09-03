package comp2402a5;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;


/**
 * An implementation of binary trees that stores Integers 
 * (as well as other numbers for traversals)
 * based on the ODS BinaryTree class by  P. Morin.
 *
 */
public class BinaryTree {
	

	int count; //Global count variable :D
	/**
	 * The root of this tree
	 */
	protected Node r;

	/**
	 * This tree's "null" node
	 */
	protected Node nil;

	/**
	 * Create an empty binary tree
	 */
	public BinaryTree() {}
	
	/**
	 * Create a binary tree with specified root value
	 */
	public BinaryTree(Integer data) {
		r = new Node(data);
		r.parent = nil;
		r.left = nil;
		r.right = nil;
	}

	/**
	 * Create a binary tree with specified root value
	 * (assume u's left/right children are already specified)
	 */
	public BinaryTree(Node u) {
		r = u;
		r.parent = nil;
	}

	
	
	/**
	 * Compute the depth (distance to the root) of u
	 * @param u
	 * @return the distanct between u and the root, r
	 */
	public int depth(Node u) {
		int d = 0;
		while (u != r) {
			u = u.parent;
			d++;
		}
		return d;
	}
	
	/**
	 * Compute the size (number of nodes) of this tree
	 * @warning uses recursion so could cause a stack overflow
	 * @return the number of nodes in this tree
	 */
	public int size() {
		return size(r);
	}
	
	/**
	 * @return the size of the subtree rooted at u
	 */
	protected int size(Node u) {
		if (u == nil) return 0;
		return 1 + size(u.left) + size(u.right);
	}
	
	
	/**
	 * Compute the maximum depth of any node in this tree
	 * @return the maximum depth of any node in this tree
	 */
	public int height() {
		return height(r);
	}
	
	/**
	 * @return the size of the subtree rooted at u
	 */
	protected int height(Node u) {
		if (u == nil) return -1;
		return 1 + Math.max(height(u.left), height(u.right));
	}

	
	/**
	 * @return
	 */
	public boolean isEmpty() {
		return r == nil;
	}
	
	/**
	 * Make this tree into the empty tree
	 */
	public void clear() {
		r = nil;
	}

	/**
	 * clears the preorderN, postorderN and inorderN 
	 * values in the tree to -1.
	 */
	public void reset(){
		// do not change this method
		resetHelper(r);
	}

	protected void resetHelper(Node u){
		// do not change this method
		if (u == nil) return;
		u.preorderN = u.postorderN = u.inorderN = -1;
		resetHelper(u.left);
		resetHelper(u.right);

	}


	/********************************************************
	 * !TODO!
    *
	 * these methods set the preorderN, postOrderN and inorderN
	 * values for the nodes in the tree rooted at r
	 *******************************************************/
	//Initial function call without params
	public void preOrderNumbers(){
		count = 0;						//Set count to zero
		preOrderNumbers(r);				//Call recursive 'helper' with root
	}
	//Recursive preOrder function which takes a node as a param
	public void preOrderNumbers(Node u){
		u.preorderN = count;			//Sets count for node on arrival
		count++;						//Increment count
		if(u.left != null)				//If left node exists, go there. Call this function on it
			preOrderNumbers(u.left);	
		if(u.right != null)				//If right node exists, go there. Call this function on it 
			preOrderNumbers(u.right);		
	}
	//Initial postOrder function call without params
	public void postOrderNumbers(){
		count = 0;					
		postOrderNumbers(r);		
	}
	//Recursive postOrder function which takes a node as a param
	public void postOrderNumbers(Node u){
		if(u.left != null) 				//If left node exists calls again on it
			postOrderNumbers(u.left);
		if(u.right != null)
			postOrderNumbers(u.right);	//If right node exists calls again on it
		u.postorderN = count;			//Sets count for node before exiting
		count++;						//Increment count
	}
	//Initial inOrder function call without params
	public void inOrderNumbers(){
		count = 0;						//Set count to zero
		inOrderNumbers(r);				//call recursive funtion with root
	}
	//Recursive inOrder function which takes a node as a param
	public void inOrderNumbers(Node u){
		if(u.left != null)				//if left esixts recursive call it
			inOrderNumbers(u.left);
		u.inorderN = count;				//Sets count for node before checking right
		count++;						//Increment count 
		if(u.right != null)				//If right node exists recursiely call
			inOrderNumbers(u.right);
	}


	public void preOrderNumbersNonRecursive(){
		Stack<Node> stack = new Stack<Node>();	//Create stack to keep nodes to visit
		count = 0;								//Set count to 0
		Node temp = r;							//have a node to traverse the tree with

		while (!stack.isEmpty() || temp != null){	//If temp exists and the stack isnt empty 
			if (temp != null){						//if temp exists we set its counter and increment
				temp.preorderN = count;
				count++;

				if (temp.right != null)				//Save temp.right to visit later
					stack.push(temp.right);
				temp = temp.left;
			}else{									//Else get a new temp
				temp = stack.pop();
			}
		}

	}


	public void postOrderNumbersNonRecursive(){
		Stack<Node> stack = new Stack<Node>();	//Stack to keep nodes to visit
		count = 0;								//Set count = 0
		Node temp = r;							//Start traversing at root
		Node lastTemp = null;					//We must keep a flag for our last visited if we walk out of our tree

		while (!stack.isEmpty() || temp != null){	//If stack is empty or temp exists
			if (temp != null){						//If temp exists go to its left and save temp for later
				stack.push(temp);		
				temp = temp.left;
			}else{
				//If the right child of the stack exists and its the same as the last visited nodes right set it to temp
				if ((stack.peek()).right != null && lastTemp != (stack.peek()).right){ 
					temp = (stack.peek()).right;
				} else{								//Else set the stack top count
					(stack.peek()).postorderN = count;
					count++;						//Increment
					lastTemp = stack.pop();			//set the last visited to the stack top
				}
			}
		}

	}

	public void inOrderNumbersNonRecursive(){
		Stack<Node> stack = new Stack<Node>();
		count = 0;
		Node temp = r;

		while( !stack.isEmpty() || temp != null){	//if the stack has elements or the temp exists run if statement
			if (temp != null){						//if temp exists push to stack and go left
				stack.push(temp);
				temp = temp.left;
			}else{									//else pop form stack set count and move to right child
				temp = stack.pop();
				temp.inorderN = count;
				count++;
				temp = temp.right;
			}

		}
	}

}
