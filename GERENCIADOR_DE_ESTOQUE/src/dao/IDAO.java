package dao;

import java.util.List;

import main.Status;

public interface IDAO<T> {
	
	public int save(T produto);
	
	public T getProduto(int id);
	
	public List<T> listProduto();
	
	public void update(T produto);
	
	public void remove(int id);
	
	public Status sellProduct(int id, int quantity);
}
