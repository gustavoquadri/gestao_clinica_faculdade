package dao.interfaces;

import model.Consulta;

import java.util.List;

public interface ConsultaDAO {
    void inserir(Consulta consulta);
    List<Consulta> listarTodos();
}