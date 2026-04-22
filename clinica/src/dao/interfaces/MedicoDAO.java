package dao.interfaces;

import model.Medico;

import java.util.List;

public interface MedicoDAO {
    void inserir(Medico medico);
    List<Medico> listarTodos();
    Medico buscarPorId(int id);
}