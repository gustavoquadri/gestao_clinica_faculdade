package app;

import dao.hsqldb.LogConsultaDAOHSQLDB;
import dao.init.HSQLDBDatabaseInitializer;
import dao.init.SQLiteDatabaseInitializer;
import dao.sqlite.ConsultaDAOSQLite;
import dao.sqlite.EspecialidadeDAOSQLite;
import dao.sqlite.MedicoDAOSQLite;
import dao.sqlite.PacienteDAOSQLite;
import model.*;

import java.util.List;

public class Main {
    public static void main(String[] args){
        inicializarBancos();
        testarDAOs();
    }

    private static void inicializarBancos(){
        System.out.println("=== INICIALIZANDO BANCOS ===");
        SQLiteDatabaseInitializer.initialize();
        HSQLDBDatabaseInitializer.initialize();
    }

    private static void testarDAOs(){
        System.out.println("\n=== TESTANDO DAOS ===");

        EspecialidadeDAOSQLite especialidadeDAO = new EspecialidadeDAOSQLite();
        PacienteDAOSQLite pacienteDAO = new PacienteDAOSQLite();
        MedicoDAOSQLite medicoDAO = new MedicoDAOSQLite();
        ConsultaDAOSQLite consultaDAO = new ConsultaDAOSQLite();
        LogConsultaDAOHSQLDB logDAO = new LogConsultaDAOHSQLDB();

        System.out.println("\n--- ESPECIALIDADES ---");
        List<Especialidade> especialidades = especialidadeDAO.listarTodos();
        for(Especialidade e : especialidades){
            System.out.println(e);
        }

        System.out.println("\n--- PACIENTES ---");
        List<Paciente> pacientes = pacienteDAO.listarTodos();
        for(Paciente p : pacientes){
            System.out.println(p);
        }

        System.out.println("\n--- MÉDICOS ---");
        List<Medico> medicos = medicoDAO.listarTodos();
        for(Medico m : medicos){
            System.out.println(m);
        }

        System.out.println("\n--- CONSULTAS ---");
        List<Consulta> consultas = consultaDAO.listarTodos();
        for(Consulta c : consultas){
            System.out.println(c);
        }

        System.out.println("\n--- LOGS DE CONSULTA ---");
        List<LogConsulta> logs = logDAO.listarTodos();
        for(LogConsulta l : logs){
            System.out.println(l);
        }

        System.out.println("\n=== TESTE DE INSERÇÃO ===");

        Especialidade novaEspecialidade = new Especialidade(0, "Dermatologia");
        especialidadeDAO.inserir(novaEspecialidade);
        System.out.println("Especialidade inserida: " + novaEspecialidade);

        Paciente novoPaciente = new Paciente(0, "Eduardo Bigolin", "444.444.444-44", "12/09/1992");
        pacienteDAO.inserir(novoPaciente);
        System.out.println("Paciente inserido: " + novoPaciente);

        Medico novoMedico = new Medico(0, "Dr. Joao Vitor de Carli", "CRM45678", novaEspecialidade);
        medicoDAO.inserir(novoMedico);
        System.out.println("Médico inserido: " + novoMedico);

        Consulta novaConsulta = new Consulta(0, "22/04/2026", "16:00", novoMedico, novoPaciente, "Consulta dermatológica");
        consultaDAO.inserir(novaConsulta);
        System.out.println("Consulta inserida: " + novaConsulta);

        LogConsulta novoLog = new LogConsulta(
                novaConsulta.getId(),
                novoMedico.getNome(),
                novoPaciente.getNome(),
                novoMedico.getEspecialidade().getNome(),
                novaConsulta.getDatConsult(),
                novaConsulta.getHrConsult()
        );
        logDAO.inserir(novoLog);
        System.out.println("Log inserido: " + novoLog);

        System.out.println("\n--- CONSULTAS APÓS INSERÇÃO ---");
        for(Consulta c : consultaDAO.listarTodos()){
            System.out.println(c);
        }

        System.out.println("\n--- LOGS APÓS INSERÇÃO ---");
        for(LogConsulta l : logDAO.listarTodos()){
            System.out.println(l);
        }
    }
}