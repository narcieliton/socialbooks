package com.narcielitonlopes.socialbooks.resources;

import com.narcielitonlopes.socialbooks.domain.Comentario;
import com.narcielitonlopes.socialbooks.domain.Livro;
import com.narcielitonlopes.socialbooks.services.LivrosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/livros")
public class LivrosResource {

    @Autowired
    private LivrosService livrosService;

    @RequestMapping(method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE
    })
    public ResponseEntity<List<Livro>> listar(){
        return  ResponseEntity.status(HttpStatus.OK).body(livrosService.listar());
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> salvar(@Valid @RequestBody Livro livro) {
        livro =  livrosService.salvar(livro);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(livro.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> buscar(@PathVariable("id") Long id){
        Livro livro = livrosService.buscar(id);

        CacheControl cacheControl = CacheControl.maxAge(20, TimeUnit.SECONDS);

        return ResponseEntity.status(HttpStatus.OK).cacheControl(cacheControl).body(livro);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deletar(@PathVariable("id") Long id){
        livrosService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> atualizar(@RequestBody Livro livro, @PathVariable("id") Long id) {
        livro.setId(id);
        livrosService.atualizar(livro);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}/comentarios", method = RequestMethod.POST)
    public ResponseEntity<Void> adicionarComentario(@PathVariable("id") Long livroId,
                                                    @RequestBody Comentario comentario){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        comentario.setUsuario(authentication.getName());

        livrosService.salvarComentario(livroId, comentario);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

       return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/{id}/comentarios", method = RequestMethod.GET)
    public ResponseEntity<List<Comentario>> listarComentarios(@PathVariable("id") Long livroId){
        List<Comentario> comentarios = livrosService.listarComentarios(livroId);

        return ResponseEntity.status(HttpStatus.OK).body(comentarios);
    }
}
