package model;

public class Paciente {

    private int id;
    private String nome;
    private String cpf;
    private String datNasc;

    public Paciente() {
    }

    public Paciente(int id, String nome, String cpf, String datNasc) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.datNasc = datNasc;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDatNasc() {
        return this.datNasc;
    }

    public void setDatNasc(String datNasc) {
        this.datNasc = datNasc;
    }

    @Override
    public String toString() {
        return "Paciente{id=" + this.id + ", nome=" + this.nome + ", cpf=" + this.cpf + ", datNasc=" + this.datNasc + "}";
    }

}
