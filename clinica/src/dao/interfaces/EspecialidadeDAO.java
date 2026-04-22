package dao.interfaces;

import model.Especialidade;

import java.util.List;

public interface EspecialidadeDAO {
    void inserir(Especialidade especialidade);
    List<Especialidade> listarTodos();
    Especialidade buscarPorId(int id);
}