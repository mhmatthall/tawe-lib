
public class Book extends Resource {
	protected static int fineDay;
	protected static int fineMax;
	private String author;
	private String publisher;
	private String genre;
	private int ISBN;
	private String language;
	
	public Book(String author, String publisher, String genre, int ISBN, String language) {
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
	

