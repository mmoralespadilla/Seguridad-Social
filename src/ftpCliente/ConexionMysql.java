package ftpCliente;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class ConexionMysql {
	private static Connection con;
	public static String email;
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

	public static int comprobarLogin(String usuario, String contraseña) {
		int tipoLogin = -3;
		if (iniciarConexion()) {
			String query = "select * from usuarios where usuario = '" + usuario + "'";
			try {
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				if (rs.next()) {
					String usuarioBuscado = rs.getString(1);
					String contraseñaBuscada = rs.getString(2);
					String emailBuscado = rs.getString(4);
					if (contraseñaBuscada.equals(contraseña)) {
						email = emailBuscado;
						tipoLogin = rs.getInt(5);
					} else {
						tipoLogin = -2;
					}
				} else {
					tipoLogin = -1;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			cerrarConexion();
		}
		return tipoLogin;
	}
	public static boolean insertarMovimiento(String usuario, String operacion, String descripcion) {
		boolean correcto = false;
		if (iniciarConexion()) {
			String query = "insert into movimientos values (default,'"+usuario+"','"+operacion+"','"+new Date()+"', '"+descripcion+"'";
			try {
				Statement st = con.createStatement();
				st.execute(query);
				correcto = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			cerrarConexion();
		}
		return correcto;
	}
	
	public static void cerrarConexion() {
		try {
			con.close();
			System.out.println("Conexiï¿½n sql cerrada");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
