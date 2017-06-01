package com.golosov.services.implementations;

import com.golosov.dao.interfaces.TypeDao;
import com.golosov.entities.Type;
import com.golosov.exceptions.DaoException;
import com.golosov.services.dto.Converter;
import com.golosov.services.dto.dto.TypeDto;
import com.golosov.services.exceptions.NotFoundException;
import com.golosov.services.exceptions.ServiceException;
import com.golosov.services.interfaces.TypeService;
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
            typeDao.save(type);
            logger.debug("Type: " + type + " successfully saved!");
        } catch (DaoException e) {
            logger.error("Error was thrown in type service method type save: " + e);
            throw new ServiceException(e);
        }
        return type.getId();
    }

    @Override
    public void delete(long id) {
        try {
            Type type = typeDao.getById(id);
            if(type == null){
                throw new NotFoundException("Type not found!");
            }
            typeDao.delete(id);
            logger.debug("Type by id: " + id + " successfully deleted!");
        } catch (DaoException e) {
            logger.error("Error was thrown in type service method type delete: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(TypeDto typeDto) {
        Type currentType = Converter.typeDtoToTypeEntityConverter(typeDto);
        try {
            Type type = typeDao.getById(currentType.getId());
            if(type == null){
                throw new NotFoundException("Type not found!");
            }
            typeDao.update(currentType);
            logger.debug("Type: " + currentType + " successfully updated!");
        } catch (DaoException e) {
            logger.error("Error was thrown in type service method type update: " + e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<TypeDto> getAll() {
        List<TypeDto> typeDtos = new ArrayList<>();
        try {
            List<Type> types = typeDao.getAll();
            logger.debug("All types successfully found!");
            types.forEach(type -> {
                TypeDto typeDto = Converter.typeEntityToTypeDtoConverter(type);
                typeDtos.add(typeDto);
            });
        } catch (DaoException e) {
            logger.error("Error was thrown in type service method type getAll: " + e);
            throw new ServiceException(e);
        }
        return typeDtos;
    }

    @Override
    public TypeDto get(long id) {
        TypeDto typeDto;
        try {
            Type type = typeDao.getById(id);
            if(type == null){
                throw new NotFoundException("Type not found!");
            }
            logger.debug("Type: " + type + " successfully found!");
            typeDto = Converter.typeEntityToTypeDtoConverter(type);
        } catch (DaoException e) {
            logger.error("Error was thrown in type service method type get: " + e);
            throw new ServiceException(e);
        }
        return typeDto;
    }
}
