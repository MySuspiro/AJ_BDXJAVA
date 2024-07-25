package logico;

import java.io.Serializable;

public class Empleado extends Persona implements Serializable{
	private String puestoLaboral;

	public Empleado(String cedula, String nombre, String dir, String codigo, String telefono, char sexo,String correoElectronico, String puestoLaboral) {
		super(cedula, nombre, dir, codigo, telefono,sexo,correoElectronico);
		this.puestoLaboral=puestoLaboral;
		codigo = "EM-"+Clinica.getInstance().getcodPers();
	}

	public String getPuestoLaboral() {
		return puestoLaboral;
	}

	public void setPuestoLaboral(String puestoLaboral) {
		this.puestoLaboral = puestoLaboral;
	}
	

}
