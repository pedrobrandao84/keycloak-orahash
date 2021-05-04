package com.avanadebrasil.keycloak.database;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class HashQueryDataBase {

    private Connection conn;
    private DataBaseProperties dataBaseProperties;
    private PreparedStatement psQueryHash;
    private String commandQueryHash = null;

    private static final String COMMAND_QUERY_HASH =
            "select DBMS_UTILITY.GET_HASH_VALUE(?, 100000, 1073741824) "
            + " from dual";

    private void readPropertiesDataBase(){
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("META-INF/database.properties"));
            dataBaseProperties = new DataBaseProperties(properties.getProperty("database.host"), properties.getProperty("database.port"), properties.getProperty("database.name"),
                    properties.getProperty("database.user"), properties.getProperty("database.password"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void crateConnectionDB() {
        if (conn == null) {
            try {
                this.readPropertiesDataBase();
                conn = DriverManager.getConnection(dataBaseProperties.getJdbcUrl(), dataBaseProperties.getUserDb(),
                        dataBaseProperties.getPasswordDb());
                psQueryHash = conn.prepareStatement(COMMAND_QUERY_HASH);

            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("");
            }
        }
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
