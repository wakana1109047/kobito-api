package com.example.kobitoapi.repository;

import com.example.kobitoapi.dto.Kobito;
import com.example.kobitoapi.exception.KobitoNotFoundException;
import com.example.kobitoapi.mapper.KobitoMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class KobitoRepository {
    private final KobitoMapper kobitoMapper;

    public KobitoRepository(KobitoMapper kobitoMapper) {
        this.kobitoMapper = kobitoMapper;
    }

    public List<Kobito> findAll(){
        return kobitoMapper.findAll();
    }

    public int insertKobito(Kobito kobito){
        return kobitoMapper.insert(kobito);
    }

    public Kobito findById(Long id){
        return kobitoMapper.findById(id);
    }

    public boolean delete(long id){
        return kobitoMapper.delete(id) == 1;
    }

    public Kobito update(Kobito kobito){
        if(kobitoMapper.update(kobito) == 0){
            throw new KobitoNotFoundException(kobito.getId());
        }
        return kobito;
    }

    public List<Kobito> findByName(String name){
        return kobitoMapper.findByName(name);
    }
}
