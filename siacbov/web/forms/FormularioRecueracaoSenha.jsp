<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="java.sql.ResultSet"%>
<div id="login">        
    <form class="login_form" name="formLogin" action="/siac/control?cls=Funcionario&mtd=recuperarUsuario" method="POST">
        <fieldset>
            <legend><span>&nbsp;Esqueci Minha Senha&nbsp;</span></legend>
            <ul>
                <li><label>CPF:</label>
                    <input type="text" name="func_cpf" required pattern="^(\d{3}\.\d{3}\.\d{3}-\d{2})|(\d{11})$" placeholder="888.888.888-88"  autofocus required="" /><br />
                </li>
                <li><label>E-mail:</label>
                    <input type="email" name="func_email" required="" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" placeholder="vitor@examplo.com"/>
                </li>
            </ul>
        </fieldset>
        <ul>
            <li class="btnAcessar">
                <button class="submit_login" type="submit" name="enviarFormEditarDestinatario" value="Salvar">Enviar</button>
            </li>
            <li class="btnAcessar">
                <button class="submit_login" type="reset" name="cancelarFormCadDestinatario" value="Cancelar" onclick="javascript:history.back(1)">Voltar para Login</button>
            </li>
        </ul>
    </form>
</div>
