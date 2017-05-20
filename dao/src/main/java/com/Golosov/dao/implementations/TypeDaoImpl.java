package com.Golosov.dao.implementations;

import com.Golosov.dao.interfaces.TypeDao;
import com.Golosov.entities.Type;
import org.springframework.stereotype.Repository;

/**
 * Created by Андрей on 16.05.2017.
 */
@Repository
public class TypeDaoImpl extends AbstractDao<Type> implements TypeDao {

    private static final String FROM_TYPE = "from card_type";

    public TypeDaoImpl() {
        super(Type.class, FROM_TYPE);
    }
}
