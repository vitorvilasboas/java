import java.util.Scanner;//importando biblioteca
public class questaoTres {
	public static void main(String[] args){
		double somaSalarios = 0;
		for(int contador=1; contador<=10; contador++){
			//Recebendo o salário do usuário
			Scanner entrada = new Scanner(System.in);
			System.out.print("Entre com o 1º salario: ");
			String salarioStr = entrada.nextLine();
			double salario = Double.parseDouble(salarioStr);
			somaSalarios = somaSalarios + salario;
		}
		double media = somaSalarios/10;
		System.out.println("A media dos salarios eh:"+ media);
	}
}