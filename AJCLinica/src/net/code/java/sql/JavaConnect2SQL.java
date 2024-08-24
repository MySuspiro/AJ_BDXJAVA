package net.code.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import logico.CitaMedica;
import logico.Clinica;
import logico.Consulta;
import logico.Doctor;
import logico.Empleado;
import logico.Enfermedad;
import logico.Historial;
import logico.Paciente;
import logico.Persona;
import logico.Sintoma;
import logico.User;
import logico.Vacuna;
import sun.nio.cs.ext.ISCII91;

public class JavaConnect2SQL {
	public static JavaConnect2SQL cone = null;
	private static String connectionUrl = "jdbc:sqlserver://192.168.100.118:1433;" + "database=AnJo_Clinica;"
			+ "user=an.rosario;" // TU USER
			+ "password=Eict@2024;" // TU CLAVE
			+ "encrypt=true;" + "trustServerCertificate=true;" + "loginTimeout=30;";
	private static Connection cn = getConnection();

	public static JavaConnect2SQL getInstace() {
		if (cone == null) {
			cone = new JavaConnect2SQL();
		}
		return cone;
	}

	public static Connection getConnection() {
		Connection cn = null;
		// String cadena = "jdbc:sqlserver://" + ip + ":" + puerto + "/" + db;
		try {
			cn = DriverManager.getConnection(connectionUrl);
			JOptionPane.showMessageDialog(null, "Se conecto correctamente a la Base de Datos");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error al conectar la base de datos: " + e.toString());
		}
		return cn;
	}

	/*
	 * public void CloseConection() { cn.close(); }
	 */

	public void deleteWithString(String tableName, String conditionColumn, String conditionValue) {
		String query = "DELETE FROM " + tableName + " WHERE " + conditionColumn + " = '" + conditionValue + "'";

		try (PreparedStatement ps = cn.prepareStatement(query); ResultSet rs = ps.executeQuery();) {

			try {
				int rowsAffected = ps.executeUpdate();

				if (rowsAffected > 0) {
					JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// String queryString = "Select " + columns + "From " + TableName + Condition;
	public ArrayList<Doctor> getMisDoctor(String Condition) {
		ArrayList<Doctor> misDocs = new ArrayList<>();
		String query = "Select Codigo, Cedula, Nombre, Telefono, Direccion, Sexo, CorreoElectronico, Especialidad, UserName From Doctor "
				+ Condition;

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String codigo = rs.getString("Codigo");
				String cedula = rs.getString("Cedula");
				String nombre = rs.getString("Nombre");
				String telefono = rs.getString("Telefono");
				String dir = rs.getString("Direccion");
				char sexo = rs.getString("Sexo").charAt(0);
				String correoElectronico = rs.getString("CorreoElectronico");
				String especialidad = rs.getString("Especialidad");
				String userName = rs.getString("UserName");
				
				Doctor doc = new Doctor(cedula, nombre, dir, codigo, telefono, sexo, correoElectronico, especialidad,userName);
				misDocs.add(doc);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return misDocs;
	}

	public void agregarDoctor(Doctor doc, String userName) {
		String query = "Insert Into Doctor (Codigo, Cedula, Nombre, Telefono, Direccion, Sexo, CorreoElectronico, Especialidad, UserName) "
				+ "Values (?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, doc.getCodigo());
			ps.setString(2, doc.getCedula());
			ps.setString(3, doc.getNombre());
			ps.setString(4, doc.getTelefono());
			ps.setString(5, doc.getDir());
			ps.setString(6, Character.toString(doc.getSexo()));
			ps.setString(7, doc.getCorreoElectronico());
			ps.setString(8, doc.getEspecialidad());
			ps.setString(9, userName);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public void updateDoctor(Doctor doc) {
		String query = "Update Doctor Set Cedula = ?, Nombre = ?, Telefono = ?, Direccion = ?, Sexo = ?, CorreoElectronico = ?, Especialidad = ?"
				+ " WHERE Codigo = ?";

		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, doc.getCedula());
			ps.setString(2, doc.getNombre());
			ps.setString(3, doc.getTelefono());
			ps.setString(4, doc.getDir());
			ps.setString(5, Character.toString(doc.getSexo()));
			ps.setString(6, doc.getCorreoElectronico());
			ps.setString(7, doc.getEspecialidad());
			ps.setString(8, doc.getCodigo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public User buscarUserByName(String codigo) {
		User aux = null;
		boolean encontrado = false;
		int i = 0;
		ArrayList<User> MisUsers = getMisUsers("");
		while (!encontrado && i < MisUsers.size()) {
			User user = MisUsers.get(i);
			if (user.getUserName().equals(codigo)) {
				aux = user;
				encontrado = true;
			}
			i++;
		}

		return aux;
	}

	public CitaMedica buscarCitaByCode(String Code) {
		CitaMedica aux = null;
		boolean encontrado = false;
		int i = 0;
		ArrayList<CitaMedica> misCitas = getMisCitas("");
		while (!encontrado && i < misCitas.size()) {
			if (misCitas.get(i).getCodigo().equalsIgnoreCase(Code)) {
				aux = misCitas.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}

	/*
	 * public static void main(String[] args) { //Example Update usage try {
	 * deleteWithString("Doctor", "Cedula", "1231"); } catch (Exception e) {
	 * System.out.println("nosirvio"); }
	 * 
	 * 
	 * 
	 * try { updateDoctor("Nombre", "DesdeJavaMAMA", "Cedula", "1231"); } catch
	 * (Exception e) { System.out.println("nosirvio"); }
	 * 
	 * 
	 * 
	 * // Example Select usage try { ArrayList <Doctor> misDoc =
	 * getMisDoctor("WHERE cedula = '22345678901'"); if(misDoc != null) { for
	 * (Doctor doctor : misDoc) { System.out.println(doctor.getCedula());
	 * 
	 * } }else { System.out.println("cest vacie"); } } catch (Exception e) {
	 * System.out.println("nosirvio"); }
	 * 
	 * }
	 */

	public static Connection getCn() {
		return cn;
	}

	public static void setCn(Connection cn) {
		JavaConnect2SQL.cn = cn;
	}

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	public ArrayList<User> getMisUsers(String Condition) {
		ArrayList<User> misUsers = new ArrayList<>();
		String query = "Select UserName, Contrasena, Tipo From usuario " + Condition;

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String userName = rs.getString("UserName");
				String pass = rs.getString("Contrasena");
				String tipo = rs.getString("Tipo");
				Persona persona = null;// BORRAR CUANDO SE ARREGLE
				User user = new User(tipo, userName, pass, persona);
				misUsers.add(user);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return misUsers;
	}

	public ArrayList<String> getMisPaciente_Vacuna(Paciente pac) {
		ArrayList<String> misVacunas = new ArrayList<>();
		String query = "Select PacienteCodigo, VacunaCodigo From Paciente_Vacuna " + "Where PacienteCodigo = '"
				+ pac.getCodigo() + "'";

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String VacunaCodigo = rs.getString("VacunaCodigo");
				misVacunas.add(VacunaCodigo);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return misVacunas;
	}
	
	public ArrayList<String> getTop5PacxVac() {
		ArrayList<String> misVacunas = new ArrayList<>();
		String query = "select VacunaCodigo, Count(PacienteCodigo) As Cant from Paciente_Vacuna " + 
				"group by VacunaCodigo " + 
				"Order by Cant desc";
				

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			int i = 0;
			while (rs.next() && i < 5) {
				String VacunaCodigo = rs.getString("VacunaCodigo");
				misVacunas.add(VacunaCodigo);
				i++;
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return misVacunas;
	}
	
	public int getCantPacxVac(Vacuna vac) {
		int cant = 0;
		String query = "select VacunaCodigo, Count(PacienteCodigo) As Cant from Paciente_Vacuna " + 
				"group by VacunaCodigo " + 
				"Having VacunaCodigo = '"+ vac.getCodigo() + 
				"' Order by Cant asc";
				

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				cant = rs.getInt("Cant");
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cant;
	}
	
	
	public int getCantPacxEnfVig(Enfermedad enf) {
		int cant = 0;
		String query = "select EnfermedadCodigo, Count(PacienteCodigo) As Cant from Enfermedad_Condicion_Paciente " + 
				"group by EnfermedadCodigo " + 
				"Having EnfermedadCodigo = '"+ enf.getCodigo() + 
				"' Order by Cant asc";
				

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				cant = rs.getInt("Cant");
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return cant;
	}

	public ArrayList<String> getMisPaciente_Consulta(Paciente pac) {
		ArrayList<String> misConsultas = new ArrayList<>();
		String query = "Select PacienteCodigo, Codigo From ConsultaMedica " + "Where PacienteCodigo = '"
				+ pac.getCodigo() + "'";

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String ConsultaCodigo = rs.getString("Codigo");
				misConsultas.add(ConsultaCodigo);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return misConsultas;
	}

	public Consulta buscarConsultaXCod(String code) {
		Consulta consulta = null;
		String query = "Select Codigo, Fecha, Descripcion, Estado, DoctorCodigo, PacienteCodigo, EnfermedadCodigo, VacunaCodigo, Prioridad  From ConsultaMedica " + "Where Codigo = '" + code
				+ "'";

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String codigoConsulta = rs.getString("Codigo");
				String diagnostico = rs.getString("descripcion");
				String status = rs.getString("Estado");
				Date fecha = rs.getDate("Fecha");
				Enfermedad enfermedad = buscarEnfermedadByCode(rs.getString("EnfermedadCodigo"));
				Paciente paciente = buscarPacienteByCodigo(rs.getString("PacienteCodigo"));
				Vacuna vac = buscarVacunaByCod(rs.getString("VacunaCodigo"));
				Doctor doctor = buscarDoctorByCodigo(rs.getString("DoctorCodigo"));
				int prioridad = rs.getInt("Prioridad");
				
				consulta = new Consulta(codigoConsulta, diagnostico, enfermedad, paciente, doctor, status, vac, fecha,
						prioridad);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return consulta;
	}

	public ArrayList<String> getMisEnfermedad_Condicion_Paciente(Paciente pac) {
		ArrayList<String> misEnfermedades = new ArrayList<>();
		String query = "Select PacienteCodigo, EnfermedadCodigo From Enfermedad_Condicion_Paciente "
				+ "Where PacienteCodigo = '" + pac.getCodigo() + "'";

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String EnfermedadCodigo = rs.getString("EnfermedadCodigo");
				misEnfermedades.add(EnfermedadCodigo);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return misEnfermedades;
	}

	public void agregarVacPaciente(Paciente pac, Vacuna vac) {
		String query = "INSERT INTO Paciente_Vacuna (PacienteCodigo, VacunaCodigo) VALUES (?,?);";
		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, pac.getCodigo());
			ps.setString(2, vac.getCodigo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
		removeOneVac(vac);
	}

	public void agregarEnfPaciente(Paciente pac, Enfermedad enf) {
		String query = "INSERT INTO Enfermedad_Condicion_Paciente (PacienteCodigo, EnfermedadCodigo)"
				+ " VALUES (?,?);";
		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, pac.getCodigo());
			ps.setString(2, enf.getCodigo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public void deleteEnfPaciente(Paciente pac, Enfermedad enf) {
		String query = "Delete From Enfermedad_Condicion_Paciente Where"
				+ " PacienteCodigo = ? AND EnfermedadCodigo = ?;";
		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, pac.getCodigo());
			ps.setString(2, enf.getCodigo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public ArrayList<Enfermedad> getMisEnfermedades(String Condition) {
		ArrayList<Enfermedad> misEnfermedades = new ArrayList<>();
		String query = "Select Codigo, Nombre, Estado, Descripcion From Enfermedad_Condicion " + Condition;

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String codigo = rs.getString("Codigo");
				String nombre = rs.getString("Nombre");
				int estado = rs.getInt("Estado");
				String descripcion = rs.getString("Descripcion");
				String status = (estado != 0)? "Vigilancia":"Normal";
				Enfermedad enfermedad = new Enfermedad(codigo, nombre, status, descripcion);
				misEnfermedades.add(enfermedad);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return misEnfermedades;
	}
	

	public Enfermedad buscarEnfermedadByCode(String Code) {
		Enfermedad aux = null;
		boolean encontrado = false;
		int i = 0;
		ArrayList<Enfermedad> misEnfermedades = getMisEnfermedades("");
		while (!encontrado && i < misEnfermedades.size()) {
			if (misEnfermedades.get(i).getCodigo().equalsIgnoreCase(Code)) {
				aux = misEnfermedades.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}

	public Enfermedad buscarEnfermedadByNom(String nom) {
		Enfermedad aux = null;
		boolean encontrado = false;
		int i = 0;
		ArrayList<Enfermedad> misEnfermedades = getMisEnfermedades("");
		while (!encontrado && i < misEnfermedades.size()) {
			if (misEnfermedades.get(i).getNombre().equalsIgnoreCase(nom)) {
				aux = misEnfermedades.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}

	public Doctor buscarDoctorByCodigo(String codigoPersona) {
		Doctor aux = null;
		boolean encontrado = false;
		int i = 0;
		ArrayList<Doctor> misPersonas = getMisDoctor("");
		while (!encontrado && i < misPersonas.size()) {
			if (misPersonas.get(i).getCodigo().equalsIgnoreCase(codigoPersona)) {
				aux = misPersonas.get(i);
				encontrado = true;
			}
			i++;
		}

		return aux;
	}

	public Empleado buscarEmpleadoByCodigo(String codigoPersona) {
		Empleado aux = null;
		boolean encontrado = false;
		int i = 0;
		ArrayList<Empleado> misPersonas = getMisEmpleado("");
		while (!encontrado && i < misPersonas.size()) {
			if (misPersonas.get(i).getCodigo().equalsIgnoreCase(codigoPersona)) {
				aux = misPersonas.get(i);
				encontrado = true;
			}
			i++;
		}

		return aux;
	}

	public Paciente buscarPacienteByCodigo(String codigoPersona) {
		Paciente aux = null;
		boolean encontrado = false;
		int i = 0;
		ArrayList<Paciente> misPersonas = getMisPacientes("");
		while (!encontrado && i < misPersonas.size()) {
			if (misPersonas.get(i).getCodigo().equalsIgnoreCase(codigoPersona)) {
				aux = misPersonas.get(i);
				encontrado = true;
			}
			i++;
		}

		return aux;
	}

	public Paciente buscarPacienteByCedula(String cedula) {
		Paciente aux = null;
		boolean encontrado = false;
		int i = 0;
		ArrayList<Paciente> misPersonas = getMisPacientes("");
		if (!misPersonas.isEmpty()) {
			while (!encontrado && i < misPersonas.size()) {
				if (misPersonas.get(i).getCedula().equalsIgnoreCase(cedula)) {
					aux = misPersonas.get(i);
					encontrado = true;
				}
				i++;
			}
		}
		return aux;
	}

	public ArrayList<Vacuna> getMisVacunas(String Condition) {
		ArrayList<Vacuna> misVacunas = new ArrayList<>();
		String query = "select Codigo, Nombre, Descripcion, Cant, EnfermedadCodigo From Vacuna " + Condition;

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String codigo = rs.getString("Codigo");
				String nombre = rs.getString("Nombre");
				String descripcion = rs.getString("descripcion");
				int cant = rs.getInt("Cant");
				Enfermedad enf = buscarEnfermedadByCode(rs.getString("EnfermedadCodigo"));
				Vacuna vacuna = new Vacuna(codigo, nombre, descripcion, cant, enf);
				misVacunas.add(vacuna);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return misVacunas;
	}

	public ArrayList<Paciente> getMisPacientes(String Condition) {
		ArrayList<Paciente> misPacs = new ArrayList<>();
		String query = "Select Codigo, Cedula, Nombre, Telefono, Direccion, Sexo, CorreoElectronico, seguro, peso, altura, tipoSangre From Paciente"
				+ Condition;

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String codigo = rs.getString("Codigo");
				String cedula = rs.getString("Cedula");
				String nombre = rs.getString("Nombre");
				String telefono = rs.getString("Telefono");
				String dir = rs.getString("Direccion");
				char sexo = rs.getString("Sexo").charAt(0);
				String correoElectronico = rs.getString("CorreoElectronico");
				String seguro = rs.getString("seguro");
				int peso = rs.getInt("peso");
				int altura = rs.getInt("altura");
				String tipoSangre = rs.getString("tipoSangre");

				Paciente pac = new Paciente(cedula, nombre, dir, codigo, telefono, sexo, correoElectronico, seguro,
						peso, altura, tipoSangre);
				misPacs.add(pac);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return misPacs;
	}

	public Vacuna buscarVacunaByCod(String cod) {
		Vacuna aux = null;
		boolean encontrado = false;
		int i = 0;
		ArrayList<Vacuna> misVacunas = getMisVacunas("");
		while (!encontrado && i < misVacunas.size()) {
			if (misVacunas.get(i).getCodigo().equalsIgnoreCase(cod)) {
				aux = misVacunas.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}

	public Vacuna buscarVacunaByNom(String nom) {
		Vacuna aux = null;
		boolean encontrado = false;
		int i = 0;
		ArrayList<Vacuna> misVacunas = getMisVacunas("");
		while (!encontrado && i < misVacunas.size()) {
			if (misVacunas.get(i).getNombre().equalsIgnoreCase(nom)) {
				aux = misVacunas.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}

	public ArrayList<Consulta> getMisConsultas(String Condition) {
		ArrayList<Consulta> misConsultas = new ArrayList<>();
		String query = "Select Codigo, Fecha, Descripcion, Estado, DoctorCodigo, PacienteCodigo, EnfermedadCodigo, VacunaCodigo, Prioridad From ConsultaMedica "
				+ Condition;

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String codigoConsulta = rs.getString("Codigo");
				String diagnostico = rs.getString("descripcion");
				String status = rs.getString("Estado");
				Date fecha = rs.getDate("Fecha");
				Enfermedad enfermedad = buscarEnfermedadByCode(rs.getString("EnfermedadCodigo"));
				Paciente paciente = buscarPacienteByCodigo(rs.getString("PacienteCodigo"));
				Vacuna vac = buscarVacunaByCod(rs.getString("VacunaCodigo"));
				Doctor doctor = buscarDoctorByCodigo(rs.getString("DoctorCodigo"));
				int prioridad = rs.getInt("Prioridad");
				Consulta consulta = new Consulta(codigoConsulta, diagnostico, enfermedad, paciente, doctor, status, vac,
						fecha, prioridad);
				misConsultas.add(consulta);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return misConsultas;
	}

	public ArrayList<CitaMedica> getMisCitas(String Condition) {
		ArrayList<CitaMedica> misCitas = new ArrayList<>();
		String query = "Select Codigo, Fecha, Hora, PerCedula, PerNombre, DocCod from CitaMedica " + Condition;

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String codigo = rs.getString("Codigo");
				String cedPaciente = rs.getString("PerCedula");
				String nomPaciente = rs.getString("PerNombre");
				String hora = rs.getString("Hora");
				Date fecha = rs.getDate("Fecha");
				Doctor doctor = buscarDoctorByCodigo(rs.getString("DocCod"));
				CitaMedica cita = new CitaMedica(codigo, cedPaciente, nomPaciente, doctor, hora, fecha);
				misCitas.add(cita);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return misCitas;
	}

	public void agregarPaciente(Paciente pac) {
		String query = "INSERT INTO Paciente (Codigo, Cedula, Nombre, Telefono, Direccion, Sexo, CorreoElectronico, Seguro, TipoSangre, Altura, Peso)"
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?);";
		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, pac.getCodigo());
			ps.setString(2, pac.getCedula());
			ps.setString(3, pac.getNombre());
			ps.setString(4, pac.getTelefono());
			ps.setString(5, pac.getDir());
			ps.setString(6, Character.toString(pac.getSexo()));
			ps.setString(7, pac.getCorreoElectronico());
			ps.setString(8, pac.getSeguro());
			ps.setString(9, pac.getTipoSangre());
			ps.setInt(10, pac.getAltura());
			ps.setInt(11, pac.getPeso());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public void updatePaciente(Paciente pac) {
		String query = "Update Paciente Set Cedula = ?, Nombre = ?, Telefono = ?, Direccion = ?, Sexo = ?, CorreoElectronico = ?, Seguro = ?, TipoSangre = ?, Altura = ?, Peso = ?"
				+ " WHERE Codigo = ?";

		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, pac.getCedula());
			ps.setString(2, pac.getNombre());
			ps.setString(3, pac.getTelefono());
			ps.setString(4, pac.getDir());
			ps.setString(5, Character.toString(pac.getSexo()));
			ps.setString(6, pac.getCorreoElectronico());
			ps.setString(7, pac.getSeguro());
			ps.setString(8, pac.getTipoSangre());
			ps.setInt(9, pac.getAltura());
			ps.setInt(10, pac.getPeso());
			ps.setString(11, pac.getCodigo());


			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public void agregarVacuna(Vacuna vac) {
		String query = "INSERT INTO Vacuna (Codigo, Nombre, Descripcion, Cant, EnfermedadCodigo)"
				+ " VALUES (?,?,?,?,?)";
		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, vac.getCodigo());
			ps.setString(2, vac.getNombre());
			ps.setString(3, vac.getDescripcion());
			ps.setInt(4, vac.getCant());
			ps.setString(5, vac.getEnf().getCodigo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public void agregarUser(User user) {
		String query = "INSERT INTO usuario Set (UserName, Contrasena, Tipo)" + " VALUES (?,?,?)";

		try (PreparedStatement ps = cn.prepareStatement(query)) {
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getPass());
			Persona persona = user.getPersona();
			if (persona != null) {
				if (persona instanceof Doctor) {
					ps.setString(3, "Doctor");
				} else if (persona instanceof Empleado) {
					ps.setString(3, "Empleado");
				}

			} else {
				ps.setString(3, "Administrador");
			}
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public void updateUser(User user) {
		String query = "Update usuario Set Contrasena = ?, Tipo = ? WHERE UserName = ?";

		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, user.getPass());
			ps.setString(2, user.getTipo());
			ps.setString(3, user.getUserName());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public Doctor buscarDoctorByNom(String nom) {
		Doctor aux = null;
		boolean encontrado = false;
		int i = 0;
		ArrayList<Doctor> misPersonas = getMisDoctor("");
		while (!encontrado && i < misPersonas.size()) {
			if (misPersonas.get(i).getNombre().equalsIgnoreCase(nom)) {
				aux = misPersonas.get(i);
				encontrado = true;
			}
			i++;
		}
		return aux;
	}

	public void updateVacuna(Vacuna vac) {
		String query = "Update Vacuna Set Nombre = ?, Descripcion = ?, Cant = ?, EnfermedadCodigo = ?"
				+ " WHERE Codigo = ?";

		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, vac.getNombre());
			ps.setString(2, vac.getDescripcion());
			ps.setInt(3, vac.getCant());
			ps.setString(4, vac.getEnf().getCodigo());
			ps.setString(5, vac.getCodigo());
			
			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error al Modificar el registro.");
			e.printStackTrace();
		}
	}

	public void removeOneVac(Vacuna vac) {
		String query = "Update Vacuna Set Cant = ? WHERE Codigo = ?";

		try (PreparedStatement ps = cn.prepareStatement(query)) {
			int newcant = vac.getCant() - 1;
			ps.setInt(1, newcant);
			ps.setString(2, vac.getCodigo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error al Modificar el registro.");
			e.printStackTrace();
		}
	}

	public void agregarEnfermedad(Enfermedad enf) {
		String query = "INSERT INTO Enfermedad_Condicion (Codigo, Nombre, Estado, Descripcion)" + " VALUES (?,?,?,?)";
		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, enf.getCodigo());
			ps.setString(2, enf.getNombre());
			ps.setInt(3, (enf.getStatus().equalsIgnoreCase("Vigilancia"))? 1:0);
			ps.setString(4, enf.getDescripcion());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public void updateEnfermedad(Enfermedad enf) {
		String query = "UPDATE Enfermedad_Condicion "
	             + "SET Nombre = ?, Estado = ?, Descripcion = ? "
	             + "WHERE Codigo = ?";
		try (PreparedStatement ps = cn.prepareStatement(query)) {
			ps.setString(1, enf.getNombre());
			ps.setInt(2, (enf.getStatus().equalsIgnoreCase("Vigilancia"))? 1:0);
			ps.setString(3, enf.getDescripcion());
			ps.setString(4, enf.getCodigo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error al Modificar el registro.");
			e.printStackTrace();
		}
	}

	public void agregarCita(CitaMedica cita) {
		String query = "INSERT INTO CitaMedica (Codigo, Fecha, Hora, PerCedula, PerNombre, DocCod)"
				+ " VALUES (?,?,?,?,?,?)";
		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, cita.getCodigo());
			ps.setDate(2, new java.sql.Date(cita.getFecha().getTime()));
			ps.setString(3, cita.getHora());
			ps.setString(4, cita.getCedPaciente());
			ps.setString(5, cita.getNomPaciente());
			ps.setString(6, cita.getDoctor().getCodigo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public void updateCita(CitaMedica cita) {
		String query = "Update CitaMedica Set Fecha = ?, Hora = ?, PerCedula = ?, PerNombre = ?, DocCod =?"
				+ " WHERE Codigo = ?";

		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setDate(1, new java.sql.Date(cita.getFecha().getTime()));
			ps.setString(2, cita.getHora());
			ps.setString(3, cita.getCedPaciente());
			ps.setString(4, cita.getNomPaciente());
			ps.setString(5, cita.getDoctor().getCodigo());
			ps.setString(5, cita.getCodigo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error al Modificar el registro.");
			e.printStackTrace();
		}
	}

	public void agregarConsulta(Consulta consulta) {
		String query = "INSERT INTO ConsultaMedica (Codigo, Fecha, Descripcion, Estado, DoctorCodigo, PacienteCodigo, EnfermedadCodigo, VacunaCodigo, Prioridad)"
				+ " VALUES (?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, consulta.getCodigoConsulta());
			ps.setDate(2, new java.sql.Date(consulta.getFechaConsulta().getTime()));
			ps.setString(3, consulta.getDiagnostico());
			ps.setString(4, consulta.getStatus());
			ps.setString(5, consulta.getDoctor().getCodigo());
			ps.setString(6, consulta.getPaciente().getCodigo());
			ps.setString(7, null);
			ps.setString(8, null);
			if(consulta.getEnfermedad() != null) {
				ps.setString(7, consulta.getEnfermedad().getCodigo());
			}
			
			if(consulta.getVac() != null) {
				ps.setString(8, consulta.getVac().getCodigo());
			}
			ps.setInt(9, consulta.getPrioridad());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public static void updateConsulta(Consulta consulta) {
		String query = "Update ConsultaMedica Set Fecha = ?, Descripcion = ?, Estado = ?, DoctorCodigo = ?, PacienteCodigo = ?, EnfermedadCodigo = ?, VacunaCodigo = ?, Prioridad = ?"
				+ " WHERE Codigo = ?";

		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setDate(1, new java.sql.Date(consulta.getFechaConsulta().getTime()));
			ps.setString(2, consulta.getDiagnostico());
			ps.setString(3, consulta.getStatus());
			ps.setString(4, consulta.getDoctor().getCodigo());
			ps.setString(5, consulta.getPaciente().getCodigo());
			ps.setString(6, consulta.getEnfermedad().getCodigo());
			ps.setString(7, consulta.getVac().getCodigo());
			ps.setInt(8, consulta.getPrioridad());
			ps.setString(9, consulta.getCodigoConsulta());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error al Modificar el registro.");
			e.printStackTrace();
		}
	}

	public ArrayList<Empleado> getMisEmpleado(String Condition) {
		ArrayList<Empleado> misEmps = new ArrayList<>();
		String query = "Select Codigo, Cedula, Nombre, Telefono, Direccion, Sexo, CorreoElectronico, PuestoLaboral, UserName From Empleado "
				+ Condition;

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String codigo = rs.getString("Codigo");
				String cedula = rs.getString("Cedula");
				String nombre = rs.getString("Nombre");
				String telefono = rs.getString("Telefono");
				String dir = rs.getString("Direccion");
				char sexo = rs.getString("Sexo").charAt(0);
				String correoElectronico = rs.getString("CorreoElectronico");
				String puestoLaboral = rs.getString("PuestoLaboral");
				String UserName = rs.getString("UserName");

				Empleado emp = new Empleado(cedula, nombre, dir, codigo, telefono, sexo, correoElectronico,
						puestoLaboral, UserName);
				misEmps.add(emp);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return misEmps;
	}

	public void agregarEmpleado(Empleado emp, String userName) {
		String query = "Insert Into Empleado (Codigo, Cedula, Nombre, Telefono, Direccion, Sexo, CorreoElectronico, PuestoLaboral, UserName) "
				+ "Values (?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, emp.getCodigo());
			ps.setString(2, emp.getCedula());
			ps.setString(3, emp.getNombre());
			ps.setString(4, emp.getTelefono());
			ps.setString(5, emp.getDir());
			ps.setString(6, Character.toString(emp.getSexo()));
			ps.setString(7, emp.getCorreoElectronico());
			ps.setString(8, emp.getPuestoLaboral());
			ps.setString(9, userName);

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public void updateEmpleado(Empleado emp) {
		String query = "Update Empleado Set Cedula = ?, Nombre = ?, Telefono = ?, Direccion = ?, Sexo = ?, CorreoElectronico = ?, PuestoLaboral = ?"
				+ " WHERE Codigo = ?";

		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, emp.getCedula());
			ps.setString(2, emp.getNombre());
			ps.setString(3, emp.getTelefono());
			ps.setString(4, emp.getDir());
			ps.setString(5, Character.toString(emp.getSexo()));
			ps.setString(6, emp.getCorreoElectronico());
			ps.setString(7, emp.getPuestoLaboral());
			ps.setString(8, emp.getCodigo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				System.out.println("Error al insertar el registro.");
			}
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error al Modificar el registro.");
			e.printStackTrace();
		}
	}

	public ArrayList<Sintoma> getMisSintomas(String Condition) {
		ArrayList<Sintoma> misSintomas = new ArrayList<>();
		String query = "Select Codigo, Nombre From Sintoma " + Condition;

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String codigo = rs.getString("Codigo");
				String nombre = rs.getString("Nombre");
				Sintoma sintoma = new Sintoma(codigo, nombre);
				misSintomas.add(sintoma);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return misSintomas;
	}

	public ArrayList<String> getMisSintomasNoms(String Condition) {
		ArrayList<String> misSintomas = new ArrayList<>();
		String query = "Select Nombre From Sintoma " + Condition;

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String nombre = rs.getString("Nombre");
				String sintoma = nombre;
				misSintomas.add(sintoma);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return misSintomas;
	}
	
	public Sintoma BuscarSintomasXNoms(String nom) {
		Sintoma miSintoma = null;
		String query = "Select Codigo, Nombre From Sintoma Where Nombre = '" + nom +"'";

		try {
			PreparedStatement ps = cn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String codigo = rs.getString("Codigo");
				String nombre = rs.getString("Nombre");
				miSintoma = new Sintoma(codigo, nombre);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return miSintoma;
	}

	public void agregarSintoma(Sintoma sint) {
		String query = "Insert Into Sintoma (Nombre) " + "Values (?)";
		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, sint.getNombre());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}
	
	public void agregarSintomaXConsulta(Consulta cons, Sintoma sint) {
		String query = "insert into ConsultaMedica_Sintoma (ConsultaMedicaCodigo, SintomaCodigo) Values (?,?)";
		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, cons.getCodigoConsulta());
			ps.setString(2, sint.getCodigo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
			} else {
				System.out.println("Sintoma no entro");
			}
			ps.close();
		} catch (SQLException e) {
			System.out.println("Error al insertar el registro.");
			e.printStackTrace();
		}
	}

	public static void updateSintoma(Sintoma sint) {
		String query = "Update Sintoma Set Nombre = ?" + " WHERE Codigo = ?";

		try (PreparedStatement ps = cn.prepareStatement(query)) {

			ps.setString(1, sint.getNombre());
			ps.setString(2, sint.getCodigo());

			int rowsAffected = ps.executeUpdate();

			if (rowsAffected > 0) {
				JOptionPane.showMessageDialog(null, "Inserción realizada exitosamente.", "Registro",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Inserción no ha sido realizada.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			ps.close();

		} catch (SQLException e) {
			System.out.println("Error al Modificar el registro.");
			e.printStackTrace();
		}
	}

}