package com.app.dao.board;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BoardMessageDaoImpl implements BoardMessageDao {

    private final SessionFactory sessionFactory;

    @Autowired
    public BoardMessageDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
