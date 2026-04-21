package app;

import model.Consulta;
import model.Especialidade;
import model.Medico;
import model.Paciente;

public class Main {
    public static void main(String[] args) {
        Especialidade especialidade = new Especialidade(1, "Cardiologia");
        Medico medico = new Medico(1, "Dr. Gustavo", "CRM123", especialidade);
        Paciente paciente = new Paciente(1, "Erika Seben", "052.810.370-99", "20/06/2026");
        Consulta consulta = new Consulta(1, "21/04/2026", "16:40", medico, paciente, "Consulta de rotina");

        System.out.println(especialidade);
        System.out.println(medico);
        System.out.println(paciente);
        System.out.println(consulta);
    }

}
