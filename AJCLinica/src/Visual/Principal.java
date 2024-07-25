package Visual;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javafx.scene.chart.NumberAxis;
import logico.Clinica;
import logico.Doctor;
import logico.Enfermedad;
import logico.Persona;
import logico.Vacuna;

import java.awt.FlowLayout;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextPane;

public class Principal extends JFrame {

	private JPanel contentPane;
	private Dimension dim;
	private JMenu mnAdministracion;
	//probando socket
	static Socket sfd = null;
	static DataInputStream EntradaSocket;
	static DataOutputStream SalidaSocket;
	private JPanel panel;
	private static JFreeChart chartEnfV;
	private ChartPanel chartEnfVPanel;
	private static DefaultCategoryDataset datasetEnfV;
	private static JFreeChart chartEnf;
	private static ChartPanel chartEnfPanel;
	private static DefaultPieDataset datasetEnf;
	private static JFreeChart chartVac;
	private ChartPanel chartVacPanel;
	private static DefaultCategoryDataset datasetVac;
	private static JFreeChart chartDoc;
	private ChartPanel chartDocPanel;
	private static DefaultCategoryDataset datasetDoc;



	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("hospital.png"));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {

				//NUEVO CAROLINA PROBANDO
				FileOutputStream empresa2;
				ObjectOutputStream empresaWrite;
				try {
					empresa2 = new  FileOutputStream("laclinica17.dat");
					empresaWrite = new ObjectOutputStream(empresa2);
					empresaWrite.writeObject(Clinica.getInstance());
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		setResizable(false);
		setTitle("Cl\u00EDnica");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		dim=getToolkit().getScreenSize();
		setSize(dim.width, dim.height-35);
		setLocationRelativeTo(null);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnNewMenu_3 = new JMenu("Cita");
		menuBar.add(mnNewMenu_3);

		JMenuItem mntmNewMenuItem_6 = new JMenuItem("Registrar");
		mntmNewMenuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Doctor") ){
					Doctor doc=Clinica.getInstance().buscarDoctorByUser(Clinica.getLoginUser().getPersona().getCodigo());
					RegCita as= new RegCita(null,doc);
					as.setModal(true);
					as.setVisible(true);
				}else {
					RegCita3 regCita= new RegCita3(null);
					regCita.setModal(true);
					regCita.setVisible(true);	
				}

			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_6);

		JMenuItem mntmNewMenuItem_8 = new JMenuItem("Listar");
		mntmNewMenuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Doctor") ){
					Doctor doc=Clinica.getInstance().buscarDoctorByUser(Clinica.getLoginUser().getPersona().getCodigo());
					ListarCita2 listc= new ListarCita2(doc);
					listc.setModal(true);
					listc.setVisible(true);
				}else {
					ListarCita listc= new ListarCita();
					listc.setModal(true);
					listc.setVisible(true);
				}
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_8);

		JMenuItem mntmNewMenuItem_14 = new JMenuItem("Agenda Semanal");
		mntmNewMenuItem_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AgendaSemanal as= new AgendaSemanal();
				as.setModal(true);
				as.setVisible(true);
			}
		});
		mnNewMenu_3.add(mntmNewMenuItem_14);

		JMenu mnNewMenu_1 = new JMenu("Consulta");
		if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Empleado") ){
			mnNewMenu_1.setEnabled(false);
		}
		menuBar.add(mnNewMenu_1);

		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Registrar");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Doctor") ){
					Doctor doc = Clinica.getInstance().buscarDoctorByUser(Clinica.getLoginUser().getPersona().getCodigo());
					RegConsulta2 regConsul= new RegConsulta2();
					regConsul.setModal(true);
					regConsul.setVisible(true);
				}else {
					RegConsulta2 regConsul= new RegConsulta2();
					regConsul.setModal(true);
					regConsul.setVisible(true);	
				}
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_4);

		JMenuItem mntmNewMenuItem_5 = new JMenuItem("Listar");
		mntmNewMenuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Doctor") ){
					Doctor doc=Clinica.getInstance().buscarDoctorByUser(Clinica.getLoginUser().getPersona().getCodigo());
					ListarConsulta2 as= new ListarConsulta2(doc);
					as.setModal(true);
					as.setVisible(true);
				}
				else
				{
					ListarConsulta as= new ListarConsulta();
					as.setModal(true);
					as.setVisible(true);

				}
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_5);

		JMenu mnNewMenu_2 = new JMenu("Paciente");
		if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Empleado") ){
			mnNewMenu_2.setEnabled(false);
		}
		menuBar.add(mnNewMenu_2);

		JMenuItem mntmNewMenuItem = new JMenuItem("Listar");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Administrador") ){
					ListarPaciente listPaciente= new ListarPaciente(true);
					listPaciente.setModal(true);
					listPaciente.setVisible(true);
				}else {
					ListarPaciente listPaciente= new ListarPaciente(false);
					listPaciente.setModal(true);
					listPaciente.setVisible(true);
				}
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem);

		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Historial");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PacienteHistorial2 paH= new PacienteHistorial2();
				paH.setModal(true);
				paH.setVisible(true);

			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_1);

		JMenu mnNewMenu = new JMenu("Doctor");
		if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Empleado") ){
			mnNewMenu.setEnabled(false);
		}
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem_7 = new JMenuItem("Agenda Semanal Citas");
		mntmNewMenuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Doctor") ){
					Doctor doc=Clinica.getInstance().buscarDoctorByUser(Clinica.getLoginUser().getPersona().getCodigo());
					AgendaSemanal2 as= new AgendaSemanal2(doc);
					as.setModal(true);
					as.setVisible(true);
				}
				else
				{
					AgendaSemanal3 as= new AgendaSemanal3();
					as.setModal(true);
					as.setVisible(true);

				}

			}
		});
		mnNewMenu.add(mntmNewMenuItem_7);

		JMenu mnNewMenu_4 = new JMenu("Enfermedad");
		if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Empleado") ){
			mnNewMenu_4.setEnabled(false);
		}
		menuBar.add(mnNewMenu_4);

		JMenuItem mntmNewMenuItem_10 = new JMenuItem("Listar");
		mntmNewMenuItem_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Administrador") ){
					//Doctor doc=Clinica.getInstance().buscarDoctorByUser(Clinica.getLoginUser().getPersona().getCodigo());
					ListarEnf listEnf= new ListarEnf(true);
					listEnf.setModal(true);
					listEnf.setVisible(true);
				}else {
					ListarEnf listEnf= new ListarEnf(false);
					listEnf.setModal(true);
					listEnf.setVisible(true);
				}
			}
		});
		mnNewMenu_4.add(mntmNewMenuItem_10);

		JMenu mnNewMenu_5 = new JMenu("Vacuna");
		if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Empleado") ){
			mnNewMenu_5.setEnabled(false);
		}
		menuBar.add(mnNewMenu_5);

		JMenuItem mntmNewMenuItem_12 = new JMenuItem("Listar");
		mntmNewMenuItem_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(Clinica.getLoginUser().getTipo().equalsIgnoreCase("Administrador") ){
					//Doctor doc=Clinica.getInstance().buscarDoctorByUser(Clinica.getLoginUser().getPersona().getCodigo());
					ListarVacuna aux = new ListarVacuna(true);
					aux.setModal(true);
					aux.setVisible(true);
				}else {
					ListarVacuna aux = new ListarVacuna(false);
					aux.setModal(true);
					aux.setVisible(true);
				}
			}
		});
		mnNewMenu_5.add(mntmNewMenuItem_12);

		mnAdministracion = new JMenu("Administraci\u00F3n");

		if(!Clinica.getLoginUser().getTipo().equalsIgnoreCase("Administrador")){
			mnAdministracion.setEnabled(false);
		}
		menuBar.add(mnAdministracion);

		JMenu mnNewMenu_8 = new JMenu("Enfermedad");
		mnAdministracion.add(mnNewMenu_8);

		JMenuItem mntmNewMenuItem_15 = new JMenuItem("Registrar");
		mntmNewMenuItem_15.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegEnf regEnf= new RegEnf(null);
				regEnf.setModal(true);
				regEnf.setVisible(true);	
			}
		});
		mnNewMenu_8.add(mntmNewMenuItem_15);

		JMenuItem mntmNewMenuItem_16 = new JMenuItem("Listar");
		mntmNewMenuItem_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarEnf listEnf= new ListarEnf(true);
				listEnf.setModal(true);
				listEnf.setVisible(true);
			}
		});
		mnNewMenu_8.add(mntmNewMenuItem_16);

		JMenu mnNewMenu_9 = new JMenu("Vacuna");
		mnAdministracion.add(mnNewMenu_9);

		JMenuItem mntmNewMenuItem_17 = new JMenuItem("Registrar");
		mntmNewMenuItem_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegVacuna regDoc= new RegVacuna(null);
				regDoc.setModal(true);
				regDoc.setVisible(true);
			}
		});
		mnNewMenu_9.add(mntmNewMenuItem_17);

		JMenuItem mntmNewMenuItem_18 = new JMenuItem("Listar");
		mntmNewMenuItem_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarVacuna regDoc= new ListarVacuna(true);
				regDoc.setModal(true);
				regDoc.setVisible(true);
			}
		});
		mnNewMenu_9.add(mntmNewMenuItem_18);

		JMenu mnNewMenu_10 = new JMenu("Doctor");
		mnAdministracion.add(mnNewMenu_10);

		JMenuItem mntmNewMenuItem_19 = new JMenuItem("Registrar");
		mntmNewMenuItem_19.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegDoctor regDoc= new RegDoctor(null);
				regDoc.setModal(true);
				regDoc.setVisible(true);
			}
		});
		mnNewMenu_10.add(mntmNewMenuItem_19);

		JMenuItem mntmNewMenuItem_20 = new JMenuItem("Listar");
		mntmNewMenuItem_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarDoctor listDoc= new ListarDoctor();
				listDoc.setModal(true);
				listDoc.setVisible(true);
			}
		});
		mnNewMenu_10.add(mntmNewMenuItem_20);

		JMenu mnNewMenu_7 = new JMenu("Empleado");
		mnAdministracion.add(mnNewMenu_7);

		JMenuItem mntmNewMenuItem_9 = new JMenuItem("Registrar");
		mntmNewMenuItem_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegEmpleado regEmp= new RegEmpleado(null);
				regEmp.setModal(true);
				regEmp.setVisible(true);
			}
		});
		mnNewMenu_7.add(mntmNewMenuItem_9);

		JMenuItem mntmNewMenuItem_21 = new JMenuItem("Listar");
		mntmNewMenuItem_21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ListarEmp listEmp= new ListarEmp();
				listEmp.setModal(true);
				listEmp.setVisible(true);
			}
		});
		mnNewMenu_7.add(mntmNewMenuItem_21);

		JMenu mnNewMenu_11 = new JMenu("Administrador");
		mnAdministracion.add(mnNewMenu_11);

		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Registrar");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegUser users = new RegUser();
				users.setModal(true);
				users.setVisible(true);
			}
		});
		mnNewMenu_11.add(mntmNewMenuItem_3);

		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Respaldo");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//probando el socket
				try {
					sfd = new Socket("localhost", 7007);
					EntradaSocket = new DataInputStream(new BufferedInputStream(sfd.getInputStream()));
					SalidaSocket = new DataOutputStream(new BufferedOutputStream(sfd.getOutputStream()));

					try (FileInputStream fis = new FileInputStream("laclinica17.dat")) {
						byte[] buffer = new byte[4096];
						int bytesRead;
						while ((bytesRead = fis.read(buffer)) != -1) {
							SalidaSocket.write(buffer, 0, bytesRead);
						}
						SalidaSocket.flush();
						System.out.println("Respaldo enviado correctamente.");
						JOptionPane.showMessageDialog(null, "Respaldo enviado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

					} catch (IOException eo) {
						System.out.println("Error al enviar el respaldo: " + eo.getMessage());
						JOptionPane.showMessageDialog(null, "Error al enviar el respaldo: " + eo.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					}
				} catch (UnknownHostException uhe) {
					System.out.println("No se puede acceder al servidor.");
					JOptionPane.showMessageDialog(null, "No se puede acceder al servidor", "Error", JOptionPane.ERROR_MESSAGE);
				} catch (IOException ioe) {
					System.out.println("Comunicación rechazada.");
					JOptionPane.showMessageDialog(null, "Comunicación rechazada.", "Error", JOptionPane.ERROR_MESSAGE);
					
				} finally {
					try {
						if (sfd != null) sfd.close();
					} catch (IOException eo) {
						eo.printStackTrace();
					}
				}}
			//
		});
		mnAdministracion.add(mntmNewMenuItem_2);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new JPanel();
		panel.setBounds(0, 0, 0, 0);
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(panel);
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 954, 1914, 30);
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
		flowLayout.setVgap(10);
		contentPane.add(panel_1);
		initVac();
		initEnfV();
		initEnf();
		initCitaXDoc();
	}

	public void initVac() {
	    // Fuente de Datos
	    datasetVac = new DefaultCategoryDataset();
	    for (Vacuna aux : Clinica.getInstance().getMisVacunas()) {
	    	datasetVac.setValue((int)Clinica.getInstance().vacunaCantPacientes(aux), aux.getEnf().getNombre(), aux.getNombre());
	    }

	    // Creando el Grafico
	    chartVac = ChartFactory.createBarChart3D("Vacunas Puestas", "Vacunas", "Cantidad de Vacunas",
	    		datasetVac, PlotOrientation.VERTICAL, true, true, false);
	    chartVac.setBackgroundPaint(Color.white);
	    chartVac.getTitle().setPaint(Color.black);
	    CategoryPlot p = chartVac.getCategoryPlot();
	    p.setRangeGridlinePaint(Color.red);
	    
	    
	    // Mostrar Grafico
	    chartVacPanel = new ChartPanel(chartVac);
	    getContentPane().add(chartVacPanel);
	    chartVacPanel.setBounds(12, 470, 877, 471);
	}

	
	
	public void initEnfV() {
		// Fuente de Datos
		datasetEnfV = new DefaultCategoryDataset();
		for (Enfermedad aux : Clinica.getInstance().getMisEnfermedades()) {
			if(aux.getStatus().equalsIgnoreCase("Vigilancia")) {
				datasetEnfV.setValue(Clinica.getInstance().EnfermoCantPacientes(aux), "", aux.getNombre());
			}
		}

		// Creando el Grafico
		chartEnfV = ChartFactory.createBarChart3D("Enfermedades en Vigilancia", "Enfermedades", "Cantidad de Casos",
				datasetEnfV, PlotOrientation.VERTICAL, false, false, false);
		
		
		// Mostrar Grafico
		chartEnfVPanel = new ChartPanel(chartEnfV);
		getContentPane().add(chartEnfVPanel);
		chartEnfVPanel.setBounds(12, 13, 877, 444);
	}
	
	public void initEnf() {
		// Fuente de Datos
		datasetEnf = new DefaultPieDataset();
		int CantVig = Clinica.getInstance().CantPacientesEnfermosVig();
		int CantEnf = Clinica.getInstance().CantPacientesEnfermos();
		datasetEnf.setValue("Pacientes Enfermos", CantEnf-CantVig);
		datasetEnf.setValue("Enfermos en Vigilancia", CantVig);
		datasetEnf.setValue("Pacientes Sanos", Clinica.getInstance().CantPacientes()-CantEnf);
		
		// Creando el Grafico
		chartEnf = ChartFactory.createPieChart("Estado actual de Pacientes", 
				datasetEnf, true, true, false);

		// Mostrar Grafico
		chartEnfPanel = new ChartPanel(chartEnf);
		getContentPane().add(chartEnfPanel);
		chartEnfPanel.setBounds(1025, 13, 877, 444);
	}
	
	public void initCitaXDoc() {
		// Fuente de Datos
		datasetDoc = new DefaultCategoryDataset();
		for (Persona aux : Clinica.getInstance().getMisPersonas()) {
			if(aux instanceof Doctor) {
				datasetDoc.setValue(Clinica.getInstance().CantCitasXDoc((Doctor)aux), "", aux.getNombre());
			}
		}

		// Creando el Grafico
		chartDoc = ChartFactory.createBarChart3D("Doctores X Citas", "Doctores", "Cantidad de Citas",
				datasetDoc, PlotOrientation.VERTICAL, false, false, false);
		
		
		// Mostrar Grafico
		chartDocPanel = new ChartPanel(chartDoc);
		getContentPane().add(chartDocPanel);
		chartDocPanel.setBounds(1025, 470, 877, 471);
	}
	
	

	// Método para actualizar el gráfico con nuevos datos
	public static void UpdateGraphs() {
		datasetVac.clear();

		datasetVac = new DefaultCategoryDataset();
		for (Vacuna aux : Clinica.getInstance().getMisVacunas()) {
			datasetVac.setValue(Clinica.getInstance().vacunaCantPacientes(aux), aux.getEnf().getNombre(), aux.getNombre());
		}
		// Actualizar datos del gráfico
		chartVac.getCategoryPlot().setDataset(datasetVac);
		
		datasetEnfV.clear();
		for (Enfermedad aux : Clinica.getInstance().getMisEnfermedades()) {
			if(aux.getStatus().equalsIgnoreCase("Vigilancia")) {
				datasetEnfV.setValue(Clinica.getInstance().EnfermoCantPacientes(aux), "", aux.getNombre());
			}
		}
		// Actualizar datos del gráfico
		chartEnfV.getCategoryPlot().setDataset(datasetEnfV);
		
		datasetEnf.clear();
		int CantVig = Clinica.getInstance().CantPacientesEnfermosVig();
		int CantEnf = Clinica.getInstance().CantPacientesEnfermos();
		datasetEnf.setValue("Pacientes Enfermos", CantEnf-CantVig);
		datasetEnf.setValue("Enfermos en Vigilancia", CantVig);
		datasetEnf.setValue("Pacientes Sanos", Clinica.getInstance().CantPacientes()-CantEnf);
		// Actualizar datos del gráfico
		chartEnfPanel.repaint();
		
		// Actualizar datos del gráfico
		datasetDoc.clear();
		for (Persona aux : Clinica.getInstance().getMisPersonas()) {
			if(aux instanceof Doctor) {
				datasetDoc.setValue(Clinica.getInstance().CantCitasXDoc((Doctor)aux), "", aux.getNombre());
			}
		}
		chartDoc.getCategoryPlot().setDataset(datasetDoc);

		
	}
}