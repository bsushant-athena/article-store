package com.article.dao;

import java.sql.*;

public class H2Dao implements AbstractDao {

    private Connection connection;

    //@org.aspectj.lang.annotation.Before
    public void init() {
        //data connection init
    }

    //@org.aspectj.lang.annotation.After
    public void destroy() {
        // connections close.
    }

    @Override
    public void post ( ) {

    }

    @Override
    public void get ( ) {

    }

    @Override
    public void put ( ) {

    }

    @Override
    public void patch ( ) {

    }

    @Override
    public void delete ( ) {

    }
}
