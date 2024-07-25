package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;

import logico.CitaMedica;
import logico.Clinica;
import logico.Doctor;
import logico.Persona;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.DefaultComboBoxModel;

public class RegCita extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCod;
	private JFormattedTextField txtFech;
	DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    DateFormatter df = new DateFormatter(format);
    private JTextField txtCedPaciente;
    private JTextField txtNomPaciente;
    private CitaMedica miCita= null;
    private JButton btnReg;
    MaskFormatter mask = null;
    private JComboBox cbxHora;
    private JTextField txtDoctor;
    private Doctor Midoc=null;

	/**
	 * Create the dialog.
	 */
	public RegCita(CitaMedica cit, final Doctor doc) {
		miCita = cit;
		Midoc=doc;
		setIconImage(Toolkit.getDefaultToolkit().getImage("cita.png"));
		setResizable(false);
		if(miCita == null) {
			setTitle("Registro de Cita");
		} else {
			setTitle("Modificar Cita");
		}
		setBounds(100, 100, 531, 314);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				if(miCita != null) {
					btnReg = new JButton("Modificar");
				} else {
					btnReg = new JButton("Registrar");
				}				
				btnReg.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						//Doctor doc = null;
						Date fech = null;
						try {
							fech = format.parse(txtFech.getText());
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
		                Calendar calendar = Calendar.getInstance();
		                try {
		                    Date date = format.parse(txtFech.getText());
		                    calendar.setTime(date);
		                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

		                    if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
		                        JOptionPane.showMessageDialog(null, "No se pueden agendar citas los fines de semana (sábado o domingo).", "Error", JOptionPane.ERROR_MESSAGE);
		                        return; 
		                    }
		                } catch (ParseException ex) {
		                    ex.printStackTrace();
		                  
		                }

						//System.out.println("toy aqui1" );
		                
		                //NUEVO
		                
			             
		                String horaSeleccionada = cbxHora.getSelectedItem().toString();
		                String[] partesHora = horaSeleccionada.split(" : ");
		                int hora = Integer.parseInt(partesHora[0]);
		                int minutos = Integer.parseInt(partesHora[1]);

		                // Establecer la hora seleccionada en la fecha
		                Calendar calendario = Calendar.getInstance();
		                calendario.setTime(fech);
		                calendario.set(Calendar.HOUR_OF_DAY, hora);
		                calendario.set(Calendar.MINUTE, minutos);

		                // Obtener la fecha actual 
		                Calendar calendarioActual = Calendar.getInstance();
		                calendarioActual.set(Calendar.HOUR_OF_DAY, 0);
		                calendarioActual.set(Calendar.MINUTE, 0);
		                calendarioActual.set(Calendar.SECOND, 0);
		                calendarioActual.set(Calendar.MILLISECOND, 0);

		                // Comparar la fecha y hora seleccionadas con la fecha y hora actual
		                Date fechaHoraSeleccionada = calendario.getTime();
		                Date fechaActual = calendarioActual.getTime();
		                if (fechaHoraSeleccionada.before(fechaActual)) {
		                    JOptionPane.showMessageDialog(null, "No se puede agendar una cita en una fecha y hora anterior a la actual.", "Error", JOptionPane.ERROR_MESSAGE);
		                    return;
		                }
		                
		                //
		                
		                
		                if (doc != null && miCita == null && doctorTieneCita(doc, fech, cbxHora.getSelectedItem().toString(),miCita) == false
		                	    && cbxHora.getSelectedIndex() != 0
		                	    && !txtNomPaciente.getText().isEmpty() 
		                	    && !txtCedPaciente.getText().isEmpty() 
		                	) {
							CitaMedica cita = new CitaMedica(txtCod.getText(), txtCedPaciente.getText(), txtNomPaciente.getText(), doc, cbxHora.getSelectedItem().toString(), fech);
							Clinica.getInstance().agregarCita(cita);
							
							JOptionPane.showMessageDialog(null, "Cita Agendada Exitosamente", "Agenda", JOptionPane.INFORMATION_MESSAGE);
							Clean();
						} else if ( miCita != null && doctorTieneCita(doc,fech,cbxHora.getSelectedItem().toString(),miCita)==false) {
							System.out.println("toy aqui" );
							
							miCita.setNomPaciente(txtNomPaciente.getText());
							miCita.setCedPaciente(txtCedPaciente.getText());
							miCita.setDoctor(doc);
							
							Date date = null;
							try {
								date = format.parse(txtFech.getText());
							} catch (ParseException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							miCita.setFecha(date);
							miCita.setHora(cbxHora.getSelectedItem().toString());
							
							

							Clinica.getInstance().modificarCita(miCita);
							JOptionPane.showMessageDialog(null, "Cita Modificada Exitosamente", "Agenda", JOptionPane.INFORMATION_MESSAGE);
							dispose();
							ListarCita.loadCitas();
						}else {
						    
						    JOptionPane.showMessageDialog(null, "Error: El doctor ya tiene una cita para esa fecha y hora.", "Agenda", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				btnReg.setActionCommand("OK");
				buttonPane.add(btnReg);
				getRootPane().setDefaultButton(btnReg);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			{
			
	        try {
	            mask = new MaskFormatter("## : ##");
	            mask.setPlaceholderCharacter('_');
	        } catch (java.text.ParseException e) {
	            e.printStackTrace();
	        }
			}
		}

		{
			JPanel panel = new JPanel();
			panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel.setBounds(12, 13, 490, 218);
			contentPanel.add(panel);
			panel.setLayout(null);
			
			JLabel lblNewLabel = new JLabel("Codigo:");
			lblNewLabel.setBounds(31, 21, 56, 16);
			panel.add(lblNewLabel);
			
			JLabel lblNewLabel_5 = new JLabel("Tel\u00E9fono Persona:");
			lblNewLabel_5.setBounds(16, 138, 116, 16);
			panel.add(lblNewLabel_5);
			
			txtCedPaciente = new JTextField();
			txtCedPaciente.setBounds(16, 176, 211, 22);
			panel.add(txtCedPaciente);
			txtCedPaciente.setColumns(10);
			{
				txtCod = new JTextField();
				txtCod.setBounds(118, 18, 356, 22);
				panel.add(txtCod);
				txtCod.setEditable(false);
				txtCod.setColumns(10);
				txtCod.setText("CM-" + Clinica.getInstance().getcodCita());
			}
			{
				JLabel lblNewLabel_1 = new JLabel("Fecha:");
				lblNewLabel_1.setBounds(263, 138, 56, 16);
				panel.add(lblNewLabel_1);
			}
			
				        txtFech = new JFormattedTextField(df);
				        txtFech.setBounds(263, 176, 75, 22);
				        panel.add(txtFech);
				        txtFech.setValue(new Date());
				        {
				        	JLabel lblNewLabel_3 = new JLabel("Hora:");
				        	lblNewLabel_3.setBounds(368, 138, 56, 16);
				        	panel.add(lblNewLabel_3);
				        }
				        	        		{
				        	        			JLabel lblNewLabel_4 = new JLabel("Doctor:");
				        	        			lblNewLabel_4.setBounds(243, 58, 56, 16);
				        	        			panel.add(lblNewLabel_4);
				        	        		}
				        	        	    
				        	        		{
				        	        			txtNomPaciente = new JTextField();
				        	        			txtNomPaciente.setBounds(16, 95, 211, 22);
				        	        			panel.add(txtNomPaciente);
				        	        			txtNomPaciente.setColumns(10);
				        	        		}
				        	        		{
				        	        			JLabel lblNewLabel_2 = new JLabel("Nombre Persona:");
				        	        			lblNewLabel_2.setBounds(16, 58, 128, 16);
				        	        			panel.add(lblNewLabel_2);
				        	        		}
				        	        		{
				        	        			cbxHora = new JComboBox();
				        	        			cbxHora.setModel(new DefaultComboBoxModel(new String[] {"<Seleccione>", "9 : 00", "10 : 00", "11 : 00", "12 : 00", "13 : 00", "14 : 00", "15 : 00", "16 : 00", "17 : 00", "18 : 00"}));
				        	        			cbxHora.setBounds(368, 176, 106, 22);
				        	        			panel.add(cbxHora);
				        	        		}
				        	        		{
				        	        			txtDoctor = new JTextField();
				        	        			txtDoctor.setEditable(false);
				        	        			txtDoctor.setText(doc.getNombre());
				        	        			txtDoctor.setBounds(243, 95, 231, 22);
				        	        			panel.add(txtDoctor);
				        	        			txtDoctor.setColumns(10);
				        	        		}
				        	        		
		}
		
		
		loadCita();
        
	}
	
	private void Clean() {
		txtCod.setText("CM-" + Clinica.getInstance().getcodCita());
		txtFech.setValue(new Date());
	    txtDoctor.setText(Midoc.getNombre());
		txtNomPaciente.setText("");
		txtCedPaciente.setText("");
		cbxHora.setSelectedIndex(0);
	}
	
	private void loadCita() {
	    if (miCita != null) {
	        txtCod.setText(miCita.getCodigo());
	        txtNomPaciente.setText(miCita.getNomPaciente());
	        txtCedPaciente.setText(miCita.getCedPaciente());
	        txtDoctor.setText(Midoc.getNombre());

	        txtFech.setText(new SimpleDateFormat("dd-MM-yyyy").format(miCita.getFecha()));


	       String hora = miCita.getHora();
	        for (int i = 0; i < cbxHora.getItemCount(); i++) {
	            String horaCombo = (String) cbxHora.getItemAt(i);
	            if (horaCombo.equals(hora)) {
	                cbxHora.setSelectedIndex(i);
	                break;
	            }
	        }
	    }
	}
	
	/*public boolean doctorTieneCita(Doctor doctor, Date fecha, String hora) {
	    Calendar calendarFecha = Calendar.getInstance();
	    calendarFecha.setTime(fecha);

	    for (CitaMedica cita : Clinica.getInstance().getMisCitas()) {
	        if (cita.getDoctor().equals(doctor)) {
	            Date citaFecha = cita.getFecha();
	            Calendar calendarCitaFecha = Calendar.getInstance();
	            calendarCitaFecha.setTime(citaFecha);

	          
	            if (calendarCitaFecha.get(Calendar.YEAR) == calendarFecha.get(Calendar.YEAR)
	                && calendarCitaFecha.get(Calendar.MONTH) == calendarFecha.get(Calendar.MONTH)
	                && calendarCitaFecha.get(Calendar.DAY_OF_MONTH) == calendarFecha.get(Calendar.DAY_OF_MONTH)
	                && cita.getHora().equals(hora)) {
	                return true; // El doctor ya tiene una cita en el mismo día y hora
	            }
	        }
	    }

	    return false; // El doctor no tiene una cita en el mismo día y hora
	}*/
	
	public boolean doctorTieneCita(Doctor doctor, Date fecha, String hora, CitaMedica citaModificar) {
	    Calendar calendarFecha = Calendar.getInstance();
	    calendarFecha.setTime(fecha);

	    for (CitaMedica cita : Clinica.getInstance().getMisCitas()) {
	        // Excluir la cita que se está modificando de la comparación
	        if (cita != citaModificar && cita.getDoctor().equals(doctor)) {
	            Date citaFecha = cita.getFecha();
	            Calendar calendarCitaFecha = Calendar.getInstance();
	            calendarCitaFecha.setTime(citaFecha);

	           
	            if (calendarCitaFecha.get(Calendar.YEAR) == calendarFecha.get(Calendar.YEAR)
	                && calendarCitaFecha.get(Calendar.MONTH) == calendarFecha.get(Calendar.MONTH)
	                && calendarCitaFecha.get(Calendar.DAY_OF_MONTH) == calendarFecha.get(Calendar.DAY_OF_MONTH)
	                && cita.getHora().equals(hora)) {
	                return true; // El doctor ya tiene una cita en el mismo día y hora
	            }
	        }
	    }

	    return false; // El doctor no tiene una cita en el mismo día y hora
	}


}