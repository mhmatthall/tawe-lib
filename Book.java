/*
 * TODO Complete setter methods
 */
public class Book extends Resource {
	protected static double fineDay = 2.00;
	protected static double fineMax = 25.00;
	private String author;
	private String publisher;
	private String genre;
	private int ISBN;
	private String language;
	
	public Book(String title, int year, Thumbnail thumbnail, String author, String publisher, String genre, int ISBN, String language) {

		super (title, year, thumbnail);
		this.author = author;
		this.publisher = publisher;
		this.genre = genre;
		this.ISBN = ISBN;
		this.language = language;

	}
	
	public String getAuthor() {
		return author;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public int getISBN() {
		return ISBN;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public void setAuthor(String author) {
		
	}
	
	public void setPublisher(String publisher) {
			
	}
	
	public void setGenre(String genre) {
		
	}
	
	public void setISBN(int ISBN) {
		
	}
	
	public void setLanguage(String Language) {
		
	}
}
	

