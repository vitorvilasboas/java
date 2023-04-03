import java.util.Scanner;//importando biblioteca
public class questaoCinco {
	public static void main(String[] args){
		String[] vetorPlacas = new String[20];
		for(int contador=0; contador<=4; contador++){
			Scanner entrada = new Scanner(System.in);
			System.out.print("Entre com "+(contador+1)+"a placa: ");
			String placa = entrada.nextLine();
			vetorPlacas[contador] = placa;
		}
		System.out.println("\nPLACAS CADASTRADAS: ");
		for(int contador=0; contador<=19; contador++){
			System.out.println("Placa "+(contador+1)+": "+vetorPlacas[contador]);
		}
	}
}