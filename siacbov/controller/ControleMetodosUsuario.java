package br.edu.cio.controller;

import br.edu.cio.controller.ControleMetodosComuns;
import br.edu.cio.model.Funcionario;
import br.edu.cio.model.Usuario;
import br.edu.cio.model.dao.FuncionarioDAO;
import br.edu.cio.model.dao.UsuarioDAO;
import br.edu.cio.util.DAOFactory;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


public class ControleMetodosUsuario implements ControleMetodosComuns {

    UsuarioDAO acessoBDUsuario = DAOFactory.gerarUsuarioDAO();

    @Override
    public void cadastrar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario u = new Usuario();

        u.setLogin(request.getParameter("cmp_usu_login"));
        u.setSenha(request.getParameter("cmp_usu_senha"));
        FuncionarioDAO acessoBDFuncionario = DAOFactory.gerarFuncionarioDAO();
        Funcionario f = acessoBDFuncionario.buscarPorIdDAO(Integer.parseInt(request.getParameter("cmp_usu_funcionario")));
        u.setFuncionario(f);
        /* CÓDIGO EMAIL SEND
        try {
            acessoBDUsuario.inserirDAO(u);
            SimpleEmail emailSimples = new SimpleEmail();

            emailSimples.setHostName("smtp.gmail.com");
            emailSimples.setSmtpPort(465);
            emailSimples.setFrom("alexifto@gmail.com", "Alex");
            emailSimples.addTo("loteamentoresidencialsalmon@hotmail.com");
            emailSimples.setSubject("SIAC - Confirmação de cadastro");
            emailSimples.setMsg("Senhor Alex, seu cadastro foi realizado com sucesso !");
            emailSimples.setSSL(true);
            emailSimples.setAuthentication("alexifto@gmail.com", "a3l3e3x3");
            emailSimples.send();
            request.setAttribute("mensagem", "Usuario cadastrado com sucesso, em breve lhe enviaremos um email de confirmação de cadastro !");
            request.getRequestDispatcher("principal?f=index").forward(request, response);//retorna para a index apresentando o erro
        } catch (ServletException | IOException ex) {
            request.setAttribute("mensagem", "Usuario não cadastrado.");
            request.getRequestDispatcher("principal?f=index").forward(request, response);//retorna para a index apresentando o erro
            Logger.getLogger(ControleMetodosUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    @Override
    public void editar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("mensagem", "Senha alterada com sucesso.");
        request.getRequestDispatcher("principal?d=&a=&f=home").forward(request, response);//retorna para a index apresentando o erro
    }

    @Override
    public void excluir(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void buscar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    public void autenticar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");//recebe o dado informado no formLogin do index
        String senha = null;//recebe o dado informado no formLogin do index

        try {
            senha = encrypta(request.getParameter("senha"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //UsuarioDAO acessoBD = DAOFactory.gerarUsuarioDAO();
        UsuarioDAO acessoBD = DAOFactory.gerarUsuarioDAO();
        Usuario autenticado = acessoBD.autenticarDAO(login, senha);//chamando o método autenticar dentro da classe UsuarioDAOMySQL que implementa UsuarioDAO

        if (autenticado != null) {
            HttpSession sessao = request.getSession();
            sessao.setAttribute("usuario_logado", autenticado);//USUÁRIO DE SESSÃO
            sessao.setAttribute("idLogado", autenticado.getId());
            request.getRequestDispatcher("principal.jsp?d=&f=home").forward(request, response);//despacha para a proxima página 
        } else {
            System.out.println("Não autenticou o usuario");
            request.setAttribute("mensagem", "Usuário ou senha inválidos!");
            request.getRequestDispatcher("index.jsp").forward(request, response);//retorna para a index apresentando o erro
        }
    }

    public static String encrypta(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BigInteger hash = new BigInteger(1, md.digest(password.getBytes()));
        return String.format("%32x", hash);
    }

    @Override
    public void editarSalvar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void detalhar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void recuperarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            
            String email2 = request.getParameter("email");
            SimpleEmail emailSimples = new SimpleEmail();
            
            emailSimples.setHostName("smtp.gmail.com");
            emailSimples.setSmtpPort(465);
            emailSimples.setFrom("alexifto@gmail.com", "Alex");
            emailSimples.addTo(email2);
            emailSimples.setSubject("SIAC - Confirmação de cadastro");
            emailSimples.setMsg("Recuperação de Senha \n"
                    + "SIAC - Sistema Bovino \n"
                    + "de: alexifto@gmail.com \n"
                    + "para: "+email2+ " \n"
                    + "Senha de Recuperação: a1b2c3d4");
            emailSimples.setSSL(true);
            emailSimples.setAuthentication("alexifto@gmail.com", "a3l3e3x3");
            emailSimples.send();
            
            /* Site para downloads das bibliotecas comons email e java mail.
            http://commons.apache.org/proper/commons-email/download_email.cgi
            http://www.oracle.com/technetwork/java/javasebusiness/downloads/java-archive-downloads-eeplat-419426.html#javamail-1.4.7-oth-JPR
            */
        } catch (EmailException ex) {
            Logger.getLogger(ControleMetodosUsuario.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Override
    public void carregar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
