<script language="javascript">
    c=0
    du="";
    function escondediv(dv,n){		
        for(i=1;i<=n;i++){			
            if(i==dv ){
                if(du!=dv){
                    document.getElementById('mdiv'+i).style.display="inline"
                    du=dv
                }else{
                    du=""
                    document.getElementById('mdiv'+i).style.display="none"
                }
            }else{
                document.getElementById('mdiv'+i).style.display="none"				  					
            }								
        }
    }
    function reveza(qq){
        document.getElementById(qq).className="itens_menu_r"
    }
    function volta(qq){
        document.getElementById(qq).className="itens_menu"
    }
    n_divs='9' //Nº máximo de itens de menu
</script>
   
<div id="titulo_menu"  >
    <a href="javascript:void(escondediv('1',n_divs))">Cadastros</a>
</div>
    <div id="mdiv1"  class="submenu" style="display:none">
        <ul>
            <li><a href="principal?d=forms&a=buscar&f=propriedade" onmouseover="reveza('um')" onmouseout="volta('um')" >Propriedade</a></li>
            <li><a href="principal?d=forms&a=buscar&f=areaPastagem" >Áreas de Pastagem</a></li>
            <li><a href="principal?d=forms&a=buscar&f=animal" >Animais</a></li>
            <li><a href="principal?d=forms&a=buscar&f=lote" >Lotes de Animais</a></li>
            <li><a href="principal?d=forms&a=buscar&f=funcionario" >Funcionários</a></li>
            <li><a href="principal?d=forms&a=buscar&f=undProcessamento"  >Unidades de Processamento</a></li>
            <li><a href="principal?d=forms&a=buscar&f=sensor" >Sensores</a></li>
            <li><a href="principal?d=forms&a=buscar&f=destinatario">Destinatários</a></li>
        </ul>
    </div>

<div id="titulo_menu"  >
    <a href="javascript:void(escondediv('2',n_divs))">Monitoramento</a>
</div>
    <div id="mdiv2"  class="submenu" style="display:none">
        <ul>
            <li><a href="principal?d=pages&a=&f=previsaoCio" onmouseover="reveza('um')" onmouseout="volta('um')">Previsão de Cio</a></li>
            <li><a href="principal?d=pages&f=gfcAtividade&a=buscar">Atividade Animal</a></li>
            <li><a href="forms/grafTeste.jsp">Histórico de Atividade/Leitura</a></li>
        </ul>
    </div>

<div id="titulo_menu"  >
    <a href="javascript:void(escondediv('3',n_divs))">Ciclos Estrais</a>
</div>
    <div id="mdiv3"  class="submenu" style="display:none">
        <ul>
            <li><a href="principal?d=forms&a=cadastrar&f=cio" onmouseover="reveza('um')" onmouseout="volta('um')">Informar Ciclo</a></li>
            <li><a href="/siac/control?cls=Cio&mtd=listarPendentes">Avaliar Ciclos</a></li>
            <li><a href="principal?d=forms&a=buscar&f=cio">Ciclos Registrados</a></li>
            <li><a href="principal?d=forms&a=buscar&f=alerta">Alertas Emitidos</a></li> 
            <li><a href="/siac/control?cls=Monitoramento&mtd=carregar">Calendário Estral</a></li>
        </ul>
    </div>

<div id="titulo_menu"  >
    <a href="javascript:void(escondediv('4',n_divs))">Gráficos</a>
</div>
    <div id="mdiv4"  class="submenu" style="display:none">
        <ul>
            <li><a href="principal?d=pages&f=gfcAtividade&a=buscar">Atividade Animal</a></li>
        </ul>
    </div>

<div id="titulo_menu" >
    <a href="javascript:void(escondediv('5',n_divs))">Relatórios</a>
</div>
    <div id="mdiv5"  class="submenu" style="display:none">
        <ul>
            <li><a href="principal?d=pages&a=&f=relatorios">Relatório Personalizado</a></li>
        </ul>
    </div>

<div id="titulo_menu">
    <a href="/siac/control?cls=Config&mtd=carregar">Configuração</a>
</div>


<!--
<div id="titulo_menu"  >
    <a href="javascript:void(escondediv('5',n_divs))">Inseminações</a>
</div>
    <div id="mdiv5"  class="submenu" style="display:none">
        <ul>
            <li><a href="" onmouseover="reveza('um')" onmouseout="volta('um')">Informar Inseminação</a></li>
            <li><a href="">Animais Prontos</a></li>
            <li><a href="">Inseminações Registradas</a></li>
            <li><a href="">Calendário de Inseminações</a></li> 
            <li><a href="">Avaliar Inseminação</a></li>
        </ul>
    </div>

<div id="titulo_menu"  >
    <a href="javascript:void(escondediv('6',n_divs))">Gestações e Partos</a>
</div>
    <div id="mdiv6"  class="submenu" style="display:none">
        <ul>
            <li><a href="" onmouseover="reveza('um')" onmouseout="volta('um')">Informar Gestação</a></li>
            <li><a href="">Informar Parto</a></li>
            <li><a href="">Previsão de Partos</a></li>
            <li><a href="">Gestações Registradas</a></li>
            <li><a href="">Partos Registrados</a></li>
        </ul>
    </div>

<div id="titulo_menu"  >
    <a href="javascript:void(escondediv('7',n_divs))">Produção</a>
</div>
    <div id="mdiv7"  class="submenu" style="display:none">
        <ul>
            <li><a href="" onmouseover="reveza('um')" onmouseout="volta('um')">Ordenhas</a></li>
            <li><a href="">Períodos de Lactação</a></li>
        </ul>
    </div>
-->