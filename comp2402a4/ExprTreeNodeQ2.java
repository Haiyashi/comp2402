package comp2402a4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Stack;
import java.lang.Character;
import java.lang.Integer;

public class ExprTreeNodeQ2 {

	public Object value;
	public char operator;
	public int num;
	public boolean isOp;
	public ExprTreeNodeQ2 parent;
	public ExprTreeNodeQ2 lchild;
	public ExprTreeNodeQ2 rchild;
	
	// Default Constructor
	public ExprTreeNodeQ2() {
		int num;		//num used when its a number
		char operator;	//char used to store operation (+-*/)

		value = null;	//unused
	    isOp  = false;	//If its an operator
		parent = null;	//above node (operator)
		lchild = null;  //first value to 'math'
		rchild = null;  //second value to 'math'
    }
	
	// You will likely want to add additional constructors here...
		//... uhh ... guess not
	
	
	/**
	 * This function is expected to evaluate your expression tree and return the
	 * evaluated result
	 * @param u - the root of the tree to be traversed
	 * @return  - the result
	 */
	public static int eval(ExprTreeNodeQ2 u) {
	//Leaves are always numbers, therefor if it isnt an operator its a leaf
		if (!u.isOp)
			return (u.num);
	//This is the recursive bit that will find the result of the left 
		int left = eval(u.lchild);
		int right = eval(u.rchild);
	
	//This is a case that will perform operations based on the operand
		if (u.operator == '/')	//will perform division
			return left / right;	
		if (u.operator == '*')	//will perform multiplication
			return left * right;
		if (u.operator == '+')	//will perform addition
			return left + right;
		if (u.operator == '-')	//will perform subtraction
			return left - right;
		
		return 0;
	}
	
	/**
	 * This function is expected to traverse your expression tree and construct
	 * a string representation using infix; e.g., ((1 + 2) - 3) ...
	 * @param u - the root of the tree to be traversed
	 * @return  - the infix string representation
	 */
	public static String infix(ExprTreeNodeQ2 u) {
		if (u.isOp){
			String left = infix(u.lchild);	//recursively call infix with smaller parts
			String right = infix(u.rchild);	// "                                   "
			return "(" + left + ") " + u.operator + " (" + right + ")";
		}else{
			return Integer.toString(u.num);	//base care for recursive call
		}
	}

	/**
	 * This function is expected to build the expression tree from the data that
	 * is contained in the file associated with the BufferedReader, and then
	 * this function must call your infix and eval functions
	 * @param r - the BufferedReader
	 * @param w - the PrintWriter
	 */

	//Integer.parseInt
	public static void build(BufferedReader r, PrintWriter w) throws IOException {
		Stack<ExprTreeNodeQ2> toDo = new Stack<ExprTreeNodeQ2>();
		boolean flag =  false;	//flag if there a violation of binary tree structure
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			String ops = line;	//stoprs the operations
			char isDig = line.charAt(0); //stores first digit to seperate number from non numbers
			System.out.println(line);	//will print the input 
			if (Character.isDigit(isDig) == true || line.length()  > 1){ //if the currently line is a number (+/-) this will run
				ExprTreeNodeQ2 number = new ExprTreeNodeQ2();
//				number.value = element; 
				int v = Integer.parseInt(line);
				number.num = Integer.parseInt(line);
				number.isOp  = false;		//it is an operator because it isnt a digit
				number.parent = null;
				number.lchild = null;		//digits will never have children
				number.rchild = null;		//Im like a digit... D:
				toDo.add(number);
			} else {//if the line is a operator this will run
				if (toDo.size() >= 2){//this will stop an operation from running if there are too few values to perform the operation
					ExprTreeNodeQ2 right = toDo.pop();//pops from te stack of vals to perform the operation
					ExprTreeNodeQ2 left = toDo.pop();
					ExprTreeNodeQ2 result = new ExprTreeNodeQ2();
	//				result.value = element;
					result.operator = isDig;	//set the operator for use when evaluating a printing.
					result.isOp  = true;		//it is an operator because it isnt a digit
					result.lchild = left;		//left child is the second top item from stack
					result.rchild = right;		//right child is top item from stack
					toDo.add(result);
				}else{	//if there wasnt enough items to perfom the operation this would set off a flag
					flag =  true;
					break;
				}
			}
		}

		if (toDo.size() == 1 && flag == false){ // if there is only one root and no other items remaing to push into the stack. The root is, the final result
			// DO NOT REMOVE THESE LINES //I didnt remove these lines
			w.println(infix(toDo.peek()));
			w.println(eval(toDo.peek()));		
		}else{	
			System.out.println("Invalid Input");		//give warnings
			System.out.println("Program complete...");
		}
	}
	
	/**
	 * Open a BufferedReader and a PrintWriter, either from System.in/System.out
	 * or from filenames specified on the command line, then call build.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader r;
			PrintWriter w;
			if (args.length == 0) {
				r = new BufferedReader(new InputStreamReader(System.in));
				w = new PrintWriter(System.out);
			} else if (args.length == 1) {
				r = new BufferedReader(new FileReader(args[0]));
				w = new PrintWriter(System.out);				
			} else {
				r = new BufferedReader(new FileReader(args[0]));
				w = new PrintWriter(new FileWriter(args[1]));
			}
			build(r, w);
			w.flush();
		} catch (IOException e) {
			System.err.println(e);
			System.exit(-1);
		}
	}
	
}