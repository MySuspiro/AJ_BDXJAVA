package logico;
import java.io.Serializable;
import java.util.Date;

public class Sintoma implements Serializable{//
	private static final long serialVersionUID = 1L;
    private String codigo;
    private String nombre;

	
    public Sintoma(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    // Métodos getter y setter para el atributo 'nombre'
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}