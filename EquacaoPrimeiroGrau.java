import java.util.Scanner;
public class EquacaoPrimeiroGrau{
	public static void main(String[] args){
		Scanner entrada = new Scanner(System.in);
		System.out.print("Informe o valor de 'a': ");//solicita valor
		String aStr = entrada.nextLine();//captura valor digitado
		double a = Double.parseDouble(aStr);//converte Texto para Real
		
		System.out.print("Informe o valor de 'b': ");//solicita valor
		String bStr = entrada.nextLine();//captura valor digitado
		double b = Double.parseDouble(bStr);//converte Texto para Real

	}
	
	/* 
	CRIAR AQUI UM MÉTODO QUE RECEBA OS VALORES 'a' e 'b' e 
	CALCULE A EQUAÇÃO DO 1º GRAU. EM SEGUIDA IMPRIMA A RAIZ NA TELA.
	*/
}