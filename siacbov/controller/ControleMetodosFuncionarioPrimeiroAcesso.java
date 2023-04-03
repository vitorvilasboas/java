package br.edu.cio.controller;

import br.edu.cio.controller.ControleMetodosComuns;
import static br.edu.cio.controller.ControleMetodosUsuario.encrypta;
import br.edu.cio.model.AreaPastagem;
import br.edu.cio.model.Funcionario;
import br.edu.cio.model.Lote;
import br.edu.cio.model.Propriedade;
import br.edu.cio.model.Usuario;
import br.edu.cio.model.dao.FuncionarioDAO;
import br.edu.cio.model.dao.LoteDAO;
import br.edu.cio.model.dao.PropriedadeDAO;
import br.edu.cio.model.dao.UsuarioDAO;
import br.edu.cio.model.dao.mysql.FuncionarioDAOMySQL;
import br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL;
import br.edu.cio.util.DAOFactory;
import br.edu.cio.util.MetodosUteis;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class ControleMetodosFuncionarioPrimeiroAcesso implements ControleMetodosComuns {

    FuncionarioDAO acessoBDFuncionario = DAOFactory.gerarFuncionarioDAO();
    PropriedadeDAO acessoBDPropriedade = DAOFactory.gerarPropriedadeDAO();

    @Override

    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
         Funcionario f = new Funcionario();
         String cpf = request.getParameter("func_cpf");
         String email = request.getParameter("func_email");
         String nome = request.getParameter("func_nome");
         String usuario = request.getParameter("func_login");
         String senha = request.getParameter("func_senha");
         //        f.setId(Integer.parseInt(""));
         //        f.setMatricula(request.getParameter(""));
         f.setNome(request.getParameter("func_nome"));
         f.setCpf(request.getParameter("func_cpf"));
         f.setRg(request.getParameter("func_rg"));
         f.setOrgao_emissor(request.getParameter("func_orgao_emissor"));
         f.setPis(request.getParameter("func_pis"));
         f.setPai(request.getParameter("func_pai"));
         f.setMae(request.getParameter("func_mae"));
         f.setEndereco(request.getParameter("func_endereco"));
         f.setCidade(request.getParameter("func_cidade"));
         f.setUf(request.getParameter("func_uf"));
         f.setDt_nascimento(request.getParameter("func_dt_nascimento"));
         f.setDt_admissao(request.getParameter("func_dt_admissao"));
         f.setCargo(request.getParameter("func_cargo"));
         f.setFone(request.getParameter("func_fone"));
         f.setEmail(request.getParameter("func_email"));
         f.setLogin(request.getParameter("func_login"));
         f.setSenha(request.getParameter("func_senha"));

         Propriedade prop = acessoBDPropriedade.buscarPorIdDAO(Integer.parseInt(request.getParameter("func_propriedade")));
         f.setPropriedade(prop);

         try {

         acessoBDFuncionario.cadastrarDAO(f);

         SimpleEmail emailSimples = new SimpleEmail();

         emailSimples.setHostName("smtp.gmail.com");//servidor smtp dp gmail
         emailSimples.setSmtpPort(465);//porta do servido smtp do gmail
         emailSimples.setFrom("alexifto@gmail.com", "SIAC - Sistema Bovino");//autenticação do email do sistema
         emailSimples.addTo(email);//email do destinatario
         emailSimples.setSubject("SIAC - Confirmação de cadastro");//assunto
         emailSimples.setMsg("De: alexifto@gmail.com \n para: " + email + " \n Sr(a): " + nome + ", seu cadastro foi realizado com sucesso ! \n Dados para acessso: \n Usuario: " + usuario + " \n Senha: " + senha + " ");
         emailSimples.setSSL(true);
         emailSimples.setAuthentication("alexifto@gmail.com", "a3l3e3x3");
         emailSimples.send();//finalza todos os metodos enviando o email

         request.setAttribute("mensagem", " Funcionario cadastrado com sucesso !");
         request.getRequestDispatcher("principal?d=forms&a=buscar&f=funcionario").forward(request, response);//retorna para a index apresentando o erro

         } catch (ServletException | IOException ex) {
         request.setAttribute("mensagem", "Funcionario não cadastrado.");
         request.getRequestDispatcher("PrimeiroAcesso.jsp").forward(request, response);//retorna para a index apresentando o erro
         } catch (EmailException ex) {
         Logger.getLogger(ControleMetodosFuncionarioPrimeiroAcesso.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        Funcionario func = acessoBDFuncionario.buscarPorIdDAO(id);
        request.setAttribute("funcionarios", func);
        request.getRequestDispatcher("principal?d=forms&a=editar&f=funcionario").forward(request, response);//despacha para a proxima página

    }

    @Override
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição

        if (!acessoBDFuncionario.excluirDAO(id)) {
            request.setAttribute("mensagem", "Erro ao tentar excluir Funcionário.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=funcionario").forward(request, response);

        } else {
            request.setAttribute("mensagem", "Funcionário excluido com sucesso.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=funcionario").forward(request, response);
        }
    }

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String valorBusca = request.getParameter("cmp_bsc_funcionario");//recebe o dado informado no formLogin do index
        List<Funcionario> resultado = acessoBDFuncionario.buscarDAO(valorBusca);
        if (resultado != null) {
            request.setAttribute("funcionariosEncontrados", resultado);
            request.getRequestDispatcher("principal?d=forms&a=resultBusca&f=funcionario").forward(request, response);//despacha para a proxima página
        } else {
            request.setAttribute("mensagem", "Funcionario não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=funcionario").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    @Override
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));//recuperando id para edição
        int id2 = Integer.parseInt(request.getParameter("id"));

        String email = request.getParameter("func_email");
        String nome = request.getParameter("func_nome");
        String usuario = request.getParameter("func_login");
        String senha = request.getParameter("func_senha");
        Funcionario f = new Funcionario();

        f.setId(id);
//        f.setMatricula(request.getParameter(""));
        f.setNome(request.getParameter("func_nome"));
        f.setCpf(request.getParameter("func_cpf"));
        f.setRg(request.getParameter("func_rg"));
        f.setOrgao_emissor(request.getParameter("func_orgao_emissor"));
        f.setPis(request.getParameter("func_pis"));
        f.setPai(request.getParameter("func_pai"));
        f.setMae(request.getParameter("func_mae"));
        f.setEndereco(request.getParameter("func_endereco"));
        f.setCidade(request.getParameter("func_cidade"));
        f.setUf(request.getParameter("func_uf"));
        f.setDt_nascimento(request.getParameter("func_dt_nascimento"));
        f.setDt_admissao(request.getParameter("func_dt_admissao"));
        f.setCargo(request.getParameter("func_cargo"));
        f.setFone(request.getParameter("func_fone"));
        f.setEmail(request.getParameter("func_email"));
        f.setLogin(request.getParameter("func_login"));
        f.setSenha(request.getParameter("func_senha"));
        Propriedade prop = acessoBDPropriedade.buscarPorIdDAO(Integer.parseInt(request.getParameter("func_propriedade")));
        f.setPropriedade(prop);
        try {
            acessoBDFuncionario.editarDAO(f);
            SimpleEmail emailSimples = new SimpleEmail();

            emailSimples.setHostName("smtp.gmail.com");//servidor smtp dp gmail
            emailSimples.setSmtpPort(465);//porta do servido smtp do gmail
            emailSimples.setFrom("alexifto@gmail.com", "SIAC - Sistema Bovino");//autenticação do email do sistema
            emailSimples.addTo(email);//email do destinatario
            emailSimples.setSubject("SIAC - Edição Confirmada");//assunto
            emailSimples.setMsg("De: alexifto@gmail.com \n para: " + email + " \n Sr(a): " + nome + ", a edição do seu cadastro foi realizado com sucesso ! \n Dados para acessso: \n Usuario: " + usuario + " \n Senah: " + senha + " ");
            emailSimples.setSSL(true);
            emailSimples.setAuthentication("alexifto@gmail.com", "a3l3e3x3");
            emailSimples.send();//finalza todos os metodos enviando o email
//            f = acessoBDFuncionario.buscarPorIdDAO(id2);
            request.setAttribute("funcionarios", f);
            request.setAttribute("mensagem", "Funcionário atualizada com sucesso!");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=funcionario").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", "Área de Pastagem  não atualizada.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=funcionario").forward(request, response);//retorna para a index apresentando o erro
        } catch (EmailException ex) {
            Logger.getLogger(ControleMetodosFuncionarioPrimeiroAcesso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void detalhar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String valorBusca = request.getParameter("codigo");//recuperando id para edição
        List<Funcionario> resultado = acessoBDFuncionario.buscarDAO(valorBusca);
        if (resultado != null) {
            request.setAttribute("funcionariosEncontrados", resultado);
            request.getRequestDispatcher("principal?d=forms&a=detalhar&f=funcionario").forward(request, response);//despacha para a proxima página
        } else {
            request.setAttribute("mensagem", "Funcionario não cadastrado.");
            request.getRequestDispatcher("principal?d=forms&a=buscar&f=funcionario").forward(request, response);//retorna para a index apresentando o erro
        }

    }

    @Override
    public void recuperarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Funcionario f = new Funcionario();
        f.setCpf(request.getParameter("func_cpf"));
        f.setEmail(request.getParameter("func_email"));

        String cpf = request.getParameter("func_cpf");
        String email = request.getParameter("func_email");

        Funcionario autenticado = acessoBDFuncionario.autenticarDAO(cpf, email);//chamando o método autenticar dentro da classe UsuarioDAOMySQL que implementa UsuarioDAO

        if (autenticado != null) {
            try {
                String email2 = request.getParameter("func_email");
                SimpleEmail emailSimples = new SimpleEmail();

                emailSimples.setHostName("smtp.gmail.com");//servidor smtp dp gmail
                emailSimples.setSmtpPort(465);//porta do servido smtp do gmail
                emailSimples.setFrom("alexifto@gmail.com", "SIAC - Sistema Bovino");//autenticação do email do sistema
                emailSimples.addTo(email);//email do destinatario
                emailSimples.setSubject("SIAC - Recuperação de Senha");//assunto
                String cpf1 = request.getParameter("func_cpf");
                ResultSet rs = MetodosUteisDAOMySQL.getFuncionarioPorCPF(cpf1);

                while (rs.next()) {
                    emailSimples.setMsg("Usuario :  " + rs.getString("func_login") + " \n Senha : " + rs.getString("func_senha") + "");

                }

                emailSimples.setSSL(true);
                emailSimples.setAuthentication("alexifto@gmail.com", "a3l3e3x3");
                emailSimples.send();//finalza todos os metodos enviando o email
            } catch (Exception e) {
                System.out.println("Email não enviado !");
            }

            request.getRequestDispatcher("principal.jsp?d=&f=home").forward(request, response);//despacha para a proxima página
        } else {
            System.out.println("Este email não pertence a nenhum usuario cadastrado no sistema !");
            request.setAttribute("mensagem", "CPF ou EMAIL inválidos!");
            request.getRequestDispatcher("index.jsp").forward(request, response);//retorna para a index apresentando o erro
        }

    }

    @Override
    public void carregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
