package se.su.dsv.MyAldaList;
import java.util.Iterator;

public class MyALDAList<E> implements ALDAList<E>{

    Node<E> node;
    int size;

    public void add(E element){
        if(node == null){
            node = new Node<>(element);
        } else {
            node.setNext(new Node<>(element));
        }
        size++;
    }

	public void add(int index, E element){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        } 
        Node<E> leftNode = get(index-1, true);
        Node<E> rightNode = leftNode.getNext();
        Node<E> newNode = new Node<>(element);
        newNode.setNext(rightNode);
        leftNode.shiftNext(newNode);
        size++;
    }

	public E remove(int index){
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        } 
        Node<E> leftNode = get(index-1, true);
        Node <E> nodeToBeRemoved = leftNode.getNext();
        Node<E> rightNode = nodeToBeRemoved.getNext();
        leftNode.shiftNext(rightNode);
        return nodeToBeRemoved.getData();

    }

	public boolean remove(E element){
        int index = find(element);
        if(index == -1){
            return false;
        }

        remove(index);
        return true;

    }

	public E get(int index){
        return get(index, true).getData();
    }

    private Node<E> get(int index, boolean lookingForNodes){
        if(index < 0 || index >= size()){
            throw new IndexOutOfBoundsException();
        }
        Node<E> nodeToGet = node;
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
        if(node == null){
            return -1;
        }
        E foundNode = node.getData();
        int counter = 0;
        while(foundNode != element){
            foundNode = node.getNext().getData();
            counter++;
            if(foundNode == null){
                return -1;
            }
        }
        return counter;
    }

	public int indexOf(E element){
        return find(element);
    }

	public void clear(){
        node = null;
        size = 0;
    }

	public int size(){
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    private class Node<E> {

        E data;
        Node<E> nextNode;

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