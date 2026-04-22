package ui.swing;

import controller.EspecialidadeController;
import controller.MedicoController;
import model.Especialidade;
import model.Medico;

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

public class MedicoPanel extends JPanel {

    private final MedicoController controller = new MedicoController();
    private final EspecialidadeController especialidadeController = new EspecialidadeController();

    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField nomeField = new JTextField(20);
    private final JTextField crmField = new JTextField(10);
    private final JComboBox<EspecialidadeItem> especialidadeCombo = new JComboBox<>();
    private int selectedId = 0;

    public MedicoPanel(){
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        tableModel = new DefaultTableModel(new String[]{"ID", "Nome", "CRM", "Especialidade"}, 0){
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
        gbc.gridx = 0; gbc.gridy = 0; form.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1; form.add(nomeField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; form.add(new JLabel("CRM:"), gbc);
        gbc.gridx = 1; form.add(crmField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; form.add(new JLabel("Especialidade:"), gbc);
        gbc.gridx = 1; form.add(especialidadeCombo, gbc);

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
        nomeField.setText((String) tableModel.getValueAt(row, 1));
        crmField.setText((String) tableModel.getValueAt(row, 2));
        String especialidadeNome = (String) tableModel.getValueAt(row, 3);
        for(int i = 0; i < especialidadeCombo.getItemCount(); i++){
            if(especialidadeCombo.getItemAt(i).nome.equals(especialidadeNome)){
                especialidadeCombo.setSelectedIndex(i);
                break;
            }
        }
    }

    private void limparForm(){
        selectedId = 0;
        nomeField.setText("");
        crmField.setText("");
        if(especialidadeCombo.getItemCount() > 0) especialidadeCombo.setSelectedIndex(0);
        table.clearSelection();
    }

    private void salvar(){
        EspecialidadeItem item = (EspecialidadeItem) especialidadeCombo.getSelectedItem();
        if(item == null){
            JOptionPane.showMessageDialog(this, "Cadastre uma especialidade antes.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            if(selectedId == 0){
                controller.criar(nomeField.getText(), crmField.getText(), item.id);
            } else {
                controller.atualizar(selectedId, nomeField.getText(), crmField.getText(), item.id);
            }
            limparForm();
            recarregar();
        } catch(IllegalArgumentException | IllegalStateException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void remover(){
        if(selectedId == 0){
            JOptionPane.showMessageDialog(this, "Selecione um médico na tabela.");
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
        DefaultComboBoxModel<EspecialidadeItem> comboModel = new DefaultComboBoxModel<>();
        for(Especialidade e : especialidadeController.listarTodos()){
            comboModel.addElement(new EspecialidadeItem(e.getId(), e.getNome()));
        }
        especialidadeCombo.setModel(comboModel);

        tableModel.setRowCount(0);
        for(Medico m : controller.listarTodos()){
            String esp = m.getEspecialidade() != null ? m.getEspecialidade().getNome() : "";
            tableModel.addRow(new Object[]{m.getId(), m.getNome(), m.getCrm(), esp});
        }
    }

    private static class EspecialidadeItem {
        final int id;
        final String nome;
        EspecialidadeItem(int id, String nome){ this.id = id; this.nome = nome; }
        @Override public String toString(){ return nome; }
    }
}
