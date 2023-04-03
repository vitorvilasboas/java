/*PROGRAMA chamado CalculaNotas que imprima a média
de tres notas atribuidas por você.*/
import java.util.Scanner;
public class CalculaNotas{//classe
	public static void main (String args[]){//método principal	
		double somaRetornada = solicitarAoUsuario();//invocar método solicitarAoUsuario
		calcularMedia(somaRetornada);//passando como Parâmetro
	}
	public static double solicitarAoUsuario(){
		Scanner entrada = new Scanner(System.in);
		/* Solicitando ao usuário a 1ª Nota*/
		System.out.print("Entre com a primeira nota: ");
		String nota1 = entrada.nextLine();
		double n1 = Double.parseDouble(nota1);
		/* Solicitando ao usuário a 2ª Nota*/
		System.out.print("\nEntre com a segunda nota: ");
		String nota2 = entrada.nextLine();
		double n2 = Double.parseDouble(nota2);
		/* Solicitando ao usuário a 3ª Nota*/
		System.out.print("\nEntre com a terceira nota: ");
		String nota3 = entrada.nextLine();
		double n3 = Double.parseDouble(nota3);
		
		double soma = n1+n2+n3;
		return soma;
	}
	public static void calcularMedia(double somaParametro){
		/*Calculando a média a partir das notas informadas*/
		double media = (somaParametro)/3;
		System.out.print("\nMedia: "+media);//imprime a média
		if(media >= 6){
			System.out.println("\n\nAPROVADO");
		} else {
			System.out.println("\n\nREPROVADO");
		}
	}
	
}	




	
	

