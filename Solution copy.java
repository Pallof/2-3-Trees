import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

//DONT FORGET TO MAKE SURE X AND Y ARE IN THE CORRECT POSITION
//CHECK THE TWO STRINGS AND IMPLEMENT A SWAP IF NEEDED

public class Solution {
    //we will need an update function???? + Search Function
    //Say throws exceptions instead of
    public static void main(String[] args) throws Exception {

        //my output.flush is in the printRange function
        BufferedWriter output = new BufferedWriter(new OutputStreamWriter(System.out, "ASCII"), 4096);

        TwoThreeTree newTree = new TwoThreeTree();

        Scanner sc = new Scanner(System.in);

        //THIS WHOLE INPUT NEEDS TO BE FIXED, A COMPLETE MESS, also make this more readable
        //wait, we can just use a switch statement here and check our input int value , bless u zahran
        int size = sc.nextInt();
        int i = 0;
        //int j = 0;
        //output.write("Planet name and entrance fee K \n");
        //int count = 0;
        while(i < size) {
            int type = sc.nextInt();
            switch(type){
                //QUERY 1 works
                case 1:
                    String planetName = sc.next();
                    int entranceFee = sc.nextInt();
                    //output.write("ARE U WRITING YES NO?");
                    twothree.insert(planetName, entranceFee, newTree);
                    i++;
                    break;
                //QUERY 2 works
                case 2:
                    String planetName1 = sc.next();
                    String planetName2 = sc.next();
                    int priceIncrease = sc.nextInt();
                    //make sure the names aint backwards, thought u were sly huh?
                    //System.out.println(count);
                    if(planetName1.compareTo(planetName2) <= 0) {
                        //output.write("addrange step 1 \n");
                        twothree.addRange(newTree.root, planetName1, planetName2, newTree.height, priceIncrease, output);
                    }
                    else {
                        twothree.addRange(newTree.root, planetName2, planetName1, newTree.height, priceIncrease, output);
                    }

                    i++;
                    break;

                //QUERY 3, THIS really likes giving me problems
                case 3:
                    String desiredPlanet = sc.next();
                    twothree.search(newTree.root, desiredPlanet, newTree.height, 0, output);
                    i++;
                    break;

                default:
                    //please take out at the end
                    output.write("if you are seeing this statement you really fucked up");
                    break;
            }
//I KEEP RETURNING ALL -1'S SO EITHER INSERT IT BROKEN OR SEARCH IS
        }
        sc.close();
        output.flush();
            //please look at the note above....
    }
}
class Node {
    String guide;
    //our lazy update value, we need to fix our do_insert now
    int value = 0;
    // guide points to max key in subtree rooted at node
}

class InternalNode extends Node {
    Node child0, child1, child2;

    // child0 and child1 are always non-null
    // child2 is null iff node has only 2 children
}

class LeafNode extends Node {
    // guide points to the key
    //int value;
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
    //this is the function that adds to the guide values and not the leaf node itself
    //if we just add to the leafnode itself that defeats the point of having a lazy update??????

    //THIS NEEDS TO BE ITERATIVE
    static void addAll(Node p, int height, int addVal, BufferedWriter output) throws Exception {
        //what if i just did p.lazy += addVal, u know what that is what i will do
        if (height == 0) {
            ((LeafNode) p).value += addVal;
        } else {
            p.value += addVal;
            //((InternalNode)p).child0.value +=addVal;
            //((InternalNode)p).child1.value +=addVal;
            //addAll(((InternalNode) p).child0, height - 1, addVal, output);
            //addAll(((InternalNode) p).child1, height - 1, addVal, output);

           // if (((InternalNode) p).child2 != null) {
                //((InternalNode) p).child2.value +=addVal;
               // addAll(((InternalNode) p).child2, height - 1, addVal, output);
            //}

        }
    }
//need to cast p into a leaf node, leaf node can also access guide



    // MY PRINT LESS THAN OR EQUAL TO FUNCTION
    static void addLE(Node p, String x, int height, int addVal, BufferedWriter output) throws Exception{
        if(height==0){
            // PLEASE BE WARY OF THIS INEQUALITY, DOUBLE CHECK WHEN RUNNING MAY CAUSE FUTURE ISSUES
            if(x.compareTo(p.guide) >= 0 ){
                ((LeafNode)p).value += addVal;
            }
        }
        else if(x.compareTo(((InternalNode)p).child0.guide) <= 0){
            addLE(((InternalNode)p).child0, x, height-1, addVal, output);
        }
        else if(((InternalNode)p).child2 == null || x.compareTo(((InternalNode)p).child1.guide) <= 0){
            addAll(((InternalNode)p).child0, height-1, addVal, output);
            addLE(((InternalNode)p).child1, x, height-1, addVal, output);
        }
        else{
            addAll(((InternalNode)p).child0, height-1, addVal, output);
            //changed this to height-1 and the one above
            addAll(((InternalNode)p).child1, height-1, addVal, output);
            addLE(((InternalNode)p).child2, x, height-1, addVal, output);
        }
    }
    //MY PRINT GREATER THAN OR EQUAL TO FUNCTION;
    static void addGE(Node p, String x, int height, int addVal, BufferedWriter output) throws Exception{
        if(height==0){
            //CHANGED THE INEQUALITY SIGN HERE, fixed 1 of 2 problems with this.
            if(x.compareTo(p.guide) <= 0){ // This part works right
                ((LeafNode)p).value += addVal;
            }
        }

        //Test check here again
        else if(x.compareTo(((InternalNode)p).child0.guide) <= 0){
            addGE(((InternalNode)p).child0, x, height-1, addVal, output);
            //changed  to height-1 here
            addAll(((InternalNode)p).child1, height-1, addVal, output);

            if (((InternalNode)p).child2 != null) {
                //changed to height-1 here as well
                addAll(((InternalNode)p).child2, height-1, addVal, output);
            }
        }
        //Casted 2nd part of the statement with (Internal Node)
        else if(((InternalNode)p).child2 == null || x.compareTo(((InternalNode)p).guide) <= 0){
            addGE(((InternalNode)p).child1, x, height-1, addVal, output);
            //this is a problem statement   BIG OL ERROR RIGHT HERE WHY IS THAT??????? U TELL ME GOOD SIR
            //Houston we fixed the problem
            if (((InternalNode)p).child2 != null) {
                addGE(((InternalNode)p).child2, x, height-1, addVal, output);
            }
        }
        else{
            addGE(((InternalNode)p).child2, x, height-1, addVal, output);
        }
    }

    static void addRange(Node P, String x, String y, int height, int addVal, BufferedWriter output) throws Exception{
        if(height==0){
            //Trying to cast as leaf node
            //you might need to double check this statement;
            if(x.compareTo(P.guide)<=0 && P.guide.compareTo(y) <= 0) {
                //output.write("addrange if height =0 \n");
                ((LeafNode)P).value += addVal;
            }
        }


        else if(y.compareTo(((InternalNode)P).child0.guide) <= 0){
            //output.write("1st else if addRange \n");
            addRange(((InternalNode)P).child0, x, y, height-1, addVal, output);
        }

        else if(((InternalNode)P).child2 == null || y.compareTo(((InternalNode)P).child1.guide) <= 0){
            //output.write("2nd else if in addRange \n");
            if(x.compareTo(((InternalNode)P).child0.guide) <= 0){
                //output.write(" addrange 2.1 \n");
                addGE(((InternalNode)P).child0, x, height-1, addVal, output);
                addLE(((InternalNode)P).child1, y, height-1, addVal, output);
            }
            else{
                //output.write(" addrange 2.2 \n");
                addRange(((InternalNode)P).child1, x , y ,height-1, addVal, output);
            }
        }
        else{
            //output.write("Final else statement in addRange \n");
            if(x.compareTo(((InternalNode)P).child0.guide) <= 0){
                //output.write(" range 3.1 \n");  //this is our special case
                addGE(((InternalNode)P).child0, x, height-1, addVal, output);
                addAll(((InternalNode)P).child1, height-1, addVal, output);
                addLE(((InternalNode)P).child2, y, height-1, addVal, output);
            }

            else if(x.compareTo(((InternalNode)P).child1.guide) <= 0){
                //output.write(" range 3.2 \n");
                addGE(((InternalNode)P).child1, x, height-1, addVal, output);
                addLE(((InternalNode)P).child2, y, height-1, addVal, output);
            }

            else{
                //output.write(" range 3.3 \n");
                addRange(((InternalNode)P).child2, x, y, height-1, addVal, output);
            }
        }


    }
    //out search function as we traverse needs to add values up from the guide in which we inserted
//search function works right kinda....
    static void search(Node p, String x, int height, int searchVal, BufferedWriter output) throws Exception {
        //when searching you will need to add the guide values to add, this function will need modifying

        if(height==0){
            if(x.equals(((LeafNode)p).guide)){
                //output.write(searchVal + " search value almost there \n");
                searchVal += ((LeafNode)p).value;
                output.write(searchVal + "\n");
                //return;
            }
            else{
                //this means we didn't find the node we were looking for
                output.write("-1" + "\n");
                //return;
            }
        }
        else if(x.compareTo(((InternalNode)p).child0.guide) <= 0){
            search(((InternalNode)p).child0, x, height-1, searchVal+p.value, output);
        }
        else if( ((InternalNode)p).child2 == null || x.compareTo(((InternalNode)p).child1.guide) <= 0){
            search(((InternalNode)p).child1, x, height-1, searchVal+p.value, output);
        }
        else{
            //i am not sure if this is how you should pass the search val variable.......relook at this
            search(((InternalNode)p).child2, x, height-1, searchVal+p.value, output);
        }
    }
    static void insert(String key, int value, TwoThreeTree tree) {
        // insert a key value pair into tree (overwrite existing value
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
            //
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
            //DON'T CHANGE ANYTHING IN THE LEAF NODE, dont be a dingdong dan

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
            //System.out.println(" our magical 3 lines for codes + p.lazy " + p.lazy + " \n");
            //here we need to implement the 'snowplow', should be about 3 lines of code - Shoup
            (q).child0.value += p.value;
            (q).child1.value += p.value;
            if(q.child2 != null) { q.child2.value += p.value; }
            q.value = 0;

            //THIS IS THE SEARCH FUNCTION IN THE DO_INSERT
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
        // moving existing entries to the right

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






