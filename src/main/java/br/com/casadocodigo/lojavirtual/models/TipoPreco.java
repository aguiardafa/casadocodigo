package br.com.casadocodigo.lojavirtual.models;

public enum TipoPreco {
	EBOOK("E-book"), IMPRESSO("Livro Impresso"), COMBO("Combo (E-book + Livro Impresso)");
	
	public String descricao;
	
	TipoPreco(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
}
