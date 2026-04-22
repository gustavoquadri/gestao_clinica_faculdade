package model;

public class LogConsulta {
    private int id;
    private int consultaId;
    private String medicoNome;
    private String pacienteNome;
    private String especialidadeNome;
    private String dataConsulta;
    private String horaConsulta;

    public LogConsulta() {
    }

    public LogConsulta(int id, int consultaId, String medicoNome, String pacienteNome,
                       String especialidadeNome, String dataConsulta, String horaConsulta) {
        this.id = id;
        this.consultaId = consultaId;
        this.medicoNome = medicoNome;
        this.pacienteNome = pacienteNome;
        this.especialidadeNome = especialidadeNome;
        this.dataConsulta = dataConsulta;
        this.horaConsulta = horaConsulta;
    }

    public LogConsulta(int consultaId, String medicoNome, String pacienteNome,
                       String especialidadeNome, String dataConsulta, String horaConsulta) {
        this.consultaId = consultaId;
        this.medicoNome = medicoNome;
        this.pacienteNome = pacienteNome;
        this.especialidadeNome = especialidadeNome;
        this.dataConsulta = dataConsulta;
        this.horaConsulta = horaConsulta;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getConsultaId() {
        return this.consultaId;
    }

    public void setConsultaId(int consultaId) {
        this.consultaId = consultaId;
    }

    public String getMedicoNome() {
        return this.medicoNome;
    }

    public void setMedicoNome(String medicoNome) {
        this.medicoNome = medicoNome;
    }

    public String getPacienteNome() {
        return this.pacienteNome;
    }

    public void setPacienteNome(String pacienteNome) {
        this.pacienteNome = pacienteNome;
    }

    public String getEspecialidadeNome() {
        return this.especialidadeNome;
    }

    public void setEspecialidadeNome(String especialidadeNome) {
        this.especialidadeNome = especialidadeNome;
    }

    public String getDataConsulta() {
        return this.dataConsulta;
    }

    public void setDataConsulta(String dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getHoraConsulta() {
        return this.horaConsulta;
    }

    public void setHoraConsulta(String horaConsulta) {
        this.horaConsulta = horaConsulta;
    }

    @Override
    public String toString() {
        return "LogConsulta{id=" + this.id + ", consultaId=" + this.consultaId + ", medicoNome=" + this.medicoNome + ", pacienteNome=" + this.pacienteNome + ", dataConsulta=" + this.dataConsulta + ", horaConsulta=" + this.horaConsulta + "}";
    }
}