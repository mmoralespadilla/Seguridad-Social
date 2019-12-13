package ftpCliente;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
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
	public static DefaultTableModel dtm;
	public static JTable table;
	private static PrimerFtp ftp;
	private static modeloTextoInterfaz modeloTexto;
	private ControladorBotonesFtp controlBotones;

	/**
	 * Create the frame.
	 */
	public InterfazFtp(PrimerFtp ftp) {
		this.ftp = ftp;
		controlBotones = new ControladorBotonesFtp(ftp);
		
		modeloTexto = new modeloTextoInterfaz();
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
		JMenu mnArchivo = crearMenu (modeloTexto.getTituloArchivo(), menuBar);				
		titulosMenuItemArchivo = llenarListaTituloArchivo();
		crearItems(titulosMenuItemArchivo, mnArchivo);		
					
		
		//Transfer menu
		JMenu mnTransferencia = crearMenu (modeloTexto.getTituloTransferencia(), menuBar);				
		titulosMenuItemTransferencia = llenarListaTituloTransferencia ();
		crearItems (titulosMenuItemTransferencia, mnTransferencia);		
		
		//Server menu		
		JMenu mnServidor = crearMenu(modeloTexto.getTituloServidor(), menuBar);				
		titulosMenuItemServidor = llenarListaTituloServidor ();
		crearItems (titulosMenuItemServidor, mnServidor);		
	
		//Help menu		
		JMenu mnAyuda = crearMenu(modeloTexto.getTituloAyuda(), menuBar);
		ponerPropiedadesMenu(mnAyuda);
		menuBar.add(mnAyuda);	
		titulosMenuItemAyuda = llenarListaTituloAyuda();
		crearItems (titulosMenuItemAyuda, mnAyuda);
		
		//Email menu		
		JMenu mnCorreo = crearMenu(modeloTexto.getTituloCorreo(), menuBar);
		ponerPropiedadesMenu(mnCorreo);	
		menuBar.add(mnCorreo);	
		titulosMenuItemCorreo = llenarListaTituloCorreo();
		crearItems(titulosMenuItemCorreo, mnCorreo);	
		
		//Global Layout
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new java.awt.Color(218, 230, 228));
		
		//Buttons			
		crearBotones (titulosMenuItemTransferencia, 60);
		
		
		JLabel lblRuta = new JLabel(modeloTexto.getTituloRuta());
		lblRuta.setBounds(50, 30, 75, 16);
		lblRuta.setFont(fuenteTitulo);
		contentPane.add(lblRuta);
		
		JLabel lblUsuario= new JLabel(modeloTexto.getTituloUsuario());
		lblUsuario.setBounds(615, 30, 165, 16);
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
	
	
	private void crearBotones(ArrayList <String> titulos, int y) {	
		for (int i = 0 ; i<titulos.size(); i++) {			
			JButton boton = new JButton(titulos.get(i));
			
			boton.addActionListener(controlBotones );
			boton.setBounds(615, y, 160, 40);
			y += 65;
			ponerPropiedadesBoton(boton);
			contentPane.add(boton);	
		}	
			
	}
	
	private JMenu crearMenu(String textoMenu, JMenuBar barraMenu) {
		JMenu menu = new JMenu(textoMenu);
		ponerPropiedadesMenu(menu);
		barraMenu.add(menu);
		return menu;
	}
	
	private void crearItems(ArrayList <String> titulos, JMenu menu ) {
		for (int i = 0 ; i<titulos.size(); i++) {
			JMenuItem mntmTransfer = new JMenuItem(titulos.get(i));
			mntmTransfer.addActionListener(controlBotones);
			ponerPropiedadesMenuItem(mntmTransfer);
			menu.add(mntmTransfer);
		}		
	}
	
	private void ponerPropiedadesMenu(JMenu menu) {
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);	
		menu.setForeground(Color.WHITE);
		menu.setBackground(new java.awt.Color(30, 105, 90));
		menu.setFont(fuenteTitulo);		
	}	
	private void ponerPropiedadesMenuItem(JMenuItem item) {
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 13);	
		item.setBackground(new java.awt.Color(218, 230, 228));
		item.setFont(fuenteTitulo);		
	}	
	private void ponerPropiedadesBoton(JButton boton){
		
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);	
		boton.setForeground(Color.WHITE);
		boton.setBackground(new java.awt.Color(30, 105, 90));
		boton.setFont(fuenteTitulo);
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);	
		boton.setBorder(compound);		
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
