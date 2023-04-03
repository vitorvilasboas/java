import java.util.Scanner;
public class Frutas{//utilizando estruturas de repetição
	public static void main (String args[]){
		int contador;
		Scanner entrada = new Scanner(System.in);
		//Laço de repetição -> for
		for(contador=1;contador<=5;contador++){//de 1 até 5 faça
			System.out.print("Entre com o nome da "+contador+"a fruta: ");
			String fruta = entrada.nextLine();
			System.out.println("Fruta "+contador+": "+fruta);
		}
		System.out.println("***************************************");
		//Laço de repetição -> while
		contador = 1;
		while(contador<=5){//enquanto menor que 5
			System.out.print("Entre com o nome da "+contador+"a fruta: ");
			String fruta = entrada.nextLine();
			System.out.println("Fruta "+contador+": "+fruta);
			contador = contador + 1;
			//contador++;
		}
	}
}