package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.Status;
import model.Produto;

public class ProdutoDAOImpl implements IDAO<Produto>{
	private Connection connection;
	
	public ProdutoDAOImpl(Connection connect) 
	{
		this.connection = connect;
	}

	@Override
	public int save(Produto produto) {
		if (produto.getPrice() < 0 || produto.getQuantity() < 0) return 0;
		
		int r = 0;
		String sql = "INSERT INTO produto(nome, preco, quantidade) VALUES(?,?,?)";
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql))
		{	
			pstmt.setString(1, produto.getName());
			pstmt.setDouble(2, produto.getPrice());
			pstmt.setInt(3, produto.getQuantity());
			
			r = pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return r;
	}

	@Override
	public Produto getProduto(int id) {
		Produto produto = null;
		
		try (Statement stmt = connection.createStatement();
			 ResultSet result = stmt.executeQuery("SELECT * FROM produto WHERE id = " + id))
		{
			
			while (result.next())
			{
				produto = new Produto(result.getInt(1), 
									  result.getString(2),
									  result.getDouble(3),
									  result.getInt(4));
			}
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return produto;
	}

	@Override
	public List<Produto> listProduto() {
		List<Produto> produtos = new ArrayList<>();
		
		try (Statement stmt = connection.createStatement();
			 ResultSet result = stmt.executeQuery("SELECT * FROM produto"))
		{
			
			while (result.next())
			{
				Produto p = new Produto(result.getInt(1),
										result.getString(2),
										result.getDouble(3),
										result.getInt(4));
				
				produtos.add(p);
			}
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return produtos;
	}

	@Override
	public void update(Produto produto) {
		String sql = "UPDATE produto SET nome = ?, preco = ?, quantidade = ? WHERE id = " + produto.getId();
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql))
		{	
			pstmt.setString(1, produto.getName());
			pstmt.setDouble(2, produto.getPrice());
			pstmt.setInt(3, produto.getQuantity());
			
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void remove(int id) {
		String sql = "DELETE FROM produto WHERE id = " + id;
		
		try (PreparedStatement pstmt = connection.prepareStatement(sql))
		{
			pstmt.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public Status sellProduct(int id, int quantity) {
		Produto product = getProduto(id);
		
		if (product == null) return Status.PRODUCTNOTFOUND;
		
		if (product.getQuantity() < quantity) return Status.OUTOFSTOCK;
		
		product.setQuantity(product.getQuantity() - quantity);
		
		update(product);
		
		return Status.SUCCESS;
	}
	
	

}
