package dao;

import java.util.List;

import metier.entities.Produit;

/**
 * @author kamal
 *
 */
public interface IProduitDao {

	public Produit saveProduit(Produit p);
	
	public List<Produit> produitsParMotCle(String motCle);
	
	public Produit getProduit(int id);
	
	public Produit updateProduit(Produit p);
	
	public void deleteProduit(int id);
}
