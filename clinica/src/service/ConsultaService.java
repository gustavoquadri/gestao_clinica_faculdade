package service;

import dao.hsqldb.LogConsultaDAOHSQLDB;
import dao.interfaces.ConsultaDAO;
import dao.interfaces.LogConsultaDAO;
import dao.interfaces.MedicoDAO;
import dao.interfaces.PacienteDAO;
import dao.sqlite.ConsultaDAOSQLite;
import dao.sqlite.MedicoDAOSQLite;
import dao.sqlite.PacienteDAOSQLite;
import model.Consulta;
import model.LogConsulta;
import model.Medico;
import model.Paciente;

import java.util.List;
import java.util.regex.Pattern;

public class ConsultaService {

    private static final Pattern DATA_PATTERN = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
    private static final Pattern HORA_PATTERN = Pattern.compile("\\d{2}:\\d{2}");

    private final ConsultaDAO consultaDAO;
    private final MedicoDAO medicoDAO;
    private final PacienteDAO pacienteDAO;
    private final LogConsultaDAO logConsultaDAO;

    public ConsultaService(){
        this(new ConsultaDAOSQLite(), new MedicoDAOSQLite(), new PacienteDAOSQLite(), new LogConsultaDAOHSQLDB());
    }

    public ConsultaService(ConsultaDAO consultaDAO, MedicoDAO medicoDAO, PacienteDAO pacienteDAO, LogConsultaDAO logConsultaDAO){
        this.consultaDAO = consultaDAO;
        this.medicoDAO = medicoDAO;
        this.pacienteDAO = pacienteDAO;
        this.logConsultaDAO = logConsultaDAO;
    }

    public Consulta criar(String dataConsulta, String horaConsulta, int medicoId, int pacienteId, String observacao){
        validarDataHora(dataConsulta, horaConsulta);
        Medico medico = medicoDAO.buscarPorId(medicoId);
        if(medico == null){
            throw new IllegalArgumentException("Médico não encontrado: id=" + medicoId);
        }
        Paciente paciente = pacienteDAO.buscarPorId(pacienteId);
        if(paciente == null){
            throw new IllegalArgumentException("Paciente não encontrado: id=" + pacienteId);
        }
        if(temConflitoDeAgenda(medicoId, dataConsulta, horaConsulta, 0)){
            throw new IllegalStateException("Médico já tem consulta marcada para esse dia e horário.");
        }

        Consulta consulta = new Consulta(0, dataConsulta.trim(), horaConsulta.trim(), medico, paciente, observacao);
        consultaDAO.inserir(consulta);

        LogConsulta log = new LogConsulta(
                consulta.getId(),
                medico.getNome(),
                paciente.getNome(),
                medico.getEspecialidade() != null ? medico.getEspecialidade().getNome() : "",
                consulta.getDatConsult(),
                consulta.getHrConsult()
        );
        logConsultaDAO.inserir(log);
        return consulta;
    }

    public void atualizar(int id, String dataConsulta, String horaConsulta, int medicoId, int pacienteId, String observacao){
        Consulta existente = consultaDAO.buscarPorId(id);
        if(existente == null){
            throw new IllegalArgumentException("Consulta não encontrada: id=" + id);
        }
        validarDataHora(dataConsulta, horaConsulta);
        Medico medico = medicoDAO.buscarPorId(medicoId);
        if(medico == null){
            throw new IllegalArgumentException("Médico não encontrado: id=" + medicoId);
        }
        Paciente paciente = pacienteDAO.buscarPorId(pacienteId);
        if(paciente == null){
            throw new IllegalArgumentException("Paciente não encontrado: id=" + pacienteId);
        }
        if(temConflitoDeAgenda(medicoId, dataConsulta, horaConsulta, id)){
            throw new IllegalStateException("Médico já tem outra consulta marcada para esse dia e horário.");
        }

        existente.setDatConsult(dataConsulta.trim());
        existente.setHrConsult(horaConsulta.trim());
        existente.setMedico(medico);
        existente.setPaciente(paciente);
        existente.setObservacao(observacao);
        consultaDAO.atualizar(existente);
    }

    public void remover(int id){
        if(consultaDAO.buscarPorId(id) == null){
            throw new IllegalArgumentException("Consulta não encontrada: id=" + id);
        }
        consultaDAO.remover(id);
    }

    public Consulta buscarPorId(int id){
        return consultaDAO.buscarPorId(id);
    }

    public List<Consulta> listarTodos(){
        return consultaDAO.listarTodos();
    }

    public List<LogConsulta> listarLogs(){
        return logConsultaDAO.listarTodos();
    }

    private void validarDataHora(String dataConsulta, String horaConsulta){
        if(dataConsulta == null || !DATA_PATTERN.matcher(dataConsulta.trim()).matches()){
            throw new IllegalArgumentException("Data da consulta deve estar no formato dd/MM/yyyy.");
        }
        if(horaConsulta == null || !HORA_PATTERN.matcher(horaConsulta.trim()).matches()){
            throw new IllegalArgumentException("Hora da consulta deve estar no formato HH:mm.");
        }
    }

    private boolean temConflitoDeAgenda(int medicoId, String data, String hora, int idParaIgnorar){
        String d = data.trim();
        String h = hora.trim();
        for(Consulta c : consultaDAO.listarTodos()){
            if(c.getId() == idParaIgnorar) continue;
            if(c.getMedico() != null && c.getMedico().getId() == medicoId
                    && d.equals(c.getDatConsult()) && h.equals(c.getHrConsult())){
                return true;
            }
        }
        return false;
    }
}
