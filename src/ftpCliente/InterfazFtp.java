package ftpCliente;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class InterfazFtp extends JFrame {

	private JPanel contentPane;
	public static DefaultTableModel dtm;
	public static JTable table;
	private static ControladorFtp ftp;
	private static ModeloTextoInterfaz modeloTexto;
	private static CreadorInterfaz creador;
	private JLabel lblRuta;

	/**
	 * Create the frame.
	 */
	public InterfazFtp(ControladorFtp ftp) {
		// Actulizar
		Font fuenteTitulo = new Font("Dialog", Font.BOLD, 14);
		modeloTexto = new ModeloTextoInterfaz();
		this.ftp = ftp;
		lblRuta = new JLabel(modeloTexto.getTituloRuta());
		lblRuta.setBounds(50, 30, 515, 16);
		lblRuta.setFont(fuenteTitulo);
		creador = new CreadorInterfaz(ftp, lblRuta);
		ArrayList<String> titulosMenuItemArchivo;
		ArrayList<String> titulosMenuItemTransferencia;
		ArrayList<String> titulosMenuItemServidor;
		ArrayList<String> titulosMenuItemAyuda;
		ArrayList<String> titulosMenuItemCorreo;
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 855, 547);
		

		// Menu
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(new java.awt.Color(30, 105, 90));
		Border line = new LineBorder(Color.BLACK);
		Border margin = new EmptyBorder(5, 15, 5, 15);
		Border compound = new CompoundBorder(line, margin);
		menuBar.setBorder(compound);
		setJMenuBar(menuBar);

		// File menu
		JMenu mnArchivo = creador.crearMenu(modeloTexto.getTituloArchivo(), menuBar);
		JMenuItem mnCambioUsuario = new JMenuItem(modeloTexto.getTituloCambiarUsuario());
		Font fuenteTituloItem = new Font("Dialog", Font.BOLD, 13);	
		mnCambioUsuario.setBackground(new java.awt.Color(218, 230, 228));
		mnCambioUsuario.setFont(fuenteTitulo);	
		mnArchivo.add(mnCambioUsuario);
		mnCambioUsuario.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.out.println("Hola");
				InterfazLogin login = new InterfazLogin();
				login.setVisible(true);
			}
		});

		// Transfer menu
		JMenu mnTransferencia = creador.crearMenu(modeloTexto.getTituloTransferencia(), menuBar);
		titulosMenuItemTransferencia = llenarListaTituloTransferencia();
		creador.crearItems(titulosMenuItemTransferencia, mnTransferencia);

		// Server menu
		JMenu mnServidor = creador.crearMenu(modeloTexto.getTituloServidor(), menuBar);
		titulosMenuItemServidor = llenarListaTituloServidor();
		creador.crearItems(titulosMenuItemServidor, mnServidor);

		// Help menu
		JMenu mnAyuda = creador.crearMenu(modeloTexto.getTituloAyuda(), menuBar);
		creador.ponerPropiedadesMenu(mnAyuda);
		menuBar.add(mnAyuda);
		titulosMenuItemAyuda = llenarListaTituloAyuda();
		creador.crearItems(titulosMenuItemAyuda, mnAyuda);

		// Email menu
		JMenu mnCorreo = creador.crearMenu(modeloTexto.getTituloCorreo(), menuBar);
		creador.ponerPropiedadesMenu(mnCorreo);
		menuBar.add(mnCorreo);
		titulosMenuItemCorreo = llenarListaTituloCorreo();
		creador.crearItems(titulosMenuItemCorreo, mnCorreo);

		// Global Layout
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new java.awt.Color(218, 230, 228));

		// Buttons
		creador.crearBotones(titulosMenuItemTransferencia, 60, contentPane, 1);

		// Label
		

		JLabel lblUsuario = new JLabel(modeloTexto.getTituloUsuario());
		lblUsuario.setBounds(615, 30, 205, 16);
		lblUsuario.setFont(fuenteTitulo);
		contentPane.add(lblUsuario);

		// Table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(50, 60, 515, 365);
		contentPane.add(scrollPane);
		dtm = new DefaultTableModel();
		table = new JTable(dtm) {
			public boolean isCellEditable(int rowIndex, int vColIndex) {
				return false;
			}
		};
		
		MouseAdapterFtp adapterTable = new MouseAdapterFtp(ftp, lblRuta);
		table.addMouseListener(adapterTable);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		// Cabeceras de la tabla
		dtm.addColumn(modeloTexto.getTituloCabeceraTabla());
		table.setSelectionMode(0);

		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.setViewportView(table);

		JButton btnAtras = creador.elaborarBoton(modeloTexto.getTituloBotonAtras(), 460, 20, 105);
		lblUsuario.setText("Usuario: " + ftp.getUser());
		contentPane.add(btnAtras);
		contentPane.add(lblRuta);
		recargarTabla();
	}

	private ArrayList<String> llenarListaTituloCorreo() {
		ArrayList<String> titulosMenuItemCorreo = new ArrayList();
		titulosMenuItemCorreo.add(modeloTexto.getTituloCorreoAbrir());
		return titulosMenuItemCorreo;
	}

	private ArrayList<String> llenarListaTituloAyuda() {
		ArrayList<String> titulosMenuItemAyuda = new ArrayList();
		titulosMenuItemAyuda.add(modeloTexto.getTituloAyudaSobre());
		return titulosMenuItemAyuda;
	}

	private ArrayList<String> llenarListaTituloTransferencia() {
		ArrayList<String> titulosMenuItemTransferencia = new ArrayList();
		titulosMenuItemTransferencia.add(modeloTexto.getTituloSubirFichero());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloDescargarFichero());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloCrearFichero());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloCrearCarpeta());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloEliminar());
		titulosMenuItemTransferencia.add(modeloTexto.getTituloCambiarNombre());
		return titulosMenuItemTransferencia;

	}

	private ArrayList<String> llenarListaTituloServidor() {
		ArrayList<String> titulosMenuItemServidor = new ArrayList();
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
		for (int i = 0; i < ftp.getFicheros().length; i++) {
			Object[] row = { ftp.getFicheros()[i].getName() };
			dtm.addRow(row);
		}
	}

}
