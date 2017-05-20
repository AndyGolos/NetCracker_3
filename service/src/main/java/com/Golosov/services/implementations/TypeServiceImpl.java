package com.Golosov.services.implementations;

import com.Golosov.dao.interfaces.TypeDao;
import com.Golosov.entities.Type;
import com.Golosov.exceptions.DaoException;
import com.Golosov.services.dto.converters.Converter;
import com.Golosov.services.dto.dto.TypeDto;
import com.Golosov.services.exceptions.ServiceException;
import com.Golosov.services.interfaces.TypeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 17.05.2017.
 */
@Service
@Transactional(rollbackFor = DaoException.class)
public class TypeServiceImpl implements TypeService {

    private static Logger logger = Logger.getLogger(TypeServiceImpl.class);

    @Autowired
    private TypeDao typeDao;

    @Override
    public long save(TypeDto typeDto) {
        Type type = Converter.typeDtoToTypeEntityConverter(typeDto);
        try {
            return typeDao.save(type);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public void delete(long id) {
        try {
            typeDao.delete(id);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public void update(TypeDto typeDto) {
        Type type = Converter.typeDtoToTypeEntityConverter(typeDto);
        try {
            typeDao.update(type);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
    }

    @Override
    public List<TypeDto> getAll() {
        List<TypeDto> typeDtos = new ArrayList<>();
        try {
            List<Type> types = typeDao.getAll();
            types.forEach(type -> {
                TypeDto typeDto = Converter.typeEntityToTypeDtoConverter(type);
                typeDtos.add(typeDto);
            });
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return typeDtos;
    }

    @Override
    public TypeDto get(long id) {
        TypeDto typeDto;
        try {
            Type type = typeDao.getById(id);
            typeDto = Converter.typeEntityToTypeDtoConverter(type);
        } catch (DaoException dao) {
            logger.error("Error was thrown in service: " + dao);
            throw new ServiceException();
        }
        return typeDto;
    }
}
