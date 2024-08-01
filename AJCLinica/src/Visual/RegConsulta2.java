package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.TitledBorder;

import com.sun.corba.se.impl.ior.iiop.JavaSerializationComponent;
import com.sun.org.apache.bcel.internal.generic.LoadClass;
import com.sun.org.apache.xml.internal.resolver.helpers.Debug;

import logico.Clinica;
import logico.Consulta;
import logico.Doctor;
import logico.Enfermedad;
import logico.Historial;
import logico.Paciente;
import logico.Persona;
import logico.Vacuna;
import net.code.java.sql.JavaConnect2SQL;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class RegConsulta2 extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCedPaciente;
	private JTextField txtTel;
	private JTextField txtNom;
	private JTextField txtSeguro;
	private JTextField txtEmail;
	private JTextField txtCodigoCons;
	private JTextArea txtDir;
	private JRadioButton rdbHombre;
	private JRadioButton rdbMujer;
	private JComboBox<String> cmbDoc;
	private JComboBox<String> cmbEnf;
	private JTextArea txtDiag;
	private boolean encontrado = false;
	Paciente pac = null;
	private JRadioButton rdbVacuna;
	private JPanel panelVac;
	private JComboBox<String> cmbVac;
	private JRadioButton rdbEnf;
	private JRadioButton rdbSano;
	private JPanel PanEnf;
	private JButton btnHistorial;
	private JSpinner spnAlt;
	private JSpinner spnPes;
	private JComboBox<String> cmbSangre;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			RegConsulta2 dialog = new RegConsulta2();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public RegConsulta2() {
		setBounds(100, 100, 580, 812);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage("editar.png"));
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Paciente", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(12, 13, 538, 413);
		contentPanel.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Cedula:");
		lblNewLabel.setBounds(25, 28, 44, 16);
		panel.add(lblNewLabel);
		
		txtCedPaciente = new JTextField();
		txtCedPaciente.setBounds(94, 25, 295, 22);
		panel.add(txtCedPaciente);
		txtCedPaciente.setColumns(10);
		txtCedPaciente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
	            char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
	            	e.consume();
	            }
			}
		});
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				pac = null;
				try {
					pac = JavaConnect2SQL.getInstace().buscarPacienteByCedula(txtCedPaciente.getText());
				} catch (Exception e2) {
					// TODO: handle exception
				}
				if(pac != null) {
					encontrado = true;
					JOptionPane.showMessageDialog(null, "Paciente encontrado", "Pacientes", JOptionPane.INFORMATION_MESSAGE);
					loadPaciente(pac);
					btnHistorial.setEnabled(true);
					
				} else {
					JOptionPane.showMessageDialog(null, "Paciente no encontrado", "Pacientes", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnNewButton.setBounds(414, 24, 97, 25);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Nombre");
		lblNewLabel_1.setBounds(47, 63, 56, 16);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Direccion:");
		lblNewLabel_2.setBounds(47, 291, 78, 16);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Telefono:");
		lblNewLabel_3.setBounds(291, 63, 78, 16);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Email:");
		lblNewLabel_4.setBounds(47, 139, 56, 16);
		panel.add(lblNewLabel_4);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Sexo", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(291, 215, 196, 85);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		rdbHombre = new JRadioButton("Hombre");
		rdbHombre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbHombre.setSelected(true);
				rdbMujer.setSelected(false);
			}
		});
		rdbHombre.setBounds(38, 11, 73, 25);
		panel_1.add(rdbHombre);
		
		rdbMujer = new JRadioButton("Mujer");
		rdbMujer.setBounds(38, 47, 61, 25);
		panel_1.add(rdbMujer);
		
		JLabel lblNewLabel_5 = new JLabel("Seguro:");
		lblNewLabel_5.setBounds(291, 139, 56, 16);
		panel.add(lblNewLabel_5);
		
		txtTel = new JTextField();
		txtTel.setBounds(291, 98, 196, 22);
		panel.add(txtTel);
		txtTel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
	            char c = e.getKeyChar();
			    if (!Character.isDigit(c) && c != '-' && c != '(' && c != ')') {
	            	e.consume();
	            }
			}
		});
		txtTel.setColumns(10);
		
		txtNom = new JTextField();
		txtNom.setBounds(47, 98, 196, 22);
		panel.add(txtNom);
		txtNom.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
	            char c = e.getKeyChar();
	            if (!Character.isLetter(c)&& c != ' ') {
	            	e.consume();
	            }
			}
		});
		txtNom.setColumns(10);
		
		txtDir = new JTextArea();
		txtDir.setBounds(47, 326, 196, 65);
		panel.add(txtDir);
		
		txtSeguro = new JTextField();
		txtSeguro.setBounds(291, 174, 196, 22);
		panel.add(txtSeguro);
		txtSeguro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
	            char c = e.getKeyChar();
                if (!Character.isDigit(c)) {
	            	e.consume();
	            }
			}
		});
		txtSeguro.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(47, 174, 196, 22);
		panel.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblNewLabel_8 = new JLabel("Altura: (cm)");
		lblNewLabel_8.setBounds(291, 326, 78, 16);
		panel.add(lblNewLabel_8);
		
		JLabel lblNewLabel_10 = new JLabel("Peso: (lb)");
		lblNewLabel_10.setBounds(291, 375, 78, 16);
		panel.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("Tipo de Sangre:");
		lblNewLabel_11.setBounds(47, 215, 196, 16);
		panel.add(lblNewLabel_11);
		
		spnAlt = new JSpinner();
		spnAlt.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(5)));
		spnAlt.setBounds(413, 323, 78, 22);
		panel.add(spnAlt);
		
		spnPes = new JSpinner();
		spnPes.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(5)));
		spnPes.setBounds(413, 372, 78, 22);
		panel.add(spnPes);
		
		cmbSangre = new JComboBox<>();
		cmbSangre.setBounds(47, 250, 196, 22);
		panel.add(cmbSangre);
		cmbSangre.addItem("O+");
		cmbSangre.addItem("O-");
		cmbSangre.addItem("AB+");
		cmbSangre.addItem("AB-");
		cmbSangre.addItem("A+");
		cmbSangre.addItem("A-");
		cmbSangre.addItem("B+");
		cmbSangre.addItem("B-");
		
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Consulta", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(12, 439, 538, 278);
		contentPanel.add(panel_2);
		panel_2.setLayout(null);
		
		PanEnf = new JPanel();
		PanEnf.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Enfermo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		PanEnf.setBounds(36, 89, 226, 74);
		panel_2.add(PanEnf);
		PanEnf.setLayout(null);
		PanEnf.setVisible(false);
		
		cmbEnf = new JComboBox<>();
		cmbEnf.setBounds(14, 27, 196, 22);
		PanEnf.add(cmbEnf);
		cmbEnf.addItem("<Seleccione>");
		
		JLabel lblNewLabel_6 = new JLabel("Codigo:");
		lblNewLabel_6.setBounds(50, 17, 56, 16);
		panel_2.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Doctor:");
		lblNewLabel_7.setBounds(292, 17, 56, 16);
		panel_2.add(lblNewLabel_7);
		
		cmbDoc = new JComboBox<>();
		cmbDoc.setBounds(292, 50, 196, 22);
		panel_2.add(cmbDoc);
		cmbDoc.addItem("<Seleccione>");
		for (Doctor aux : JavaConnect2SQL.getInstace().getMisDoctor("")) {
			if(aux != null) {
				if(aux instanceof Doctor) {
					cmbDoc.addItem(aux.getNombre());
				}
			}
		}
		
		txtCodigoCons = new JTextField();
		txtCodigoCons.setEditable(false);
		txtCodigoCons.setColumns(10);
		txtCodigoCons.setBounds(50, 50, 196, 22);
		panel_2.add(txtCodigoCons);
		txtCodigoCons.setText("C-"+Clinica.getInstance().getcodCons());
		
		JLabel lblNewLabel_9 = new JLabel("Diagnostico:");
		lblNewLabel_9.setBounds(50, 165, 120, 16);
		panel_2.add(lblNewLabel_9);
		
		txtDiag = new JTextArea();
		txtDiag.setBounds(50, 194, 438, 64);
		panel_2.add(txtDiag);
		
		panelVac = new JPanel();
		panelVac.setBorder(new TitledBorder(null, "Vacuna", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelVac.setBounds(274, 89, 226, 74);
		panel_2.add(panelVac);
		panelVac.setLayout(null);
		panelVac.setVisible(false);
		
		cmbVac = new JComboBox<>();
		cmbVac.setBounds(18, 27, 196, 22);
		panelVac.add(cmbVac);
		cmbVac.addItem("<Selected>");
		for (Vacuna aux : JavaConnect2SQL.getInstace().getMisVacunas("")) {
			if(aux != null) {
				cmbVac.addItem(aux.getNombre());
			}	
		}
		
		rdbVacuna = new JRadioButton("Vacunado");
		rdbVacuna.setBounds(292, 121, 196, 25);
		panel_2.add(rdbVacuna);
		
		rdbEnf = new JRadioButton("Esta Enfermo");
		rdbEnf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbSano.setVisible(false);
				rdbEnf.setVisible(false);
				PanEnf.setVisible(true);
				cmbEnf.removeAllItems();
				cmbVac.setSelectedIndex(0);
				cmbEnf.addItem("<Selected>");
				for (Enfermedad aux : JavaConnect2SQL.getInstace().getMisEnfermedades("")) {
					if(aux != null) {
						cmbEnf.addItem(aux.getNombre());
					}	
				}
			}
		});
		rdbEnf.setBounds(50, 85, 127, 25);
		panel_2.add(rdbEnf);
		
		rdbSano = new JRadioButton("Esta Sano");
		rdbSano.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				rdbSano.setVisible(false);
				rdbEnf.setVisible(false);
				PanEnf.setVisible(true);
				pac = JavaConnect2SQL.getInstace().buscarPacienteByCedula(txtCedPaciente.getText());
				cmbEnf.removeAllItems();
				cmbVac.setSelectedIndex(0);
				cmbEnf.addItem("<Selected>");
				try {
					for (Enfermedad aux : pac.getHist().getMisEnfermedades()){
						if(aux != null) {
							cmbEnf.addItem(aux.getNombre());
						}
					}

				} catch (Exception e2) {
				}
							}
		});
		rdbSano.setBounds(50, 121, 127, 25);
		panel_2.add(rdbSano);
		rdbVacuna.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				panelVac.setVisible(true);
				rdbVacuna.setVisible(false);
				cmbVac.setSelectedIndex(0);
			}
		});
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Registrar");
				okButton.addMouseListener(new MouseAdapter() {
				    @Override
				    public void mouseClicked(MouseEvent e) {
				        String cedula = txtCedPaciente.getText();
				        Paciente paciente = searchOrCreatePatient(cedula);

				        if (paciente != null) {
				            // If patient exists, update their information
				            registerConsulta(paciente);

				        } else {
				            JOptionPane.showMessageDialog(null, "Consulta No Pudo Ser Registrada", "Consulta Fallida", JOptionPane.INFORMATION_MESSAGE);
				        }
				    }
				});
				
				btnHistorial = new JButton("Ver Historial");
				btnHistorial.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						PacienteHistorial ph = new PacienteHistorial(pac);
						ph.setModal(true);
						ph.setVisible(true);
					}
				});
				btnHistorial.setEnabled(false);
				buttonPane.add(btnHistorial);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				
				JButton btnNewButton_1 = new JButton("Undo Consulta");
				btnNewButton_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cleanConsu();
					}
				});
				buttonPane.add(btnNewButton_1);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	protected void loadPaciente(Paciente paci) {
		txtCedPaciente.setText(paci.getCedula());
		txtEmail.setText(paci.getCorreoElectronico());
		txtDir.setText(paci.getDir());
		txtNom.setText(paci.getNombre());
		txtTel.setText(paci.getTelefono());
		if(pac.getSeguro() != null) {
			txtSeguro.setText(paci.getSeguro());
		}
		if(paci.getSexo() == 'H') {
			rdbHombre.setSelected(true);
			rdbMujer.setSelected(false);
		} else {
			rdbHombre.setSelected(false);
			rdbMujer.setSelected(true);
		}
		spnAlt.setValue(paci.getAltura());
		spnPes.setValue(paci.getPeso());
		cmbSangre.setSelectedItem(paci.getTipoSangre());
		
	}
	private Paciente searchOrCreatePatient(String cedula) {
	    Paciente paciente = JavaConnect2SQL.getInstace().buscarPacienteByCedula(cedula);

	    if (paciente == null) {
	        char sex = rdbHombre.isSelected() ? 'H' : 'M';
	        paciente = new Paciente(cedula, txtNom.getText(), txtDir.getText(), "P-" + Clinica.getInstance().getcodPac(), txtTel.getText(), sex, txtEmail.getText(), txtSeguro.getText(), (int)spnPes.getValue(), (int)spnAlt.getValue(), cmbSangre.getSelectedItem().toString());
	        JavaConnect2SQL.getInstace().agregarPaciente(paciente);
	    }

	    return paciente;
	}

	private void updatePatient(Paciente paciente) {
	    paciente.setNombre(txtNom.getText());
	    paciente.setDir(txtDir.getText());
	    paciente.setTelefono(txtTel.getText());
	    paciente.setCorreoElectronico(txtEmail.getText());
	    paciente.setSeguro(txtSeguro.getText());
	    paciente.setSexo(rdbHombre.isSelected() ? 'H' : 'M');
	    paciente.setAltura((int)spnAlt.getValue());
		paciente.setPeso((int)spnPes.getValue());
		paciente.setTipoSangre(cmbSangre.getSelectedItem().toString());
		JavaConnect2SQL.getInstace().updatePaciente(paciente);
	}


	private void registerConsulta(Paciente paciente) {
	    Doctor doctor = null;
	    doctor = JavaConnect2SQL.getInstace().buscarDoctorByNom(cmbDoc.getSelectedItem().toString());
	    Enfermedad enfermedad = null;
	    Vacuna vacuna = null;
	    if(encontrado || !verificarCedulaRepetida(txtCedPaciente.getText()) && doctor != null) {
	    	if (rdbEnf.isSelected() || rdbSano.isSelected()) {
		        enfermedad = JavaConnect2SQL.getInstace().buscarEnfermedadByNom(cmbEnf.getSelectedItem().toString());
		    }

		    if (rdbVacuna.isSelected()) {
		        vacuna = JavaConnect2SQL.getInstace().buscarVacunaByNom(cmbVac.getSelectedItem().toString());
		        paciente.getHist().addMisVacunas(vacuna);
		    }

		    String status = "Investigando";
		    
		    if (enfermedad != null) {
				if (rdbEnf.isSelected()) {
					status = "Enfermo";
					paciente.getHist().addMisEnfermedades(enfermedad);
				} else if (rdbSano.isSelected()) {
					status = "Sano";
					paciente.getHist().eliminarMisEnfermedades(enfermedad);
				}
			}

		    Consulta consulta = new Consulta(txtCodigoCons.getText(), txtDiag.getText(), enfermedad, paciente, doctor, status, vacuna);

		    int option = JOptionPane.showConfirmDialog(null, "Desea agregar la consulta al historial del paciente?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
		    if (option == JOptionPane.OK_OPTION) {
		        paciente.getHist().addMisConsultas(consulta);
		    }

	        updatePatient(paciente);
		    JavaConnect2SQL.getInstace().agregarConsulta(consulta);

		    JOptionPane.showMessageDialog(null, "Consulta Registrada Exitosamente", "Consulta", JOptionPane.INFORMATION_MESSAGE);

		    Clean();

	    }else {
		    JOptionPane.showMessageDialog(null, "Consulta Registrada Exitosamente", "Consulta", JOptionPane.INFORMATION_MESSAGE);
	    }
	    	}
	
	private void Clean() {
		txtCedPaciente.setText("");
		txtCodigoCons.setText("C-"+Clinica.getInstance().getcodCons());
		txtEmail.setText("");
		txtDiag.setText("");
		txtDir.setText("");
		txtNom.setText("");
		txtSeguro.setText("");
		txtTel.setText("");
		rdbHombre.setSelected(false);
		rdbMujer.setSelected(false);
		cmbDoc.setSelectedIndex(0);
		cmbEnf.setSelectedIndex(0);
		rdbSano.setVisible(true);
		rdbSano.setSelected(false);
		rdbEnf.setVisible(true);
		rdbEnf.setSelected(false);
		rdbVacuna.setVisible(true);
		rdbVacuna.setSelected(false);
		panelVac.setVisible(false);
		PanEnf.setVisible(false);
		btnHistorial.setEnabled(false);
		pac = null;
		
	}
	
	private void cleanConsu() {
		txtDiag.setText("");
		cmbDoc.setSelectedIndex(0);
		cmbEnf.setSelectedIndex(0);
		rdbSano.setVisible(true);
		rdbSano.setSelected(false);
		rdbEnf.setVisible(true);
		rdbEnf.setSelected(false);
		rdbVacuna.setVisible(true);
		rdbVacuna.setSelected(false);
		panelVac.setVisible(false);
		PanEnf.setVisible(false);
		btnHistorial.setEnabled(false);
	}
	
	public boolean verificarCedulaRepetida(String cedula) {
	    for (Paciente persona : JavaConnect2SQL.getInstace().getMisPacientes("")) {
	        if (persona.getCedula().equals(txtCedPaciente.getText())) {
	        	return true;
	        }else {
	        	return false;
	        }
	    }
	    return true; // The cedula is not repeated.
	}
}
