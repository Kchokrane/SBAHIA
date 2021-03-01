package metier.entities;

import java.io.Serializable;

/**
 * @author kamal
 *
 */

/*
 * This class a Java bean
 */
public class Produit implements Serializable {


	private int id;
	private String designation;
	private int quantite;
	private double prix;
	private byte[] Image;
	
	// Constructors :
	
	public Produit() {
		
	}

	public Produit(int id ,String designation,  int quantite,double prix ,byte[] image) {
		this.id = id;
		this.designation = designation;
		this.prix = prix;
		this.quantite = quantite;
		this.Image = Image;
	}

	public Produit(int id, String designation, int quantite, double prix) {
		this.id = id;
		this.designation = designation;
		this.prix = prix;
		this.quantite = quantite;
	}

	public byte[] getImage() {
		return Image;
	}

	public void setImage(byte[] image) {
		Image = image;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	@Override
	public String toString() {
		return "Produit [Image="+Image+" ,id=" + id + ", designation=" + designation + ", prix=" + prix + ", quantite=" + quantite + "]";
	}
	
	
	
}
