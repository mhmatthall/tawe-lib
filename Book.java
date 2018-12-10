/**
 * The Class Book.
 * Models books in the library
 * @author Ben Rochford
 */

public class Book extends Resource {
	
	protected static double fineDay = 2.00;
	protected static double fineMax = 25.00;
	private String author;
	private String publisher;
	private String genre;
	private String ISBN;
	private String language;
	
	/**
	 * Used for creating a new book
	 *
	 * @param title the title of the book
	 * @param year the year of the book
	 * @param thumbnail the thumbnail of the book
	 * @param author the author of the book
	 * @param publisher the publisher of the book
	 * @param genre the genre of the book
	 * @param ISBN the ISBN of the book
	 * @param language the language of the book
	 */
	public Book(String title, int year, Thumbnail thumbnail, String author, 
			String publisher, String genre, String ISBN, String language) {
		super(title, year, thumbnail);
		this.author = author;
		this.publisher = publisher;
		this.genre = genre;
		this.ISBN = ISBN;
		this.language = language;
	}
	
	/**
	 * Book Constructor for getting a book from the database
	 *
	 * @param resourceID the resource ID
	 * @param title the title of the book
	 * @param year the year of the book
	 * @param thumbnail the thumbnail of the book
	 * @param queue the ResourceQueue for the book
	 * @param author the author of the book
	 * @param publisher the publisher of the book
	 * @param genre the genre of the book
	 * @param ISBN the ISBN of the book
	 * @param language the language of the book
	 */
	public Book(String resourceID, String title, int year, Thumbnail thumbnail, 
			RequestQueue queue, String author, String publisher, String genre, 
			String ISBN, String language) {
		super(resourceID, title, year, thumbnail, queue);
		this.author = author;
		this.publisher = publisher;
		this.genre = genre;
		this.ISBN = ISBN;
		this.language = language;
	}
	
	/* 
	 * Human readable toString
	 */
	public String toString() {	
		String x;
		x = ("Title: " + title + " \nYear: " + year + " \nAuthor: " + author + 
				" \nPublisher: " + publisher + " \nGenre: " + genre + 
				" \nISBN: " + ISBN + "\nLanguage " + language);
		return x;
	}

	/**
	 * Gets the author.
	 *
	 * @return author of the book
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the author.
	 *
	 * @param author new author 
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * Gets the publisher.
	 *
	 * @return publisher of the book
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * Sets the publisher.
	 *
	 * @param publisher new publisher
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * Gets the genre.
	 *
	 * @return genre of the book
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * Sets the genre.
	 *
	 * @param genre new genre
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * Gets the ISBN.
	 *
	 * @return the ISBN of the book
	 */
	public String getISBN() {
		return ISBN;
	}

	/**
	 * Sets the ISBN.
	 *
	 * @param iSBN new ISBN
	 */
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	/**
	 * Gets the language.
	 *
	 * @return the language of the book
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Sets the language.
	 *
	 * @param language new language
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	
	/**
	 * Gets the fine per day when overdue.
	 *
	 * @return the fine per day
	 */
	public static double getFineDay() {
		return fineDay;
	}

	/**
	 * Gets maximum amount of fine
	 *
	 * @return Maximum fine amount
	 */
	public static double getFineMax() {
		return fineMax;
	}
		

}
	

