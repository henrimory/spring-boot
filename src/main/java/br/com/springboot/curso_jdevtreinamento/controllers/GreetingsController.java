package br.com.springboot.curso_jdevtreinamento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_jdevtreinamento.model.Usuario;
import br.com.springboot.curso_jdevtreinamento.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {
	
	@Autowired /*IC/CD ou CDI - Injeçaõ de deoendencia*/
	private UsuarioRepository usuarioRepository;
	
	
    /**
     *
     * @param name the name to greet
     * @return greeting text
     */
	/*
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    
    public String greetingText(@PathVariable String name) {
        return "Curso Spring Boot " + name + "!";
    }
    */
    
    
    @RequestMapping(value="/olamundo/{nome}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String retornaOlaMundo(@PathVariable String nome) {
    	
    	Usuario usuario = new Usuario();
    	usuario.setNome(nome);
    	
    	usuarioRepository.save(usuario);//grava as informações no banco de dados
    	
    	return "Olá mundo " + nome;    	
    	
    }
    
  //método para listar usuarios na base de dados
    @GetMapping(value = "listatodos")//primeiro metodo de api
    @ResponseBody /*retorna os dados para o corpo da resposta*/
    public ResponseEntity<List<Usuario>> listaUsuario(){
    	List<Usuario> usuarios = usuarioRepository.findAll();//executa a consulta no banco de dados
    	
    	return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);//retorna a lista em json
    }
    
  //método para salvar
    @PostMapping(value = "salvar")//mapeia a url
    @ResponseBody //descrição da resposta
    public ResponseEntity<Usuario> salvar (@RequestBody Usuario usuario)/*receve os dados para salvar*/ {
    	
    	Usuario user = usuarioRepository.save(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
    	
    }
    
    
  //método para atualizar
    @PutMapping(value = "atualizar")//mapeia a url
    @ResponseBody //descrição da resposta
    public ResponseEntity<?> atualizar (@RequestBody Usuario usuario)/*receve os dados para salvar*/ {
    	
    	if (usuario.getId() == null) {
    		return new ResponseEntity<String>("Id não foi informado para atualização.", HttpStatus.OK);
    	}
    	
    	Usuario user = usuarioRepository.saveAndFlush(usuario);
    	
    	return new ResponseEntity<Usuario>(user, HttpStatus.OK);
    	
    }
    
    //método para deletar
    @DeleteMapping(value = "delete")//mapeia a url
    @ResponseBody //descrição da resposta
    public ResponseEntity<String> delete (@RequestParam Long iduser)/*receve os dados para deletar*/ {
    	
    	usuarioRepository.deleteById(iduser);
    	
    	return new ResponseEntity<String>("User deletado com suscesso!", HttpStatus.OK);
    	
    }
    
    
    //método para buscar um usuario na base
    @GetMapping(value = "buscaruserid")//mapeia a url
    @ResponseBody //descrição da resposta
    public ResponseEntity<Usuario> buscaruserid (@RequestParam(name = "iduser") Long iduser)/*receve os dados para consultar*/ {
    	
    	Usuario usuario = usuarioRepository.findById(iduser).get();
    	
    	return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    	
    }
    
    
  //método para buscar através das letras digitadas na base de dados
    @GetMapping(value = "buscarPorNome")//mapeia a url
    @ResponseBody //descrição da resposta
    public ResponseEntity<List<Usuario>> buscarPorNome (@RequestParam(name = "name") String name)/*receve os dados para consultar*/ {
    	
    	List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());// trim para retirar os espaços e converte para maiuscula quando for feito a consulta
    	
    	return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
    	
    }
    
    
    
    
}
