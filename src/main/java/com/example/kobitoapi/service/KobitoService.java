package com.example.kobitoapi.service;

import com.example.kobitoapi.dto.Kobito;
import com.example.kobitoapi.exception.KobitoArgumentNotValidException;
import com.example.kobitoapi.exception.KobitoNotFoundException;
import com.example.kobitoapi.repository.KobitoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KobitoService {
    private final KobitoRepository kobitoRepository;

    public KobitoService(KobitoRepository kobitoRepository) {
        this.kobitoRepository = kobitoRepository;
    }

    public List<Kobito> findAll(){
        return kobitoRepository.findAll();
    }

    public Kobito saveKobito(Kobito kobito) {
        if (kobito.getName() == null || kobito.getName().isEmpty()) {
            throw new KobitoArgumentNotValidException("Kobito's name is empty");
        }

        if (kobito.getDescription() == null || kobito.getDescription().isEmpty()) {
            throw new KobitoArgumentNotValidException("Kobito's description is empty");
        }
        kobitoRepository.insertKobito(kobito);
        return kobito;
    }

    public Kobito findById(Long id){
        Kobito kobito = kobitoRepository.findById(id);

        if(kobito == null){
            throw new KobitoNotFoundException(id);
        }
        return kobito;
    }
    public boolean delete(long id){
        if(!kobitoRepository.delete(id)){
            throw new KobitoNotFoundException(id);
        }
        return true;
    }

    @Transactional
    public Kobito update(Kobito kobito, long id){
        if (kobito.getName() == null || kobito.getName().isEmpty()) {
            throw new KobitoArgumentNotValidException("Kobito's name is empty");
        }

        if (kobito.getDescription() == null || kobito.getDescription().isEmpty()) {
            throw new KobitoArgumentNotValidException("Kobito's description is empty");
        }

        Kobito targetKobito = kobitoRepository.findById(id);
        if(targetKobito == null){
            throw new KobitoNotFoundException(id);
        }
        kobito.setId(id);
        kobito.setCreatedAt(targetKobito.getCreatedAt());
        return kobitoRepository.update(kobito);
    }

    public List<Kobito> findByName(String name){
        return kobitoRepository.findByName(name);
    }
}
