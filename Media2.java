import javax.swing.JOptionPane;
public class Media{
	public static void main(String[] args){
		//criando um vetor de 5 posições do tipo double(real)
		double[] kaeliton = new double[5];
		double soma = 0;
		for(int contador=0;contador<=4;contador++){
			//Solicitando valores ao usuário
			String juliane = JOptionPane.showInputDialog("Entre com o "
			+(contador+1)+"o numero real: ");
			
			//Convertendo texto em double (real)
			double numConvertido = Double.parseDouble(juliane);
			
			//armazenando na posição atual do vetor
			kaeliton[contador] = numConvertido;
			
			soma = soma + kaeliton[contador];
		}
		double media = soma / 5;
		JOptionPane.showMessageDialog(null, "Media: "+media);
	}
}