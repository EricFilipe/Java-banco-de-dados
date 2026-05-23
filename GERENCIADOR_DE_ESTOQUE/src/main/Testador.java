package main;

import java.util.List;
import java.util.Scanner;

import dao.ProdutoDAOImpl;
import factory.ConnectionFactory;
import model.Produto;

public class Testador {
	
	private static Scanner sc = new Scanner(System.in);
	
	public static void menu(ProdutoDAOImpl productDAO)
	{
		while (true)
		{
			System.out.println("Escolha a operação: ");
			System.out.println("1- Cadastrar novo produto");
			System.out.println("2- Listar todos os produtos");
			System.out.println("3- Atualizar um produto(por id)");
			System.out.println("4- Excluir produto");
			System.out.println("5- Vender produto");
			System.out.println("0- Sair");
			int option = sc.nextInt();
			
			switch (option)
			{
				case 1:
					save(productDAO);
					break;
				
				case 2:
					listProdutos(productDAO);
					break;
					
				case 3:
					update(productDAO);
					break;
					
				case 4:
					delete(productDAO);
					break;
					
				case 5:
					sellProduct(productDAO);
					break;
					
				case 0:
					System.exit(0);
					
				default:
					System.out.println("Opção inválida");
					System.out.println();
			}
		}
	}
	
	private static void save(ProdutoDAOImpl productDAO)
	{
		Produto p = new Produto();
		
		System.out.println("Informe nome, preço e quantidade, nessa ordem: ");
		p.setName(sc.next());
		p.setPrice(sc.nextDouble());
		p.setQuantity(sc.nextInt());
		
		if (productDAO.save(p) > 0)
		{
			System.out.println("Produto salvo");
			System.out.println();
		}
		else
		{
			System.out.println("Erro ao salvar produto. Verifique as informações inseridas e tente novamente");
			System.out.println();
		}
	}
	
	private static void listProdutos(ProdutoDAOImpl productDAO)
	{
		List<Produto> productList = productDAO.listProduto();
		productList.forEach(p -> System.out.println(p));
		System.out.println();
	}
	
	private static void update(ProdutoDAOImpl productDAO)
	{
		System.out.println("Informe o id do produto o qual deseja atualizar: ");
		int id = sc.nextInt();
		
		Produto productToUpdate = productDAO.getProduto(id);
		
		System.out.println("O que deseja atualizar?");
		System.out.println("1- Nome");
		System.out.println("2- Preço");
		System.out.println("3- Quantidade");
		int option = sc.nextInt();
		
		switch (option)
		{
			case 1:
				System.out.println("Novo nome: ");
				productToUpdate.setName(sc.next());
				break;
				
			case 2:
				System.out.println("Novo preço: ");
				productToUpdate.setPrice(sc.nextDouble());
				break;
				
			case 3:
				System.out.println("Nova quantidade: ");
				productToUpdate.setQuantity(sc.nextInt());
				break;
			
			default:
				System.out.println("Opção inválida");
		}
		
		productDAO.update(productToUpdate);
		System.out.println("Produto atualizado");
		System.out.println();
	}
	
	private static void delete(ProdutoDAOImpl productDAO)
	{
		System.out.println("Informe o id do produto que deseja excluir: ");
		productDAO.remove(sc.nextInt());
		System.out.println("Produto removido");
		System.out.println();
	}
	
	private static void sellProduct(ProdutoDAOImpl productDAO)
	{
		System.out.println("Informe o id do produto a ser vendido e sua respectiva quantidade");
		Status status = productDAO.sellProduct(sc.nextInt(), sc.nextInt());
		status.printStatus();
		System.out.println();
	}
	
	public static void main(String[] args) {
		ConnectionFactory connect = new ConnectionFactory();
		ProdutoDAOImpl productDAO = new ProdutoDAOImpl(connect.openConnection());
		Testador.menu(productDAO);
		connect.closeConnection();
	}
	
	

}
