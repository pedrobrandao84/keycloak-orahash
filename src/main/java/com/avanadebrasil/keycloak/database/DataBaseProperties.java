package com.avanadebrasil.keycloak.database;

public class DataBaseProperties {

    private String hostDb;
    private String portDb;
    private String nameDb;
    private String userDb;
    private String passwordDb;
    private String jdbcUrl;

    public DataBaseProperties(String hostDb, String portDb, String nameDb,
                              String userDb, String passwordDb){
        super();
        this.hostDb = hostDb;
        this.portDb = portDb;
        this.nameDb = nameDb;
        this.userDb = userDb;
        this.passwordDb = passwordDb;
        this.jdbcUrl = "jdbc:oracle:thin:@" + hostDb + ":" + portDb + "/" + nameDb;
    }

    public DataBaseProperties() {
    }

    public String getHostDb() {
        return hostDb;
    }

    public void setHostDb(String hostDb) {
        this.hostDb = hostDb;
    }

    public String getPortDb() {
        return portDb;
    }

    public void setPortDb(String portDb) {
        this.portDb = portDb;
    }

    public String getNameDb() {
        return nameDb;
    }

    public void setNameDb(String nameDb) {
        this.nameDb = nameDb;
    }

    public String getUserDb() {
        return userDb;
    }

    public void setUserDb(String userDb) {
        this.userDb = userDb;
    }

    public String getPasswordDb() {
        return passwordDb;
    }

    public void setPasswordDb(String passwordDb) {
        this.passwordDb = passwordDb;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }
}
