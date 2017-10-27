package com.narcielitonlopes.socialbooks.services;

import com.narcielitonlopes.socialbooks.domain.Autor;
import com.narcielitonlopes.socialbooks.repository.AutoresRepository;
import com.narcielitonlopes.socialbooks.services.exceptions.AutorExistenteException;
import com.narcielitonlopes.socialbooks.services.exceptions.AutorNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutoresService {

    @Autowired
    private AutoresRepository autoresRepository;

    public List<Autor> listar() {
        return autoresRepository.findAll();
    }

    public Autor salvar(Autor autor){
        if(autor.getId() != null){
            Autor autorExistente = autoresRepository.findOne(autor.getId());

            if(autorExistente != null){
                throw new AutorExistenteException("O autor já existe");
            }
        }
        return autoresRepository.save(autor);
    }

    public Autor buscar(Long id){
        Autor autor = autoresRepository.findOne(id);

        if(autor == null){
            throw new AutorNaoEncontradoException("O autor não foi encontrado");
        }
        return autor;
    }

}
