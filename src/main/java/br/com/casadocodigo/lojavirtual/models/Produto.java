package br.com.casadocodigo.lojavirtual.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Produto {
	//trabalhando com JPQ é sempre obrigatório ter Id
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String titulo;
	private String Descricao;
	private int paginas;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return Descricao;
	}
	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		Descricao = descricao;
	}
	/**
	 * @return the paginas
	 */
	public int getPaginas() {
		return paginas;
	}
	/**
	 * @param paginas the paginas to set
	 */
	public void setPaginas(int paginas) {
		this.paginas = paginas;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Produto [titulo=" + titulo + ", Descricao=" + Descricao + ", paginas=" + paginas + "]";
	}
}
