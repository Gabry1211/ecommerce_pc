package model;

import java.sql.SQLException;
import java.util.Collection;
import java.sql.Date;

public interface OrdineModel {
    public void doSave(OrdineBean ordine) throws SQLException;
    public Collection<OrdineBean> doRetrieveByCliente(String codiceFiscale) throws SQLException;
    public Collection<OrdineBean> doRetrieveAll(Date from, Date to, String codiceFiscale) throws SQLException;
    public Collection<OrdineBean> doRetrieveByVenditore(int idVenditore) throws SQLException;
}
