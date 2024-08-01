package logico;

import java.io.Serializable;

public class Persona implements Serializable {
	
	private String cedula;
	private String nombre;
	private String dir;
	private String codigo;
	private String telefono;
	private char sexo;
	private String correoElectronico;
	public Persona(String cedula, String nombre, String dir, String codigo, String telefono, char sexo, String correoElectronico) {
		super();
		this.cedula = cedula;
		this.nombre = nombre;
		this.dir = dir;
		this.telefono = telefono;
		this.sexo = sexo;
		this.correoElectronico = correoElectronico;
	}
	
	
	
	
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public char getSexo() {
		return sexo;
	}
	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}



}
