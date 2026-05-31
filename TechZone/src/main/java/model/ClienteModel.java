package model;

import java.sql.SQLException;

public interface ClienteModel {
    public void doSave(ClienteBean cliente) throws SQLException;
    public ClienteBean doRetrieveByEmail(String email) throws SQLException;
    public ClienteBean doRetrieveByKey(String codiceFiscale) throws SQLException;
    public boolean checkLogin(String email, String password) throws SQLException;
}
