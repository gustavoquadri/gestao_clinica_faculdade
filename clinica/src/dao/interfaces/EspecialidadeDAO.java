package dao.interfaces;

import model.Especialidade;

import java.util.List;

public interface EspecialidadeDAO {
    void inserir(Especialidade especialidade);
    void atualizar(Especialidade especialidade);
    void remover(int id);
    Especialidade buscarPorId(int id);
    List<Especialidade> listarTodos();
}
