//Vera Sol Nygren, klny8594

package se.su.dsv.MyAldaList;
import java.util.Iterator;

public class MyALDAList<E> implements ALDAList<E>{

    Node<E> firstNode;
    Node<E> lastNode;
    int size;
    int modifications;

    public void add(E element){
        if(firstNode == null){
            firstNode = new Node<>(element);
        } else {
            Node<E> newNode = new Node<>(element);
            lastNode.setNext(newNode);
            lastNode = newNode;

        }
        size++;
        modifications++;
    }

	public void add(int index, E element){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        } else if(index == 0){
            Node<E> newNode = new Node<>(element);
            newNode.setNext(firstNode);
            firstNode = newNode;
            size++;
            modifications++;
            return;
        }

        Node<E> leftNode = getNodeByIndex(index-1);
        Node<E> rightNode = leftNode.getNext();
        Node<E> newNode = new Node<>(element);
        newNode.setNext(rightNode);
        leftNode.shiftNext(newNode);
        if(rightNode == null){
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
        if(index > 1){
            Node<E> leftNode = getNodeByIndex(index-1); 
            nodeToBeRemoved = leftNode.getNext();
            remove(nodeToBeRemoved, leftNode);
        } else {
            nodeToBeRemoved = firstNode;
            removeFirst();
        }

        return nodeToBeRemoved.getData();
    }

	public boolean remove(E element){
        return find(element, true) != -1;
    }

    private void remove(Node<E> current, Node<E> previous){
        Node<E> next = current.getNext();
        previous.shiftNext(next);
        modifications++;
        size--;
        if(current == lastNode){
            lastNode = previous;
        }
    }

    private void removeFirst(){
        Node<E> newFirstNode = firstNode.getNext();
        firstNode = newFirstNode;
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
        Node<E> nodeToGet = firstNode;
        for(int i = 0; i < index; i++){
            nodeToGet = nodeToGet.getNext();
            if(nodeToGet == null){
                throw new NullPointerException();
            }
        }
        return nodeToGet;
    }

	public boolean contains(E element){
        int contains = find(element);
        return -1 != contains;
    }

    private int find(E element){
        return find(element, false);
    }

    private int find(E element, boolean removeFoundElement){
        if(firstNode == null){
            return -1;
        }
        Node<E> foundNode = firstNode;
        Node<E> previousNode = firstNode;
        E foundNodeElement = firstNode.getData();
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
            remove(foundNode, previousNode);
        }
        return counter;
    }

	public int indexOf(E element){
        return find(element);
    }

	public void clear(){
        firstNode = null;
        lastNode = null;
        size = 0;
        modifications++;
    }

	public int size(){
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<E> {

        private int expectedModifications = modifications;
        private Node<E> currentNode = firstNode;
        private Node<E> previousNode;
        private boolean okToRemove = false;

        @Override
        public boolean hasNext() {
            return currentNode != lastNode;
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
            return previousNode.getData();
        }

        @Override
        public void remove(){
            if(modifications != expectedModifications){
                throw new java.util.ConcurrentModificationException();
            }
            if (!okToRemove){
                throw new IllegalStateException();
            }
            if(previousNode == null){
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
            if(nextNode == null){
                nextNode = node;
            } else {
                nextNode.setNext(node);
            }
        }

        public void shiftNext(Node<E> node){
            nextNode = node;
        }
    }
}