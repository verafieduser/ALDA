package se.su.dsv.MyAldaList;

/**
 * 
 * Detta är den enda av de tre klasserna ni ska göra några ändringar i. (Om ni
 * inte vill lägga till fler testfall.) Det är också den enda av klasserna ni
 * ska lämna in. Glöm inte att namn och användarnamn ska stå i en kommentar
 * högst upp, och att en eventuell paketdeklarationen måste plockas bort vid inlämningen för
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
		return false;
	}

	private T findMin() {
		return null;
	}

	public BinarySearchTreeNode<T> remove(T data) {

		return null;
	}

	public boolean contains(T data) {
		if(this.data.compareTo(data)==0){
			return true;
		} 

		if(this.data.compareTo(data)<0){
			if(left!=null){
				return left.contains(data);
			}
		} else {
			if(right!=null){
				return right.contains(data);
			}
		}
		
		return false;
		//return this.data.compareTo(data)<0 ? left.contains(data) : right.contains(data);
		// if(this.data.compareTo(data)<0){
		// 	return left.contains(data);
		// } else {
		// 	return right.contains(data);
		// }
	}

	public int size() {
		int size = 1; 
		if(left!=null){
			size += left.size();
		} 
		if(right!=null){
			size += right.size();		
		}

		return size;
	}

	public int depth() {
		return -1;
	}

	public String toString() {
		return "";
	}
}
