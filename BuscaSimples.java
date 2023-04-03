import javax.swing.JOptionPane;
public class BuscaSimples{
	public static void main(String[] args){
		int[] vetor = new int[5];
		int contador;
		boolean encontrou = false;
		for(contador=0; contador<=4; contador++){
			vetor[contador] = (2*contador) + (4*4);
			//JOptionPane.showMessageDialog(null, "Vetor["+contador+"]: 
			//"+vetor[contador]);
			System.out.println("Vetor["+contador+"]: "+vetor[contador]);
		}
		String digitado = JOptionPane.showInputDialog("Entre com um numero inteiro: ");
		int num = Integer.parseInt(digitado);
		for(contador=0; contador<=4; contador++){
			if(vetor[contador] == num){
				encontrou = true;
				break;
			}	
		}
		if(encontrou == true)
			JOptionPane.showMessageDialog(null, "O valor informado está no vetor.");
		else
			JOptionPane.showMessageDialog(null, "O valor não foi encontrado");
	}
}