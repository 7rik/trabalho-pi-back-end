package com.piiv.piiv.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "marca", length = 50)
    private String marca;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Column(name = "descricao", length = 500)
    private String descricao;

    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "anoDeFabricacao")
    private int anoDeFabricacao;

    @Column(name = "anoDoModelo")
    private int anoDoModelo;

    @Column(name = "valor")
    private double valor;
    
    @Column
    private Integer interessado;

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

	
	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
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

	public Integer getInteressado() {
		return interessado;
	}

	public void setInteressado(Integer interessado) {
		this.interessado = interessado;
	}

    
}
