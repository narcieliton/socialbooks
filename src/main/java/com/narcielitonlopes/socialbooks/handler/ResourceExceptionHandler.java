package com.narcielitonlopes.socialbooks.handler;

import com.narcielitonlopes.socialbooks.domain.DetalhesErro;
import com.narcielitonlopes.socialbooks.services.exceptions.AutorExistenteException;
import com.narcielitonlopes.socialbooks.services.exceptions.AutorNaoEncontradoException;
import com.narcielitonlopes.socialbooks.services.exceptions.LivroNaoEncontradoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(LivroNaoEncontradoException.class)
    public ResponseEntity<DetalhesErro> handleLivroNaoEncontradoException
                (LivroNaoEncontradoException e, HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(criarDetalheErroNaoEncontrado("Livro"));
    }

    @ExceptionHandler(AutorExistenteException.class)
    public ResponseEntity<DetalhesErro> handleAutorExistenteException(AutorExistenteException e,
                                                                      HttpServletRequest request){

        DetalhesErro erro = new DetalhesErro();

        erro.setStatus(409l);
        erro.setTitulo("Autor já existente");
        erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/409");
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(AutorNaoEncontradoException.class)
    public ResponseEntity<DetalhesErro> handleAutorNaoEncontradoException
            (AutorNaoEncontradoException e, HttpServletRequest request){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(criarDetalheErroNaoEncontrado("Autor"));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DetalhesErro> handlerDataIntegrityViolationException
            (DataIntegrityViolationException e, HttpServletRequest request){

        DetalhesErro erro = new DetalhesErro();

        erro.setStatus(400l);
        erro.setTitulo("Requisição inválida");
        erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/400");
        erro.setTimestamp(System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    private DetalhesErro criarDetalheErroNaoEncontrado(String recursoExistente){

        DetalhesErro erro = new DetalhesErro();

        erro.setStatus(404l);
        erro.setTitulo(criarMensagemErro("0", recursoExistente, "não pôde ser encontrado"));
        erro.setMensagemDesenvolvedor("http://erros.socialbooks.com/404");
        erro.setTimestamp(System.currentTimeMillis());

        return erro;
    }

    private String criarMensagemErro(String inicioFrase, String recurso, String mensagem){

        StringBuilder mensagemMontada = new StringBuilder();
        mensagemMontada.append(inicioFrase);
        mensagemMontada.append(" ");
        mensagemMontada.append(recurso);
        mensagemMontada.append(" ");
        mensagemMontada.append(mensagem);

        return mensagemMontada.toString();
    }
}
