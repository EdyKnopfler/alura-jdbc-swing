package br.com.alura.jdbc.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import br.com.alura.jdbc.dao.CategoriaDAO;
import br.com.alura.jdbc.dao.ProdutoDAO;
import br.com.alura.jdbc.factory.ConnectionFactory;
import br.com.alura.jdbc.modelo.Categoria;
import br.com.alura.jdbc.modelo.Produto;

public class ProdutoController {
	
	private ProdutoDAO produtoDAO;
	private CategoriaDAO categoriaDAO;
	
	public ProdutoController() {
		Connection connection = new ConnectionFactory().recuperarConexao();
		produtoDAO = new ProdutoDAO(connection);
		categoriaDAO = new CategoriaDAO(connection);
	}

	public void deletar(Integer id) {
		produtoDAO.deletar(id);
	}

	public void salvar(Produto produto) {
		produtoDAO.salvar(produto);
	}

	public List<Produto> listar() {
		List<Produto> produtos = new ArrayList<>();
		List<Categoria> categorias = categoriaDAO.listarComProdutos();
		
		for (Categoria c : categorias) {
			produtos.addAll(c.getProdutos());
		}
		
		return produtos;
	}

	public void alterar(String nome, String descricao, Integer id) {
		produtoDAO.alterar(nome, descricao, id);
	}
}
