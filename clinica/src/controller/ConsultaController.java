package controller;

import model.Consulta;
import model.LogConsulta;
import service.ConsultaService;

import java.util.List;

public class ConsultaController {

    private final ConsultaService service;

    public ConsultaController(){
        this(new ConsultaService());
    }

    public ConsultaController(ConsultaService service){
        this.service = service;
    }

    public Consulta criar(String dataConsulta, String horaConsulta, int medicoId, int pacienteId, String observacao){
        return service.criar(dataConsulta, horaConsulta, medicoId, pacienteId, observacao);
    }

    public void atualizar(int id, String dataConsulta, String horaConsulta, int medicoId, int pacienteId, String observacao){
        service.atualizar(id, dataConsulta, horaConsulta, medicoId, pacienteId, observacao);
    }

    public void remover(int id){
        service.remover(id);
    }

    public Consulta buscarPorId(int id){
        return service.buscarPorId(id);
    }

    public List<Consulta> listarTodos(){
        return service.listarTodos();
    }

    public List<LogConsulta> listarLogs(){
        return service.listarLogs();
    }
}
