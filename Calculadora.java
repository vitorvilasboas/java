import java.util.Scanner;
public class Calculadora{
	public static void main (String args[]){
		String opcao = menu();
		direciona(opcao);
	}
	public static String menu(){
		Scanner entrada = new Scanner(System.in);
		System.out.print("--------------------------------\n");
		System.out.print("|	Escolha uma operação:		\n");
		System.out.print("|	1 - Soma					\n");
		System.out.print("|	2 - Subtracao				\n");
		System.out.print("|	3 - Multiplicacao			\n");
		System.out.print("|	4 - Divisao					\n");
		System.out.print("--------------------------------\n");
		System.out.print("Escolha: ");
		String escolha = entrada.nextLine();
		return escolha;
	}
	public static void direciona(String escolha){
		switch(escolha){
			case "1": {
				double resultado = soma(pegaValor1(), pegaValor2());
				System.out.print("O resultado eh: "+resultado);
				break;
			}
			case "2": {
				double resultado = subtracao(pegaValor1(), pegaValor2());
				System.out.print("O resultado eh: "+resultado);
				break;
			}
			case "3": {
				double resultado = multiplicacao(pegaValor1(), pegaValor2());
				System.out.print("O resultado eh: "+resultado);
				break;
			}
			case "4": {
				double resultado = divisao(pegaValor1(), pegaValor2());
				System.out.print("O resultado eh: "+resultado);
				break;
			}
			default: {
				menu();
				break;
			}
		}
	}
	public static double pegaValor1(){
		Scanner entrada = new Scanner(System.in);
		System.out.print("Entre com o 1o valor: ");
		String valor1 = entrada.nextLine();
		double v1 = Double.parseDouble(valor1);
		return v1;
	}
	public static double pegaValor2(){
		Scanner entrada = new Scanner(System.in);
		System.out.print("Entre com o 2o valor: ");
		String valor2 = entrada.nextLine();
		double v2 = Double.parseDouble(valor2);
		return v2;
	}
	public static double soma(double v1, double v2){
		double soma = v1 + v2;
		return soma;
	}
	public static double multiplicacao(double v1, double v2){
		double multiplicacao = v1 * v2;
		return multiplicacao;
	}
	public static double subtracao(double v1, double v2){
		double subtracao = v1 - v2;
		return subtracao;
	}
	public static double divisao(double v1, double v2){
		double divisao = v1 / v2;
		return divisao;
	}
}