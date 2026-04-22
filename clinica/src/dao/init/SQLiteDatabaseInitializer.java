package dao.init;

import dao.sqlite.SQLiteConnection;
import util.ScriptReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabaseInitializer {

    private static final String SCRIPT_PATH = "database/sqlite_clinica.sql";
    private static final String TABELA_SENTINELA = "consulta";

    public static void initialize(){
        try(Connection conn = SQLiteConnection.getConnection()){
            if(jaInicializado(conn)){
                System.out.println("Banco SQLite já inicializado — mantendo dados existentes.");
                return;
            }
            executarScript(conn);
            System.out.println("Banco SQLite inicializado com sucesso!");
        } catch(SQLException e){
            System.out.println("Erro ao inicializar SQLite: " + e.getMessage());
        } catch(IOException e){
            System.out.println("Erro ao ler script do SQLite: " + e.getMessage());
        }
    }

    public static void resetar(){
        try(Connection conn = SQLiteConnection.getConnection()){
            executarScript(conn);
            System.out.println("Banco SQLite resetado.");
        } catch(SQLException e){
            System.out.println("Erro ao resetar SQLite: " + e.getMessage());
        } catch(IOException e){
            System.out.println("Erro ao ler script do SQLite: " + e.getMessage());
        }
    }

    private static boolean jaInicializado(Connection conn){
        try(
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + TABELA_SENTINELA)
        ){
            return true;
        } catch(SQLException e){
            return false;
        }
    }

    private static void executarScript(Connection conn) throws SQLException, IOException {
        String script = ScriptReader.readFile(SCRIPT_PATH);
        try(Statement stmt = conn.createStatement()){
            for(String command : script.split(";")){
                String sql = command.trim();
                if(!sql.isEmpty()){
                    stmt.execute(sql);
                }
            }
        }
    }
}
