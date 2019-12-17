package ftpCliente;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JLabel;

import org.apache.commons.net.ftp.FTPClient;

public class MouseAdapterFtp implements MouseListener {

	private PrimerFtp ftp;
	private JLabel lblRuta;

	public MouseAdapterFtp(PrimerFtp ftp, JLabel lblRuta) {
		this.ftp = ftp;
		this.lblRuta = lblRuta;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		String ruta;
		String workSpaceActual = "";
		try {
			workSpaceActual = ftp.getCliente().printWorkingDirectory();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		if (e.getClickCount() == 2) {
			ruta = (String) InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 0);
			try {
				ruta = ftp.getRutas().get(InterfazFtp.posicionRuta) + "/" + ruta;
				ftp.getCliente().changeWorkingDirectory(ruta);
				if (!workSpaceActual.equals(ftp.getCliente().printWorkingDirectory())) {
					ftp.getRutas().add(ruta);
					InterfazFtp.posicionRuta++;
					lblRuta.setText("Ruta: " + ruta);

				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			InterfazFtp.recargarTabla();
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}