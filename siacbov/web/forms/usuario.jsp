<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="java.sql.ResultSet"%>
<link rel="stylesheet" media="screen" href="css/cad_form.css" />
<%  switch (request.getParameter("a")) {
        case "cadastrar": {
%>



<div id="formCadastro">        
    <form class="form_padrao" name="formCadUsuario" action="/siac/control?cls=Usuario&mtd=inserir" method="POST">
       <ul>
           <li>
               <h2>Cadastro de Usuario</h2>
           </li>
           <li>
           <label> Login:</label>
           <input type="text" name="cmp_usu_login" value="" size="30" />
           </li>
           <li>
           <label>Senha de Acesso:</label>
            <input type="password" name="cmp_usu_senha" value="" size="30" />
            </li>
            <li>
           <label>Funcionario</label>
            <select name="cmp_usu_funcionario" type="text" autofocus required="">
                <option>Selecione...</option>
                <%
                    ResultSet rs2 = MetodosUteisDAOMySQL.getFuncionario();
                    while (rs2.next()) {
                %>
                <option value="<%= rs2.getString("func_id")%>">  <%= rs2.getString("func_nome")%> </option>

                <%
                    }
                %>
            </select>
           </li>
           <li>
               <button class="submit" type="submit" value="Enviar" name="enviarFormCadUsuario" >Salvar</button>
           </li>
       </ul>
    </form>
</div>
<%
        break;
    }
    case "editar": {
%>

<%
        break;
    }

    case "excluir": {
%>

<%
        break;
    }

    case "listar": {
%>

<%
        break;
    }

    case "buscar": {
%>
<div id="formBuscarUsuario">        
   <form class="form_padrao" action="/siac/control?cls=Usuario&mtd=buscar" method="POST">
        <ul>
            <li>
                <h1>Usuarios</h1>
            </li>
            <li>
                <input placeholder="Informe o Código " type="text" name="cmp_bsc_usuario"  size="50" />
                <button class="submit" type="submit" name="btn_bsc_usuario">Buscar Usuario</button>
            </li>
            <li>
                <button class="submit" >
                    <a style="text-decoration: none;" href="principal?d=forms&a=cadastrar&f=usuario"> Incluir Usuario </a>
                </button></li>

        </ul>
    </form>
</div>
<%
        break;
    }

    case "aut": {
%>

<%
            break;
        }

        default:
            break;
    }
%>