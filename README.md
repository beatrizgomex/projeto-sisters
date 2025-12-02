# Plataforma Web SIsters

## MEDIÇÕES DO SLA

## TESTES DE CARGA #1

### Cadastrar usuária

#### Tipo de operação: inserir

#### Arquivos envolvidos:

[UsuariaController](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/controller/UsuariaController.java)

[UsuariaService](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/service/UsuariaService.java)

[UsuariaRequest](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/controller/dto/request/UsuariaRequest.java)

[UsuariaRepository](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/infrastructure/repository/UsuariaRepository.java)

[AlunaRepository](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/controller/dto/request/UsuariaRequest.java)

[SecurityConfig](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/security/SecurityConfig.java)


#### Descrição das configurações:

-   *Processador:* AMD Ryzen 7 5825U
-   *RAM:* 16 GB
-   *Armazenamento:* SSD (máquina local)

### Medição 1

#### Data da medição: 23/11/2025

#### Testes de carga (SLA):

[Script de teste](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/usuarias_todas.js)

[Resultados](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/inserir20.jpg)
[Resultados](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/inserir100.jpg)
[Resultados](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/inserir500.jpg)

#### Latência média por carga

-   20 VUs: *6.19 ms*
-   100 VUs: *7.72 ms*
-   500 VUs: *96.3 ms*
[Gráfico](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/testeinserirl.jpg)

#### Vazão média por carga

-   20 VUs: *186.18*
-   100 VUs: *897.88*
-   500 VUs: *2303.08*
[Gráfico](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/testeinserirv.jpg)

#### Concorrência

-   20 VUs: min=20 / max=20
-   100 VUs: min=100 / max=100
-   500 VUs: min=500 / max=500
[Gráfico](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/testeinserirc.jpg)

#### Hipoteses

Os testes mostram que, acima de 100 usuários virtuais, a latência aumenta bastante. Isso pode acontecer por alguns motivos: o event loop do Node.js pode estar sendo bloqueado por operações síncronas ou muito pesadas de CPU; o sistema pode estar enfrentando gargalos de I/O, especialmente em operações de banco de dados; o hardware também pode ter chegado ao limite, com CPU atingindo 100% em cargas maiores e a falta de cache faz com que operações repetidas fiquem mais lentas.

#### Otimização realizada

O código atualizado é superior porque melhora a velocidade, a limpeza e a robustez do sistema. O e-mail agora é processado e verificado apenas uma vez, e a checagem de e-mail duplicado é feita diretamente pelo banco de dados usando a restrição UNIQUE, o que elimina uma consulta extra e reduz o gargalo de processamento. A lógica de validação de domínio (UNIRIO vs. EDU.UNIRIO) foi simplificada, tornando as regras mais claras e evitando caminhos de execução complexos. Além disso, a inclusão de um try/catch protege a operação de salvamento contra erros de concorrência, tornando o fluxo mais confiável e pronto para alta demanda.

### Medição 2

## TESTES DE CARGA #2

### Listagem de Notícias

#### Tipo de operação: leitura 

#### Arquivos envolvidos:

[NoticiaController](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/controller/NoticiaController.java)

[NoticiaService](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/service/NoticiaService.java)

[application.properties](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/resources/application.properties)

#### Descrição das configurações:
- Processador: 	Intel(R) Core(TM) i3-7100U
- RAM: 8 GB
- Armazenamento: SSD(máquina local)

#### Testes de carga (SLA):

[Script de teste](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/teste-leitura-noticia-50VUs.js)
[Script de teste](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/teste-leitura-noticia-100VUs.js)

[Resultados](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/leitura50.jpg)
[Resultados](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/leitura100.jpg)

#### Latência média por carga
-   50 VUs: *17.14ms*
-   100 VUs: *14.52 ms*
[Gráfico](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/testeleitural.jpg)

#### Vazão média por carga
- 50 VUs: *26.28*
- 100 VUs: *96.80*
[Gráfico](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/testeleiturav.jpg)

#### Concorrência

- 50 VUs: min=50 / max=50
- 100 VUs: min=100 / max=100

[Gráfico](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/testeleiturac1.jpg)

### Hipóteses
**Pool de Conexões do Banco:** Mesmo sendo uma operação de leitura, o aumento de usuários pode ter pressionado o número máximo de conexões disponíveis no HikariCP (padrão do Spring, geralmente configurado para 10). Com 50 ou 100 usuários simultâneos, algumas requisições podem ter aguardado fila, elevando a latência ocasionalmente.

**Warm-up da JVM:** Em testes de estresse curtos, as primeiras requisições podem apresentar maior tempo de resposta devido ao período de aquecimento e otimização da JVM (JIT), o que justifica pequenas variações iniciais antes da estabilização dos tempos de resposta.

**Garbage Collection:** Como o volume de respostas cresce com o número de usuários, é possível que a JVM tenha executado ciclos de Garbage Collection durante o pico de carga, resultando em oscilações momentâneas de latência.

### Medição 2

#### Data da medição: 30/11/2025

#### Latência média por carga
-   50 VUs: *79.91 ms*
-   100 VUs: *65.40 ms*
  <img width="790" height="490" alt="image" src="https://github.com/user-attachments/assets/fefd284e-ccbb-48c2-8c9d-9f0afe547cbb" />


#### Vazão média por carga
- 50 VUs: *45.10*
- 100 VUs: *90.68*
<img width="790" height="490" alt="image" src="https://github.com/user-attachments/assets/379b864e-51ab-4ed7-9635-df980a654b1b" />


#### Concorrência

- 50 VUs: min=50 / max=50
- 100 VUs: min=100 / max=100
 <img width="860" height="596" alt="image" src="https://github.com/user-attachments/assets/0ea64b91-e60b-4d63-9c90-e341c387c34d" />


### Melhorias/otimizações
O aumento do maximumPoolSize do Hikari de 10 para 30 no arquivo application.properties reduziu significativamente a contenção por conexão.
#### Latência
Comparando a Medição 1 com os resultados atuais:

50 VUs: aumento de 17.14 ms → 79.91 ms

100 VUs: aumento de 14.52 ms → 65.40 ms

Mesmo com o aumento, ambos os cenários permaneceram dentro do SLA, já que os valores de p95 ficaram abaixo de 500 ms (125.4 ms em 50 VUs e 280.97 ms em 100 VUs).

#### Vazão

Comparação entre as medições:

50 VUs: 26.28 → 45.10 req/s

100 VUs: 96.80 → 90.68 req/s

Em 50 VUs houve ganho significativo, enquanto em 100 VUs ocorreu uma leve redução, possivelmente devido a saturações momentâneas do serviço ou limitações externas ao script.

<img width="1390" height="590" alt="image" src="https://github.com/user-attachments/assets/ddb87b5d-4cff-4b1b-aaa1-2877bd4ce907" />


#### Concorrência

50 VUs: min = 50 / max = 50

100 VUs: min = 100 / max = 100

Isso confirma que o sistema suportou a concorrência configurada sem quedas de VUs, interrupções ou erros relacionados a limitação de usuários simultâneos.

### Cadastrar Usuária

#### Tipo de operação: inserir 

#### Arquivos envolvidos:

[UsuariaController](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/controller/UsuariaController.java)

[UsuariaService](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/service/UsuariaService.java)

[UsuariaRequest](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/controller/dto/request/UsuariaRequest.java)

[UsuariaRepository](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/infrastructure/repository/UsuariaRepository.java)

[AlunaRepository](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/controller/dto/request/UsuariaRequest.java)

[SecurityConfig](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/security/SecurityConfig.java)

#### Descrição das configurações:

-   *Processador:* AMD Ryzen 7 5825U
-   *RAM:* 16 GB
-   *Armazenamento:* SSD (máquina local)

[Script de teste](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/usuarias_todas.js)

#### Latência média por carga
-   20 VUs: *3.02 ms*
-   100 VUs: *2.65 ms*
[Gráfico](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/latenciaMedia2.jpg)

#### Vazão média por carga
- 20 VUs: *193.13*
- 100 VUs: *969.06*
[Gráfico](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/vazao2.jpg)

#### Concorrência

- 20 VUs: min=20 / max=20
- 100 VUs: min=100 / max=100
[Gráfico](https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/concorrencia2.jpg)


### Hipóteses

Os testes atuais demonstram que, até 100 Usuários Virtuais - VUs, o sistema está operando em um estado ideal, com a Vazão escalando de forma linear 193.13 req/s -> 969.06 req/s e a Latência Média permanecendo extremamente baixa (e até ligeiramente menor, de 3.02 ms para 2.65 ms), indicando utilização eficiente dos recursos e um warm-up bem-sucedido da JVM.

### Medição 2

#### Data da medição: 30/11/2025

#### Latência média por carga
-   20 VUs: *3.02 ms*
-   100 VUs: *2.65 ms*
<img width="790" height="490" alt="image" src="https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/latenciaMedia2.jpg" />

  #### Vazão média por carga
- 20 VUs: *193.13*
- 100 VUs: *969.06*
<img width="790" height="490" alt="image" src="https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/vazao2.jpg" />
 
 #### Concorrência

- 20 VUs: min=20 / max=20
- 100 VUs: min=100 / max=100
<img width="790" height="490" alt="image" src="https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/concorrencia2.jpg" />

### Melhorias/otimizações
A intervenção realizada na arquitetura do backend eliminou um gargalo que estava presente mesmo em cargas moderadas, resultando em uma execução de código muito mais rápida e eficiente.
#### Latência
Comparando a Medição 1 com os resultados atuais:

20 VUs: redução de 6.19 ms → 3.02 ms

100 VUs: redução de 7.72 ms → 2.65 ms

A redução superior a 50% em ambos os cenários é um indicativo de que a otimização foi altamente eficaz. O sistema agora é muito mais rápido para processar e responder às requisições sob as mesmas condições de carga.
<img width="1390" height="590" alt="image" src="https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/comparacaoLatencia.jpg" />

#### Vazão

Comparação entre as medições:

20 VUs: 186.18 req/s → 193.13 req/s

100 VUs: 897.88 req/s → 969.06 req/s

O sistema não apenas ficou mais rápido, mas também conseguiu processar mais requisições por segundo, especialmente no cenário de 100VUs, onde a capacidade aumentou em mais de 71 req/s. Isso comprova que a otimização permitiu que o sistema aproveitasse melhor os recursos disponíveis para completar mais tarefas

<img width="1390" height="590" alt="image" src="https://github.com/beatrizgomex/projeto-sisters/blob/main/testes-carga/comparacaoVazao.jpg" />


#### Concorrência

20 VUs: min = 20 / max = 20

100 VUs: min = 100 / max = 100

Isso confirma que o sistema suportou a concorrência configurada sem quedas de VUs, interrupções ou erros relacionados a limitação de usuários simultâneos, sendo mais estável e eficiente. 


