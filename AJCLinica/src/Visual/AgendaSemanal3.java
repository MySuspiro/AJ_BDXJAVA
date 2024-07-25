package Visual;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import logico.CitaMedica;
import logico.Clinica;
import logico.Doctor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class AgendaSemanal3 extends JDialog {
    private JTable table;
    private Calendar calendar;
    private JTextField txtCedulaBusqueda;
    private String cedulaBusqueda = "";

    public AgendaSemanal3() {
        calendar = Calendar.getInstance();
        
        setTitle("Agenda Semanal");
		setIconImage(Toolkit.getDefaultToolkit().getImage("calendario.png"));
        setSize(833, 755);
        setLocationRelativeTo(null);
        setModal(true); 

        JPanel searchPanel = new JPanel();
        JLabel lblCedula = new JLabel("Ingrese la cédula del doctor:");
        txtCedulaBusqueda = new JTextField(10);
        JButton btnBuscar = new JButton("Buscar");
        searchPanel.add(lblCedula);
        searchPanel.add(txtCedulaBusqueda);
        searchPanel.add(btnBuscar);
        getContentPane().add(searchPanel, BorderLayout.NORTH);

        table = new JTable(10, 6);
        table.setRowHeight(60);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        String[] diasSemana = {"Hora", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes"};
        model.setColumnIdentifiers(diasSemana);

        String[] horas = {"9 : 00", "10 : 00", "11 : 00", "12 : 00", "13 : 00", "14 : 00", "15 : 00", "16 : 00", "17 : 00", "18 : 00"};
        for (int i = 0; i < horas.length; i++) {
            model.setValueAt(horas[i], i, 0);
        }
        
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Agregar botones para cambiar de semana
        JButton btnSemanaAnterior = new JButton("Semana Anterior");
        JButton btnSemanaSiguiente = new JButton("Semana Siguiente");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSemanaAnterior);
        buttonPanel.add(btnSemanaSiguiente);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cedulaBusqueda = txtCedulaBusqueda.getText();
                actualizarAgenda();
            }
        });

        btnSemanaAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.WEEK_OF_YEAR, -1); // Restar una semana
                actualizarAgenda();
            }
        });

        btnSemanaSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1); // Sumar una semana
                actualizarAgenda();
            }
        });

        actualizarAgenda();
    }

    private void actualizarAgenda() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();

        int numeroSemana = calendar.get(Calendar.WEEK_OF_YEAR);
        int ano = calendar.get(Calendar.YEAR);
        setTitle("Agenda Semanal - Semana del " + obtenerFechaInicioSemana(numeroSemana, ano) + " al " + obtenerFechaFinSemana(numeroSemana, ano));

      
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 1; j < model.getColumnCount(); j++) {
                model.setValueAt("", i, j);
            }
        }

        
        for (CitaMedica cita : Clinica.getInstance().getMisCitas()) {
            Doctor doctor = cita.getDoctor();
            if (doctor != null && doctor.getCedula().equalsIgnoreCase(cedulaBusqueda)) {
                // Verificar si la cita está en la semana actual
                Calendar calCita = Calendar.getInstance();
                calCita.setTime(cita.getFecha());
                int semanaCita = calCita.get(Calendar.WEEK_OF_YEAR);
                int anoCita = calCita.get(Calendar.YEAR);
                if (semanaCita == numeroSemana && anoCita == ano) {
                    String horaCita = cita.getHora();
                    int fila = -1;
                    for (int i = 0; i < model.getRowCount(); i++) {
                        if (model.getValueAt(i, 0).equals(horaCita)) {
                            fila = i;
                            break;
                        }
                    }
                    if (fila != -1) {
                        int diaSemana = obtenerDiaSemana(cita.getFecha()) - 1;
                        String pacienteInfo = "<html>" + cita.getNomPaciente() + "<br>" + cita.getCedPaciente() + "</html>";
                        model.setValueAt(pacienteInfo, fila, diaSemana); 
                    }
                }
            }
        }
    }


    // Método para obtener el día de la semana a partir de una fecha
    private int obtenerDiaSemana(Date fecha) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    private String obtenerFechaInicioSemana(int numeroSemana, int ano) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.WEEK_OF_YEAR, numeroSemana);
        cal.set(Calendar.YEAR, ano);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        return cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
    }

    // Método para obtener la fecha de fin de la semana
    private String obtenerFechaFinSemana(int numeroSemana, int ano) {
        Calendar cal = Calendar.getInstance();
        cal.clear();
        cal.set(Calendar.WEEK_OF_YEAR, numeroSemana);
        cal.set(Calendar.YEAR, ano);
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek() + 6);
        return cal.get(Calendar.DAY_OF_MONTH) + "/" + (cal.get(Calendar.MONTH) + 1) + "/" + cal.get(Calendar.YEAR);
    }

    public void showDialog() {
        actualizarAgenda(); // Mostrar citas iniciales al abrir la ventana
        setVisible(true);
    }
}

