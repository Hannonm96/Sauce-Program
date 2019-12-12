import java.io.Serializable;

// Sauce.java
// Marshall Hannon
// 12/11/19
// This is a sauce class that has attributes like name, quantity, and heat rating. 
public class Sauce implements Serializable {
	private String name = "";
	private int quantity = 0;
	private int heatRating = 0;
	
	/** This no arg constructor */
	Sauce() {	
	}
	
	/** This constructor takes name, quantity, and heat rating*/
	public Sauce(String name, int quantity, int heatRating) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.heatRating = heatRating;
	}
	
	/** This Method gets returns the name attribute*/
	public String getName() {
		return name;
	}
	
	/** This Method sets the name attribute*/
	public void setName(String name) {
		this.name = name;
	}
	
	/** This Method gets returns the quantity attribute*/
	public int getQuantity() {
		return quantity;
	}
	
	/** This Method sets the quantity attribute*/
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/** This Method gets returns the heat rating attribute*/
	public int getHeatRating() {
		return heatRating;
	}
	
	/** This Method sets the heat rating attribute*/
	public void setHeatRating(int heatRating) {
		this.heatRating = heatRating;
	}
	
	
}
