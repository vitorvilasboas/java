import java.util.Scanner;//importando biblioteca
public class questaoQuatro {
	public static void main(String[] args){
		Scanner entrada = new Scanner(System.in);
		System.out.print("Entre com o valor do produto: ");
		String valorStr = entrada.nextLine();
		double valor = Double.parseDouble(valorStr);
		System.out.print("Entre com a qtd de parcelas: ");
		String qtdParcStr = entrada.nextLine();
		double qtdParc = Double.parseDouble(qtdParcStr);
		double valorParcelas = 0;
		if(qtdParc > 10){
			valorParcelas = (valor/qtdParc) * 1.05;			
		} else {
			valorParcelas = valor/qtdParc;
		}
		System.out.println("Valor Parcelas: "+valorParcelas);
	}
}