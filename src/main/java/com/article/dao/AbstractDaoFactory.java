package com.article.dao;

public class AbstractDaoFactory {

    public static com.article.dao.AbstractDao getFactory(String database) {
        if("h2".equals (database)) {
            return new H2Dao ();
        } else if("oracle".equals ( database )) {
            return new OracleDao ();
        }
        return null;
    }
}
