package com.intiformation.tpcomptes.utilitaire;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionMySQL {
	
		//db + jdbc driver info
		private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		private static final String URL = "jdbc:mysql://localhost:3306/db_gestion_comptes";
		private static final String USER = "root";
		private static final String PASSWORD = "password";
		private static Connection connexion;
		
		/**
		 * On ne peut pas instancier d'objet de cette classe
		 */
		private ConnexionMySQL() {
			
		}

		/**
		 * @return une connexion JDBC vers la BDD
		 */
		public static Connection getConnection()
		{
			try {
				Class.forName(JDBC_DRIVER);
				connexion = DriverManager.getConnection(URL, USER, PASSWORD);
				return connexion;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}

}
