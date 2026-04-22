package dao.hsqldb;

import dao.interfaces.LogConsultaDAO;
import model.LogConsulta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogConsultaDAOHSQLDB implements LogConsultaDAO {

    @Override
    public void inserir(LogConsulta logConsulta){
        String sql = "INSERT INTO log_consulta (consulta_id, medico_nome, paciente_nome, especialidade_nome, data_consulta, hora_consulta) VALUES (?, ?, ?, ?, ?, ?)";
        try(
            Connection conn = HSQLDBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            stmt.setInt(1, logConsulta.getConsultaId());
            stmt.setString(2, logConsulta.getMedicoNome());
            stmt.setString(3, logConsulta.getPacienteNome());
            stmt.setString(4, logConsulta.getEspecialidadeNome());
            stmt.setString(5, logConsulta.getDataConsulta());
            stmt.setString(6, logConsulta.getHoraConsulta());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                logConsulta.setId(rs.getInt(1));
            }
        } catch(SQLException e){
            System.out.println("Erro ao inserir log de consulta: " + e.getMessage());
        }
    }

    @Override
    public List<LogConsulta> listarTodos(){
        List<LogConsulta> lista = new ArrayList<>();
        String sql = "SELECT id, consulta_id, medico_nome, paciente_nome, especialidade_nome, data_consulta, hora_consulta FROM log_consulta ORDER BY id";
        try(
            Connection conn = HSQLDBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ){
            while(rs.next()){
                LogConsulta log = new LogConsulta();
                log.setId(rs.getInt("id"));
                log.setConsultaId(rs.getInt("consulta_id"));
                log.setMedicoNome(rs.getString("medico_nome"));
                log.setPacienteNome(rs.getString("paciente_nome"));
                log.setEspecialidadeNome(rs.getString("especialidade_nome"));
                log.setDataConsulta(rs.getString("data_consulta"));
                log.setHoraConsulta(rs.getString("hora_consulta"));
                lista.add(log);
            }
        } catch(SQLException e){
            System.out.println("Erro ao listar logs de consulta: " + e.getMessage());
        }
        return lista;
    }
}