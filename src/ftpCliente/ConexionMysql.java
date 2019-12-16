package ftpCliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ConexionMysql {
	private static Connection con;

	public static boolean iniciarConexion() {
		boolean cargada = false;
		try {
			// carga el controlador
			Class contr = Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException cnfe) {
			System.out.println("com.mysql.jdbc.Driver");
		}
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/" + "segsoc", "root", "");
			System.out.println("Conexion sql realizada");
			cargada = true;
		} catch (SQLException sqle) {
			con = null;
			System.out.println(sqle.getMessage());
			JOptionPane.showMessageDialog(null, "Error al establecer la conexion");
		}
		return cargada;
	}

	public static int comprobarLogin(String email, String contraseña) {
		int tipoLogin = -3;
		iniciarConexion();
		String query = "select * from usuarios where email = '"+email+"'";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				String emailBuscado = rs.getString(1);
				String contraseñaBuscada = rs.getString(2);
				if(contraseñaBuscada.equals(contraseña)) {
					tipoLogin = rs.getInt(2);
				}else {
					tipoLogin = -2;
				}
			}else {
				tipoLogin = -1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cerrarConexion();
		return tipoLogin;
	}

	public static void cerrarConexion() {
		try {
			con.close();
			System.out.println("Conexi�n sql cerrada");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
