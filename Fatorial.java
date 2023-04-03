import java.util.Scanner;
public class Fatorial{
	public static void main (String args[]){
		Scanner entrada = new Scanner(System.in);
		System.out.print("Entre com um numero inteiro: ");
		String numeroStr = entrada.nextLine();
		int fatorial = Integer.parseInt(numeroStr);
		for(int i=fatorial-1;i>=1;i--){// i-- decrementa i em 1 (i = i - 1)
			fatorial = fatorial * i;
		}
		System.out.println("\nO fatorial eh: "+fatorial);
	}
}