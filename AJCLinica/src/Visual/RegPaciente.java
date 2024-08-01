package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.sun.corba.se.impl.encoding.CodeSetConversion.BTCConverter;

import logico.Clinica;
import logico.Doctor;
import logico.Historial;
import logico.Paciente;
import logico.Persona;
import net.code.java.sql.JavaConnect2SQL;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class RegPaciente extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextArea txtDireccion;
	private JComboBox<String> cbSexo;
	private JTextField txtCedula;
	private Paciente miPaciente=null;
	private JTextField txtTelefono;
	private JTextField txtSeguro;
	private JTextField txtCorreoE;
	private boolean esAdmin=false;
	private JButton okButton;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public RegPaciente(Paciente paciente,boolean valido) {
		esAdmin=valido;
		miPaciente=paciente;
		setIconImage(Toolkit.getDefaultToolkit().getImage("editar.png"));
		setResizable(false);
		if (miPaciente!=null) 
		{
			setTitle("Modificar Paciente");
			
		}else 
		{
			setTitle("Registrar Paciente");
		}
		setBounds(100, 100, 605, 480);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(null);
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JLabel label = new JLabel("C\u00F3digo:");
				label.setBounds(44, 15, 56, 16);
				panel.add(label);
			}
			{
				txtCodigo = new JTextField();
				txtCodigo.setEditable(false);
				txtCodigo.setText("P-"+Clinica.getInstance().getcodPac());
				txtCodigo.setColumns(10);
				txtCodigo.setBounds(44, 46, 228, 22);
				panel.add(txtCodigo);
			}
			{
				JLabel label = new JLabel("Nombre:");
				label.setBounds(44, 83, 56, 16);
				panel.add(label);
			}
			{
				txtNombre = new JTextField();
				txtNombre.addKeyListener(new KeyAdapter() {
					@Override
					public void keyTyped(KeyEvent e) {
						 // Obtener la tecla presionada
		                char c = e.getKeyChar();
		                // Verificar si la tecla es una letra
		                if (!Character.isLetter(c)&& c != ' ') {
		                    // Consumir el evento para evitar que se escriba el carácter no válido
		                    e.consume();
		                }
					}
				});
				txtNombre.setColumns(10);
				txtNombre.setBounds(44, 114, 228, 22);
				panel.add(txtNombre);
			}
			{
				JLabel label = new JLabel("Direcci\u00F3n:");
				label.setBounds(44, 287, 70, 16);
				panel.add(label);
			}
			{
				txtDireccion = new JTextArea();
				txtDireccion.setBounds(44, 318, 500, 53);
				panel.add(txtDireccion);
			}
			{
				JLabel lblSexo = new JLabel("Sexo");
				lblSexo.setBounds(316, 151, 97, 16);
				panel.add(lblSexo);
			}
			{
				cbSexo = new JComboBox<String>();
				cbSexo.setModel(new DefaultComboBoxModel<String>(new String[] {"Femenino", "Masculino"}));
				cbSexo.setSelectedIndex(-1);
				cbSexo.setMaximumRowCount(2);
				cbSexo.setToolTipText("");
				cbSexo.setBounds(316, 182, 107, 22);
				panel.add(cbSexo);
			}
			{
				JLabel lblCdula = new JLabel("C\u00E9dula:");
				lblCdula.setBounds(316, 15, 56, 16);
				panel.add(lblCdula);
			}
			{
				txtCedula = new JTextField();
				txtCedula.addKeyListener(new KeyAdapter() {
					/*@Override
					public void keyTyped(KeyEvent e) {
						 // Obtener la tecla presionada
		                char c = e.getKeyChar();
		                // Verificar si la tecla es un número
		                if (!Character.isDigit(c)) {
		                    // Consumir el evento para evitar que se escriba el carácter no válido
		                    e.consume();
		                }
					}*/
				});
				txtCedula.setColumns(10);
				txtCedula.setBounds(316, 46, 228, 22);
				panel.add(txtCedula);
			}
			
			JLabel lblTelfono = new JLabel("Tel\u00E9fono:");
			lblTelfono.setBounds(316, 83, 56, 16);
			panel.add(lblTelfono);
			
			txtTelefono = new JTextField();
			txtTelefono.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
				    char c = e.getKeyChar();
				    
				    // Verificar si la tecla es un número o el carácter '-'
				    if (!Character.isDigit(c) && c != '-') {
				        e.consume(); 
				    }
				}
			});
			txtTelefono.setColumns(10);
			txtTelefono.setBounds(316, 114, 228, 22);
			panel.add(txtTelefono);
			
			JLabel lblLmiteDeCrdito = new JLabel("Seguro:");
			lblLmiteDeCrdito.setBounds(44, 151, 129, 16);
			panel.add(lblLmiteDeCrdito);
			
			txtSeguro = new JTextField();
			txtSeguro.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					 // Obtener la tecla presionada
	                char c = e.getKeyChar();
	                // Verificar si la tecla es una letra
	                if (!Character.isLetter(c)&& c != ' ') {
	                    // Consumir el evento para evitar que se escriba el carácter no válido
	                    e.consume();
	                }
				}
			});
			txtSeguro.setColumns(10);
			txtSeguro.setBounds(44, 182, 228, 22);
			panel.add(txtSeguro);
			{
				JLabel lblCorreoElectrnico = new JLabel("Correo Electr\u00F3nico:");
				lblCorreoElectrnico.setBounds(44, 219, 129, 16);
				panel.add(lblCorreoElectrnico);
			}
			{
				txtCorreoE = new JTextField();
				txtCorreoE.setColumns(10);
				txtCorreoE.setBounds(44, 250, 500, 22);
				panel.add(txtCorreoE);
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("Registrar");
				if (miPaciente!=null) {

					okButton.setText("Modificar");
				}

				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						//if(verificarCedulaRepetida()==true) {
						//
						
						if (miPaciente==null)
						{
							if (checkFields()==true){
								
								if(verificarCedulaRepetida()==true) {
							
						
						
									char sexo;
									String sexoSeleccionado = (String) cbSexo.getSelectedItem();
									if (sexoSeleccionado.equals("Femenino"))
									{
										sexo='F';
									}
									else 
									{
									sexo='M';
									}
									
									//Paciente paciente = new Paciente(txtCedula.getText(),txtNombre.getText(),txtDireccion.getText(),txtCodigo.getText(),txtTelefono.getText(),sexo,txtCorreoE.getText(),txtSeguro.getText());
									//Clinica.getInstance().agregarPersona(paciente);
									//paciente.setHist(hist);
									JOptionPane.showMessageDialog(null,"Operación satisfactoria","Registro", JOptionPane.INFORMATION_MESSAGE);
									//dispose();
								    clean();
								}
								else
								{
									JOptionPane.showMessageDialog(null,"Existe una Persona con esa Cédula");
								}
							    
							}
							else
							{
								 JOptionPane.showMessageDialog(null,"Todos los campos deben estar llenos");
							}
					    
						
						}else
						{
							miPaciente.setDir(txtDireccion.getText());
							miPaciente.setNombre(txtNombre.getText());
							miPaciente.setCedula(txtCedula.getText());
							miPaciente.setSeguro(txtSeguro.getText());
							miPaciente.setTelefono(txtTelefono.getText());
							miPaciente.setCorreoElectronico(txtCorreoE.getText());
							char sexo;
							String sexoSeleccionado = (String) cbSexo.getSelectedItem();
							if (sexoSeleccionado.equals("Femenino"))
							{
								sexo='F';
							}
							else 
							{
							sexo='M';
							}
							miPaciente.setSexo(sexo);
							if (checkFields()==true)
							{
								JavaConnect2SQL.getInstace().updatePaciente(miPaciente);
								dispose();
								ListarPaciente.loadPacientes();
							}
							else
							{
								JOptionPane.showMessageDialog(null,"Todos los campos deben estar llenos");
								
							}
						}
						
					//}
						/*else
						{
							JOptionPane.showMessageDialog(null,"Existe una Persona con esa Cédula");
						}*/
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		loadPaciente();
	}
	
	private void loadPaciente() {
		if (miPaciente!=null && esAdmin==true) {
			txtCodigo.setText(miPaciente.getCodigo());
			txtCedula.setText(miPaciente.getCedula());
			txtNombre.setText(miPaciente.getNombre());
			txtTelefono.setText(miPaciente.getTelefono());
			txtSeguro.setText(miPaciente.getSeguro());
			txtCorreoE.setText(miPaciente.getCorreoElectronico());
			char sexo=miPaciente.getSexo();
			if (sexo=='F')
			{
				cbSexo.setSelectedIndex(0);
			}
			else {
				cbSexo.setSelectedIndex(1);
			}
			txtDireccion.setText(miPaciente.getDir());
			
			
		}
		else if (miPaciente!=null && esAdmin==false) 
		{
			txtCedula.setEditable(false);
			txtNombre.setEditable(false);
			txtTelefono.setEditable(false);
			txtSeguro.setEditable(false);
			txtCorreoE.setEditable(false);
			txtDireccion.setEditable(false);
			cbSexo.setEnabled(false);
			okButton.setEnabled(false);
			
			txtCodigo.setText(miPaciente.getCodigo());
			txtCedula.setText(miPaciente.getCedula());
			txtNombre.setText(miPaciente.getNombre());
			txtTelefono.setText(miPaciente.getTelefono());
			txtSeguro.setText(miPaciente.getSeguro());
			txtCorreoE.setText(miPaciente.getCorreoElectronico());
			char sexo=miPaciente.getSexo();
			if (sexo=='F')
			{
				cbSexo.setSelectedIndex(0);
			}
			else {
				cbSexo.setSelectedIndex(1);
			}
			txtDireccion.setText(miPaciente.getDir());
			
		}
		
	}
	

	private void clean() {
		txtNombre.setText("");
		txtCedula.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtSeguro.setText("");
		txtCorreoE.setText("");
		cbSexo.setSelectedIndex(-1);
		txtCodigo.setText("P-"+Clinica.getInstance().getcodPac());
		
	}
	
	private boolean checkFields() {
		
		if (txtNombre.getText().equals("") || txtCedula.getText().equals("") || txtDireccion.getText().equals("") || txtSeguro.getText().equals("")|| txtTelefono.getText().equals("") || txtCorreoE.getText().equals("")  ||  cbSexo.getSelectedIndex()==-1)
		{
			return false;
			
		}
		else 
		{
			return true;
		}

	}
	
	public boolean verificarCedulaRepetida() {
		if(JavaConnect2SQL.getInstace().getMisPacientes("").isEmpty()) {
			for (Paciente persona : JavaConnect2SQL.getInstace().getMisPacientes("")) {
		        if (persona.getCedula().equals(txtCedula.getText())) {
		            return false;//se repite 
		        }
		    }
		}
		return true; //no se repite
	}
}

