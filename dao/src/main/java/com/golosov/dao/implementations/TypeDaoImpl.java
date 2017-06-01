package com.golosov.dao.implementations;

import com.golosov.dao.interfaces.TypeDao;
import com.golosov.entities.Type;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

/**
 * Created by Андрей on 16.05.2017.
 */
@Repository
public class TypeDaoImpl extends AbstractDao<Type> implements TypeDao {

    private static Logger logger = Logger.getLogger(TypeDaoImpl.class);

    private static final String FROM_TYPE = "from card_type";

    public TypeDaoImpl() {
        super(Type.class, FROM_TYPE);
    }
}
