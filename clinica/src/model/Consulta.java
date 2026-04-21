package model;

public class Consulta {

    private int id;
    private String datConsult;
    private String hrConsult;
    private Medico medico;
    private Paciente paciente;
    private String observacao;

    public Consulta() {
    }

    public Consulta(int id, String datConsult, String hrConsult, Medico medico, Paciente paciente, String observacao) {
        this.id = id;
        this.datConsult = datConsult;
        this.hrConsult = hrConsult;
        this.medico = medico;
        this.paciente = paciente;
        this.observacao = observacao;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDatConsult() {
        return this.datConsult;
    }

    public void setDatConsult(String datConsult) {
        this.datConsult = datConsult;
    }

    public String getHrConsult() {
        return this.hrConsult;
    }

    public void setHrConsult(String hrConsult) {
        this.hrConsult = hrConsult;
    }

    public Medico getMedico() {
        return this.medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Paciente getPaciente() {
        return this.paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public String getObservacao() {
        return this.observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        return "Consulta{id=" + this.id + ", datConsult=" + this.datConsult + ", hrConsult=" + this.hrConsult + ", medico=" + this.medico + ", paciente=" + this.paciente + ", observacao=" + this.observacao + "}";
    }

}
