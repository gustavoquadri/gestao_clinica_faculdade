package ui.console;

import controller.ConsultaController;
import controller.EspecialidadeController;
import controller.MedicoController;
import controller.PacienteController;
import dao.init.HSQLDBDatabaseInitializer;
import dao.init.SQLiteDatabaseInitializer;
import model.Consulta;
import model.Especialidade;
import model.LogConsulta;
import model.Medico;
import model.Paciente;

import java.util.List;
import java.util.Scanner;

public class ConsoleApp {

    private final Scanner scanner = new Scanner(System.in);
    private final EspecialidadeController especialidadeController = new EspecialidadeController();
    private final PacienteController pacienteController = new PacienteController();
    private final MedicoController medicoController = new MedicoController();
    private final ConsultaController consultaController = new ConsultaController();

    public static void main(String[] args){
        System.out.println("=== INICIALIZANDO BANCOS ===");
        SQLiteDatabaseInitializer.initialize();
        HSQLDBDatabaseInitializer.initialize();
        new ConsoleApp().run();
    }

    public void run(){
        boolean sair = false;
        while(!sair){
            System.out.println();
            System.out.println("=== CLÍNICA - MENU PRINCIPAL ===");
            System.out.println("1. Especialidades");
            System.out.println("2. Pacientes");
            System.out.println("3. Médicos");
            System.out.println("4. Consultas");
            System.out.println("5. Logs de consultas (HSQLDB)");
            System.out.println("0. Sair");
            switch(lerLinha("> ")){
                case "1": menuEspecialidades(); break;
                case "2": menuPacientes(); break;
                case "3": menuMedicos(); break;
                case "4": menuConsultas(); break;
                case "5": listarLogs(); break;
                case "0": sair = true; break;
                default: System.out.println("Opção inválida.");
            }
        }
        System.out.println("Até logo!");
    }

    private void menuEspecialidades(){
        while(true){
            System.out.println();
            System.out.println("--- ESPECIALIDADES ---");
            System.out.println("1. Listar");
            System.out.println("2. Buscar por id");
            System.out.println("3. Inserir");
            System.out.println("4. Atualizar");
            System.out.println("5. Remover");
            System.out.println("0. Voltar");
            switch(lerLinha("> ")){
                case "1":
                    for(Especialidade e : especialidadeController.listarTodos()) System.out.println(e);
                    break;
                case "2": {
                    int id = lerInt("ID: ");
                    Especialidade e = especialidadeController.buscarPorId(id);
                    System.out.println(e != null ? e : "Não encontrada.");
                    break;
                }
                case "3":
                    executar(() -> {
                        String nome = lerLinha("Nome: ");
                        Especialidade e = especialidadeController.criar(nome);
                        System.out.println("Inserida: " + e);
                    });
                    break;
                case "4":
                    executar(() -> {
                        int id = lerInt("ID: ");
                        String nome = lerLinha("Novo nome: ");
                        especialidadeController.atualizar(id, nome);
                        System.out.println("Atualizada.");
                    });
                    break;
                case "5":
                    executar(() -> {
                        int id = lerInt("ID: ");
                        especialidadeController.remover(id);
                        System.out.println("Removida.");
                    });
                    break;
                case "0": return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private void menuPacientes(){
        while(true){
            System.out.println();
            System.out.println("--- PACIENTES ---");
            System.out.println("1. Listar");
            System.out.println("2. Buscar por id");
            System.out.println("3. Inserir");
            System.out.println("4. Atualizar");
            System.out.println("5. Remover");
            System.out.println("0. Voltar");
            switch(lerLinha("> ")){
                case "1":
                    for(Paciente p : pacienteController.listarTodos()) System.out.println(p);
                    break;
                case "2": {
                    int id = lerInt("ID: ");
                    Paciente p = pacienteController.buscarPorId(id);
                    System.out.println(p != null ? p : "Não encontrado.");
                    break;
                }
                case "3":
                    executar(() -> {
                        String nome = lerLinha("Nome: ");
                        String cpf = lerLinha("CPF (###.###.###-##): ");
                        String dt = lerLinha("Data nascimento (dd/MM/yyyy): ");
                        Paciente p = pacienteController.criar(nome, cpf, dt);
                        System.out.println("Inserido: " + p);
                    });
                    break;
                case "4":
                    executar(() -> {
                        int id = lerInt("ID: ");
                        String nome = lerLinha("Nome: ");
                        String cpf = lerLinha("CPF: ");
                        String dt = lerLinha("Data nascimento: ");
                        pacienteController.atualizar(id, nome, cpf, dt);
                        System.out.println("Atualizado.");
                    });
                    break;
                case "5":
                    executar(() -> {
                        int id = lerInt("ID: ");
                        pacienteController.remover(id);
                        System.out.println("Removido.");
                    });
                    break;
                case "0": return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private void menuMedicos(){
        while(true){
            System.out.println();
            System.out.println("--- MÉDICOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Buscar por id");
            System.out.println("3. Inserir");
            System.out.println("4. Atualizar");
            System.out.println("5. Remover");
            System.out.println("0. Voltar");
            switch(lerLinha("> ")){
                case "1":
                    for(Medico m : medicoController.listarTodos()) System.out.println(m);
                    break;
                case "2": {
                    int id = lerInt("ID: ");
                    Medico m = medicoController.buscarPorId(id);
                    System.out.println(m != null ? m : "Não encontrado.");
                    break;
                }
                case "3":
                    executar(() -> {
                        String nome = lerLinha("Nome: ");
                        String crm = lerLinha("CRM: ");
                        listarEspecialidadesResumo();
                        int espId = lerInt("ID da especialidade: ");
                        Medico m = medicoController.criar(nome, crm, espId);
                        System.out.println("Inserido: " + m);
                    });
                    break;
                case "4":
                    executar(() -> {
                        int id = lerInt("ID: ");
                        String nome = lerLinha("Nome: ");
                        String crm = lerLinha("CRM: ");
                        listarEspecialidadesResumo();
                        int espId = lerInt("ID da especialidade: ");
                        medicoController.atualizar(id, nome, crm, espId);
                        System.out.println("Atualizado.");
                    });
                    break;
                case "5":
                    executar(() -> {
                        int id = lerInt("ID: ");
                        medicoController.remover(id);
                        System.out.println("Removido.");
                    });
                    break;
                case "0": return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private void menuConsultas(){
        while(true){
            System.out.println();
            System.out.println("--- CONSULTAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Buscar por id");
            System.out.println("3. Inserir");
            System.out.println("4. Atualizar");
            System.out.println("5. Remover");
            System.out.println("0. Voltar");
            switch(lerLinha("> ")){
                case "1":
                    for(Consulta c : consultaController.listarTodos()) System.out.println(c);
                    break;
                case "2": {
                    int id = lerInt("ID: ");
                    Consulta c = consultaController.buscarPorId(id);
                    System.out.println(c != null ? c : "Não encontrada.");
                    break;
                }
                case "3":
                    executar(() -> {
                        String data = lerLinha("Data (dd/MM/yyyy): ");
                        String hora = lerLinha("Hora (HH:mm): ");
                        listarMedicosResumo();
                        int medicoId = lerInt("ID do médico: ");
                        listarPacientesResumo();
                        int pacienteId = lerInt("ID do paciente: ");
                        String obs = lerLinha("Observação: ");
                        Consulta c = consultaController.criar(data, hora, medicoId, pacienteId, obs);
                        System.out.println("Inserida: " + c);
                        System.out.println("(log gravado no HSQLDB)");
                    });
                    break;
                case "4":
                    executar(() -> {
                        int id = lerInt("ID: ");
                        String data = lerLinha("Data (dd/MM/yyyy): ");
                        String hora = lerLinha("Hora (HH:mm): ");
                        listarMedicosResumo();
                        int medicoId = lerInt("ID do médico: ");
                        listarPacientesResumo();
                        int pacienteId = lerInt("ID do paciente: ");
                        String obs = lerLinha("Observação: ");
                        consultaController.atualizar(id, data, hora, medicoId, pacienteId, obs);
                        System.out.println("Atualizada.");
                    });
                    break;
                case "5":
                    executar(() -> {
                        int id = lerInt("ID: ");
                        consultaController.remover(id);
                        System.out.println("Removida.");
                    });
                    break;
                case "0": return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    private void listarLogs(){
        System.out.println();
        System.out.println("--- LOGS DE CONSULTAS (HSQLDB) ---");
        List<LogConsulta> logs = consultaController.listarLogs();
        if(logs.isEmpty()){
            System.out.println("Nenhum log registrado.");
            return;
        }
        for(LogConsulta l : logs) System.out.println(l);
    }

    private void listarEspecialidadesResumo(){
        System.out.println("Especialidades disponíveis:");
        for(Especialidade e : especialidadeController.listarTodos()){
            System.out.println("  " + e.getId() + " - " + e.getNome());
        }
    }

    private void listarMedicosResumo(){
        System.out.println("Médicos disponíveis:");
        for(Medico m : medicoController.listarTodos()){
            System.out.println("  " + m.getId() + " - " + m.getNome() + " (" + m.getCrm() + ")");
        }
    }

    private void listarPacientesResumo(){
        System.out.println("Pacientes disponíveis:");
        for(Paciente p : pacienteController.listarTodos()){
            System.out.println("  " + p.getId() + " - " + p.getNome() + " (" + p.getCpf() + ")");
        }
    }

    private void executar(Runnable acao){
        try{
            acao.run();
        } catch(IllegalArgumentException | IllegalStateException e){
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    private String lerLinha(String prompt){
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int lerInt(String prompt){
        while(true){
            try{
                return Integer.parseInt(lerLinha(prompt).trim());
            } catch(NumberFormatException e){
                System.out.println("Digite um número inteiro.");
            }
        }
    }
}
