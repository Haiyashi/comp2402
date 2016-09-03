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


//READ ME!!! 
//Didnt comment because this is pretty much the exect same code from q2 
//with slight additions I did comment


public class ExprTreeNodeQ3 {

	public Object value;
	public char operator;
	public int num;
	public boolean isOp;
	public ExprTreeNodeQ3 parent;
	public ExprTreeNodeQ3 lchild;
	public ExprTreeNodeQ3 rchild;
	
	// Default Constructor
	public ExprTreeNodeQ3() {
		int num;
		char operator;

		value = null;
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
	public static int eval(ExprTreeNodeQ3 u) {
	//Leaves are always numbers, therefor if it isnt an operator its a leaf
		if (!u.isOp)
			return (u.num);
	//This is the recursive bit that will find the result of the left 
		int left = eval(u.lchild);
		int right = eval(u.rchild);
	
	//This is a case that will perform operations based on the operand
		if (u.operator == '/')
			return left / right;
		if (u.operator == '*')
			return left * right;
		if (u.operator == '+')
			return left + right;
		if (u.operator == '-')
			return left - right;
		if (u.operator == '_')	//this operator represents a negation
			return (-1)*right;
		
		return 0;
	}
	
	/**
	 * This function is expected to traverse your expression tree and construct
	 * a string representation using infix; e.g., ((1 + 2) - 3) ...
	 * @param u - the root of the tree to be traversed
	 * @return  - the infix string representation
	 */
	public static String infix(ExprTreeNodeQ3 u) {
		if (u.isOp){
			String left = infix(u.lchild);
			String right = infix(u.rchild);
			return "(" + left + ") " + u.operator + " (" + right + ")";
		}else if(u.operator == '_'){	// in the case of a negation I want my prints to be nicely formatted
			String right = infix(u.rchild);
			return "-" + " (" + right + ")";
		}else{
			return Integer.toString(u.num);
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
		Stack<ExprTreeNodeQ3> toDo = new Stack<ExprTreeNodeQ3>();
		boolean flag =  false;
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			String ops = line;
			char isDig = line.charAt(0);
			System.out.println(line);
			if (Character.isDigit(isDig) == true || line.length()  > 1){
				ExprTreeNodeQ3 number = new ExprTreeNodeQ3();
//				number.value = element; 
				int v = Integer.parseInt(line);
				number.num = Integer.parseInt(line);
				number.isOp  = false;		//it is an operator because it isnt a digit
				number.parent = null;
				number.lchild = null;
				number.rchild = null;
				toDo.add(number);
			} else {
				if (toDo.size() >= 2){
					ExprTreeNodeQ3 right = toDo.pop();
					ExprTreeNodeQ3 left = toDo.pop();
					ExprTreeNodeQ3 result = new ExprTreeNodeQ3();
	//				result.value = element;
					result.operator = isDig;
					result.isOp  = true;		//it is an operator because it isnt a digit
					result.lchild = left;		//left child is the 
					result.rchild = right;
					toDo.add(result);

				}else if (toDo.size() == 1 && isDig == '-') {
					ExprTreeNodeQ3 right = toDo.pop();
					ExprTreeNodeQ3 left = new ExprTreeNodeQ3();
					left.isOp = false;
					left.num = -1;
					ExprTreeNodeQ3 result = new ExprTreeNodeQ3();
	//				result.value = element;
					result.operator = '_';
					result.isOp  = true;		//it is an operator because it isnt a digit
					result.rchild = right;
					left.num = -1;
					result.lchild = left;
					toDo.add(result);					
				}else{
					flag =  true;
					break;
				}
			}
		}

		if (toDo.size() == 1 && flag == false){
			// DO NOT REMOVE THESE LINES
			w.println(infix(toDo.peek()));
			w.println(eval(toDo.peek()));		
		}else{
			System.out.println("Invalid Input");
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