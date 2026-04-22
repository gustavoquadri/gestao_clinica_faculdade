package service;

import dao.interfaces.ConsultaDAO;
import dao.interfaces.EspecialidadeDAO;
import dao.interfaces.MedicoDAO;
import dao.sqlite.ConsultaDAOSQLite;
import dao.sqlite.EspecialidadeDAOSQLite;
import dao.sqlite.MedicoDAOSQLite;
import model.Consulta;
import model.Especialidade;
import model.Medico;

import java.util.List;

public class MedicoService {

    private final MedicoDAO medicoDAO;
    private final EspecialidadeDAO especialidadeDAO;
    private final ConsultaDAO consultaDAO;

    public MedicoService(){
        this(new MedicoDAOSQLite(), new EspecialidadeDAOSQLite(), new ConsultaDAOSQLite());
    }

    public MedicoService(MedicoDAO medicoDAO, EspecialidadeDAO especialidadeDAO, ConsultaDAO consultaDAO){
        this.medicoDAO = medicoDAO;
        this.especialidadeDAO = especialidadeDAO;
        this.consultaDAO = consultaDAO;
    }

    public Medico criar(String nome, String crm, int especialidadeId){
        validarCampos(nome, crm);
        Especialidade especialidade = especialidadeDAO.buscarPorId(especialidadeId);
        if(especialidade == null){
            throw new IllegalArgumentException("Especialidade não encontrada: id=" + especialidadeId);
        }
        if(existeCrm(crm, 0)){
            throw new IllegalArgumentException("Já existe um médico com esse CRM.");
        }
        Medico medico = new Medico(0, nome.trim(), crm.trim(), especialidade);
        medicoDAO.inserir(medico);
        return medico;
    }

    public void atualizar(int id, String nome, String crm, int especialidadeId){
        Medico existente = medicoDAO.buscarPorId(id);
        if(existente == null){
            throw new IllegalArgumentException("Médico não encontrado: id=" + id);
        }
        validarCampos(nome, crm);
        Especialidade especialidade = especialidadeDAO.buscarPorId(especialidadeId);
        if(especialidade == null){
            throw new IllegalArgumentException("Especialidade não encontrada: id=" + especialidadeId);
        }
        if(existeCrm(crm, id)){
            throw new IllegalArgumentException("Já existe outro médico com esse CRM.");
        }
        existente.setNome(nome.trim());
        existente.setCrm(crm.trim());
        existente.setEspecialidade(especialidade);
        medicoDAO.atualizar(existente);
    }

    public void remover(int id){
        if(medicoDAO.buscarPorId(id) == null){
            throw new IllegalArgumentException("Médico não encontrado: id=" + id);
        }
        for(Consulta c : consultaDAO.listarTodos()){
            if(c.getMedico() != null && c.getMedico().getId() == id){
                throw new IllegalStateException("Médico possui consultas cadastradas — remoção bloqueada.");
            }
        }
        medicoDAO.remover(id);
    }

    public Medico buscarPorId(int id){
        return medicoDAO.buscarPorId(id);
    }

    public List<Medico> listarTodos(){
        return medicoDAO.listarTodos();
    }

    private void validarCampos(String nome, String crm){
        if(nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Nome do médico é obrigatório.");
        }
        if(crm == null || crm.trim().isEmpty()){
            throw new IllegalArgumentException("CRM é obrigatório.");
        }
        if(crm.trim().length() > 20){
            throw new IllegalArgumentException("CRM deve ter no máximo 20 caracteres.");
        }
    }

    private boolean existeCrm(String crm, int idParaIgnorar){
        String alvo = crm.trim();
        for(Medico m : medicoDAO.listarTodos()){
            if(m.getId() != idParaIgnorar && alvo.equals(m.getCrm())){
                return true;
            }
        }
        return false;
    }
}
