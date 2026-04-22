package dao.init;

import dao.sqlite.SQLiteConnection;
import util.ScriptReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLiteDatabaseInitializer {

    private static final String SCRIPT_PATH = "database/sqlite_clinica.sql";

    public static void initialize(){
        try(
                Connection conn = SQLiteConnection.getConnection();
                Statement stmt = conn.createStatement()
        ){
            String script = ScriptReader.readFile(SCRIPT_PATH);
            String[] commands = script.split(";");

            for(String command : commands){
                String sql = command.trim();

                if(!sql.isEmpty()){
                    stmt.execute(sql);
                }
            }

            System.out.println("Banco SQLite inicializado com sucesso!");

        } catch(SQLException e){
            System.out.println("Erro ao inicializar SQLite: " + e.getMessage());
        } catch(IOException e){
            System.out.println("Erro ao ler script do SQLite: " + e.getMessage());
        }
    }
}