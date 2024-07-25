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
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import logico.CitaMedica;
import logico.Clinica;
import logico.Enfermedad;

import javax.swing.JRadioButton;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

public class RegEnf extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextArea txtDescripcion;
	private JRadioButton rdbVig;
	private Enfermedad miEnf=null;

	/**
	 * Launch the application.
	 */


	/**
	 * Create the dialog.
	 */
	public RegEnf(Enfermedad enf) {
		miEnf=enf;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage("enfermedades.png"));
		if (miEnf!=null) 
		{
			setTitle("Modificar Enfermedad");
			
		}else 
		{
			setTitle("Registrar Enfermedad");
		}
		setBounds(100, 100, 450, 313);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 13, 420, 217);
		contentPanel.add(panel);
		panel.setLayout(null);
		{
			JPanel panel_1 = new JPanel();
			panel_1.setBounds(228, 37, 149, 87);
			panel.add(panel_1);
			panel_1.setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			panel_1.setLayout(null);
			{
				rdbVig = new JRadioButton("Bajo Vigilancia");
				rdbVig.setBounds(18, 33, 111, 25);
				panel_1.add(rdbVig);
			}
		}
		{
			txtCodigo = new JTextField();
			txtCodigo.setBounds(126, 11, 251, 22);
			panel.add(txtCodigo);
			txtCodigo.setEditable(false);
			txtCodigo.setColumns(10);
			txtCodigo.setText("E-"+Clinica.getInstance().getcodEnf());
		}
		{
			JLabel lblNewLabel = new JLabel("Codigo:");
			lblNewLabel.setBounds(35, 14, 56, 16);
			panel.add(lblNewLabel);
		}
		{
			JLabel lblNewLabel_1 = new JLabel("Nombre:");
			lblNewLabel_1.setBounds(29, 44, 56, 16);
			panel.add(lblNewLabel_1);
		}
		{
			txtNombre = new JTextField();
			txtNombre.setBounds(29, 74, 149, 22);
			panel.add(txtNombre);
			txtNombre.setColumns(10);
		}
		{
			JLabel lblNewLabel_2 = new JLabel("Descripcion:");
			lblNewLabel_2.setBounds(29, 110, 156, 16);
			panel.add(lblNewLabel_2);
		}
		{
			txtDescripcion = new JTextArea();
			txtDescripcion.setBounds(29, 140, 348, 60);
			panel.add(txtDescripcion);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Registrar");
				if (miEnf!=null) {

					okButton.setText("Modificar");
				}

				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (checkFields()==true)
						{
							if (miEnf==null)
							{
								
								String status;
								if(rdbVig.isSelected() == true) {
									status = "Vigilancia";
								}else {
									status = "Normal";
								}
								Enfermedad enf = new Enfermedad(txtCodigo.getText(), txtNombre.getText(), status, txtDescripcion.getText());
								Clinica.getInstance().agregarEnfermedad(enf);
								JOptionPane.showMessageDialog(null, "Enfermedad registrada correcta", "Enfermedad", JOptionPane.INFORMATION_MESSAGE);
								Clean();
								
							}else
							{
								miEnf.setDescripcion(txtDescripcion.getText());
								miEnf.setCodigo(txtCodigo.getText());
								miEnf.setNombre(txtNombre.getText());
								if(rdbVig.isSelected() == true) {
									miEnf.setStatus("Vigilancia");
								}else {
									miEnf.setStatus("Normal");
								}
								
								Clinica.getInstance().modificarEnfermedad(miEnf);
								dispose();
								ListarEnf.loadEnfermedades();
								
							}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Todos los campos deben estar llenos");
						
					}
						
					}
				});
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
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	loadEnf();	
	}
	
	private void Clean() {
		txtCodigo.setText("E-" + Clinica.getInstance().getcodEnf());
		txtDescripcion.setText("");
		txtNombre.setText("");
		rdbVig.setSelected(false);
		
	}
	
	private void loadEnf() {
		if (miEnf!=null) {
			txtCodigo.setText(miEnf.getCodigo());
			txtDescripcion.setText(miEnf.getDescripcion());
			txtNombre.setText(miEnf.getNombre());
			if(miEnf.getStatus().equalsIgnoreCase("Vigilancia"))
			{
				rdbVig.setSelected(true);
				
			}
			else
			{
				rdbVig.setSelected(false);
			}
		
		}
	}
	
private boolean checkFields() {
		
		if (txtNombre.getText().equals("") || txtDescripcion.getText().equals(""))
		{
			return false;
			
		}
		else 
		{
			return true;
		}

	}
}
