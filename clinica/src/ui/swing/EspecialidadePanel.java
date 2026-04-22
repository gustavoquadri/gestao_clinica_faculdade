package ui.swing;

import controller.EspecialidadeController;
import model.Especialidade;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

public class EspecialidadePanel extends JPanel {

    private final EspecialidadeController controller = new EspecialidadeController();
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField nomeField = new JTextField(25);
    private int selectedId = 0;

    public EspecialidadePanel(){
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        tableModel = new DefaultTableModel(new String[]{"ID", "Nome"}, 0){
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
    }

    private void limparForm(){
        selectedId = 0;
        nomeField.setText("");
        table.clearSelection();
    }

    private void salvar(){
        try{
            if(selectedId == 0){
                controller.criar(nomeField.getText());
            } else {
                controller.atualizar(selectedId, nomeField.getText());
            }
            limparForm();
            recarregar();
        } catch(IllegalArgumentException | IllegalStateException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void remover(){
        if(selectedId == 0){
            JOptionPane.showMessageDialog(this, "Selecione uma especialidade na tabela.");
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
        tableModel.setRowCount(0);
        for(Especialidade e : controller.listarTodos()){
            tableModel.addRow(new Object[]{e.getId(), e.getNome()});
        }
    }
}
