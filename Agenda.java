package projetoagenda;
import javax.swing.JOptionPane;
import java.util.Scanner;
public class Agenda {
    public static PessoaAgenda[] vetorAgenda = new PessoaAgenda[2];
	public static void main(String[] args) {
		int i;
		int opcao = menu();
		DirecionaAgenda(opcao);
	}

	public static int menu(){
		Scanner entrada = new Scanner(System.in);
		System.out.println("--------------------------------------------\n");
		System.out.println("|Escolha Uma operação:                     |\n");
		System.out.println("| 1 - Inserir novo Contato                 |\n");
		System.out.println("| 2 - Buscar Contato                       |\n");
		System.out.println("| 3 - Deletar Contato                      |\n");
		System.out.println("--------------------------------------------\n");
		System.out.println("Escolha: ");
		String escolha = entrada.nextLine();
				int esc = Integer.parseInt(escolha);
		return esc;
	}

	public static void DirecionaAgenda(int esc){
		switch (esc) {
			case 1:{
			 PessoaAgenda pessoa = new PessoaAgenda();
			 pessoa.nome = (JOptionPane.showInputDialog("Digite o nome da pessoa: "));
			 pessoa.endereco = (JOptionPane.showInputDialog("Digite o endereço o endereço: "));
			 pessoa.e_mail = (JOptionPane.showInputDialog("Digite o e-mail do contato: "));
			 pessoa.telefone = (JOptionPane.showInputDialog("Digite o telefone do contato: "));
			 pessoa.cpf = (JOptionPane.showInputDialog("Digite o numero do cpf: "));
			 for(int i=0; i<2; i++){
				 if(vetorAgenda[i] == null){
					vetorAgenda[i] = pessoa;
					break;
				 }
			 }
			int opcao = menu();
			DirecionaAgenda(opcao);
			break; }
			
			case 2:{

			String digitando = JOptionPane.showInputDialog("Entre com um numero do cpf: ");
			PessoaAgenda encontrado = new PessoaAgenda();
			encontrado = null;
			for (int i=0; i<2; i++){
				if(vetorAgenda[i] != null){
					if(vetorAgenda[i].cpf.equals(digitando)){
						encontrado = vetorAgenda[i];
						break;
					}
				}
			}
			if(encontrado!=null)
			JOptionPane.showMessageDialog(null,"Nome: "+encontrado.nome+"\n"+encontrado.cpf+"\n"+encontrado.e_mail+"\n"+encontrado.endereco+"\n"+encontrado.telefone);
			else
			JOptionPane.showMessageDialog(null,"Contato não  encontrado");
			int opcao = menu();
			DirecionaAgenda(opcao);
			break;
			}
			
			case 3:{
			String digitando = JOptionPane.showInputDialog("Entre com um numero do cpf: ");
			PessoaAgenda encontrado = new PessoaAgenda();
			encontrado = null;
			for (int i=0; i<2; i++){
				if(vetorAgenda[i] != null){
					if(vetorAgenda[i].cpf.equals(digitando)){
						vetorAgenda[i]=null;
						break;
					}
				}
			}
			int opcao = menu();
			DirecionaAgenda(opcao);
			break;                 
			}
		}
	}
}
    

