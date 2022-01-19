//Vera Sol Nygren, klny8594

package se.su.dsv.MyAldaList;
import java.util.Iterator;

public class MyALDAList<E> implements ALDAList<E>{

    private Node<E> leftSentinel = new Node<>(null);
    private Node<E> rightSentinel = new Node<>(null);
    private Node<E> lastNode;
    private int size;
    private int modifications;

    public MyALDAList(){
        leftSentinel.setNext(rightSentinel);
    }

    public void add(E element){
        Node<E> newNode = new Node<>(element);
        if(leftSentinel.getNext()==rightSentinel){
            leftSentinel.setNext(newNode);
        } else {
            lastNode.setNext(newNode);
        }
        newNode.setNext(rightSentinel);
        lastNode = newNode;

        size++;
        modifications++;
    }

	public void add(int index, E element){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        Node<E> newNode = new Node<>(element);

        if(index == 0){
            newNode.setNext(leftSentinel.getNext());
            leftSentinel.setNext(newNode);

            if(newNode.getNext() == rightSentinel){
                lastNode = newNode;
            }

            size++;
            modifications++;
            return;
        }

        Node<E> leftNode = getNodeByIndex(index-1);
        Node<E> rightNode = leftNode.getNext();

        newNode.setNext(rightNode);
        leftNode.setNext(newNode);
        if(rightNode == rightSentinel){
            lastNode = newNode;
        }
        size++;
        modifications++;
    }

	public E remove(int index){
        if(index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        } 
        Node<E> nodeToBeRemoved;
        if(index > 0){
            Node<E> leftNode = getNodeByIndex(index-1); 
            nodeToBeRemoved = leftNode.getNext();
            remove(nodeToBeRemoved, leftNode);
        } else {
            nodeToBeRemoved = leftSentinel.getNext();
            removeFirst();
        }

        return nodeToBeRemoved.getData();
    }

	public boolean remove(E element){
        return find(element, true) != -1;
    }

    private void remove(Node<E> current, Node<E> previous){
        Node<E> next = current.getNext();
        previous.setNext(next);
        modifications++;
        size--;
        if(current == lastNode){
            lastNode = previous;
        }
    }

    private void removeFirst(){
        leftSentinel.setNext(leftSentinel.getNext().getNext());
        Node<E> newFirstNode = leftSentinel.getNext();
        if(newFirstNode.getNext() == rightSentinel){
            lastNode = newFirstNode;
        }
        size--;
        modifications++;
    }


	public E get(int index){
        return getNodeByIndex(index).getData();
    }

    private Node<E> getNodeByIndex(int index){
        if(index < 0 || index >= size()){
            throw new IndexOutOfBoundsException();
        }
        Node<E> nodeToGet = leftSentinel.getNext();
        for(int i = 0; i < index; i++){
            nodeToGet = nodeToGet.getNext();
            if(nodeToGet == null || nodeToGet == rightSentinel){
                throw new NullPointerException();
            }
        }
        return nodeToGet;
    }

	public boolean contains(E element){
        int contains = find(element);
        return -1 != contains;
    }

	public int indexOf(E element){
        return find(element);
    }

    private int find(E element){
        return find(element, false);
    }

    private int find(E element, boolean removeFoundElement){
        Node<E> foundNode = leftSentinel.getNext();

        if(foundNode == rightSentinel){
            return -1;
        }

        Node<E> previousNode = foundNode;
        E foundNodeElement = foundNode.getData();
        int counter = 0;

        while(foundNodeElement != element){
            previousNode = foundNode;
            foundNode = foundNode.getNext();

            if(foundNode == null){
                return -1;
            }
            
            foundNodeElement = foundNode.getData();
            counter++;
        }
        if(removeFoundElement){
            if(counter == 0){
                removeFirst();
            } else {
                remove(foundNode, previousNode); 
            }

        }
        return counter;
    }

	public void clear(){
        leftSentinel.setNext(rightSentinel);
        lastNode = null;
        size = 0;
        modifications++;
    }

	public int size(){
        return size;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<E> node = leftSentinel.getNext();
        if(node != rightSentinel){

            sb.append(node.getData().toString());

            node = node.getNext();
            while(node!=rightSentinel){

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
        private boolean okToRemove = false;

        @Override
        public boolean hasNext() {
            return currentNode.getNext() != rightSentinel;
        }
        @Override
        public E next() {
            if(modifications != expectedModifications){
                throw new java.util.ConcurrentModificationException();
            }
            if (!hasNext()){
                throw new java.util.NoSuchElementException();
            }

            previousNode = currentNode;
            currentNode = currentNode.getNext();
            
            okToRemove = true;
            return currentNode.getData();
        }

        @Override
        public void remove(){
            if(modifications != expectedModifications){
                throw new java.util.ConcurrentModificationException();
            }
            if (!okToRemove){
                throw new IllegalStateException();
            }
            if(previousNode == leftSentinel){
                removeFirst();
            } else {
                MyALDAList.this.remove(currentNode, previousNode);
            }

            expectedModifications++;
            okToRemove = false;
        }
    }

    private class Node<E> {

        private E data;
        private Node<E> nextNode;

        public Node(E data){
            this.data = data;
        }

        public Node<E> getNext(){
            return nextNode;
            
        }

        public E getData(){

            return data;
        }

        public void setNext(Node<E> node){
            nextNode = node;
        }

        public void shiftNext(Node<E> node){
            nextNode = node;
        }
    }
}