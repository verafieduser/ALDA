// Klassen i denna fil måste döpas om till DHeap för att testerna ska fungera. 
package se.su.dsv.MyAldaList;

//BinaryHeap class
//
//CONSTRUCTION: with optional capacity (that defaults to 100)
//            or an array containing initial items
//
//******************PUBLIC OPERATIONS*********************
//void insert( x )       --> Insert x
//Comparable deleteMin( )--> Return and remove smallest item
//Comparable findMin( )  --> Return smallest item
//boolean isEmpty( )     --> Return true if empty; else false
//void makeEmpty( )      --> Remove all items
//******************ERRORS********************************
//Throws UnderflowException as appropriate

/**
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * 
 * @author Mark Allen Weiss
 */
public class DHeap<AnyType extends Comparable<? super AnyType>> {

    int childrenPerNode;

    /**
     * Construct the d-heap. Default amount of children permitted is 2 (binary heap)
     */
    public DHeap() {
        this(2);
    }

    /**
     * Construct the binary heap.
     * 
     * @param childrenPerNode how many children each node is permitted.
     */
    public DHeap(int childrenPerNode) {
        if (childrenPerNode < 2) {
            throw new IllegalArgumentException();
        }
        currentSize = 0;
        this.childrenPerNode = childrenPerNode;
        array = (AnyType[]) new Comparable[DEFAULT_CAPACITY];
    }

    /**
     * Construct the binary heap given an array of items.
     */
    public DHeap(AnyType[] items, int childrenPerNode) {
        if (childrenPerNode < 2) {
            throw new IllegalArgumentException();
        }
        currentSize = items.length;
        array = (AnyType[]) new Comparable[(currentSize + childrenPerNode) * 11 / 10];

        int i = 1;
        for (AnyType item : items)
            array[i++] = item;
        buildHeap();
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * 
     * @param x the item to insert.
     */
    public void insert(AnyType x) {
        if (currentSize == array.length - 1){
            enlargeArray(array.length * childrenPerNode + 1);            
        }

        boolean keepPercolating = true;

        // Percolate up
        int index = ++currentSize;

        while (keepPercolating) {
            int parent;
            if (index == 1) {
                break;
            } 

            if(index > 1){
                if(index>childrenPerNode+1){
                    parent = parentIndex(index);
                    if(x.compareTo(array[parent])<0){
                        array[index] = array[parent];
                        index = parent;
                    } else {
                        keepPercolating = false;
                    }
                } else {
                    if(x.compareTo(array[1])<0){
                        array[index] = array[1];
                        index = 1;

                    }
                    keepPercolating = false;
                }
            }
        }
        array[index] = x;
    }

    private void enlargeArray(int newSize) {
        AnyType[] old = array;
        array = (AnyType[]) new Comparable[newSize];
        for (int i = 0; i < old.length; i++)
            array[i] = old[i];
    }

    /**
     * Find the smallest item in the priority queue.
     * 
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin() {
        if (isEmpty())
            throw new UnderflowException();
        return array[1];
    }

    /**
     * Remove the smallest item from the priority queue.
     * 
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin() {
        if (isEmpty())
            throw new UnderflowException();

        AnyType minItem = findMin();
        array[1] = array[currentSize--];
        percolateDown(1);

        return minItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap() {
        for (int i = currentSize / childrenPerNode; i > 0; i--)
            percolateDown(i);
    }

    /**
     * Test if the priority queue is logically empty.
     * 
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty() {
        currentSize = 0;
    }

    private static final int DEFAULT_CAPACITY = 10;

    private int currentSize; // Number of elements in heap
    private AnyType[] array; // The heap array

    /**
     * Internal method to percolate down in the heap.
     * 
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown(int hole) {
        int child;
        AnyType tmp = array[hole];

        for (; firstChildIndex(hole) <= currentSize; hole = child) {
            child = firstChildIndex(hole);
            int smallestChild = child;
            for(int i = 1; child+i<=currentSize && i<childrenPerNode;i++){
                if(array[child + i].compareTo(array[smallestChild]) < 0){
                    smallestChild=child+i;
                } 
            }
            child = smallestChild;

            if (array[child].compareTo(tmp) < 0)
                array[hole] = array[child];
            else
                break;
        }
        array[hole] = tmp;
    }

    // Test program
    public static void main(String[] args) {
        int numItems = 10000;
        DHeap<Integer> h = new DHeap<>();
        int i = 37;

        for (i = 37; i != 0; i = (i + 37) % numItems)
            h.insert(i);
        for (i = 1; i < numItems; i++)
            if (h.deleteMin() != i)
                System.out.println("Oops! " + i);
    }

    public int parentIndex(int nodeIndex) {
        if (nodeIndex < 2) {
            throw new IllegalArgumentException("Node index was: " + nodeIndex);
        }
        return (nodeIndex - 2) / childrenPerNode + 1;
    }

    public int firstChildIndex(int nodeIndex) {
        if (nodeIndex == 0) {
            throw new IllegalArgumentException("Node index was: " + nodeIndex);
        }

        return childrenPerNode * nodeIndex + 2 - childrenPerNode;
    }

    public int size() {
        return currentSize;
    }

    AnyType get(int index) {
        return array[index];
    }
public String printHeap () {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(AnyType element : array) {
            sb.append(element);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(AnyType element : array) {
            sb.append(element);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

}
