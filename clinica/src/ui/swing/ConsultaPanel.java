package ui.swing;

import controller.ConsultaController;
import controller.MedicoController;
import controller.PacienteController;
import model.Consulta;
import model.Medico;
import model.Paciente;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class ConsultaPanel extends JPanel {

    private final ConsultaController controller = new ConsultaController();
    private final MedicoController medicoController = new MedicoController();
    private final PacienteController pacienteController = new PacienteController();

    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField dataField = new JTextField(10);
    private final JTextField horaField = new JTextField(5);
    private final JComboBox<MedicoItem> medicoCombo = new JComboBox<>();
    private final JComboBox<PacienteItem> pacienteCombo = new JComboBox<>();
    private final JTextField observacaoField = new JTextField(25);
    private int selectedId = 0;

    public ConsultaPanel(){
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        tableModel = new DefaultTableModel(new String[]{"ID", "Data", "Hora", "Médico", "Paciente", "Observação"}, 0){
            @Override public boolean isCellEditable(int r, int c){ return false; }
        };
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> onSelect());
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createTitledBorder("Cadastro"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; gbc.gridy = 0; form.add(new JLabel("Data (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1; form.add(dataField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; form.add(new JLabel("Hora (HH:mm):"), gbc);
        gbc.gridx = 1; form.add(horaField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; form.add(new JLabel("Médico:"), gbc);
        gbc.gridx = 1; form.add(medicoCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 3; form.add(new JLabel("Paciente:"), gbc);
        gbc.gridx = 1; form.add(pacienteCombo, gbc);
        gbc.gridx = 0; gbc.gridy = 4; form.add(new JLabel("Observação:"), gbc);
        gbc.gridx = 1; form.add(observacaoField, gbc);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton novoBtn = new JButton("Novo");
        JButton salvarBtn = new JButton("Salvar");
        JButton removerBtn = new JButton("Remover");
        JButton recarregarBtn = new JButton("Recarregar");
        novoBtn.addActionListener(e -> limparForm());
        salvarBtn.addActionListener(e -> salvar());
        removerBtn.addActionListener(e -> remover());
        recarregarBtn.addActionListener(e -> recarregar());
        buttons.add(novoBtn);
        buttons.add(salvarBtn);
        buttons.add(removerBtn);
        buttons.add(recarregarBtn);

        JPanel south = new JPanel(new BorderLayout());
        south.add(form, BorderLayout.CENTER);
        south.add(buttons, BorderLayout.SOUTH);
        add(south, BorderLayout.SOUTH);

        recarregar();
    }

    private void onSelect(){
        int row = table.getSelectedRow();
        if(row < 0) return;
        selectedId = (int) tableModel.getValueAt(row, 0);
        dataField.setText((String) tableModel.getValueAt(row, 1));
        horaField.setText((String) tableModel.getValueAt(row, 2));
        selecionarComboPorNome(medicoCombo, (String) tableModel.getValueAt(row, 3));
        selecionarComboPorNome(pacienteCombo, (String) tableModel.getValueAt(row, 4));
        Object obs = tableModel.getValueAt(row, 5);
        observacaoField.setText(obs != null ? obs.toString() : "");
    }

    private void limparForm(){
        selectedId = 0;
        dataField.setText("");
        horaField.setText("");
        if(medicoCombo.getItemCount() > 0) medicoCombo.setSelectedIndex(0);
        if(pacienteCombo.getItemCount() > 0) pacienteCombo.setSelectedIndex(0);
        observacaoField.setText("");
        table.clearSelection();
    }

    private void salvar(){
        MedicoItem medico = (MedicoItem) medicoCombo.getSelectedItem();
        PacienteItem paciente = (PacienteItem) pacienteCombo.getSelectedItem();
        if(medico == null || paciente == null){
            JOptionPane.showMessageDialog(this, "Cadastre um médico e um paciente antes.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            if(selectedId == 0){
                controller.criar(dataField.getText(), horaField.getText(), medico.id, paciente.id, observacaoField.getText());
            } else {
                controller.atualizar(selectedId, dataField.getText(), horaField.getText(), medico.id, paciente.id, observacaoField.getText());
            }
            limparForm();
            recarregar();
        } catch(IllegalArgumentException | IllegalStateException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void remover(){
        if(selectedId == 0){
            JOptionPane.showMessageDialog(this, "Selecione uma consulta na tabela.");
            return;
        }
        try{
            controller.remover(selectedId);
            limparForm();
            recarregar();
        } catch(IllegalArgumentException | IllegalStateException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void recarregar(){
        DefaultComboBoxModel<MedicoItem> medicoModel = new DefaultComboBoxModel<>();
        for(Medico m : medicoController.listarTodos()){
            medicoModel.addElement(new MedicoItem(m.getId(), m.getNome()));
        }
        medicoCombo.setModel(medicoModel);

        DefaultComboBoxModel<PacienteItem> pacienteModel = new DefaultComboBoxModel<>();
        for(Paciente p : pacienteController.listarTodos()){
            pacienteModel.addElement(new PacienteItem(p.getId(), p.getNome()));
        }
        pacienteCombo.setModel(pacienteModel);

        tableModel.setRowCount(0);
        for(Consulta c : controller.listarTodos()){
            String medico = c.getMedico() != null ? c.getMedico().getNome() : "";
            String paciente = c.getPaciente() != null ? c.getPaciente().getNome() : "";
            tableModel.addRow(new Object[]{c.getId(), c.getDatConsult(), c.getHrConsult(), medico, paciente, c.getObservacao()});
        }
    }

    private <T> void selecionarComboPorNome(JComboBox<T> combo, String nome){
        if(nome == null) return;
        for(int i = 0; i < combo.getItemCount(); i++){
            if(combo.getItemAt(i).toString().equals(nome)){
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    private static class MedicoItem {
        final int id;
        final String nome;
        MedicoItem(int id, String nome){ this.id = id; this.nome = nome; }
        @Override public String toString(){ return nome; }
    }

    private static class PacienteItem {
        final int id;
        final String nome;
        PacienteItem(int id, String nome){ this.id = id; this.nome = nome; }
        @Override public String toString(){ return nome; }
    }
}
