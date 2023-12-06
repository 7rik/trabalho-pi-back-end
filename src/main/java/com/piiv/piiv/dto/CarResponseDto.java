package com.piiv.piiv.dto;

import com.piiv.piiv.entities.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarResponseDto {
    private Integer id;
    private String marca;
    private String modelo;
    private String descricao;
    private int anoDeFabricacao;
    private int anoDoModelo;
    private double valor;
    private byte[] foto;
    private Usuario interessado;
    
    
    
	public byte[] getFoto() {
		return foto;
	}
	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getAnoDeFabricacao() {
		return anoDeFabricacao;
	}
	public void setAnoDeFabricacao(int anoDeFabricacao) {
		this.anoDeFabricacao = anoDeFabricacao;
	}
	public int getAnoDoModelo() {
		return anoDoModelo;
	}
	public void setAnoDoModelo(int anoDoModelo) {
		this.anoDoModelo = anoDoModelo;
	}
	public double getValor() {
		return valor;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public Usuario getInteressado() {
		return interessado;
	}
	public void setInteressado(Usuario interessado) {
		this.interessado = interessado;
	}

    
}
