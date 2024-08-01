package Visual;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import logico.CitaMedica;
import logico.Clinica;
import net.code.java.sql.JavaConnect2SQL;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AgendaSemanal extends JDialog {
    private JTable table;
    private Calendar calendar;

    public AgendaSemanal() {
        calendar = Calendar.getInstance();
		setIconImage(Toolkit.getDefaultToolkit().getImage("calendario.png"));

        setTitle("Agenda Semanal");
        setSize(833, 718);
        setLocationRelativeTo(null);
        setModal(true); 

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

      
        JButton btnSemanaAnterior = new JButton("Semana Anterior");
        JButton btnSemanaSiguiente = new JButton("Semana Siguiente");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSemanaAnterior);
        buttonPanel.add(btnSemanaSiguiente);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        btnSemanaAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.WEEK_OF_YEAR, -1); 
                actualizarAgenda();
            }
        });

        btnSemanaSiguiente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.WEEK_OF_YEAR, 1); 
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

     
        ArrayList<String>[][] citas = new ArrayList[model.getRowCount()][model.getColumnCount()];
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                citas[i][j] = new ArrayList<>(); // Inicializar cada lista dentro de la matriz
            }
        }


      
        for (CitaMedica cita : JavaConnect2SQL.getInstace().getMisCitas("")) {
            Calendar calCita = Calendar.getInstance();
            calCita.setTime(cita.getFecha());
            int semanaCita = calCita.get(Calendar.WEEK_OF_YEAR);
            int anoCita = calCita.get(Calendar.YEAR);
            if (semanaCita == numeroSemana && anoCita == ano) {
                String horaCita = cita.getHora();
                System.out.println("Hora: " +horaCita);
                int fila = -1;
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(horaCita)) {
                        fila = i;
                        System.out.println("Fila: " +horaCita);
                        break;
                    }
                }
                int diaSemana = obtenerDiaSemana(cita.getFecha()) - 1;
                System.out.println("dia : " +diaSemana);
                String citaInfo = cita.getNomPaciente() + " - " + cita.getCedPaciente() + " (Dr. " + cita.getDoctor().getNombre() + ")";
                citas[fila][diaSemana].add(citaInfo);
                
            }
        }


        // Mostrar las citas médicas almacenadas en la matriz en la tabla
        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 1; j < model.getColumnCount(); j++) {
                ArrayList<String> citasDiaHora = citas[i][j];
                if (!citasDiaHora.isEmpty()) {
                    String pacienteInfo = "<html>";
                    for (String citaInfo : citasDiaHora) {
                        pacienteInfo += citaInfo + "<br>";
                    }
                    pacienteInfo += "</html>";
                    table.getColumnModel().getColumn(j).setCellRenderer(new MultiLineTableCellRenderer());
                    model.setValueAt(pacienteInfo, i, j);
                }
            }
        }
        

    }

    private class MultiLineTableCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel(value == null ? "" : value.toString());
            label.setToolTipText(value == null ? "" : value.toString());
            label.setVerticalAlignment(SwingConstants.TOP);
            return label;
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

