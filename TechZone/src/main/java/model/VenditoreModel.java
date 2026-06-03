package model;

import java.sql.SQLException;

public interface VenditoreModel {
    public void doSave(VenditoreBean venditore) throws SQLException;
    public VenditoreBean doRetrieveByEmail(String email) throws SQLException;
    public VenditoreBean doRetrieveByKey(String codiceFiscale) throws SQLException;
    public java.util.Collection<VenditoreBean> doRetrieveAll() throws SQLException;
    public boolean checkLogin(String cf, String password) throws SQLException;
}
