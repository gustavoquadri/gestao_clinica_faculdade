package controller;

import model.Especialidade;
import service.EspecialidadeService;

import java.util.List;

public class EspecialidadeController {

    private final EspecialidadeService service;

    public EspecialidadeController(){
        this(new EspecialidadeService());
    }

    public EspecialidadeController(EspecialidadeService service){
        this.service = service;
    }

    public Especialidade criar(String nome){
        return service.criar(nome);
    }

    public void atualizar(int id, String nome){
        service.atualizar(id, nome);
    }

    public void remover(int id){
        service.remover(id);
    }

    public Especialidade buscarPorId(int id){
        return service.buscarPorId(id);
    }

    public List<Especialidade> listarTodos(){
        return service.listarTodos();
    }
}
