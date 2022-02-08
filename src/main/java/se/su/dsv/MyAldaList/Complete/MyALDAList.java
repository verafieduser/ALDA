//Vera Sol Nygren, klny8594

package se.su.dsv.MyAldaList.Complete;

import java.util.Iterator;
import java.util.LinkedList;

public class MyALDAList<E> implements ALDAList<E> {

    private Node<E> leftSentinel = new Node<>(null);
    private Node<E> rightSentinel = new Node<>(null);
    /**
     * Added variable lastNode even though it made the class more complicated,
     * to prevent that add(E element) would take O(N) since it always adds elements
     * to the end of the list. Now, you can just add a new node
     * as the last nodes next node.
     */
    private Node<E> lastNode;
    private int size;
    /**
     * Keeps track of the amount of modifications. Used together with the MyIterator
     * inner class to
     * prevent concurrent modification.
     */
    private int modifications;

    public MyALDAList() {
        leftSentinel.setNext(rightSentinel);
    }

    /**
     * O(1)
     * 
     * @param <E>     : Can be any type.
     * @param element : the element added to the list. Always added to the end of
     *                the list in this method.
     */
    public void add(E element) {
        Node<E> newNode = new Node<>(element);
        if (leftSentinel.getNext() == rightSentinel) {
            leftSentinel.setNext(newNode);
        } else {
            lastNode.setNext(newNode);
        }
        newNode.setNext(rightSentinel);
        lastNode = newNode;

        size++;
        modifications++;
    }

    private void ordoExample(){
        String str = "";
        int n = 0;



    }

    /**
     * O(N) due to having to locate the index specified where the element is to be
     * added. This cost occurs in the method getNodeByIndex(int index)
     * 
     * @param index   : index where element is to be added. Element currently at
     *                that position and to the right of it
     *                will be shifted one step to the right.
     * @param element : the element that will be added.
     */
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> newNode = new Node<>(element);

        if (index == 0) {
            newNode.setNext(leftSentinel.getNext());
            leftSentinel.setNext(newNode);

            if (newNode.getNext() == rightSentinel) {
                lastNode = newNode;
            }

            size++;
            modifications++;
            return;
        }

        Node<E> leftNode = getNodeByIndex(index - 1);
        Node<E> rightNode = leftNode.getNext();

        newNode.setNext(rightNode);
        leftNode.setNext(newNode);
        if (rightNode == rightSentinel) {
            lastNode = newNode;
        }
        size++;
        modifications++;


    }

    /**
     * O(N) due to having to traverse the list in order to reach the specified
     * index. The cost occurs by the call of the method getNodeByIndex(int index)
     * 
     * @param index : the index of the element to be removed. All elements to the
     *              right of it will be shifted leftwards one step as a result.
     *              If the list is instantiated as being a list of Integers, be
     *              careful not
     *              to accidently use this method to attempt to remove an element.
     * @return returns the element that was removed from the list.
     */
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> nodeToBeRemoved;
        if (index > 0) {
            Node<E> leftNode = getNodeByIndex(index - 1);
            nodeToBeRemoved = leftNode.getNext();
            remove(nodeToBeRemoved, leftNode);
        } else {
            nodeToBeRemoved = leftSentinel.getNext();
            remove(nodeToBeRemoved, leftSentinel);
        }

        return nodeToBeRemoved.getData();
    }

    /**
     * O(N)
     * 
     * @param element : the element that should be removed from the list. If the
     *                list is instantiated as a list of Integers, be careful not to
     *                use this method to attempt to remove by index.
     * @return returns true if element was found and deleted, false if not.
     */
    public boolean remove(E element) {
        return find(element, true) != -1;
    }

    /**
     * O(1). Helper method for removal of elements. Created to avoid iterator call
     * to remove(E element) leading to O(N^2)
     */
    private void remove(Node<E> toBeRemoved, Node<E> previous) {
        Node<E> next = toBeRemoved.getNext();
        previous.setNext(next);
        modifications++;
        size--;

        //if the one to be removed is the last node in the list, make the previous node the lastNode.
        if (toBeRemoved == lastNode) {
            lastNode = previous;

            //if there are no nodes left, then make the lastNode null.
            if(lastNode == leftSentinel){
                lastNode = null;
            }
            
        } 

    }

    public E get(int index) {
        return getNodeByIndex(index).getData();
    }

    /** O(N). Returns node located at the specified index. */
    private Node<E> getNodeByIndex(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> nodeToGet = leftSentinel.getNext();
        for (int i = 0; i < index; i++) {
            nodeToGet = nodeToGet.getNext();
            if (nodeToGet == null || nodeToGet == rightSentinel) {
                throw new NullPointerException();
            }
        }
        return nodeToGet;
    }

    /** O(N). Returns true if list contains element, false if not. */
    public boolean contains(E element) {
        int contains = indexOf(element);
        return -1 != contains;
    }

    /**
     * Returns the index of E element.
     * 
     * @return index if found, -1 if not found
     */
    public int indexOf(E element) {
        return find(element, false);
    }

    /**
     * Finds the index of E element - with the option to remove the found object.
     * Mostly used to improve code reusability by not repeating code in remove(E
     * element)
     */
    private int find(E element, boolean removeFoundElement) {
        
        Node<E> foundNode = leftSentinel.getNext();

        if (foundNode == rightSentinel) {
            return -1;
        }

        Node<E> previousNode = leftSentinel;
        E foundNodeElement = foundNode.getData();
        int counter = 0;

        while (foundNodeElement != element) {
            previousNode = foundNode;
            foundNode = foundNode.getNext();

            if (foundNode == null) {
                return -1;
            }

            foundNodeElement = foundNode.getData();
            counter++;
        }
        if (removeFoundElement) {
            remove(foundNode, previousNode);
        }
        return counter;
    }

    /** Fully empties the list. */
    public void clear() {
        leftSentinel.setNext(rightSentinel);
        lastNode = null;
        size = 0;
        modifications++;
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> node = leftSentinel.getNext();
        if (node != rightSentinel) {

            sb.append(node.getData().toString());

            node = node.getNext();
            while (node != rightSentinel) {

                sb.append(", " + node.getData().toString());
                node = node.getNext();
            }
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<E> {

        private int expectedModifications = modifications;
        private Node<E> currentNode = leftSentinel;
        private Node<E> previousNode;
        private boolean okToRemove;

        @Override
        public boolean hasNext() {
            return currentNode.getNext() != rightSentinel;
        }

        @Override
        public E next() {
            if (modifications != expectedModifications) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!hasNext()) {
                throw new java.util.NoSuchElementException();
            }

            previousNode = currentNode;
            currentNode = currentNode.getNext();

            okToRemove = true;
            return currentNode.getData();
        }

        @Override
        public void remove() {
            if (modifications != expectedModifications) {
                throw new java.util.ConcurrentModificationException();
            }
            if (!okToRemove) {
                throw new IllegalStateException();
            }
            MyALDAList.this.remove(currentNode, previousNode);

            expectedModifications++;
            okToRemove = false;
        }
    }

    private class Node<E> {

        private E data;
        private Node<E> nextNode;

        public Node(E data) {
            this.data = data;
        }

        public Node<E> getNext() {
            return nextNode;

        }

        public E getData() {
            return data;
        }

        public void setNext(Node<E> node) {
            nextNode = node;
        }
    }
}