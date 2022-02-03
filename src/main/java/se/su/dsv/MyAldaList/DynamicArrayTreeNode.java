package se.su.dsv.MyAldaList;

import java.util.ArrayList;

public class DynamicArrayTreeNode<T extends Comparable<T>> {
    T data;
    ArrayList<DynamicArrayTreeNode<T>> children = new ArrayList<>();

    public DynamicArrayTreeNode(T data) {
        this.data = data;
    }

    public T get(T data) {
        for (int i = 0; i < children.size(); i++) {
            //what do end of list?
            if (data.compareTo(children.get(i).data) < 0 && data.compareTo(children.get(i + 1).data) > 0) {
                return children.get(i).data;
            }
        }
        return null;
    }

    public boolean add(T data) {
        if (data == null) {
            return false;
        }

        return true;
    }

    public boolean addChild(T data, boolean nodeIsNew) {
        DynamicArrayTreeNode<T> newNode = new DynamicArrayTreeNode<>(data);
        if (data.compareTo(children.get(0).data) < 0) {
            children.add(0, newNode);
        } else {
            for (int i = 0; i < children.size(); i++) {
                if (data.compareTo(children.get(i).data) < 0) {
                    children.add(i, newNode);
                }
            }
        }

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
