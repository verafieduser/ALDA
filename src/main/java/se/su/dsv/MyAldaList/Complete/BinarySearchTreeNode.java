package se.su.dsv.MyAldaList.Complete;
/** 
 * Vera Nygren
 * klny8594
 */

/**
 * 
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) Det är också den enda av klasserna ni
 * ska lämna in. Glöm inte att namn och användarnamn ska stå i en kommentar
 * högst upp, och att en eventuell paketdeklarationen måste plockas bort vid
 * inlämningen för
 * att koden ska gå igenom de automatiska testerna.
 * 
 * De ändringar som är tillåtna är begränsade av följande:
 * <ul>
 * <li>Ni får INTE byta namn på klassen.
 * <li>Ni får INTE lägga till några fler instansvariabler.
 * <li>Ni får INTE lägga till några statiska variabler.
 * <li>Ni får INTE använda några loopar någonstans. Detta gäller också alterntiv
 * till loopar, så som strömmar.
 * <li>Ni FÅR lägga till fler metoder, dessa ska då vara privata.
 * <li>Ni får INTE låta NÅGON metod ta en parameter av typen
 * BinarySearchTreeNode. Enbart den generiska typen (T eller vad ni väljer att
 * kalla den), String, StringBuilder, StringBuffer, samt primitiva typer är
 * tillåtna.
 * </ul>
 * 
 * @author henrikbe
 * 
 * @param <T>
 */

public class BinarySearchTreeNode<T extends Comparable<T>> {

	private T data;
	private BinarySearchTreeNode<T> left;
	private BinarySearchTreeNode<T> right;

	public BinarySearchTreeNode(T data) {
		this.data = data;
	}

	public boolean add(T data) {
		if (data == null) {
			return false;
		}
		int compareValue = this.data.compareTo(data);
		
		if (compareValue > 0) {
			if (left == null) {
				left = new BinarySearchTreeNode<>(data);
				return true;
			}
			return left.add(data);
		} else if (compareValue < 0) {
			if (right == null) {
				right = new BinarySearchTreeNode<>(data);
				return true;
			}
			return right.add(data);
		}
		return false;
	}

	private T findMin() {
		T min;
		if (left != null) {
			min = left.findMin();
		} else {
			min = data;
		}
		return min;

	}

	public BinarySearchTreeNode<T> remove(T data) {
		if (data == null) {
			return this;
		}

		if (this.data.compareTo(data) == 0) {
			return removeInThis();
		} else {
			return removeInSubTree(data);		
		}

	}

	private BinarySearchTreeNode<T> removeInThis() {
		// solution for removal of node w/ two children:
		if (right != null && left != null) {
			this.data = right.findMin(); // replaces current node with the smallest node in the right subtree
			right = right.remove(this.data); // removes the smallest node in the right subtree
			return this;
		}
		// solution for removal of node w/o no children:
		if (right == null && left == null) {
			return null;
		}
		// solution for removal of node w/ one child:
		return (right != null) ? right : left;
	}

	private BinarySearchTreeNode<T> removeInSubTree(T data) {
		int compareValue = this.data.compareTo(data);
		if (compareValue > 0 && left != null) {
			left = left.remove(data);
		} else if (compareValue < 0 && right != null) {
			right = right.remove(data);
		}
		return this;
	} 

	public boolean contains(T data) {
		if (this.data.compareTo(data) == 0) {
			return true;
		}

		if (this.data.compareTo(data) > 0) {
			if (left != null) {
				return left.contains(data);
			}
		} else {
			if (right != null) {
				return right.contains(data);
			}
		}

		return false;
	}

	public int size() {
		int size = 1;
		if (left != null) {
			size += left.size();
		}
		if (right != null) {
			size += right.size();
		}

		return size;
	}

	public int depth() {
		int leftDepth = 0;
		int rightDepth = 0;
		if (left != null) {
			leftDepth++;
			leftDepth += left.depth();
		}
		if (right != null) {
			rightDepth++;
			rightDepth += right.depth();
		}
		return rightDepth < leftDepth ? leftDepth : rightDepth;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		if (left != null) {
			sb.append(left.toString() + ", ");
		}

		sb.append(data);

		if (right != null) {
			sb.append(", " + right.toString());
		}

		return sb.toString();
	}
}
