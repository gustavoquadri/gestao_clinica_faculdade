package dao.interfaces;

import model.Medico;

import java.util.List;

public interface MedicoDAO {
    void inserir(Medico medico);
    void atualizar(Medico medico);
    void remover(int id);
    Medico buscarPorId(int id);
    List<Medico> listarTodos();
}
