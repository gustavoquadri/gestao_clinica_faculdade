package dao.interfaces;

import model.LogConsulta;

import java.util.List;

public interface LogConsultaDAO {
    void inserir(LogConsulta logConsulta);
    List<LogConsulta> listarTodos();
}