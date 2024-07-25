package Visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import logico.Clinica;
import logico.User;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JSeparator;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.Icon;
import java.awt.Toolkit;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				FileInputStream empresa;
				FileOutputStream empresa2;
				ObjectInputStream empresaRead;
				ObjectOutputStream empresaWrite;
				try {
					empresa = new FileInputStream ("laclinica17.dat");
					empresaRead = new ObjectInputStream(empresa);
					Clinica temp = (Clinica)empresaRead.readObject();
					Clinica.setClinica(temp);
					empresa.close();
					empresaRead.close();
			
				} catch (FileNotFoundException e) {
					try {
						empresa2 = new  FileOutputStream("laclinica17.dat");
						empresaWrite = new ObjectOutputStream(empresa2);
						User aux = new User("Administrador", "Admin", "Admin",null);
						Clinica.getInstance().regUser(aux);
						empresaWrite.writeObject(Clinica.getInstance());
						empresa2.close();
						empresaWrite.close();
					} catch (FileNotFoundException e1) {
					} catch (IOException e1) {
						// TODO Auto-generated catch block
					}
				} catch (IOException e) {
					
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("Clinica");
		setIconImage(Toolkit.getDefaultToolkit().getImage("hospital.png"));
		setBackground(new Color(0, 0, 0));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 572, 393);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(224, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 242, 346);
        panel.setBorder(null);
        panel.setBackground(new Color(255, 255, 255));
        contentPane.add(panel);
        panel.setLayout(null);
        
        JSeparator separator = new JSeparator();
        separator.setBackground(new Color(0, 0, 0));
        separator.setBounds(42, 217, 160, 2);
        panel.add(separator);
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setBackground(Color.BLACK);
        separator_1.setBounds(42, 151, 160, 2);
        panel.add(separator_1);

        JLabel lblUsuario = new JLabel("Iniciar Sesion:");
        lblUsuario.setForeground(new Color(0, 0, 0));
        lblUsuario.setBackground(new Color(0, 0, 0));
        lblUsuario.setFont(new Font("Vivaldi", Font.BOLD | Font.ITALIC, 30));
        lblUsuario.setBounds(20, 46, 200, 37);
        panel.add(lblUsuario);

        textField = new JTextField();
        textField.setBackground(new Color(255, 255, 255));
        textField.setText("Usuario");
		textField.setForeground(Color.GRAY);
        textField.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		if(textField.getText().equals("Usuario")) {
        			textField.setText("");
        			textField.setForeground(Color.BLACK);
				}
				if(passwordField.getText().isEmpty()) {
					passwordField.setText("********");
					passwordField.setForeground(Color.GRAY);
				}
        	}
        	
        });
        textField.setBorder(null);
        textField.setBounds(40, 129, 160, 20);
        panel.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(255, 255, 255));
		passwordField.setText("********");
		passwordField.setForeground(Color.GRAY);
        passwordField.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mousePressed(MouseEvent e) {
        		if(passwordField.getText().equals("********")) {
        			passwordField.setText("");
        			passwordField.setForeground(Color.BLACK);
					}
				if(textField.getText().isEmpty()) {
					textField.setText("Usuario");
					textField.setForeground(Color.GRAY);
				}
        	}
        });
        passwordField.setBorder(null);
        passwordField.setBounds(40, 195, 160, 20);
        panel.add(passwordField);
      
        
        JButton btnLogin = new JButton("Login");
        btnLogin.setBorder(null);
        btnLogin.setBackground(new Color(245, 245, 245));
        btnLogin.setForeground(new Color(0, 0, 0));
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Obtener el usuario y contraseña ingresados
                String username = textField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (Clinica.getInstance().confirmLogin(username, password)) {
                    Principal frame = new Principal();
                    dispose();
                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Chequee el Usuario / Contraseña e intente otra vez!", "LogIn", JOptionPane.INFORMATION_MESSAGE);
                }

                // Limpiar la contraseña almacenada
                passwordField.setText("");
            }
        });
        btnLogin.setBounds(57, 261, 126, 25);
        panel.add(btnLogin);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(240, 0, 314, 346);
        getContentPane().add(panel_1);
        ImageIcon imageIcon = new ImageIcon("Estetoscopio.png");
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setBounds(0, 0, 314, 346);
        panel_1.add(imageLabel);
        
        
        
        
        
    }
}