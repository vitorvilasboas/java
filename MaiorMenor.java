import java.util.Scanner;
import javax.swing.JOptionPane;
public class MaiorMenor {
	public static void main(String[] args){
		Scanner entrada = new Scanner(System.in);
		int[] vetor = new int[5];//instanciando vetores
		int i;
		for(i=0;i<=4;i++){
			//Solicitando o nº ao usuário de forma gráfica
			String num = JOptionPane.showInputDialog("Informe o "+(i+1)+"o numero inteiro:");
			//convertendo oq o kra digitou em inteiro
			int num2 = Integer.parseInt(num);
			//Mostrando na tela de forma gráfica
			JOptionPane.showMessageDialog(null, "Olha oq vc digitou: "+num2);
			//System.out.print("\nInforme o "+(i+1)+"o numero inteiro: ");
			//int num = Integer.parseInt(entrada.nextLine());
			//vetor[i] = num;
		}
		int maior = vetor[0];
		int menor = vetor[0];
		for(i=0;i<=4;i++){
			if(vetor[i] > maior)
				maior = vetor[i];
			if(vetor[i] < menor)
				menor = vetor[i];
		}
		System.out.print("\nMaior: "+maior);
		System.out.print("\nMenor: "+menor);
	}	
}