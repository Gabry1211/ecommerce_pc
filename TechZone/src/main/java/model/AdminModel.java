package model;

import java.sql.SQLException;

public interface AdminModel {
    public boolean checkLogin(String email, String password) throws SQLException;
    public AmministratoreBean doRetrieveByEmail(String email) throws SQLException;
}
