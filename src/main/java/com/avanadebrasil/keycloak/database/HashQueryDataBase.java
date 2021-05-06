package com.avanadebrasil.keycloak.database;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class HashQueryDataBase {

    private Connection conn;
    private PreparedStatement psQueryHash;
    private String commandQueryHash = null;

    private static final String COMMAND_QUERY_HASH =
            "select DBMS_UTILITY.GET_HASH_VALUE(?, 100000, 1073741824) "
            + " from dual";

    private void crateConnectionDB() {
        if (conn == null) {
            try {
                conn = this.getConnection();
                psQueryHash = conn.prepareStatement(COMMAND_QUERY_HASH);

            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("");
            }
        }
    }

    public Connection getConnection() {
        Connection cnn = null;
        try {
            InitialContext init = new InitialContext();
            DataSource ds = (DataSource) init.lookup("java:/jdbc/OracleDS");

            try {
                cnn = ds.getConnection();
            } catch(SQLException ex) {
                System.out.println("ERROR GETTING CONNECTION: "+ex.getMessage());
            }

        } catch(NamingException ne) {
            System.out.println("ERROR connect method: "+ne.getMessage());
        }
        return cnn;
    }

    public String returnHashPassword(String password){
        this.crateConnectionDB();
        ResultSet rsHashPassword = null;
        String hashPassord = null;
        try {
            psQueryHash.setString(1, password);
            rsHashPassword = psQueryHash.executeQuery();
            if(rsHashPassword.next()){
                hashPassord = rsHashPassword.getString(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (rsHashPassword != null){
                try {
                    rsHashPassword.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
            return hashPassord;
        }
    }

}
