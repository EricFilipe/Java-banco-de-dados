package main;

public enum Status {
	SUCCESS{

		@Override
		void printStatus() {
			System.out.println("Venda efetivada com sucesso!");
		}
		
	},
	PRODUCTNOTFOUND {
		@Override
		void printStatus() {
			System.out.println("Produto não encontrado no estoque");
		}
	},
	OUTOFSTOCK {
		@Override
		void printStatus() {
			System.out.println("Produto não tem estoque suficiente para venda");
		}
	};
	
	abstract void printStatus();
}
