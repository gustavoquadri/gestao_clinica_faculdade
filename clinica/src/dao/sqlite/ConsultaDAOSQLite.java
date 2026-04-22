package dao.sqlite;

import dao.interfaces.ConsultaDAO;
import model.Consulta;
import model.Especialidade;
import model.Medico;
import model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAOSQLite implements ConsultaDAO {

    @Override
    public void inserir(Consulta consulta){
        String sql = "INSERT INTO consulta (data_consulta, hora_consulta, medico_id, paciente_id, observacao) VALUES (?, ?, ?, ?, ?)";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            stmt.setString(1, consulta.getDatConsult());
            stmt.setString(2, consulta.getHrConsult());
            stmt.setInt(3, consulta.getMedico().getId());
            stmt.setInt(4, consulta.getPaciente().getId());
            stmt.setString(5, consulta.getObservacao());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                consulta.setId(rs.getInt(1));
            }

        } catch(SQLException e){
            System.out.println("Erro ao inserir consulta: " + e.getMessage());
        }
    }

    @Override
    public void atualizar(Consulta consulta){
        String sql = "UPDATE consulta SET data_consulta = ?, hora_consulta = ?, medico_id = ?, paciente_id = ?, observacao = ? WHERE id = ?";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setString(1, consulta.getDatConsult());
            stmt.setString(2, consulta.getHrConsult());
            stmt.setInt(3, consulta.getMedico().getId());
            stmt.setInt(4, consulta.getPaciente().getId());
            stmt.setString(5, consulta.getObservacao());
            stmt.setInt(6, consulta.getId());
            stmt.executeUpdate();
        } catch(SQLException e){
            System.out.println("Erro ao atualizar consulta: " + e.getMessage());
        }
    }

    @Override
    public void remover(int id){
        String sql = "DELETE FROM consulta WHERE id = ?";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch(SQLException e){
            System.out.println("Erro ao remover consulta: " + e.getMessage());
        }
    }

    @Override
    public Consulta buscarPorId(int id){
        String sql = "SELECT c.id AS consulta_id, c.data_consulta, c.hora_consulta, c.observacao, m.id AS medico_id, m.nome AS medico_nome, m.crm, e.id AS especialidade_id, e.nome AS especialidade_nome, p.id AS paciente_id, p.nome AS paciente_nome, p.cpf, p.data_nascimento FROM consulta c INNER JOIN medico m ON c.medico_id = m.id INNER JOIN especialidade e ON m.especialidade_id = e.id INNER JOIN paciente p ON c.paciente_id = p.id WHERE c.id = ?";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Especialidade especialidade = new Especialidade();
                    especialidade.setId(rs.getInt("especialidade_id"));
                    especialidade.setNome(rs.getString("especialidade_nome"));

                    Medico medico = new Medico();
                    medico.setId(rs.getInt("medico_id"));
                    medico.setNome(rs.getString("medico_nome"));
                    medico.setCrm(rs.getString("crm"));
                    medico.setEspecialidade(especialidade);

                    Paciente paciente = new Paciente();
                    paciente.setId(rs.getInt("paciente_id"));
                    paciente.setNome(rs.getString("paciente_nome"));
                    paciente.setCpf(rs.getString("cpf"));
                    paciente.setDatNasc(rs.getString("data_nascimento"));

                    Consulta consulta = new Consulta();
                    consulta.setId(rs.getInt("consulta_id"));
                    consulta.setDatConsult(rs.getString("data_consulta"));
                    consulta.setHrConsult(rs.getString("hora_consulta"));
                    consulta.setObservacao(rs.getString("observacao"));
                    consulta.setMedico(medico);
                    consulta.setPaciente(paciente);
                    return consulta;
                }
            }
        } catch(SQLException e){
            System.out.println("Erro ao buscar consulta por id: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Consulta> listarTodos(){
        List<Consulta> lista = new ArrayList<>();
        String sql = "SELECT c.id AS consulta_id, c.data_consulta, c.hora_consulta, c.observacao, m.id AS medico_id, m.nome AS medico_nome, m.crm, e.id AS especialidade_id, e.nome AS especialidade_nome, p.id AS paciente_id, p.nome AS paciente_nome, p.cpf, p.data_nascimento FROM consulta c INNER JOIN medico m ON c.medico_id = m.id INNER JOIN especialidade e ON m.especialidade_id = e.id INNER JOIN paciente p ON c.paciente_id = p.id ORDER BY c.id";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ){
            while(rs.next()){
                Especialidade especialidade = new Especialidade();
                especialidade.setId(rs.getInt("especialidade_id"));
                especialidade.setNome(rs.getString("especialidade_nome"));

                Medico medico = new Medico();
                medico.setId(rs.getInt("medico_id"));
                medico.setNome(rs.getString("medico_nome"));
                medico.setCrm(rs.getString("crm"));
                medico.setEspecialidade(especialidade);

                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("paciente_id"));
                paciente.setNome(rs.getString("paciente_nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setDatNasc(rs.getString("data_nascimento"));

                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("consulta_id"));
                consulta.setDatConsult(rs.getString("data_consulta"));
                consulta.setHrConsult(rs.getString("hora_consulta"));
                consulta.setObservacao(rs.getString("observacao"));
                consulta.setMedico(medico);
                consulta.setPaciente(paciente);
                lista.add(consulta);
            }
        } catch(SQLException e){
            System.out.println("Erro ao listar consultas: " + e.getMessage());
        }
        return lista;
    }
}