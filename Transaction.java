/*
 * Don't think we need setters, as this should only be created as a read-only 'receipt'
 */
//TODO implament this
public class Transaction {

	private Date tdate; // consider better name?
	private String fineId;
	private double amount;

	public Transaction(Date tdate, String fineID, double amount) {
		this.tdate = tdate;
		this.fineId = fineID;
		this.amount = amount;
	}

	public Date getTdate() {
		return tdate;
	}

	public void setTdate(Date tdate) {
		this.tdate = tdate;
	}

	public String getFineId() {
		return fineId;
	}

	public void setFineId(String fineId) {
		this.fineId = fineId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction [tdate=" + tdate + ", fineId=" + fineId + ", amount=" + amount + "]";
	}

}
