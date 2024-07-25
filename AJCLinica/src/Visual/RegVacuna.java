package Visual;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import logico.Clinica;
import logico.Enfermedad;
import logico.Vacuna;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class RegVacuna extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCod;
	private JTextField txtNom;
	private JSpinner spnCant;
	private JTextArea txtDesc;
	private JComboBox<String> cmbEnf;
	Vacuna miVacuna = null;
	private JButton btnReg;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the dialog.
	 */
	public RegVacuna(Vacuna vac) {
		miVacuna = vac;
		setIconImage(Toolkit.getDefaultToolkit().getImage("vacunas.png"));
		if(miVacuna != null) {
			setTitle("Modificar Vacuna");
		}else {
			setTitle("Registro de Vacuna");
		}
		
		setBounds(100, 100, 450, 327);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(12, 15, 408, 217);
		contentPanel.add(panel);
		
		txtCod = new JTextField();
		txtCod.setText("V-"+Clinica.getInstance().getcodVac());
		txtCod.setEditable(false);
		txtCod.setColumns(10);
		txtCod.setBounds(36, 40, 149, 22);
		panel.add(txtCod);
		
		JLabel label = new JLabel("Codigo:");
		label.setBounds(36, 12, 56, 16);
		panel.add(label);
		
		JLabel label_1 = new JLabel("Nombre:");
		label_1.setBounds(221, 12, 56, 16);
		panel.add(label_1);
		
		txtNom = new JTextField();
		txtNom.setColumns(10);
		txtNom.setBounds(221, 40, 149, 22);
		panel.add(txtNom);
		
		JLabel label_2 = new JLabel("Descripcion:");
		label_2.setBounds(36, 136, 156, 16);
		panel.add(label_2);
		
		txtDesc = new JTextArea();
		txtDesc.setBounds(36, 164, 334, 36);
		panel.add(txtDesc);
		
		JLabel lblNewLabel = new JLabel("Enfermedad:");
		lblNewLabel.setBounds(36, 74, 149, 16);
		panel.add(lblNewLabel);
		
		cmbEnf = new JComboBox<>();
		cmbEnf.setBounds(36, 102, 149, 22);
		panel.add(cmbEnf);
		cmbEnf.addItem("<Seleccione>");
		for (Enfermedad aux : Clinica.getInstance().getMisEnfermedades()) {
			cmbEnf.addItem(aux.getNombre());
		}
		
		
		JLabel lblNewLabel_1 = new JLabel("Cantidad:");
		lblNewLabel_1.setBounds(221, 74, 56, 16);
		panel.add(lblNewLabel_1);
		
		spnCant = new JSpinner();
		spnCant.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		spnCant.setBounds(221, 102, 149, 22);
		panel.add(spnCant);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				if (miVacuna != null) {
					btnReg = new JButton("Modificar");
				}else {
					btnReg = new JButton("Registro");
				}
				btnReg.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						Enfermedad enf = null;
						enf = Clinica.getInstance().buscarEnfermedadByNom(cmbEnf.getSelectedItem().toString());
						if(miVacuna == null) {
							if(txtNom.getText() != null && txtDesc.getText() != null && enf != null) {
								Vacuna vac = new Vacuna(txtNom.getText(), txtDesc.getText(), (int)spnCant.getValue(), enf);
								Clinica.getInstance().agregarVacuna(vac);
								JOptionPane.showMessageDialog(null, "Operación satisfactoria", "Vacunas", JOptionPane.INFORMATION_MESSAGE);
								clean();
							}
						}else {
							miVacuna.setDescripcion(txtDesc.getText());
							miVacuna.setNombre(txtNom.getText());
							miVacuna.SetCant((int)spnCant.getValue());
							if(txtNom.getText() != null && txtDesc.getText() != null && enf != null) {
								Clinica.getInstance().modificarVacuna(miVacuna);
								JOptionPane.showMessageDialog(null, "Operación satisfactoria", "Vacunas", DISPOSE_ON_CLOSE);
								dispose();
							}
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
		}
		loadVacuna();
	}

	protected void clean() {
		txtCod.setText("V-"+Clinica.getInstance().getcodVac());
		txtDesc.setText("");
		txtNom.setText("");
		spnCant.setValue(1);
		cmbEnf.setSelectedIndex(0);
	}

	private void loadVacuna() {
		if(miVacuna != null) {
			txtCod.setText(miVacuna.getCodigo());
			txtDesc.setText(miVacuna.getDescripcion());
			txtNom.setText(miVacuna.getNombre());
			spnCant.setValue(miVacuna.getCant());
			cmbEnf.setSelectedItem(miVacuna.getEnf().getNombre());
			cmbEnf.setEditable(false);
		}
	}
	
	
}
