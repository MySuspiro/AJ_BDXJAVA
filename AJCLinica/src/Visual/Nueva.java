package Visual;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class Nueva extends JFrame {

	private JPanel contentPane;
	static Socket sfd = null;
	static DataInputStream EntradaSocket;
	static DataOutputStream SalidaSocket;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Nueva frame = new Nueva();
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
	public Nueva() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("hospital.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnRespaldo = new JMenu("Respaldo");
		menuBar.add(mnRespaldo);
		
		JMenuItem mntmRespaldo = new JMenuItem("respaldo");
		mntmRespaldo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try
			    {
			      sfd = new Socket("127.0.0.1",7000);
			      EntradaSocket = new DataInputStream(new BufferedInputStream(sfd.getInputStream()));
			      SalidaSocket = new DataOutputStream(new BufferedOutputStream(sfd.getOutputStream()));
			      String ejemplo = new String("Esto es una prueba nueva");
			      try
			      {
			        SalidaSocket.writeUTF(ejemplo);
			        SalidaSocket.flush();
			      }
			      catch (IOException ioe)
			      {
			        System.out.println("Error: "+ioe);
			      }
			    }
			    catch (UnknownHostException uhe)
			    {
			      System.out.println("No se puede acceder al servidor.");
			      System.exit(1);
			    }
			    catch (IOException ioe)
			    {
			      System.out.println("Comunicación rechazada.");
			      System.exit(1);
			    }
			}
		});
		mnRespaldo.add(mntmRespaldo);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}