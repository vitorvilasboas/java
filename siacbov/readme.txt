OBSERVAÇÕES SOBRE O DESENVOLVIMENTO DO SISTEMA:

Simulador de Sensoriamento
Autenticação
Barra de Menus Superior: Perfil do Usuário, Documentação (Em construção), Sobre (Em construção)
Menu Cadastros: Propriedade, Áreas de Pastagem, Lotes, Animais, Und Processamento, Sensores, Funcionários, Destinatários. 
Menu Configurações: Contém os parâmetros desejáveis para as funções do sistema. Média de dias entre cios...
Menu Monitoramento: Status Próximo Cio, Leituras do Animal, 
Menu Ciclos Estrais: Informar Ciclo, Avaliar Cio, Ciclos Registrados, Alertas Emitidos, Calendário Estral.
Menu Gráficos: Gráfico Atividade Animal
Menu Relatórios

Ao cadastrar o animal, devem ser informados dados iniciais que servirão de padrão para o software. Esses dados devem ser atribuídos aos respectivos campos da tabela “info_animal”:
- Data do Início do último cio  campo “info_dt_ini_ult_cio”
- Data do Fim do último cio  campo “info_dt_fim_ult_cio”
- Intervalo Padrão de anestro (em dias) campo “info­_intervalo_padrao_anestro”
- Perfil Padrão de Atividade do Animal (Muito Calmo, Calmo, Normal, Agitado, Muito Agitado)  essa informação deve ser processada e inserida em forma de passadas no campo “info_media_passos_hora” sendo que: Muito Calmo = 30 passadas, Calmo = 40 passadas, Normal = 60 passadas, Agitado = 80 passadas, Muito agitado = 110 passadas.
A partir dos dados acima, outras informações devem ser inferidas como:
 - a idade do animal, a partir da data de nascimento e a data atual,
- a previsão de inicio do próximo cio, a partir da data do último cio, do intervalo padrão de anestro e da qtd de dias atuais em anestro  campo “info_prev_ini_prox_cio”. (Esta informação também deve ser registrada na tabela “previsão_cio” no campo “prevc_data_hora”.)
- “info_dias_em_anestro” a partir da data e da hora do ultimo cio;
- “info_tempo_atual_anestro”, a partir dos dias em anestro ( curto (0 - 15 dias), normal (15 a 25 dias), longo (25 a 35 dias) , muito longo (Acima de 35 dias) )

A CADA CICLO O SISTEMA DEVE: 
1. Analisar o arquivo middleware e verificar se existem registros não gravados no banco
2. Consultar a tabela “sensor” a fim de saber quais os sensores possuem o campo “sen_ativo” com valor 1
3. Gravar os registros dos sensores ATIVOS na tabela “leitura”.
4. Recuperar o valor dos campos “cfg_intervalo_entre_leituras”, “cfg_perc_min_atv_media”, e “cfg_perc_min_atv_alta” da tabela config.
5. Através do vinculo entre cada sensor e o animal, analisar o valor do campo “info_media_passos_hora” na tabela “info_animal” e o valor do campo “lei_qtd_passadas” do ultimo registro relacionado a cada sensor ativo na tabela “leitura”. 
6. Aplicar as fórmulas de inferência: 
variacao = (lei_qtd_passadas/cfg_intervalo_entre_leituras)-info_media_passos_hora.
perc_variacao = variação * 100 / info_media_passos_hora.
7. Classificar a atividade animal no campo “atv_classificacao” da tabela “atividade_animal” a partir dos seguintes predicados:
	Baixa  se perc_variacao  < cfg_perc_min_atv_media;
	Média  se cfg_perc_min_atv_media <= perc_variacao  < cfg_perc_min_atv_alta;
	Alta  se perc_variacao => cfg_perc_min_atv_alta.
8. Fazer a mesma classificação no campo “info_status_atividade” da tabela info_animal.
9. Recuperar as informações dos campos “info_status_cio”, “info_prev_ini_prox_cio” (PCio),  “info_tempo_atual_anestro” (TA) e “info_status_atividade” (ATV) da tabela “info_animal”.
ATUALIZA O TEMPO EM ANESTRO
10. Inferir sobre o cio, caso, o status_cio seja “0” e algum dos predicados abaixo ocorram(Lógica Fuzzy):
PCio=data_atual.... (Suspeita de Cio por data de previsão)
	ATV = media  &&  TA (muito longo)... Aviso cio demorando ou algo assim
	ATV = alta && TA (!= curto)
11. Caso alguma das situações anteriores aconteçam deve-se emitir um alerta na tela (Obs.: Posteriormente, enviar email ou SMS aos destinatários cadastrados).
12. Inserir na tabela “cio” um registro com status de suspeito ou previsto.
13. Atualizar o conhecimento nas tabelas info_animal, log_cio, previsão_cio.
Obs.: Basear-se sempre na data e hora configurados no sistema.
- Previsões de Início do Próximo Cio na tabela previsão_cio
	Quando o animal é cadastrado ou editado e o ciclo estral é encerrado
- Configurações: Classificação do tempo em anestro (pelo usuário) (OK)
	Mínimo de dias para classificação como NORMAL (12 a 17) (OK)
	Mínimo de dias para classificação como LONGO (23 a 26) (OK)
	Mínimo de dias para classificação como MUITO LONGO (29 a 35) (OK)
- Calendário Estral (Ciclos Confirmados, Encerrados e Previsões de Próximo Cio) (OK)