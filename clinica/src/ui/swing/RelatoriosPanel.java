package ui.swing;

import controller.ConsultaController;
import model.Consulta;
import model.LogConsulta;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class RelatoriosPanel extends JPanel {

    private final ConsultaController controller = new ConsultaController();
    private final DefaultTableModel consultasModel;
    private final DefaultTableModel logsModel;

    public RelatoriosPanel(){
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        consultasModel = new DefaultTableModel(
                new String[]{"ID", "Data", "Hora", "Médico", "Especialidade", "Paciente", "Observação"}, 0){
            @Override public boolean isCellEditable(int r, int c){ return false; }
        };
        JTable consultasTable = new JTable(consultasModel);

        logsModel = new DefaultTableModel(
                new String[]{"ID", "Consulta", "Médico", "Paciente", "Especialidade", "Data", "Hora"}, 0){
            @Override public boolean isCellEditable(int r, int c){ return false; }
        };
        JTable logsTable = new JTable(logsModel);

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(new JLabel("Consultas (SQLite, com dados relacionados via JOIN)"), BorderLayout.NORTH);
        topo.add(new JScrollPane(consultasTable), BorderLayout.CENTER);

        JPanel baixo = new JPanel(new BorderLayout());
        baixo.add(new JLabel("Logs de Consultas (HSQLDB)"), BorderLayout.NORTH);
        baixo.add(new JScrollPane(logsTable), BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topo, baixo);
        split.setResizeWeight(0.5);
        add(split, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton recarregarBtn = new JButton("Recarregar");
        recarregarBtn.addActionListener(e -> recarregar());
        botoes.add(recarregarBtn);
        add(botoes, BorderLayout.SOUTH);

        recarregar();
    }

    private void recarregar(){
        consultasModel.setRowCount(0);
        for(Consulta c : controller.listarTodos()){
            String medico = c.getMedico() != null ? c.getMedico().getNome() : "";
            String especialidade = (c.getMedico() != null && c.getMedico().getEspecialidade() != null)
                    ? c.getMedico().getEspecialidade().getNome() : "";
            String paciente = c.getPaciente() != null ? c.getPaciente().getNome() : "";
            consultasModel.addRow(new Object[]{
                    c.getId(), c.getDatConsult(), c.getHrConsult(),
                    medico, especialidade, paciente, c.getObservacao()
            });
        }

        logsModel.setRowCount(0);
        for(LogConsulta l : controller.listarLogs()){
            logsModel.addRow(new Object[]{
                    l.getId(), l.getConsultaId(),
                    l.getMedicoNome(), l.getPacienteNome(),
                    l.getEspecialidadeNome(), l.getDataConsulta(), l.getHoraConsulta()
            });
        }
    }
}
