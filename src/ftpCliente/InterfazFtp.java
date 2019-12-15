package ftpCliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;

public class InterfazFtp extends JFrame {

	private JPanel contentPane;
	protected int posicionRuta = 0;
	public static DefaultTableModel dtm;
	public static JTable table;
	private static PrimerFtp ftp;
	private static ModeloTextoInterfaz modeloTexto;
	private static CreadorInterfaz creador;
	
	/**
	 * Create the frame.
	 */
	public InterfazFtp(PrimerFtp ftp) {
		this.ftp = ftp;
		creador = new CreadorInterfaz(ftp);				
		modeloTexto = new ModeloTextoInterfaz();
		ArrayList <String> titulosMenuItemArchivo;
		ArrayList <String> titulosMenuItemTransferencia;
		ArrayList <String> titulosMenuItemServidor;
		ArrayList <String> titulosMenuItemAyuda;
		ArrayList <String> titulosMenuItemCorreo;
		ArrayList <String> titulosBotones;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 547);
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);

		
		//Menu
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new java.awt.Color(30, 105, 90));
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);	
		menuBar.setBorder(compound);	
		setJMenuBar(menuBar);		
		
		//File menu
		JMenu mnArchivo = creador.crearMenu (modeloTexto.getTituloArchivo(), menuBar);				
		titulosMenuItemArchivo = llenarListaTituloArchivo();
		creador.crearItems(titulosMenuItemArchivo, mnArchivo);						
		
		//Transfer menu
		JMenu mnTransferencia = creador.crearMenu (modeloTexto.getTituloTransferencia(), menuBar);				
		titulosMenuItemTransferencia = llenarListaTituloTransferencia ();
		creador.crearItems (titulosMenuItemTransferencia, mnTransferencia);		
		
		//Server menu		
		JMenu mnServidor = creador.crearMenu(modeloTexto.getTituloServidor(), menuBar);				
		titulosMenuItemServidor = llenarListaTituloServidor ();
		creador.crearItems (titulosMenuItemServidor, mnServidor);		
	
		//Help menu		
		JMenu mnAyuda = creador.crearMenu(modeloTexto.getTituloAyuda(), menuBar);
		creador.ponerPropiedadesMenu(mnAyuda);
		menuBar.add(mnAyuda);	
		titulosMenuItemAyuda = llenarListaTituloAyuda();
		creador.crearItems (titulosMenuItemAyuda, mnAyuda);
		
		//Email menu		
		JMenu mnCorreo = creador.crearMenu(modeloTexto.getTituloCorreo(), menuBar);
		creador.ponerPropiedadesMenu(mnCorreo);	
		menuBar.add(mnCorreo);	
		titulosMenuItemCorreo = llenarListaTituloCorreo();
		creador.crearItems(titulosMenuItemCorreo, mnCorreo);	
		
		//Global Layout
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new java.awt.Color(218, 230, 228));
		
		//Buttons			
		creador.crearBotones (titulosMenuItemTransferencia, 60, contentPane,1);
		
		
		JLabel lblRuta = new JLabel(modeloTexto.getTituloRuta());
		lblRuta.setBounds(50, 30, 515, 16);
		lblRuta.setFont(fuenteTitulo);
		contentPane.add(lblRuta);
		
		JLabel lblUsuario= new JLabel(modeloTexto.getTituloUsuario());
		lblUsuario.setBounds(615, 30, 205, 16);
		lblUsuario.setFont(fuenteTitulo);
		contentPane.add(lblUsuario);
		
		
		//Table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 60, 515, 365);
		contentPane.add(scrollPane);
					
		dtm = new DefaultTableModel();
		
		table = new JTable(dtm) {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		table.addMouseListener(new MouseAdapter() {
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
				if(e.getClickCount() == 2) {
					ruta = (String)InterfazFtp.dtm.getValueAt(InterfazFtp.table.getSelectedRow(), 0);
					if(!ruta.contains(".")) {
						try {
							ruta = ftp.getRutas().get(posicionRuta )+"/"+ ruta;
							ftp.getCliente().changeWorkingDirectory(ruta);
							if(!workSpaceActual.equals(ftp.getCliente().printWorkingDirectory())) {
								ftp.getRutas().add(ruta);
								posicionRuta ++;
								lblRuta.setText("Ruta: " + ruta);
								
							}
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						recargarTabla();
					}
				}
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		
		// Cabeceras de la tabla
		dtm.addColumn(modeloTexto.getTituloCabeceraTabla());	
		
		table.setSelectionMode(0);
		
		// Editor de celdas para centrar los datos
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
		
		
		//CUANDO SE VEA SI SON FICHEROS O DIRECTORIOS SE ALINEARAN EN OTRO FORMATOOOOOOOOOOOOOOOOOOOOOOOOOOOO
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnModel().getColumn(0).setCellRenderer(tcr);
		
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setViewportView(table);	
		recargarTabla();	
	}
		
	private ArrayList <String> llenarListaTituloCorreo(){
		ArrayList <String> titulosMenuItemCorreo = new ArrayList();
		titulosMenuItemCorreo.add(modeloTexto.getTituloCorreoAbrir());
		return titulosMenuItemCorreo;
	}
	
	
	private ArrayList <String> llenarListaTituloArchivo(){
		ArrayList <String> titulosMenuItemArchivo = new ArrayList();
		titulosMenuItemArchivo.add(modeloTexto.getTituloCambiarUsuario());
		return titulosMenuItemArchivo;
	}
	
	private ArrayList <String> llenarListaTituloAyuda(){
		ArrayList <String> titulosMenuItemAyuda = new ArrayList();
		titulosMenuItemAyuda.add(modeloTexto.getTituloAyudaSobre());
		return titulosMenuItemAyuda;
	}
		
	
	
	private ArrayList <String> llenarListaTituloTransferencia (){
		ArrayList <String> titulosMenuItemTransferencia = new ArrayList();
		titulosMenuItemTransferencia.add(modeloTexto.getTituloSubirFichero());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloDescargarFichero());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloCrearFichero());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloEliminarFichero());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloCrearCarpeta());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloEliminarCarpeta());
				
		return titulosMenuItemTransferencia;		
		
	}
	
	private ArrayList <String> llenarListaTituloServidor (){
		ArrayList <String> titulosMenuItemServidor = new ArrayList();
		titulosMenuItemServidor.add(modeloTexto.getTituloServidorInfor());
		titulosMenuItemServidor.add(modeloTexto.getTituloServidorHistorial());					
		return titulosMenuItemServidor;		
		
	}
	/**
	 * Metodo para vaciar la tabla
	 */
	public static void vaciarTabla() {
		int a = table.getRowCount() - 1;
		for (int i = a; i >= 0; i--) {
			dtm.removeRow(dtm.getRowCount() - 1);
		}
	}

	/**
	 * Metodo que vacia la tabla, y la vuelve a rellenar con los datos de neodatis
	 */
	public static void recargarTabla() {
		vaciarTabla();
		try {
			ftp.setFicheros(ftp.getCliente().listFiles());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(int i = 0; i < ftp.getFicheros().length; i++) {
			Object[] row = {ftp.getFicheros()[i].getName()};
			dtm.addRow(row);
		}
	}
	
}
