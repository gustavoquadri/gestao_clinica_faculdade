package service;

import dao.interfaces.ConsultaDAO;
import dao.interfaces.PacienteDAO;
import dao.sqlite.ConsultaDAOSQLite;
import dao.sqlite.PacienteDAOSQLite;
import model.Consulta;
import model.Paciente;

import java.util.List;
import java.util.regex.Pattern;

public class PacienteService {

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");
    private static final Pattern DATA_PATTERN = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");

    private final PacienteDAO pacienteDAO;
    private final ConsultaDAO consultaDAO;

    public PacienteService(){
        this(new PacienteDAOSQLite(), new ConsultaDAOSQLite());
    }

    public PacienteService(PacienteDAO pacienteDAO, ConsultaDAO consultaDAO){
        this.pacienteDAO = pacienteDAO;
        this.consultaDAO = consultaDAO;
    }

    public Paciente criar(String nome, String cpf, String dataNascimento){
        validarCampos(nome, cpf, dataNascimento);
        if(existeCpf(cpf, 0)){
            throw new IllegalArgumentException("Já existe um paciente com esse CPF.");
        }
        Paciente paciente = new Paciente(0, nome.trim(), cpf.trim(), dataNascimento.trim());
        pacienteDAO.inserir(paciente);
        return paciente;
    }

    public void atualizar(int id, String nome, String cpf, String dataNascimento){
        Paciente existente = pacienteDAO.buscarPorId(id);
        if(existente == null){
            throw new IllegalArgumentException("Paciente não encontrado: id=" + id);
        }
        validarCampos(nome, cpf, dataNascimento);
        if(existeCpf(cpf, id)){
            throw new IllegalArgumentException("Já existe outro paciente com esse CPF.");
        }
        existente.setNome(nome.trim());
        existente.setCpf(cpf.trim());
        existente.setDatNasc(dataNascimento.trim());
        pacienteDAO.atualizar(existente);
    }

    public void remover(int id){
        if(pacienteDAO.buscarPorId(id) == null){
            throw new IllegalArgumentException("Paciente não encontrado: id=" + id);
        }
        for(Consulta c : consultaDAO.listarTodos()){
            if(c.getPaciente() != null && c.getPaciente().getId() == id){
                throw new IllegalStateException("Paciente possui consultas cadastradas — remoção bloqueada.");
            }
        }
        pacienteDAO.remover(id);
    }

    public Paciente buscarPorId(int id){
        return pacienteDAO.buscarPorId(id);
    }

    public List<Paciente> listarTodos(){
        return pacienteDAO.listarTodos();
    }

    private void validarCampos(String nome, String cpf, String dataNascimento){
        if(nome == null || nome.trim().isEmpty()){
            throw new IllegalArgumentException("Nome do paciente é obrigatório.");
        }
        if(cpf == null || !CPF_PATTERN.matcher(cpf.trim()).matches()){
            throw new IllegalArgumentException("CPF deve estar no formato ###.###.###-##.");
        }
        if(dataNascimento == null || !DATA_PATTERN.matcher(dataNascimento.trim()).matches()){
            throw new IllegalArgumentException("Data de nascimento deve estar no formato dd/MM/yyyy.");
        }
    }

    private boolean existeCpf(String cpf, int idParaIgnorar){
        String alvo = cpf.trim();
        for(Paciente p : pacienteDAO.listarTodos()){
            if(p.getId() != idParaIgnorar && alvo.equals(p.getCpf())){
                return true;
            }
        }
        return false;
    }
}
