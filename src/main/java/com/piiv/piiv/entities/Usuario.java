package com.piiv.piiv.entities;

import java.util.Date;

import jakarta.persistence.*;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "login", length = 50)
	private String login;
	@Column(name = "senha", length = 50)
	private String senha;
	@Column(name = "nome", length = 50)
	private String nome;
	private Boolean ativo;
	private Boolean isAdmin;
	
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNascimento;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;
	
	public Usuario() {}
	
    // Constructor
    public Usuario(String login, String senha, String nome, Boolean ativo, Date dataNascimento, Date dataCadastro, Boolean isAdmin) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.ativo = ativo;
        this.dataNascimento = dataNascimento;
        this.dataCadastro = dataCadastro;
    }
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Boolean getAtivo() {
		return ativo;
	}
	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
}