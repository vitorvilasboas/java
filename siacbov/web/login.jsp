<div id="login">
    <form  class="login_form" name="formLogin" action="/siac/control?cls=Usuario&mtd=autenticar" method="POST">
        <fieldset>
            <legend><span>&nbsp;Entrar no Sistema&nbsp;</span></legend>
            <ul>
                <li><label> Usuario</label><input type="text" name="login"  /></li>
                <li><label> Senha</label><input type="password" name="senha"  /></li>
                <li class="recSenha"><a href="recuperarSenha.jsp">Esqueci a senha</a></li>
                <!--<a style=" text-decoration: none;" href="forms/primeiroAcesso.jsp">Primeiro Acesso</a>-->
            </ul>
        </fieldset>
        <ul>
            <li class="btnAcessar"><button class="submit_login" type="submit" value="Acessar" name="entrar">Acessar</button></li>
        </ul>
    </form>
</div>
