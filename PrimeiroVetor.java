import java.util.Scanner;
public class PrimeiroVetor {
	public static void main(String[] args){
		Scanner entrada = new Scanner(System.in);
		int[] vetor = new int[5];//instanciando vetores
		int i;
		for(i=0;i<=4;i++){
			System.out.print("\nInforme o "+(i+1)+"o valor: ");
			int num = Integer.parseInt(entrada.nextLine());
			vetor[i] = num;
		}
		for (i=0;i<=4;i++)
			System.out.print("\nVetor["+i+"]: "+vetor[i]);
	}
}