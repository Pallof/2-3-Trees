import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

//DONT FORGET TO MAKE SURE X AND Y ARE IN THE CORRECT POSITION
//CHECK THE TWO STRINGS AND IMPLEMENT A SWAP IF NEEDED

//PROGRAM IS NOT CATCHING THE EDGE CASES, RE-READ
public class Solution {

    public static void main(String[] args) throws UnsupportedEncodingException {
    	
    	//my output.flush is in the printRange function
		BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out, "ASCII"), 4096);
		
    	TwoThreeTree newTree = new TwoThreeTree();
    	
    	Scanner sc = new Scanner(System.in);
    	int size = sc.nextInt();
    	int i = 0;
    	int j = 0;
    	while(i < size) {
    		String planetName = sc.next();
    		//System.out.print(planetName);
    		int entranceFee = sc.nextInt();
    		twothree.insert(planetName, entranceFee, newTree);
    		i++;
    	}
    	
    	int querySize = sc.nextInt();
    	while(j < querySize) {
    		String planetName1 = sc.next();
    		String planetName2 = sc.next();
    		if(planetName1.compareTo(planetName2) <= 0) {
//    			twothree.printLE(newTree.root, "v", newTree., output);
    			twothree.printRange(newTree.root, planetName1, planetName2, newTree.height, output);
    		}
    		else {
    			twothree.printRange(newTree.root, planetName2, planetName1, newTree.height, output);
    		}
    		j++;
    		
    	}
    	
    	sc.close();
//    	try {
//			output.flush();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	
    	
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
    }

}


class Node {
   String guide;
   // guide points to max key in subtree rooted at node
}

class InternalNode extends Node {
   Node child0, child1, child2;
   // child0 and child1 are always non-null
   // child2 is null iff node has only 2 children
}

class LeafNode extends Node {
   // guide points to the key

   int value;
}

class TwoThreeTree {
   Node root;
   int height;

   TwoThreeTree() {
      root = null;
      height = -1;
   }
}

class WorkSpace {
// this class is used to hold return values for the recursive doInsert
// routine (see below)

   Node newNode;
   int offset;
   boolean guideChanged;
   Node[] scratch;
}

class twothree { 

		//MY PRINT ALL FUNCTION
	//for the output.write statement make sure to format is correctly
	   static void printAll(Node p, int height, BufferedWriter output){
	       
		   if(height==0){
	           try {
	        	   //need to cast p into a leaf node, leaf node can also access guide
	        	   output.write(((LeafNode)p).guide + " " + ((LeafNode)p).value + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       }
	       else{
	           printAll(((InternalNode)p).child0, height-1, output);
	           printAll(((InternalNode)p).child1, height-1, output);
	           
	           if(((InternalNode)p).child2 != null){
		           printAll(((InternalNode)p).child2, height-1, output);
		       }
	       }
	      
	       
	   
	   } 

	   // MY PRINT LESS THAN OR EQUAL TO FUNCTION
	   static void printLE(Node p, String x, int height, BufferedWriter output){
	       if(height==0){
	           if(x.compareTo(p.guide) <= 0 ){
	               try {
					output.write(((LeafNode)p).guide + " " + ((LeafNode)p).value + "\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
	       }
	       else if(x.compareTo(((InternalNode)p).child0.guide) <= 0){
	           printLE(((InternalNode)p).child0, x, height-1, output);
	       }
	       else if(((InternalNode)p).child2 == null || x.compareTo(((InternalNode)p).child1.guide) <= 0){
	           printAll(((InternalNode)p).child0, height-1, output);
	           printLE(((InternalNode)p).child1, x, height-1, output);
	       }
	       else{
	           printAll(((InternalNode)p).child0, height-1, output);
	           printAll(((InternalNode)p).child1, height-1, output);
	           printLE(((InternalNode)p).child2, x, height-1, output);
	       }
	      
	   }
	    
	   
	   //MY PRINT GREATER THAN OR EQUAL TO FUNCTION;
	   static void printGE(Node p, String x, int height, BufferedWriter output){
	       if(height==0){
	    	   //CHANGED THE INEQUALITY SIGN HERE, fixed 1 of 2 problems with this.
	          if(x.compareTo(p.guide) <= 0){
	              try {
	            	  output.write(((LeafNode)p).guide + " " + ((LeafNode)p).value + "\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	          } 
	       }
	       
	      
	       else if(x.compareTo(((InternalNode)p).child0.guide) <= 0){
	           printGE(((InternalNode)p).child0, x, height-1, output);
	           printAll(((InternalNode)p).child1, height-1, output);
	           
	           if (((InternalNode)p).child2 != null) {
	        	   printAll(((InternalNode)p).child2, height-1, output);
	           }
	       }
	       //Casted 2nd part of the statement with (Internal Node)
	       else if(((InternalNode)p).child2 == null || x.compareTo(((InternalNode)p).guide) <= 0){
	           printGE(((InternalNode)p).child1, x, height-1, output);
	           
	           if (((InternalNode)p).child2 != null) {
	        	   printAll(((InternalNode)p).child2, height-1, output);
	           }
	       }
	       else{   
	           printGE(((InternalNode)p).child2, x, height-1, output);
	       }
	       
	       
	           
	   }
	    
	   static void printRange(Node P, String x, String y, int height, BufferedWriter output){
	       if(height==0){
	    	   //Did not cast Internal Node on the first x statement
	    
	    	   //changed the inequality statements here
	           if(y.compareTo(((InternalNode)P).guide) <= 0 && ((InternalNode)P).guide.compareTo(x) <= 0){
	        	   //y.compareTo(((InternalNode)P).guide)
	               try {
	            	   output.write(((LeafNode)P).guide + " " + ((LeafNode)P).value + "\n");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	           }
	       }
	       else if(y.compareTo(((InternalNode)P).child0.guide) <= 0){
	           printRange(((InternalNode)P).child0, x, y, height-1, output);
	       }
	       
	       else if(y.compareTo(((InternalNode)P).child1.guide) <= 0 || ((InternalNode)P).child2 == null){
	           if(x.compareTo(((InternalNode)P).child0.guide) <= 0){
	               printGE(((InternalNode)P).child0, x, height-1, output);
	               printLE(((InternalNode)P).child1, y, height-1, output);
	           }
	           else{
	               printRange(((InternalNode)P).child1, x , y ,height-1, output);
	           }
	       }
	       else{
	    	   //Changed the inequality sign here, please be wary, my a-z output is having a problem at this statement
	           if(x.compareTo(((InternalNode)P).child0.guide) <= 0){
	        	   
	               printGE(((InternalNode)P).child0, x, height-1, output);
	               printAll(((InternalNode)P).child1, height-1, output);
	               printLE(((InternalNode)P).child2, y, height-1, output);
	               
	           }
	           //test here
	           else if(x.compareTo(((InternalNode)P).child1.guide) <= 0){
	               printGE(((InternalNode)P).child1, x, height-1, output);
	               printLE(((InternalNode)P).child2, y, height-1, output);
	           }
	           else{
	               printRange(((InternalNode)P).child2, x, y, height-1, output);
	           }
	       }
	       try {
				output.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
	   } 

	   static void insert(String key, int value, TwoThreeTree tree) {
	   // insert a key value pair into tree (overwrite existsing value
	   // if key is already present)

	      int h = tree.height;

	      if (h == -1) {
	          LeafNode newLeaf = new LeafNode();
	          newLeaf.guide = key;
	          newLeaf.value = value;
	          tree.root = newLeaf; 
	          tree.height = 0;
	      }
	      else {
	         WorkSpace ws = doInsert(key, value, tree.root, h);

	         if (ws != null && ws.newNode != null) {
	         // create a new root

	            InternalNode newRoot = new InternalNode();
	            if (ws.offset == 0) {
	               newRoot.child0 = ws.newNode; 
	               newRoot.child1 = tree.root;
	            }
	            else {
	               newRoot.child0 = tree.root; 
	               newRoot.child1 = ws.newNode;
	            }
	            resetGuide(newRoot);
	            tree.root = newRoot;
	            tree.height = h+1;
	         }
	      }
	   }

	   static WorkSpace doInsert(String key, int value, Node p, int h) {
	   // auxiliary recursive routine for insert

	      if (h == 0) {
	         // we're at the leaf level, so compare and 
	         // either update value or insert new leaf

	         LeafNode leaf = (LeafNode) p; //downcast
	         int cmp = key.compareTo(leaf.guide);

	         if (cmp == 0) {
	            leaf.value = value; 
	            return null;
	         }

	         // create new leaf node and insert into tree
	         LeafNode newLeaf = new LeafNode();
	         newLeaf.guide = key; 
	         newLeaf.value = value;

	         int offset = (cmp < 0) ? 0 : 1;
	         // offset == 0 => newLeaf inserted as left sibling
	         // offset == 1 => newLeaf inserted as right sibling

	         WorkSpace ws = new WorkSpace();
	         ws.newNode = newLeaf;
	         ws.offset = offset;
	         ws.scratch = new Node[4];

	         return ws;
	      }
	      else {
	         InternalNode q = (InternalNode) p; // downcast
	         int pos;
	         WorkSpace ws;

	         if (key.compareTo(q.child0.guide) <= 0) {
	            pos = 0; 
	            ws = doInsert(key, value, q.child0, h-1);
	         }
	         else if (key.compareTo(q.child1.guide) <= 0 || q.child2 == null) {
	            pos = 1;
	            ws = doInsert(key, value, q.child1, h-1);
	         }
	         else {
	            pos = 2; 
	            ws = doInsert(key, value, q.child2, h-1);
	         }

	         if (ws != null) {
	            if (ws.newNode != null) {
	               // make ws.newNode child # pos + ws.offset of q

	               int sz = copyOutChildren(q, ws.scratch);
	               insertNode(ws.scratch, ws.newNode, sz, pos + ws.offset);
	               if (sz == 2) {
	                  ws.newNode = null;
	                  ws.guideChanged = resetChildren(q, ws.scratch, 0, 3);
	               }
	               else {
	                  ws.newNode = new InternalNode();
	                  ws.offset = 1;
	                  resetChildren(q, ws.scratch, 0, 2);
	                  resetChildren((InternalNode) ws.newNode, ws.scratch, 2, 2);
	               }
	            }
	            else if (ws.guideChanged) {
	               ws.guideChanged = resetGuide(q);
	            }
	         }

	         return ws;
	      }
	   }


	   static int copyOutChildren(InternalNode q, Node[] x) {
	   // copy children of q into x, and return # of children

	      int sz = 2;
	      x[0] = q.child0; x[1] = q.child1;
	      if (q.child2 != null) {
	         x[2] = q.child2; 
	         sz = 3;
	      }
	      return sz;
	   }

	   static void insertNode(Node[] x, Node p, int sz, int pos) {
	   // insert p in x[0..sz) at position pos,
	   // moving existing extries to the right

	      for (int i = sz; i > pos; i--)
	         x[i] = x[i-1];

	      x[pos] = p;
	   }

	   static boolean resetGuide(InternalNode q) {
	   // reset q.guide, and return true if it changes.

	      String oldGuide = q.guide;
	      if (q.child2 != null)
	         q.guide = q.child2.guide;
	      else
	         q.guide = q.child1.guide;

	      return q.guide != oldGuide;
	   }


	   static boolean resetChildren(InternalNode q, Node[] x, int pos, int sz) {
	   // reset q's children to x[pos..pos+sz), where sz is 2 or 3.
	   // also resets guide, and returns the result of that

	      q.child0 = x[pos]; 
	      q.child1 = x[pos+1];

	      if (sz == 3) 
	         q.child2 = x[pos+2];
	      else
	         q.child2 = null;

	      return resetGuide(q);
	   }
	}

	    



