package ftpCliente;

import java.io.IOException;
import java.net.SocketException;

public class Princiapl {
public static void main(String[] args) {
	ClienteFTP clie = new ClienteFTP("localhost", "user", "", "email@email");
	try {
		clie.init();
	} catch (SocketException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
