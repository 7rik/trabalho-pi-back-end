package com.piiv.piiv.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.piiv.piiv.dto.UsuarioLoginDto;
import com.piiv.piiv.dto.UsuarioRegistroDto;
import com.piiv.piiv.entities.Usuario;
import com.piiv.piiv.repository.UsuarioRepository;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@GetMapping("/users")
	public ResponseEntity<List<Usuario>> getAllUsers() {
		try {
			List<Usuario> users = new ArrayList<Usuario>();

			usuarioRepository.findAll().forEach(users::add);
 
			if (users.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<Usuario> getUserById(@PathVariable("id") Integer id) {
		Optional<Usuario> userData = usuarioRepository.findById(id);

		if (userData.isPresent()) {
			return new ResponseEntity<>(userData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/users")
	public ResponseEntity<Usuario> createUser(@RequestBody UsuarioRegistroDto user) {
		Usuario usuarioDTO = usuarioRepository.findByLogin(user.getLogin());
		if(usuarioDTO != null) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
		try {
			Usuario paraCriar = new Usuario();
			paraCriar.setAtivo(user.getAtivo());
			paraCriar.setDataCadastro(new Date());
			paraCriar.setDataNascimento(user.getDataNascimento());
			paraCriar.setIsAdmin(user.getIsAdmin());
			paraCriar.setLogin(user.getLogin());
			paraCriar.setNome(user.getNome());
			paraCriar.setSenha(user.getSenha());
			paraCriar.setId(null);

			Usuario _user = usuarioRepository.save(paraCriar);
			return new ResponseEntity<>(_user, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/users/validate-login")
	public ResponseEntity<Usuario> validateLogin(@RequestBody UsuarioLoginDto userDTO) {
		try {
			Usuario user = usuarioRepository.findByLogin(userDTO.getUsername());
			
			if (user.getSenha().equals(userDTO.getPassword())) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<Usuario> updateUser(@PathVariable("id") Integer id, @RequestBody Usuario user) {
		Optional<Usuario> userData = usuarioRepository.findById(id);

		if (userData.isPresent()) {
			Usuario _user = userData.get();
			_user.setLogin(user.getLogin());
			_user.setSenha(user.getSenha());
			_user.setNome(user.getNome());
			_user.setAtivo(user.getAtivo());
			_user.setDataNascimento(user.getDataNascimento());
			return new ResponseEntity<>(usuarioRepository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PatchMapping("/users/{id}/admin")
	public ResponseEntity<Usuario> toggleOnUserAsAdmin(@PathVariable("id") Integer id) {
		Optional<Usuario> userData = usuarioRepository.findById(id);

		if (userData.isPresent()) {
			Usuario _user = userData.get();
			_user.setIsAdmin(true);
			return new ResponseEntity<>(usuarioRepository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/users/{id}/admin")
	public ResponseEntity<Usuario> toggleOffUserAsAdmin(@PathVariable("id") Integer id) {
		Optional<Usuario> userData = usuarioRepository.findById(id);

		if (userData.isPresent()) {
			Usuario _user = userData.get();
			_user.setIsAdmin(false);
			return new ResponseEntity<>(usuarioRepository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PatchMapping("/users/{id}/activate")
	public ResponseEntity<Usuario> activateUser(@PathVariable("id") Integer id) {
		Optional<Usuario> userData = usuarioRepository.findById(id);

		if (userData.isPresent()) {
			Usuario _user = userData.get();
			_user.setAtivo(true);
			return new ResponseEntity<>(usuarioRepository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/users/{id}/activate")
	public ResponseEntity<Usuario> deactivateUser(@PathVariable("id") Integer id) {
		Optional<Usuario> userData = usuarioRepository.findById(id);

		if (userData.isPresent()) {
			Usuario _user = userData.get();
			_user.setAtivo(false);
			return new ResponseEntity<>(usuarioRepository.save(_user), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Integer id) {
		try {
			usuarioRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/users")
	public ResponseEntity<HttpStatus> deleteAllUsers() {
		try {
			usuarioRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}