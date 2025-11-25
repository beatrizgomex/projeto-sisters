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


#### Data da medição: 23/11/2025

#### Descrição das configurações:

-   *Processador:* AMD Ryzen 7 5825U
-   *RAM:* 16 GB
-   *Armazenamento:* SSD (máquina local)


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

## TESTES DE CARGA #2

### Listagemm de Notícias

#### Tipo de operação: leitura 

#### Arquivos envolvidos:

[NoticiaController](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/controller/NoticiaController.java)

[NoticiaService](https://github.com/beatrizgomex/projeto-sisters/blob/main/src/main/java/com/uniriosi/projeto_sisters/service/NoticiaService.java)


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
#### Conclusão

De forma geral, os testes demonstram que a aplicação se comporta bem sob cargas moderadas, mantendo latências baixas e boa taxa de vazão até o patamar de 100 usuários simultâneos. A partir desse valor, começam a surgir indícios de saturação, especialmente no tempo de resposta, que apresenta picos bem acima da média.

As hipóteses levantadas indicam que os gargalos podem estar relacionados principalmente ao lado do servidor, especialmente ao limite do pool de conexões do banco de dados, ao aquecimento inicial da JVM e a possíveis pausas de Garbage Collection durante o estresse. Esses fatores podem fazer com que parte das requisições precise aguardar antes de ser processada, aumentando temporariamente a latência.

Ainda assim, o comportamento observado está dentro do esperado para uma aplicação rodando em ambiente local com recursos limitados, e mostra que o sistema tem boa escalabilidade inicial. Para cenários de produção, recomenda-se monitoramento em profundidade (APM), ajuste fino do HikariCP, parametrização do GC e possíveis otimizações de consultas, garantindo mais estabilidade mesmo sob cargas mais intensas.
