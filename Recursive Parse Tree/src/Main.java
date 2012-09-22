import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author jfischer
 *
 */
public class Main {

	public static int nodesCreated = 0;
	
	public static void main(String[] args) {
		String expr = null;
		TreeNode headNode = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		//Program Title/Author
		System.out.println("Basic Expression Parser - Java");
		System.out.println("By: Joel Fischer\n");
		//Hold at this point for 2 seconds
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
			System.err.println("Error Holding Thread");
			ie.printStackTrace();
		}
		//Display Grammar Rules
		System.out.println("Grammer is as follows:");
		System.out.println("e ::= n | e + e | e - e");
		System.out.println("n ::= d | nd");
		System.out.println("d ::= 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9");
		System.out.println("Please do not provide spaces");
		System.out.println("The program will print node data as it parses");
		System.out.println("After the program has parsed the tree, you will have the opportunity to traverse the tree");
		//Tell the user we want him to enter the expression
		System.out.print("\nEnter the expression to parse: ");
		
		//Set up to retrieve input
		try {
			expr = br.readLine();
		} catch (IOException ioe) {
			System.err.println("Error reading input");
			ioe.printStackTrace();
		}
		headNode = parseTree(null, expr);
		traverseTree(headNode);
	}
	
	public static TreeNode parseTree (TreeNode parent, String data) {
		//Check for the number of expressions & type if any
		data.trim(); //Remove extra spaces on the edges
		TreeNode thisNode = new TreeNode(parent, data, ++nodesCreated);
		for(int i = 0; i < data.length(); i++) {
			if(data.charAt(i) == '+' || data.charAt(i) == '-') {
				//There are two expressions (e+e || e-e)
				thisNode.dataType = "e" + data.charAt(i) + "e";
				//Print this node's data
				printNode(thisNode);
				//Send the parser down the left hand path with all data up to the 
				//expression sign
				thisNode.leftChild = parseTree(thisNode, data.substring(0, i));
				//Send the parser down the right hand path with all data after the 
				//expression sign
				thisNode.rightChild = parseTree(thisNode, data.substring(i + 1));
				//Return this node, the header node if this is the header node
				return thisNode;
			}
		}
		//We know that there are no more expressions, we are down to an (n = d || nd)
		//We need to figure out if we have one digit or multiple.
		if(data.length() > 1) {
			//We have more than one digit. We need to get the last one and pass on the rest
			try {
				Integer.parseInt(data.substring(data.length() - 1));
			} catch(NumberFormatException nfe) {
				System.err.println("This string could not parse a 0 - 9 integer from " + data.charAt(data.length() - 1));
				System.err.println("Please provide only the symbols 0 - 9, +, -");
				System.err.println("Do not provide spaces");
				System.err.println("This program will terminate in 5 seconds");
				try {
					Thread.sleep(5000);
					System.exit(-1);
				} catch (InterruptedException ie) {
					System.err.println("Could not suspend thread, terminating application");
					ie.printStackTrace();
					System.exit(-1);
				}
			}
			thisNode.dataType = "nd";
			//Print this node's data to the screen
			printNode(thisNode);
			//Send the parser down the left hand path with all numbers except the right-most
			thisNode.leftChild = parseTree(thisNode, data.substring(0, data.length() - 1));
			//Send the parser down the right hand path with only this digit
			thisNode.rightChild = parseTree(thisNode, data.substring(data.length() - 1));
		} else {
			//There is only one digit
			thisNode.dataType = "d";
			//Print this node's data to the screen
			printNode(thisNode);
		}
		return thisNode;
	}
	
	public static void printNode (TreeNode thisNode) {
		System.out.println("\nCurrent Node Number: " + thisNode.numCreated);
		System.out.println("Current Node Data Type: " + thisNode.dataType);
		System.out.println("Current Node Data: " + thisNode.data);
		if(thisNode.parent != null) {
			System.out.println("Parent Node Number: " + thisNode.parent.numCreated + "\n\n");
		} else {
			System.out.println("This Node is the HeadNode\n\n");
		}
	}
	
	public static void traverseTree (TreeNode headNode) {
		TreeNode currentNode = headNode;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String in = null;
		int inInt = 0;
		
		System.out.println("Entering Traversal Menu, HeadNode:\n");
		
		while(true) {
			System.out.println("Current Node:");
			printNode(currentNode);
			
			System.out.println("\nTraversal Menu");
			System.out.println("1. Go to Parent");
			System.out.println("2. Go to Left Child");
			System.out.println("3. Go to Right Child");
			System.out.println("4. Exit Program");
			System.out.print("Enter your choice: ");
			
			//Read the menu choice
			try {
				in = br.readLine();
			} catch (IOException ioe) {
				System.err.println("Error reading input");
				ioe.printStackTrace();
			}
			
			//Try to get an integer from the string the user entered
			try {
				inInt = Integer.parseInt(in);
			} catch(NumberFormatException nfe) {
				System.err.println("You did not enter an integer, try again");
			}
			
			//Check for an existing parent or child and go down that path if it exists
			if(inInt == 1) {
				if(currentNode.parent == null) {
					System.out.println("There is no parent, this is the HeadNode");
				} else {
					currentNode = currentNode.parent;
				}
			} else if(inInt == 2) {
				if(currentNode.leftChild == null) {
					System.out.println("There is no left child, this is a bottom level node");
				} else {
					currentNode = currentNode.leftChild;
				}
			} else if(inInt == 3) {
				if(currentNode.rightChild == null) {
					System.out.println("There is no right child, this is a bottom level node");
				} else {
					currentNode = currentNode.rightChild;
				}
			} else if(inInt == 4) {
				System.out.println("Goodbye...");
				//Exit the program
				try {
					Thread.sleep(1500);
				} catch (InterruptedException ie) {
					System.err.println("Could not suspend thread");
					ie.printStackTrace();
					System.exit(-1);
				}
				System.exit(0);
			} else {
				//The user entered an int, but not a valid one
				System.out.println("Number entered did not correspond to a menu entry, try again");
			}
		}
	}
}
