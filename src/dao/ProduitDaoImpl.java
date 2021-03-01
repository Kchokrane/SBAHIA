/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import metier.entities.Produit;

/**
 * @author kamal
 *
 */
public class ProduitDaoImpl implements IProduitDao {

	@Override
	public Produit saveProduit(Produit p) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO public.produit(id,designation, quantite, prix) VALUES ( ?,?, ?, ?)";
		
		Connection connection = SingletonConnection.getConnection();
		try {
			
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ps.setString(2, p.getDesignation());
			ps.setInt(3, p.getQuantite());
			ps.setDouble(4, p.getPrix());

			
			ps.executeUpdate();
			
			/*
			 * Dans l'affichage nous aurons besoin de l'Id du produit
			 * Pour recuperer l'Id du produit on execute une requete SELECT:
			 */

			
			ps.close();
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// On retourne un object de type Produit. 
		return p;
	}

	@Override
	public List<Produit> produitsParMotCle(String motCle) {
		// TODO Auto-generated method stub
		List<Produit> listProduit = new ArrayList();
		String sql = "SELECT * FROM produit WHERE designation LIKE ?";
		Connection connection = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, motCle);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Produit produit = new Produit();
				produit.setId(rs.getInt("id"));
				produit.setDesignation(rs.getString("designation"));
				produit.setPrix(rs.getDouble("prix"));
				produit.setQuantite(rs.getInt("quantite"));
				//produit.setImage(rs.getBytes("Image"));
				listProduit.add(produit);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listProduit;
	}

	@Override
	public Produit getProduit(int id) {
		String sql = "SELECT * FROM produit WHERE id=?";
		Produit produit = new Produit();
		Connection connection = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				produit.setId(rs.getInt("id"));
				produit.setDesignation(rs.getString("designation"));
				produit.setPrix(rs.getDouble("prix"));
				produit.setQuantite(rs.getInt("quantite"));
				produit.setImage(rs.getBytes("Image"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return produit;
	}

	@Override
	public Produit updateProduit(Produit p) {
		String sql = "UPDATE produit SET designation=?, prix=?,quantite=?,Image? WHERE id=?";
		Connection connection = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, p.getDesignation());
			ps.setDouble(2, p.getPrix());
			ps.setInt(3, p.getQuantite());
			ps.setInt(4, p.getId());
			ps.setBytes(5, p.getImage());
			
			ps.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public void deleteProduit(int id) {

		String sql = "DELETE FROM produit WHERE id = ?";
		Connection connection = SingletonConnection.getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
