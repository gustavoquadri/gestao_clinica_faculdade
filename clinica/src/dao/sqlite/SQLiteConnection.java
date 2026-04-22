package dao.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection{

    private static final String URL = "jdbc:sqlite:database/clinica_sqlite.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}