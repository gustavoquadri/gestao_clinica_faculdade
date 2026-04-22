package dao.init;

import dao.hsqldb.HSQLDBConnection;
import util.ScriptReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class HSQLDBDatabaseInitializer {

    private static final String SCRIPT_PATH = "database/hsqldb_clinica.sql";

    public static void initialize(){
        try(
                Connection conn = HSQLDBConnection.getConnection();
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

            System.out.println("Banco HSQLDB inicializado com sucesso!");

        } catch(SQLException e){
            System.out.println("Erro ao inicializar HSQLDB: " + e.getMessage());
        } catch(IOException e){
            System.out.println("Erro ao ler script do HSQLDB: " + e.getMessage());
        }
    }
}