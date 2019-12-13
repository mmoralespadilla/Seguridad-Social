package PrimerFtp;

public class Main {

	public static void main(String[] args) {
		int login = ConexionMysql.comprobarLogin("admin@gmail.com", "123");
		switch (login){
		case 0:
			System.out.println("Conectado como funcionario");
			break;
			
		case 1:
			System.out.println("Conectado como empresario");
			break;
			
		case -1:
			System.out.println("Usuario no existe");
			break;
			
		case -2:
			System.out.println("Contraseña incorrecta");
			break;
		case -3:
			System.out.println("Error de base de datos");
			break;
		}
		
		
		
		
		
		
		
		
		
	}
}
