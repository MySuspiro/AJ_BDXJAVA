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

import com.sun.org.apache.bcel.internal.generic.LoadClass;
import com.sun.org.apache.xml.internal.resolver.helpers.Debug;

import logico.Clinica;
import logico.Consulta;
import logico.Doctor;
import logico.Enfermedad;
import logico.Historial;
import logico.Paciente;
import logico.Persona;
import logico.Sintoma;
import logico.Vacuna;

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

public class RegConsulta3 extends JDialog {

	private static final long serialVersionUID = 1L;
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
	private JTextField txtDoctor;
	private JComboBox<String> cmbEnf;
	private JTextArea txtDiag;
	private boolean encontrado = false;
	Paciente pac = null;
	private JRadioButton rdbVacuna;
	private JPanel panelVac;
	private JComboBox<String> cmbVac;
	private JPanel panelSin;
	private JComboBox<String> cmbSin;
	private JRadioButton rdbEnf;
	private JRadioButton rdbSano;
	private JPanel PanEnf;
	private JButton btnHistorial;
	private javax.swing.JSpinner spnAlt;
	private javax.swing.JSpinner spnPes;
	private JComboBox<String> cmbSangre;
	private Doctor miDoc;
	private JTextField txtSintoma;

	public RegConsulta3(Doctor doctor) {
		miDoc = doctor;
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
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pac = null;
				try {
					pac = (Paciente) Clinica.getInstance().buscarPersonaByCedula(txtCedPaciente.getText());
				} catch (Exception e2) {
					// TODO: handle exception
				}
				if (pac != null) {
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
		rdbHombre.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
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
				if (!Character.isLetter(c) && c != ' ') {
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

		spnAlt = new javax.swing.JSpinner();
		spnAlt.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(5)));
		spnAlt.setBounds(413, 323, 78, 22);
		panel.add(spnAlt);

		spnPes = new javax.swing.JSpinner();
		spnPes.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(5)));
		spnPes.setBounds(413, 372, 78, 22);
		panel.add(spnPes);

		cmbSangre = new JComboBox<String>();
		cmbSangre.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[]{"O+", "O-", "A+", "A-", "B+", "B-", "AB+", "AB-"}));
		cmbSangre.setBounds(47, 240, 196, 22);
		panel.add(cmbSangre);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Consulta", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_2.setBounds(12, 439, 538, 271);
		contentPanel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel_6 = new JLabel("Doctor:");
		lblNewLabel_6.setBounds(25, 30, 56, 16);
		panel_2.add(lblNewLabel_6);

		txtDoctor = new JTextField();
		txtDoctor.setBounds(25, 54, 86, 22);
		txtDoctor.setEditable(false);
		txtDoctor.setText(doctor.getNombre());
		panel_2.add(txtDoctor);
		txtDoctor.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Codigo:");
		lblNewLabel_7.setBounds(140, 30, 56, 16);
		panel_2.add(lblNewLabel_7);

		txtCodigoCons = new JTextField();
		txtCodigoCons.setBounds(140, 54, 86, 22);
		panel_2.add(txtCodigoCons);
		txtCodigoCons.setColumns(10);
		txtCodigoCons.setEditable(false);
		String cod = "CONS-" + (Clinica.getInstance().getMisConsultas().size() + 1);
		txtCodigoCons.setText(cod);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Diagnostico", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(12, 89, 214, 169);
		panel_2.add(panel_3);
		panel_3.setLayout(null);

		JLabel lblNewLabel_9 = new JLabel("Enfermedad:");
		lblNewLabel_9.setBounds(12, 27, 98, 16);
		panel_3.add(lblNewLabel_9);

		cmbEnf = new JComboBox<String>();
		cmbEnf.setBounds(12, 56, 176, 22);
		panel_3.add(cmbEnf);

		JLabel lblNewLabel_12 = new JLabel("Diagnostico:");
		lblNewLabel_12.setBounds(12, 91, 98, 16);
		panel_3.add(lblNewLabel_12);

		txtDiag = new JTextArea();
		txtDiag.setBounds(12, 118, 176, 38);
		panel_3.add(txtDiag);

		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "Vacunas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_4.setBounds(256, 145, 214, 113);
		panel_2.add(panel_4);
		panel_4.setLayout(null);

		rdbVacuna = new JRadioButton("Si");
		rdbVacuna.setBounds(16, 27, 39, 25);
		rdbVacuna.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (rdbVacuna.isSelected()) {
					panelVac.setVisible(true);
				} else {
					panelVac.setVisible(false);
				}
			}
		});
		panel_4.add(rdbVacuna);

		JRadioButton rdbNoVacuna = new JRadioButton("No");
		rdbNoVacuna.setBounds(59, 27, 49, 25);
		rdbNoVacuna.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (rdbNoVacuna.isSelected()) {
					panelVac.setVisible(false);
				}
			}
		});
		panel_4.add(rdbNoVacuna);

		panelVac = new JPanel();
		panelVac.setBounds(16, 57, 176, 41);
		panelVac.setVisible(false);
		panel_4.add(panelVac);
		panelVac.setLayout(null);

		JLabel lblNewLabel_13 = new JLabel("Vacuna:");
		lblNewLabel_13.setBounds(0, 0, 56, 16);
		panelVac.add(lblNewLabel_13);

		cmbVac = new JComboBox<String>();
		cmbVac.setBounds(0, 13, 176, 22);
		panelVac.add(cmbVac);

		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "Sintomas", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBounds(256, 24, 214, 113);
		panel_2.add(panel_5);
		panel_5.setLayout(null);

		rdbSano = new JRadioButton("Sano");
		rdbSano.setBounds(16, 27, 59, 25);
		rdbSano.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (rdbSano.isSelected()) {
					panelSin.setVisible(false);
				}
			}
		});
		panel_5.add(rdbSano);

		rdbEnf = new JRadioButton("Enfermo");
		rdbEnf.setBounds(79, 27, 76, 25);
		rdbEnf.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (rdbEnf.isSelected()) {
					panelSin.setVisible(true);
				} else {
					panelSin.setVisible(false);
				}
			}
		});
		panel_5.add(rdbEnf);

		panelSin = new JPanel();
		panelSin.setBounds(16, 57, 176, 41);
		panelSin.setVisible(false);
		panel_5.add(panelSin);
		panelSin.setLayout(null);

		JLabel lblNewLabel_14 = new JLabel("Sintoma:");
		lblNewLabel_14.setBounds(0, 0, 56, 16);
		panelSin.add(lblNewLabel_14);

		cmbSin = new JComboBox<String>();
		cmbSin.setBounds(0, 13, 176, 22);
		panelSin.add(cmbSin);
		cmbSin.addItem("Agregar...");

		txtSintoma = new JTextField();
		txtSintoma.setBounds(0, 40, 176, 22);
		txtSintoma.setVisible(false);
		panelSin.add(txtSintoma);
		txtSintoma.setColumns(10);

		cmbSin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbSin.getSelectedItem().toString().equals("Agregar...")) {
					txtSintoma.setVisible(true);
					txtSintoma.requestFocus();
				} else {
					txtSintoma.setVisible(false);
				}
			}
		});

		JButton btnFinalizar = new JButton("Finalizar");
		btnFinalizar.setBounds(438, 723, 97, 25);
		btnFinalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (pac == null) {
					JOptionPane.showMessageDialog(null, "xd", "Pacientes", JOptionPane.INFORMATION_MESSAGE);
				} else {
					if (cmbSin.getSelectedItem().toString().equals("Agregar...")) {
						String nuevoSintoma = txtSintoma.getText();
						if (!nuevoSintoma.isEmpty()) {
							Clinica.getInstance().agregarSintoma(new Sintoma("S-"+Clinica.getInstance().getcodSin(), nuevoSintoma));
							cmbSin.addItem(nuevoSintoma);
							txtSintoma.setText("");
							txtSintoma.setVisible(false);
						}
					}
				}
			}
		});
		contentPanel.add(btnFinalizar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(324, 723, 97, 25);
		contentPanel.add(btnCancelar);
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
		Paciente paciente = (Paciente) Clinica.getInstance().buscarPersonaByCedula(cedula);

		if (paciente == null) {
			char sex = rdbHombre.isSelected() ? 'H' : 'M';
			paciente = new Paciente(cedula, txtNom.getText(), txtDir.getText(), "P-" + Clinica.getInstance().getcodPers(), txtTel.getText(), sex, txtEmail.getText(), txtSeguro.getText(), (int)spnPes.getValue(), (int)spnAlt.getValue(), cmbSangre.getSelectedItem().toString());
			Clinica.getInstance().agregarPersona(paciente);
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
		Clinica.getInstance().modificarPersona(paciente);
	}


	private void registerConsulta(Paciente paciente) {
		Doctor doctor = miDoc;
		Enfermedad enfermedad = null;
		Vacuna vacuna = null;
		if(encontrado || !verificarCedulaRepetida(txtCedPaciente.getText()) && doctor != null) {
			if (rdbEnf.isSelected() || rdbSano.isSelected()) {
				enfermedad = Clinica.getInstance().buscarEnfermedadByNom(cmbEnf.getSelectedItem().toString());
			}

			if (rdbVacuna.isSelected()) {
				vacuna = Clinica.getInstance().buscarVacunaByNom(cmbVac.getSelectedItem().toString());
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

			Consulta consulta = new Consulta(txtCodigoCons.getText(), txtDiag.getText(), enfermedad, paciente,miDoc, status, vacuna);

			int option = JOptionPane.showConfirmDialog(null, "Desea agregar la consulta al historial del paciente?", "Confirmación", JOptionPane.OK_CANCEL_OPTION);
			if (option == JOptionPane.OK_OPTION) {
				paciente.getHist().addMisConsultas(consulta);
			}

			updatePatient(paciente);
			Clinica.getInstance().agregarConsulta(consulta);

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
		txtDoctor.setText(miDoc.getNombre());
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
		txtDoctor.setText(miDoc.getNombre());
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
		for (Persona persona : Clinica.getInstance().getMisPersonas()) {
			if (persona.getCedula().equals(txtCedPaciente.getText())) {
				return true;
			}else {
				return false;
			}
		}
		return true; // The cedula is not repeated.
	}
}


