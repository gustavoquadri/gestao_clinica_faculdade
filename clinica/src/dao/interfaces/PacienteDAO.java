package dao.interfaces;

import model.Paciente;

import java.util.List;

public interface PacienteDAO {
    void inserir(Paciente paciente);
    void atualizar(Paciente paciente);
    void remover(int id);
    Paciente buscarPorId(int id);
    List<Paciente> listarTodos();
}
