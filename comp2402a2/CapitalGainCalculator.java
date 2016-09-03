package comp2402a2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Deque;
import java.util.ArrayDeque;

public class CapitalGainCalculator {
 
 /**
  * Read each of the transaction/adjustment lines, one at a time, from r. 
  * Add all the transactions to our data structure, and when we sell more then one transaction or units remove it from the structure
  * We remove from the back of our Lifo que and the front of our Fifo que
  * Output (to w) the capital gain or loss after all of the transactions have
  * been processed, according to both strategies. To clarify:
  *
  * - You must first output the result of a "sell the oldest first" approach
  *
  * - You must then output the result of a "sell the freshest first" approach
  *
  * These two numbers should be the only output of your program. Round your
  * numbers to the nearest cent (i.e., use EXACTLY two decimal places).
  *
  * @Author: Nicholas Lewanowicz
  *
  * @param r the reader to read from
  * @param w the writer to write to
  * @throws IOException
  */
 public static void doIt(BufferedReader r, PrintWriter w) throws IOException {
	//Units for Lifo and Fifo (these will be copies)
	Deque<Integer> uFifoDeque = new ArrayDeque<>();    
    Deque<Integer> uLifoDeque = new ArrayDeque<>();
	
	//Prices for Lifo and Fifo (these will be copies)
	Deque<Float> pFifoDeque = new ArrayDeque<>();   
    Deque<Float> pLifoDeque = new ArrayDeque<>();
	
	//Price per unit
    float p = 0;
	
	//Total Gains/Losees for either
	float Lifo = 0;
    float Fifo = 0;
	
	//num of total units for Fifo and Lifo
    int uFifo = 0;
    int uLifo = 0;
	
		//Loop transactions
        for (String line = r.readLine(); line != null; line = r.readLine()) {
			//Remove useless characters
			line = line.replaceAll(" ", "");
			line = line.replace("units", "");
			line = line.replace("at", "");
			line = line.replace("each", "");
			
          if (line.startsWith("buy")){
			//Remove Buy
			line = line.replace("buy", "");
			
			//Get Buy Price
			p = Float.parseFloat(line.substring(line.indexOf('$') + 1,line.length()));
			
			//Get Units bought
			line = line.substring(0, line.indexOf('$'));
			uFifo = Integer.parseInt(line);
			
			//Adds units to Data structure
			uFifoDeque.addLast(uFifo);
			uLifoDeque.addLast(uFifo);
			
			//Add price to the Data structure
			pFifoDeque.addLast(p);
			pLifoDeque.addLast(p);
			
          } else {
			  
			//Remove sell and Spaces
			line = line.replace("sell", "");
			line = line.replaceAll(" ", "");
			
			//Get Sell Price
			p = Float.parseFloat(line.substring(line.indexOf('$') + 1,line.length()));
			
			//Get Units sold
			line = line.substring(0, line.indexOf('$'));
			uFifo = Integer.parseInt(line);
			uLifo = uFifo;
			
            if(pFifoDeque.isEmpty() || pLifoDeque.isEmpty()) continue;
				//While we still have stock in FIFO
				while(uFifo != 0){
					//Block Edge Case: If we sell more units then in the top of our deck
					if(uFifoDeque.getFirst() < uFifo){
						Fifo += uFifoDeque.getFirst() * (p - pFifoDeque.getFirst());
						uFifo -= uFifoDeque.getFirst();
						pFifoDeque.removeFirst();
						uFifoDeque.removeFirst();
						if(uFifoDeque.isEmpty())uFifo = 0;
					}else{
						//If we have 'sold out' - Next transaction will clear all stock
						Fifo += uFifo * (p - pFifoDeque.getFirst());
						int temp = uFifoDeque.getFirst();
						uFifoDeque.removeFirst();
						uFifoDeque.addFirst(temp - uFifo);
						uFifo = 0; //This could have been a break but it kept not working
					}
            
				}
				//While we still have stock in LIFO
				while(uLifo != 0){
					//Block Edge Case: If we sell more units then in the bottom of our deck
					if(uLifoDeque.getLast() < uLifo){
						Lifo += uLifoDeque.getLast() * (p - pLifoDeque.getLast());
						uLifo -= uLifoDeque.getLast();
						pLifoDeque.removeLast();
						uLifoDeque.removeLast();
						if(uLifoDeque.isEmpty()) uLifo = 0;
					}else{
						//If we have 'sold out' - Next transaction will clear all stock
						Lifo += uLifo * (p - pLifoDeque.getLast());
						int temp = uLifoDeque.getLast();
						uLifoDeque.removeLast();
						uLifoDeque.addLast(temp - uLifo);
						uLifo = 0; //This could have been a break but it kept not working
					} 
				}
			}
        }
		//Print Final Values in proper format
		w.println( "$" + String.format("%.2f", Fifo));
        w.println( "$" + String.format("%.2f", Lifo));
 }

 

 /**
  * The driver.  Open a BufferedReader and a PrintWriter, either from System.in
  * and System.out or from filenames specified on the command line, then call doIt.
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
   long start = System.nanoTime();
   doIt(r, w);
   w.flush();
   long stop = System.nanoTime();
   System.out.println("Execution time: " + 10e-9 * (stop-start));
  } catch (IOException e) {
   System.err.println(e);
   System.exit(-1);
  }
 }
}
