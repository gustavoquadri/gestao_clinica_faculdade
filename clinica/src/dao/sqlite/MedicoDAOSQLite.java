package dao.sqlite;

import dao.interfaces.MedicoDAO;
import model.Especialidade;
import model.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAOSQLite implements MedicoDAO {

    @Override
    public void inserir(Medico medico){
        String sql = "INSERT INTO medico (nome, crm, especialidade_id) VALUES (?, ?, ?)";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setInt(3, medico.getEspecialidade().getId());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                medico.setId(rs.getInt(1));
            }
        } catch(SQLException e){
            System.out.println("Erro ao inserir médico: " + e.getMessage());
        }
    }

    @Override
    public List<Medico> listarTodos(){
        List<Medico> lista = new ArrayList<>();
        String sql = "SELECT m.id AS medico_id, m.nome AS medico_nome, m.crm, e.id AS especialidade_id, e.nome AS especialidade_nome FROM medico m INNER JOIN especialidade e ON m.especialidade_id = e.id ORDER BY m.id";

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
                lista.add(medico);
            }
        } catch(SQLException e){
            System.out.println("Erro ao listar médicos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void atualizar(Medico medico){
        String sql = "UPDATE medico SET nome = ?, crm = ?, especialidade_id = ? WHERE id = ?";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getCrm());
            stmt.setInt(3, medico.getEspecialidade().getId());
            stmt.setInt(4, medico.getId());
            stmt.executeUpdate();
        } catch(SQLException e){
            System.out.println("Erro ao atualizar médico: " + e.getMessage());
        }
    }

    @Override
    public void remover(int id){
        String sql = "DELETE FROM medico WHERE id = ?";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch(SQLException e){
            System.out.println("Erro ao remover médico: " + e.getMessage());
        }
    }

    @Override
    public Medico buscarPorId(int id){
       String sql = "SELECT m.id AS medico_id, m.nome AS medico_nome, m.crm, e.id AS especialidade_id, e.nome AS especialidade_nome FROM medico m INNER JOIN especialidade e ON m.especialidade_id = e.id WHERE m.id = ?";
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
                    return medico;
                }
            }
        } catch(SQLException e){
            System.out.println("Erro ao buscar médico por id: " + e.getMessage());
        }
        return null;
    }
}