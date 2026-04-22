package dao.sqlite;

import dao.interfaces.PacienteDAO;
import model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAOSQLite implements PacienteDAO {

    @Override
    public void inserir(Paciente paciente){
        String sql = "INSERT INTO paciente (nome, cpf, data_nascimento) VALUES (?, ?, ?)";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getDatNasc());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                paciente.setId(rs.getInt(1));
            }
        } catch(SQLException e){
            System.out.println("Erro ao inserir paciente: " + e.getMessage());
        }
    }

    @Override
    public List<Paciente> listarTodos(){
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cpf, data_nascimento FROM paciente ORDER BY id";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ){
            while(rs.next()){
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setDatNasc(rs.getString("data_nascimento"));
                lista.add(paciente);
            }
        } catch(SQLException e){
            System.out.println("Erro ao listar pacientes: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Paciente buscarPorId(int id){
        String sql = "SELECT id, nome, cpf, data_nascimento FROM paciente WHERE id = ?";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Paciente paciente = new Paciente();
                    paciente.setId(rs.getInt("id"));
                    paciente.setNome(rs.getString("nome"));
                    paciente.setCpf(rs.getString("cpf"));
                    paciente.setDatNasc(rs.getString("data_nascimento"));
                    return paciente;
                }
            }
        } catch(SQLException e){
            System.out.println("Erro ao buscar paciente por id: " + e.getMessage());
        }
        return null;
    }
}