import java.util.Scanner;

public class PrimeiroExemplo{//nome da classe
	public static void main(String[] args){//metodo principal
		String nome = "JULLIANY";//variavel para armazenar um valor texto
		int idade = 18;//variavel para armazenar um valor inteiro
		double peso = 50.7;//variavel para armazenar um valor real
		
		System.out.print("A pessoa cujo nome eh "+nome+" tem "+idade);
		System.out.println("anos de idade e pesa "+peso+" kilos.");
		
		//criando objeto para capturar entradas pelo teclado
		Scanner teclado = new Scanner(System.in);
		
		System.out.print("\nInforme o nome da pessoa: ");
		String entrada = teclado.nextLine();
		System.out.print("\nO nome informado foi: "+entrada);
		
		
	}//fecha metodo principal
}//fecha o bloco da classe