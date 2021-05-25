package br.com.alura.jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.alura.jdbc.modelo.Categoria;
import br.com.alura.jdbc.modelo.Produto;


public class CategoriaDAO {

	private Connection connection;

	public CategoriaDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Categoria> listar() {
		try {
			List<Categoria> categorias = new ArrayList<>();
			
			String sql = "SELECT * FROM categoria";
			
			try (PreparedStatement stm = connection.prepareStatement(sql)) {
				stm.execute();
				
				try (ResultSet rs = stm.getResultSet()) {
					while (rs.next()) {
						Categoria c = new Categoria(rs.getInt(1), rs.getString(2));
						categorias.add(c);
					}
				}
			}
			
			return categorias;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Categoria> listarComProdutos() {
		try {
			HashMap<Integer, Categoria> ids = new HashMap<>();
			List<Categoria> categorias = new ArrayList<>();
			
			String sql = "SELECT c.id, c.nome, p.id, p.nome, p.descricao " + 
			             "FROM categoria c " + 
			             "INNER JOIN produto p " + 
					     "   ON p.categoria_id = c.id";
			
			try (PreparedStatement stm = connection.prepareStatement(sql)) {
				stm.execute();
				
				try (ResultSet rs = stm.getResultSet()) {
					while (rs.next()) {
						Categoria cat = ids.get(rs.getInt(1));
						if (cat == null) {
							cat = new Categoria(rs.getInt(1), rs.getString(2));
							ids.put(rs.getInt(1), cat);
							categorias.add(cat);
						}
						Produto p = new Produto(rs.getInt(3), rs.getString(4), rs.getString(5));
						p.setCategoria(cat);
						cat.adicionar(p);
					}
				}
			}
			
			return categorias;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
