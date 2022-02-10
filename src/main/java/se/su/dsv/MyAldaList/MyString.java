package se.su.dsv.MyAldaList;

import java.util.Arrays;

public class MyString {

	private char[] data;
	
	public MyString(String title) {
		data = title.toCharArray();
	}

	public Object length() {
		return data.length;
	}

	@Override
	/**
	 * Genererar hashcoden genom att ta varannat värde
	 * i strängen och sedan förvandla den resulterande 
	 * strängen till en int. 
	 */
	public int hashCode(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < data.length;i+=2){
			sb.append(data[i]);

		}
		return Integer.parseInt(sb.toString());
	}	
	

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof MyString)) {
			return false;
		}
		MyString myString = (MyString) o;
		return Arrays.equals(data, myString.data);
	}
	

	@Override
	public String toString() {
		return new String(data);
	}
	
}
