<div id="divCadastrar">        
    <form class="form_padrao" name="formCadastrar" action="/siac/control?cls=Usuario&mtd=editar" method="POST">
        <ul>
            <li class="li_titulo"><h1>Alterar Senha</h1></li>
            <li class="li_corpo"><label>Senha Atual:</label><input style="width: 18.5em;" name="cmp_atual" type="password" autofocus required=""/></li>
            <li class="li_corpo"><label>Nova Senha:</label><input style="width: 18.5em;" name="cmp_nova" type="password" autofocus required=""/></li>
            <li class="li_corpo"><label>Repita a Nova Senha:</label><input style="width: 18.5em;" name="cmp_nova2" type="password" autofocus required=""/></li>
            <li class="li_ultimo">
                <button class="submit_incluir" type="submit" name="enviarFormCadastrar" value="Salvar">Alterar</button>
                <button class="submit_incluir" style="margin-left: 20px;" type="reset" name="cancelarFormCadastrar" value="Cancelar" onclick="javascript:history.back(1)">Cancelar</button>
            </li>
        </ul>
    </form>
</div>
