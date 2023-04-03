<%@page import="br.edu.cio.model.dao.mysql.MetodosUteisDAOMySQL"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.edu.cio.model.Funcionario"%>
<%@page import="br.edu.cio.model.dao.mysql.FuncionarioDAOMySQL"%>
<%@page import="br.edu.cio.model.dao.FuncionarioDAO"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="funcionarios" scope="request" class="br.edu.cio.model.Funcionario"/>

<%
    Funcionario f = new Funcionario();
    ArrayList<Funcionario> listaFuncionario = (ArrayList<Funcionario>) request.getAttribute("funcionariosEncontrados");
%>


<link rel="stylesheet" media="screen" href="css/cad_form.css" >
<div id="formCadastrarFuncionario">        
    <!-- <h1>Funcionario - Incluir</h1> -->
    <br>
    <form class="facaseucadastro_form" name="formCadastrarFuncionario" action="/siac/control?cls=FuncionarioPrimeiroAcesso&mtd=cadastrar" method="POST">
        <ul>
            <li>
                <h2>Faça seu Cadastro</h2>
            </li>
            <!--li>
                <label for="func_id">ID:</label>
                <input type="text" name="func_id" required="" name="numbers" pattern="[0-9]+$" max="30" min="1" title="Apenas números inteiros!" />
            </li> 
            <li>
                <label for="func_matricula">Matricula Nº:</label>
                <input type="text" name="func_matricula" required="" name="numbers" pattern="[0-9]+$" max="27" min="1" title="Preencha com números!" />
            </li-->

            <li>
                <label for="func_nome">Nome Completo:</label>
                <input type="text" name="func_nome"  autofocus required="" />
            </li>
            <li>
                <label for="func_cpf">CPF:</label>
                <input type="text" name="func_cpf" required pattern="^(\d{3}\.\d{3}\.\d{3}-\d{2})|(\d{11})$" placeholder="888.888.888-88" /><br />
            </li>

            <li>
                <label for="func_rg">RG:</label>
                <input type="text" name="func_rg" required="" name="numbers" pattern="[0-9]+$" max="35" min="1" title="Preencha com números!" />
            </li>

            <li>
                <label for="func_orgao_emissor">Órgão emissor:</label>
                <input type="text" name="func_orgao_emissor" required="" />
            </li>

            <li>
                <label for="func_pis">PIS:</label>
                <input type="text" name="func_pis" required="" name="numbers" title="Preencha com números!" />
            </li>

            <li>
                <label for="func_pai">Pai:</label>
                <input type="text" name="func_pai" required="" />
            </li>

            <li>
                <label for="func_mae">Mãe:</label>
                <input type="text" name="func_mae" required="" />
            </li>

            <li>
                <label for="func_endereco">Endereço:</label>
                <input type="text" name="func_endereco" required="" />
            </li>

            <li>
                <label for="func_cidade">Cidade:</label>
                <input type="text" name="func_cidade" required="" />
            </li>

            <li>        
                <label>UF:</label>
                <select name="func_uf" id="func_uf" style="width: 20.5em">
                    <option value="AC">AC</option><option value="AL">AL</option><option value="AM">AM</option><option value="AP">AP</option><option value="BA">BA</option><option value="CE">CE</option>
                    <option value="DF">DF</option><option value="ES">ES</option><option value="GO">GO</option><option value="MA">MA</option><option value="MG">MG</option><option value="MS">MS</option>
                    <option value="MT">MT</option><option value="PA">PA</option><option value="PB">PB</option><option value="PE">PE</option><option value="PI">PI</option><option value="PR">PR</option>
                    <option value="RJ">RJ</option><option value="RN">RN</option><option value="RO">RO</option><option value="RR">RR</option><option value="RS">RS</option><option value="SC">SC</option>
                    <option value="SE">SE</option><option value="SP">SP</option><option value="TO">TO</option>
                </select>
            </li>

            <li>
                <label for="func_dt_nascimento">Data de Nascimento:</label>
                <input type="date" name="func_dt_nascimento" required="" />
            </li>

            <li>
                <label for="func_dt_admissao">Data de Admissão:</label>
                <input type="date" name="func_dt_admissao" required="" />
            </li>

            <li>
                <label for="func_cargo">Cargo:</label>
                <input type="text" name="func_cargo" required="" />
            </li>

            <li>
                <label for="func_fone">Telefone</label><br />
                <input type="tel" name="func_fone" required="" maxlength="15"  pattern="\([0-9]{2}\) [0-9]{4,6}-[0-9]{3,4}$" /> 
            </li>

            <li>
                <label for="func_email">E-mail:</label>
                <input type="email" name="func_email" required="" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$" placeholder="john_doe@example.com"/>
            </li>
            <li>
                <label> Login:</label>
                <input type="text" name="func_login"  />
            </li>
            <li>
                <label>Senha de Acesso:</label>
                <input type="password" name="func_senha"  />
            </li>
            <li>
                <label>Propriedade:</label>
                <select name="func_propriedade" style="width: 20.5em" type="text">
                    <option value="null">Selecione...</option>
                    <%
                        ResultSet rs = MetodosUteisDAOMySQL.getPropriedade();
                        while (rs.next()) {
                    %>
                    <option value="<%= rs.getString("prop_id")%>"> <%= rs.getString("prop_codigo")%> - <%= rs.getString("prop_nome")%></option>

                    <%
                        }
                    %>
                </select>
            </li>
            <li>
                <button class="submit" type="submit" name="cadFunc" value="Salvar">Salvar</button>
                <button class="submit" type="reset" name="cancelarFormCadDestinatario" value="Cancelar" onclick="javascript:history.back(1)">Voltar para Login</button>

            </li>



        </ul>  


    </form>
    <br><br><br><br><br><br>
</div>

