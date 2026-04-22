package service;

import dao.interfaces.EspecialidadeDAO;
import dao.interfaces.MedicoDAO;
import dao.sqlite.EspecialidadeDAOSQLite;
import dao.sqlite.MedicoDAOSQLite;
import model.Especialidade;
import model.Medico;

import java.util.List;

public class EspecialidadeService {

    private final EspecialidadeDAO especialidadeDAO;
    private final MedicoDAO medicoDAO;

    public EspecialidadeService(){
        this(new EspecialidadeDAOSQLite(), new MedicoDAOSQLite());
    }

    public EspecialidadeService(EspecialidadeDAO especialidadeDAO, MedicoDAO medicoDAO){
        this.especialidadeDAO = especialidadeDAO;
        this.medicoDAO = medicoDAO;
    }

    public Especialidade criar(String nome){
        validarNome(nome);
        if(existePorNome(nome, 0)){
            throw new IllegalArgumentException("Já existe uma especialidade com esse nome.");
        }
        Especialidade especialidade = new Especialidade(0, nome.trim());
        especialidadeDAO.inserir(especialidade);
        return especialidade;
    }

    public void atualizar(int id, String nome){
        Especialidade existente = especialidadeDAO.buscarPorId(id);
        if(existente == null){
            throw new IllegalArgumentException("Especialidade não encontrada: id=" + id);
        }
        validarNome(nome);
        if(existePorNome(nome, id)){
            throw new IllegalArgumentException("Já existe outra especialidade com esse nome.");
        }
        existente.setNome(nome.trim());
        especialidadeDAO.atualizar(existente);
    }

    public void remover(int id){
        if(especialidadeDAO.buscarPorId(id) == null){
            throw new IllegalArgumentException("Especialidade não encontrada: id=" + id);
        }
        for(Medico m : medicoDAO.listarTodos()){
            if(m.getEspecialidade() != null && m.getEspecialidade().getId() == id){
                throw new IllegalStateException("Especialidade em uso por ao menos um médico — remoção bloqueada.");
            }
        }
        especialidadeDAO.remover(id);
    }

    public Especialidade buscarPorId(int id){
        return especialidadeDAO.buscarPorId(id);
    }

    public List<Especialidade> listarTodos(){
        return especialidadeDAO.listarTodos();
    }

    private void validarNome(String nome){
        if(nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Nome da especialidade é obrigatório.");
        }
        if(nome.trim().length() > 100){
            throw new IllegalArgumentException("Nome da especialidade deve ter no máximo 100 caracteres.");
        }
    }

    private boolean existePorNome(String nome, int idParaIgnorar){
        String alvo = nome.trim().toLowerCase();
        for(Especialidade e : especialidadeDAO.listarTodos()){
            if(e.getId() != idParaIgnorar && e.getNome().toLowerCase().equals(alvo)){
                return true;
            }
        }
        return false;
    }
}
