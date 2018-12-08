/**
 * 
 * @author Ben
 *
 */

public class Book extends Resource {
	protected static double fineDay = 2.00;
	protected static double fineMax = 25.00;
	private String author;
	private String publisher;
	private String genre;
	private String ISBN;
	private String language;
	
	public Book(String title, int year, Thumbnail thumbnail, String author, String publisher, String genre, String ISBN, String language) {
		super(title, year, thumbnail);
		this.author = author;
		this.publisher = publisher;
		this.genre = genre;
		this.ISBN = ISBN;
		this.language = language;
	}
	
	public Book(String resourceID, String title, int year, Thumbnail thumbnail, RequestQueue queue, String author, String publisher, String genre, String ISBN, String language) {
		super(resourceID, title, year, thumbnail, queue);
		this.author = author;
		this.publisher = publisher;
		this.genre = genre;
		this.ISBN = ISBN;
		this.language = language;
	}
	
	public String toString() {	
		String x;
		x = ("Title: " + title + " \nYear: " + year + " \nAuthor: " + author + " \nPublisher: " + publisher + " \nGenre: " + genre + " \nISBN: " + ISBN + "\nLanguage " + language);
		return x;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
		

}
	

