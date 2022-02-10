package se.su.dsv.MyAldaList;

/*
 * Denna klass ska förberedas för att kunna användas som nyckel i en hashtabell. 
 * Du får göra nödvändiga ändringar även i klasserna MyString och ISBN10.
 * 
 * Hashkoden ska räknas ut på ett effektivt sätt och följa de regler och 
 * rekommendationer som finns för hur en hashkod ska konstrueras. Notera i en 
 * kommentar i koden hur du har tänkt när du konstruerat din hashkod.
 */
public class Book {
	private MyString title;
	private MyString author;
	private ISBN10 isbn;
	private MyString content;
	private int price;

	public Book(String title, String author, String isbn, String content, int price) {
		this.title = new MyString(title);
		this.author = new MyString(author);
		this.isbn = new ISBN10(isbn);
		this.content = new MyString(content);
		this.price = price;
	}

	public MyString getTitle() {
		return title;
	}

	public MyString getAuthor() {
		return author;
	}

	public ISBN10 getIsbn() {
		return isbn;
	}

	public MyString getContent() {
		return content;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return String.format("\"%s\" by %s Price: %d ISBN: %s lenght: %s", title, author, price, isbn,
				content.length());
	}

	// @Override
	// public boolean equals(Object o){
	// 	if(o instanceof Book){
	// 		Book book = (Book) o;
	// 		if(isbn.equals(book.isbn)){
	// 			return true;
	// 		}
	// 	} 
	// 	return false;
	// }


	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof Book)) {
			return false;
		}
		Book book = (Book) o;
		return (new MyString(isbn.toString()).equals(new MyString(book.isbn.toString())));
	}


	@Override
	/**
	 *  Valde att använda ISBN eftersom det är en unik 
	 *  identifierar av böcker redan - som skiljer olika upplagor åt.
	 * Jag valde även att överlagra MyStrings hashCode istället för
	 * ISBN direkt, då jag känner att det leder till fler möjligheter
	 * att återanvända kod.
	 */
	public int hashCode(){
		return (new MyString(isbn.toString())).hashCode();
	}
}
