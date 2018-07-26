package br.com.casadocodigo.lojavirtual.models;

public class CarrinhoItem {

	private TipoPreco tipoPreco;
	private Produto produto;

	public CarrinhoItem(Produto produto, TipoPreco tipoPreco) {
		this.setProduto(produto);
		this.setTipoPreco(tipoPreco);
	}

	public TipoPreco getTipoPreco() {
		return tipoPreco;
	}

	public void setTipoPreco(TipoPreco tipoPreco) {
		this.tipoPreco = tipoPreco;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		result = prime * result + ((tipoPreco == null) ? 0 : tipoPreco.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CarrinhoItem)) {
			return false;
		}
		CarrinhoItem other = (CarrinhoItem) obj;
		if (produto == null) {
			if (other.produto != null) {
				return false;
			}
		} else if (!produto.equals(other.produto)) {
			return false;
		} else if (tipoPreco != other.tipoPreco) {
			return false;
		}
		return true;
	}

}
