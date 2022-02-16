package se.su.dsv.MyAldaList.ProjectOne;

import java.util.ArrayList;

//tree is only "widened" by balancing. 
//otherwise nodes are just sent down into matching node
//always "minimum" amount of children = 2
//should it allow for multiple identical elements? easier if not.

//TODO: could probably be linkedlist inside, instead of arraylist - because we never use the quick index access?

public class DynamicArrayTreeNode<T extends Comparable<T>> {

    /**
     * For every level of x in height width increases with 1. 
     */
    public static final int WIDTH_INCREASE_EVERY_X_DEPTH = 2;
    /**
     * The amount of children the node should have. Variable based on the height of current node through WIDTH_INCREASE_EVERY_X_DEPTH.
     */
    private int width = 1;
    /**
     * List of children a node has. Size is dynamic based on balancing, but also MINIMUM_AMOUNT_OF_CHILDREN. 
     */
    private ArrayList<DynamicArrayTreeNode<T>> children = new ArrayList<>();
    /**
     * The data held by the node.
     */
    private T data;

    /**
     * 
     * @param data
     */
    public DynamicArrayTreeNode(T data) {
        this.data = data;
    }

    public T get(T data) {
        //if data is below or equal to existing node, enter that node.
        if(data.compareTo(children.get(0).data) <= 0){
            return children.get(0).data;
        }

        //if data is above node at i, up to equal with node at i+1 - enter i+1.
        for (int i = 0; i < children.size()-1; i++) {
            if (data.compareTo(children.get(i).data) > 0 && data.compareTo(children.get(i + 1).data) <= 0) {
                return children.get(i).data;
            }
        }
        return null;
    }

    /**
     * implementations:
     * Depth. Sure that the tree can keep a check on the deepest depth? and compare changes to that? 
     * how do you decide when to send things into a sibling, or a new child?  Every x down, 1 wider? So if every x down, 1 wider - 
     * a node with a height of 6, and x=2 - it would be 3 wide. If the height is 10, then it is 5 wide?
     * 
     * Works by just adding it as a child higher up, and removing it where it was. Children and all!
     * 
     * Should go down recursively... return null if no shift, and return itself if it has a depth that is too deep?
     * 
     * How should this work with remove? When things go from wide to thin, and things need to be sent down?
     */
    public DynamicArrayTreeNode<T> balance(){
        //TODO: IMPLEMENT.
        //addChildNode(balance());
        return null;
    }

    public boolean add(T data) {
        if (data == null) {
            return false;
        }

        //if data is below or equal to existing node, enter that node.
        if(data.compareTo(children.get(0).data) <= 0){
            return children.get(0).add(data);
        }

        //if data is above node at i, up to equal with node at i+1 - enter i+1.
        for (int i = 0; i < children.size()-1; i++) {
            if (data.compareTo(children.get(i).data) > 0 && data.compareTo(children.get(i + 1).data) <= 0) {
                return children.get(i+1).add(data);
            }
        }

        return addChild(data, true);
    }

    private boolean add(DynamicArrayTreeNode<T> node){
        //is this method a good idea? Should be used for balance?
        return false;
    }

    public boolean addChild(T data, boolean nodeIsNew) {
        DynamicArrayTreeNode<T> newNode = new DynamicArrayTreeNode<>(data);
        //TODO: dont think this if is necessary!
        if (data.compareTo(children.get(0).data) < 0) {
            children.add(0, newNode);
        } else {
            for (int i = 0; i < children.size(); i++) {
                if (data.compareTo(children.get(i).data) < 0) {
                    children.add(i, newNode);
                }
            }
        }

        //TODO: balance();
        if (!nodeIsNew) {
            removeButNotInThis(data);
        }
        return true;
    }

    public boolean addChild(DynamicArrayTreeNode<T> node) {
        if (node.data.compareTo(children.get(0).data) < 0) {
            children.add(0, node);
        } else {
            for (int i = 0; i < children.size(); i++) {
                if (node.data.compareTo(children.get(i).data) < 0) {
                    children.add(i, node);
                }
            }
        }
        removeButNotInThis(node.data);

        return true;
    }

    //TODO: fix Remove stuff for dynamic implem instead of binary.
    public boolean remove(T data) {
        for (int i = 0; i < children.size(); i++) {
            if (data.compareTo(children.get(i).data) == 0) {
                // what do with children when current node is removed? send up the childrens
                // list? what do if root?
                // what do if last value in list?
            } else if (data.compareTo(children.get(i).data) < 0 && data.compareTo(children.get(i + 1).data) > 0) {
                children.get(i).remove(data);
            }
        }
        return false;
    }

    public boolean removeButNotInThis(T toBeRemoved) {
        for (int i = 0; i < children.size() - 1; i++) {
            // if node is bigger than node on the left, and smaller than the node on the
            // right - enter the node on the left.
            if (toBeRemoved.compareTo(children.get(i).data) < 0
                    && toBeRemoved.compareTo(children.get(i + 1).data) > 0) {
                return children.get(i).remove(toBeRemoved);

            }
        }
        // check the last value?

        return false;
    }
}
