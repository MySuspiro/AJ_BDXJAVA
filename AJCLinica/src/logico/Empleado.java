package logico;

import java.io.Serializable;

public class Empleado extends Persona implements Serializable{
	private String puestoLaboral;
	private String userName;

	public Empleado(String cedula, String nombre, String dir, String codigo, String telefono, char sexo,String correoElectronico, String puestoLaboral) {
		super(cedula, nombre, dir, codigo, telefono,sexo,correoElectronico);
		this.puestoLaboral=puestoLaboral;
	}
	
	public Empleado(String cedula, String nombre, String dir, String codigo, String telefono, char sexo,String correoElectronico, String puestoLaboral,String userName) {
		super(cedula, nombre, dir, codigo, telefono,sexo,correoElectronico);
		this.puestoLaboral=puestoLaboral;
		this.userName = userName;

	}

	public String getPuestoLaboral() {
		return puestoLaboral;
	}

	public void setPuestoLaboral(String puestoLaboral) {
		this.puestoLaboral = puestoLaboral;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	

}
