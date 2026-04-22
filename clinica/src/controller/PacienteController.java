package controller;

import model.Paciente;
import service.PacienteService;

import java.util.List;

public class PacienteController {

    private final PacienteService service;

    public PacienteController(){
        this(new PacienteService());
    }

    public PacienteController(PacienteService service){
        this.service = service;
    }

    public Paciente criar(String nome, String cpf, String dataNascimento){
        return service.criar(nome, cpf, dataNascimento);
    }

    public void atualizar(int id, String nome, String cpf, String dataNascimento){
        service.atualizar(id, nome, cpf, dataNascimento);
    }

    public void remover(int id){
        service.remover(id);
    }

    public Paciente buscarPorId(int id){
        return service.buscarPorId(id);
    }

    public List<Paciente> listarTodos(){
        return service.listarTodos();
    }
}
