package dao.sqlite;

import dao.interfaces.EspecialidadeDAO;
import model.Especialidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadeDAOSQLite implements EspecialidadeDAO {

    @Override
    public void inserir(Especialidade especialidade){
        String sql = "INSERT INTO especialidade (nome) VALUES (?)";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){
            stmt.setString(1, especialidade.getNome());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                especialidade.setId(rs.getInt(1));
            }
        } catch(SQLException e){
            System.out.println("Erro ao inserir especialidade: " + e.getMessage());
        }
    }

    @Override
    public List<Especialidade> listarTodos(){
        List<Especialidade> lista = new ArrayList<>();
        String sql = "SELECT id, nome FROM especialidade ORDER BY id";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ){
            while(rs.next()){
                Especialidade especialidade = new Especialidade();
                especialidade.setId(rs.getInt("id"));
                especialidade.setNome(rs.getString("nome"));
                lista.add(especialidade);
            }
        } catch(SQLException e){
            System.out.println("Erro ao listar especialidades: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public Especialidade buscarPorId(int id){
        String sql = "SELECT id, nome FROM especialidade WHERE id = ?";
        try(
            Connection conn = SQLiteConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ){
            stmt.setInt(1, id);
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Especialidade especialidade = new Especialidade();
                    especialidade.setId(rs.getInt("id"));
                    especialidade.setNome(rs.getString("nome"));
                    return especialidade;
                }
            }

        } catch(SQLException e){
            System.out.println("Erro ao buscar especialidade por id: " + e.getMessage());
        }
        return null;
    }

}