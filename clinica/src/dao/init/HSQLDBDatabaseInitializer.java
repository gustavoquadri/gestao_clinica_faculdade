package dao.init;

import dao.hsqldb.HSQLDBConnection;
import util.ScriptReader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HSQLDBDatabaseInitializer {

    private static final String SCRIPT_PATH = "database/hsqldb_clinica.sql";
    private static final String TABELA_SENTINELA = "log_consulta";

    public static void initialize(){
        try(Connection conn = HSQLDBConnection.getConnection()){
            if(jaInicializado(conn)){
                System.out.println("Banco HSQLDB já inicializado — mantendo dados existentes.");
                return;
            }
            executarScript(conn);
            System.out.println("Banco HSQLDB inicializado com sucesso!");
        } catch(SQLException e){
            System.out.println("Erro ao inicializar HSQLDB: " + e.getMessage());
        } catch(IOException e){
            System.out.println("Erro ao ler script do HSQLDB: " + e.getMessage());
        }
    }

    public static void resetar(){
        try(Connection conn = HSQLDBConnection.getConnection()){
            executarScript(conn);
            System.out.println("Banco HSQLDB resetado.");
        } catch(SQLException e){
            System.out.println("Erro ao resetar HSQLDB: " + e.getMessage());
        } catch(IOException e){
            System.out.println("Erro ao ler script do HSQLDB: " + e.getMessage());
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
