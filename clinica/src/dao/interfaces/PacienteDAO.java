package dao.interfaces;

import model.Paciente;

import java.util.List;

public interface PacienteDAO {
    void inserir(Paciente paciente);
    List<Paciente> listarTodos();
    Paciente buscarPorId(int id);
}