package dao.interfaces;

import model.Consulta;

import java.util.List;

public interface ConsultaDAO {
    void inserir(Consulta consulta);
    void atualizar(Consulta consulta);
    void remover(int id);
    Consulta buscarPorId(int id);
    List<Consulta> listarTodos();
}
