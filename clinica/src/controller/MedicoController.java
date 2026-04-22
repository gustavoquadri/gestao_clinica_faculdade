package controller;

import model.Medico;
import service.MedicoService;

import java.util.List;

public class MedicoController {

    private final MedicoService service;

    public MedicoController(){
        this(new MedicoService());
    }

    public MedicoController(MedicoService service){
        this.service = service;
    }

    public Medico criar(String nome, String crm, int especialidadeId){
        return service.criar(nome, crm, especialidadeId);
    }

    public void atualizar(int id, String nome, String crm, int especialidadeId){
        service.atualizar(id, nome, crm, especialidadeId);
    }

    public void remover(int id){
        service.remover(id);
    }

    public Medico buscarPorId(int id){
        return service.buscarPorId(id);
    }

    public List<Medico> listarTodos(){
        return service.listarTodos();
    }
}
