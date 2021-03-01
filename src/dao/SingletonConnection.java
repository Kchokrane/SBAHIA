/**
 * 
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author kamal
 *
 */

/*
 * Dans cette classe nous utilisons le design pattern Sengleton pour
 * ne cr�er qu'une seule connection pour la base de donn�es (�viter qu'� chaque
 * requette on cr�e une connection.
 * Et dans ce cas, il ne faut pas fermer la connnection, il faut la laisser ouverte.
 */
public class SingletonConnection {
	
	private static Connection connection;
	private static String url="jdbc:postgresql://localhost:5432/CAT_PROD?serverTime=UTC";
	private static String user="postgres";
	private static String password="kamal1103";
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}

}
