package model;

import java.sql.SQLException;
import java.util.Collection;

public interface ProductModel {
    public void doSave(ProdottoBean product) throws SQLException;

    public boolean doDelete(int code) throws SQLException;

    public ProdottoBean doRetrieveByKey(int code) throws SQLException;
    
    public Collection<ProdottoBean> doRetrieveAll(String order) throws SQLException;

    public void doUpdate(ProdottoBean product) throws SQLException;

    public Collection<ProdottoBean> doRetrieveByQuery(String query) throws SQLException;

    public Collection<ProdottoBean> doRetrieveByVenditore(int idVenditore) throws SQLException;
}
