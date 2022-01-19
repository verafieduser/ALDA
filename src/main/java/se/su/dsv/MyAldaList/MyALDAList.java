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

    }

	public E remove(int index){
        return null;
    }

	public boolean remove(E element){
        return false;

    }

	public E get(int index){
        if(node == null){
            return null;
        }
        Node<E> nodeToGet = node;
        for(int i = 0; i < index; i++){
            nodeToGet = nodeToGet.getNext();
            if(nodeToGet == null){
                return null;
            }
        }
        return nodeToGet.getData();
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
        return -1;
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
    }
}