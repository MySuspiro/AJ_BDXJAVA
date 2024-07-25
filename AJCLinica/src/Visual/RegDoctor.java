package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import logico.Clinica;
import logico.Doctor;
import logico.Persona;
import logico.User;

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
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class RegDoctor extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextArea txtDireccion;
	private JComboBox<String> cbSexo;
	private JTextField txtCedula;
	private Doctor miDoctor=null;
	private JTextField txtTelefono;
	private JTextField txtEspecialidad;
	private JTextField txtUsername;
	private JTextField txtContrasena;
	private JTextField txtConfirm;
	private JTextField txtCorreoE;
	private JPanel panelUser;
	private User miUser=null;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public RegDoctor(Doctor doctor) {
		miDoctor=doctor;
		setIconImage(Toolkit.getDefaultToolkit().getImage("editar.png"));
		setResizable(false);
		if (miDoctor!=null) 
		{
			setTitle("Modificar Doctor");
			
		}else 
		{
			setTitle("Registrar Doctor");
		}
		setBounds(100, 100, 664, 653);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JPanel panel = new JPanel();
			panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
			contentPanel.add(panel, BorderLayout.CENTER);
			panel.setLayout(null);
			{
				JLabel lblUsuario = new JLabel("Usuario:");
				lblUsuario.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblUsuario.setBounds(23, 398, 97, 16);
				panel.add(lblUsuario);
			}
			{
				JLabel lblNewLabel = new JLabel("Info. General");
				lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
				lblNewLabel.setBounds(26, 3, 97, 16);
				panel.add(lblNewLabel);
			}
			{
				JLabel label = new JLabel("C\u00F3digo:");
				label.setBounds(42, 28, 56, 16);
				panel.add(label);
			}
			{
				JLabel label = new JLabel("Nombre:");
				label.setBounds(42, 93, 56, 16);
				panel.add(label);
			}
			{
				JLabel label = new JLabel("Direcci\u00F3n:");
				label.setBounds(42, 291, 70, 16);
				panel.add(label);
			}
			{
				JLabel lblSexo = new JLabel("Sexo");
				lblSexo.setBounds(339, 96, 97, 16);
				panel.add(lblSexo);
			}
			{
				JLabel lblCdula = new JLabel("C\u00E9dula:");
				lblCdula.setBounds(339, 28, 56, 16);
				panel.add(lblCdula);
			}
			
			JLabel lblTelfono = new JLabel("Tel\u00E9fono:");
			lblTelfono.setBounds(42, 162, 56, 16);
			panel.add(lblTelfono);
			
			JLabel lblLmiteDeCrdito = new JLabel("Especialidad:");
			lblLmiteDeCrdito.setBounds(339, 162, 129, 16);
			panel.add(lblLmiteDeCrdito);
			{
				JPanel panel_1 = new JPanel();
				panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panel_1.setBounds(12, 13, 624, 383);
				panel.add(panel_1);
				panel_1.setLayout(null);
				{
					JLabel lblCorreoElectrnico = new JLabel("Correo Electr\u00F3nico:");
					lblCorreoElectrnico.setBounds(30, 213, 192, 16);
					panel_1.add(lblCorreoElectrnico);
				}
				{
					txtCorreoE = new JTextField();
					txtCorreoE.setColumns(10);
					txtCorreoE.setBounds(30, 243, 560, 22);
					panel_1.add(txtCorreoE);
				}
				
				txtTelefono = new JTextField();
				txtTelefono.setBounds(30, 178, 267, 22);
				panel_1.add(txtTelefono);
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
				{
					txtNombre = new JTextField();
					txtNombre.setBounds(30, 109, 267, 22);
					panel_1.add(txtNombre);
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
				}
				{
					txtCodigo = new JTextField();
					txtCodigo.setBounds(30, 45, 267, 22);
					panel_1.add(txtCodigo);
					txtCodigo.setEditable(false);
					txtCodigo.setText("D-"+Clinica.getInstance().getcodPers());
					txtCodigo.setColumns(10);
				}
				{
					cbSexo = new JComboBox<String>();
					cbSexo.setBounds(327, 109, 107, 22);
					panel_1.add(cbSexo);
					cbSexo.setModel(new DefaultComboBoxModel<String>(new String[] {"Femenino", "Masculino"}));
					cbSexo.setSelectedIndex(-1);
					cbSexo.setMaximumRowCount(2);
					cbSexo.setToolTipText("");
				}
				{
					txtCedula = new JTextField();
					txtCedula.setBounds(327, 45, 267, 22);
					panel_1.add(txtCedula);
					txtCedula.addKeyListener(new KeyAdapter() {
						@Override
						public void keyTyped(KeyEvent e) {
							 // Obtener la tecla presionada
		                char c = e.getKeyChar();
		                // Verificar si la tecla es un número
		                if (!Character.isDigit(c)) {
		                    // Consumir el evento para evitar que se escriba el carácter no válido
		                    e.consume();
		                }
						}
					});
					txtCedula.setColumns(10);
				}
				
				txtEspecialidad = new JTextField();
				txtEspecialidad.setBounds(327, 178, 267, 22);
				panel_1.add(txtEspecialidad);
				txtEspecialidad.addKeyListener(new KeyAdapter() {
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
				txtEspecialidad.setColumns(10);
				{
					txtDireccion = new JTextArea();
					txtDireccion.setBounds(30, 306, 561, 53);
					panel_1.add(txtDireccion);
				}
			}
			{
				panelUser = new JPanel();
				panelUser.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
				panelUser.setBounds(12, 409, 624, 146);
				panel.add(panelUser);
				panelUser.setLayout(null);
				{
					JLabel lblNewLabel_1 = new JLabel("Nombre de Usuario:");
					lblNewLabel_1.setBounds(30, 13, 115, 16);
					panelUser.add(lblNewLabel_1);
				}
				{
					txtUsername = new JTextField();
					txtUsername.setColumns(10);
					txtUsername.setBounds(30, 42, 267, 22);
					panelUser.add(txtUsername);
				}
				{
					JLabel lblContrase = new JLabel("Contrase\u00F1a:");
					lblContrase.setBounds(30, 82, 70, 16);
					panelUser.add(lblContrase);
				}
				{
					txtContrasena = new JTextField();
					txtContrasena.setColumns(10);
					txtContrasena.setBounds(30, 111, 267, 22);
					panelUser.add(txtContrasena);
				}
				{
					JLabel lblConfirmarContrasea = new JLabel("Confirmar Contrase\u00F1a:");
					lblConfirmarContrasea.setBounds(327, 82, 131, 16);
					panelUser.add(lblConfirmarContrasea);
				}
				{
					txtConfirm = new JTextField();
					txtConfirm.setColumns(10);
					txtConfirm.setBounds(327, 111, 267, 22);
					panelUser.add(txtConfirm);
				}
			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Registrar");
				if (miDoctor!=null) {

					okButton.setText("Modificar");
				}

				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					if(verificarCedulaRepetida()==true) {
						
						
						if (miDoctor==null)
						{
							if (checkFields()==true){
								
								if(verificarCedulaRepetida()==true && verificarUserRepetido()==true && verificarContrasena()==true) {
							
						
						
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
									
									Persona persona = new Doctor(txtCedula.getText(),txtNombre.getText(),txtDireccion.getText(),txtCodigo.getText(),txtTelefono.getText(),sexo,txtCorreoE.getText(),txtEspecialidad.getText());
									Clinica.getInstance().agregarPersona(persona);
									
				
									User user = new User("Doctor",txtUsername.getText(),txtContrasena.getText(),persona);
								    Clinica.getInstance().regUser(user);
									

									
									JOptionPane.showMessageDialog(null,"Operación satisfactoria","Registro", JOptionPane.INFORMATION_MESSAGE);
									//dispose();
								    clean();
								}
								else
								{
									JOptionPane.showMessageDialog(null,"Ha ocurrido un error, verifique cedula, username y claves");
								}
							    
							}
							else
							{
								 JOptionPane.showMessageDialog(null,"Todos los campos deben estar llenos");
							}
					    
						
						}else
						{
						    
							for (User user : Clinica.getInstance().getMisUsers()) {
							    if (user != null && user.getPersona() != null && miDoctor != null) {
							        if (user.getPersona().getCodigo().equalsIgnoreCase(miDoctor.getCodigo())) {
							            if (verificarContrasena()) {
							                user.setPass(txtContrasena.getText());
							                user.setUserName(txtUsername.getText());
							                miUser = user;
							            } else {
							                JOptionPane.showMessageDialog(null, "Las claves no son iguales");
							            }
							        }
							    }
							}

							
							miDoctor.setDir(txtDireccion.getText());
							miDoctor.setNombre(txtNombre.getText());
							miDoctor.setCedula(txtCedula.getText());
							miDoctor.setEspecialidad(txtEspecialidad.getText());
							miDoctor.setTelefono(txtTelefono.getText());
							miDoctor.setCorreoElectronico(txtCorreoE.getText());
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
							miDoctor.setSexo(sexo);
							if (checkFields()==true && verificarUserRepetido()==true)
							{
							Clinica.getInstance().modificarPersona(miDoctor);
							Clinica.getInstance().modificarUser(miUser);
							dispose();
							ListarDoctor.loadDoctores();
							}
							else
							{
								JOptionPane.showMessageDialog(null,"Todos los campos deben estar llenos/No pueden haber usuarios repetidos");
								
							}
						}
						
					}
						else
						{
							JOptionPane.showMessageDialog(null,"Existe una Persona con esa Cédula");
						}
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
		loadDoctor();
	}
	
	private void loadDoctor() {
		if (miDoctor!=null) {
			txtCodigo.setText(miDoctor.getCodigo());
			txtCedula.setText(miDoctor.getCedula());
			txtNombre.setText(miDoctor.getNombre());
			txtTelefono.setText(miDoctor.getTelefono());
			txtEspecialidad.setText(miDoctor.getEspecialidad());
			txtCorreoE.setText(miDoctor.getCorreoElectronico());
			char sexo=miDoctor.getSexo();
			if (sexo=='F')
			{
				cbSexo.setSelectedIndex(0);
			}
			else {
				cbSexo.setSelectedIndex(1);
			}
			txtDireccion.setText(miDoctor.getDir());
			
			for (User user : Clinica.getInstance().getMisUsers()) {
			    System.out.println("Current User: " + user);

			    if (user != null) {
			        System.out.println("User's Persona: " + user.getPersona());

			        if (user.getPersona() != null && miDoctor != null) {
			            if (user.getPersona().getCodigo().equalsIgnoreCase(miDoctor.getCodigo())) {
			                txtUsername.setText(user.getUserName());
			                txtContrasena.setText(user.getPass());
			                txtConfirm.setText(user.getPass());
			            }
			        } else {
			            System.out.println("Either user.getPersona() or miDoctor is null.");
			        }
			    } else {
			        System.out.println("User is null.");
			    }
			}

			
		   /* for (User user : Clinica.getInstance().getMisUsers()) {
		        if (user.getPersona().getCodigo().equalsIgnoreCase(miDoctor.getCodigo())) {
		        	txtUsername.setText(user.getUserName());
		        	txtContrasena.setText(user.getPass());
		        	txtConfirm.setText(user.getPass());
   
		        }
		    }*/
			
		}
		
	}

	private void clean() {
		txtNombre.setText("");
		txtCedula.setText("");
		txtDireccion.setText("");
		txtTelefono.setText("");
		txtEspecialidad.setText("");
		txtCorreoE.setText("");
		cbSexo.setSelectedIndex(-1);
		txtCodigo.setText("D-"+Clinica.getInstance().getcodPers());
		txtUsername.setText("");
		txtContrasena.setText("");
		txtConfirm.setText("");
		
	}
	
	private boolean checkFields() {
		
		if (txtNombre.getText().equals("") || txtCedula.getText().equals("") || txtDireccion.getText().equals("") || txtTelefono.getText().equals("") || txtEspecialidad.getText().equals("") || txtCorreoE.getText().equals("") || txtUsername.getText().equals("") ||txtContrasena.getText().equals("") ||txtConfirm.getText().equals("") ||cbSexo.getSelectedIndex()==-1)
		{
			return false;
			
		}
		else 
		{
			return true;
		}

	}
	
	
	public boolean verificarCedulaRepetida() {
	    for (Persona persona : Clinica.getInstance().getMisPersonas()) {
	        if (persona.getCedula().equals(txtCedula.getText())) {
	            if (miDoctor != null && persona.getCodigo().equalsIgnoreCase(miDoctor.getCodigo())) {
	                return true; // la cedula del que se ta modificando
	            } else {
	                return false; // se repite
	            }
	        }
	    }
	    return true; // no se repite
	}

	
	public boolean verificarUserRepetido() {
	    for (User user : Clinica.getInstance().getMisUsers()) {
	        if (user.getUserName().equals(txtUsername.getText())) {
	            // Check if the username belongs to the same doctor being modified
	            if (miDoctor != null && user.getPersona().getCodigo().equalsIgnoreCase(miDoctor.getCodigo())) {
	                return true; // es el doctor que se ta modificanto.
	            } else {
	                return false; // se repite el username y es de otro user
	            }
	        }
	    }
	    return true; // no se repite
	}

	
	
	public boolean verificarContrasena() {
		if (txtContrasena.getText().equals(txtConfirm.getText()))
		{
			return true;
		}
		else 
		{
			return false;
		}
	}
}