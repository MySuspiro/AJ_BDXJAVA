package logico;

import java.io.Serializable;

public class User implements Serializable{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tipo;
	private String userName;
	private String pass;
	private Persona persona;
	
	
	public User(String tipo, String userName, String pass,Persona persona) {
		super();
		this.tipo = tipo;
		this.userName = userName;
		this.pass = pass;
		this.persona=persona;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	

}