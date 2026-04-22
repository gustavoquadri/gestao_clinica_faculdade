package ui.swing;

import dao.init.HSQLDBDatabaseInitializer;
import dao.init.SQLiteDatabaseInitializer;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class SwingApp {

    public static void main(String[] args){
        System.out.println("=== INICIALIZANDO BANCOS ===");
        SQLiteDatabaseInitializer.initialize();
        HSQLDBDatabaseInitializer.initialize();
        SwingUtilities.invokeLater(SwingApp::iniciarUI);
    }

    private static void iniciarUI(){
        JFrame frame = new JFrame("Clínica - Gestão");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Especialidades", new EspecialidadePanel());
        tabs.addTab("Pacientes", new PacientePanel());
        tabs.addTab("Médicos", new MedicoPanel());
        tabs.addTab("Consultas", new ConsultaPanel());
        tabs.addTab("Relatórios", new RelatoriosPanel());

        frame.setContentPane(tabs);
        frame.setVisible(true);
    }
}
