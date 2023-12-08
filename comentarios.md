# Correção P4 (commit de 25/11)

  - **GRAVE** exception genérica no app (linhas 334 e 215)
  - **Veículo GRAVE** uso mensal corrente fazendo if em tipo e dando get no uso de vaga (têm o método "ehDoMes")
  - **Veículo** relatório incorreto e sem polimorfismo.
  - **Veículo** setTipoUso não está sendo chamado
  - **Estacionamento** relatórios podem ir para stream
  - **Estacionamento** usando entryset para relatório
  - **Cliente** precisa mudar o tipo de uso dos veículos quando ele muda
  - **Uso de Vaga** horista retornando 15 fixo.
  - **Uso de Vaga** turno não está nem sabendo qual turno está verificando.
  - **App** arrecadação decrescente com código que não faz sentido. basta chamar um ordenador
  - **Usos de Vaga** sem documentacao

## Nota base do grupo 11 + tarefas

  - Requisitos corretamente implementados (classes e testes): 9/12 pontos;
  - Documentação de código: 2/3 pontos;
  - Tarefas nas aulas ao longo do projeto: 5 pontos;
  

## Colaborações
  - Alice
    - arrec media horistas ✔️
    - diagrama ⚠️
  - Fernando 
    - app relatorios ➕➖
    - app robusto ⚠️
    - valor médio por uso: entrySet x values ⚠️
    - Excecoes estac ✔️
    - Uso Mensal ✔️
  - Johnata ❌ vaga sem doc
    - estacionar no veiculo ✔️
    - diagrama ✔️
    - Uso hora ❌
  - Kimberly
    - Cliente com plano de uso ➕➖
    - dados teste ✔️
    - enum tipo uso ✔️
    - mexeu em estrutura map tornando pública sem necessidade ❌
  - Pedro
    - Estruturas de armazenamento eficientes; ➕➖ (apenas criou o mapa)
    - App arrecadacao decresc ⚠️
    - Uso turno ⚠️
  - Talisson
    - app relatorio veiculos ❌
    - relat veiculos ➕➖
	
    

## Requisitos
  - Diagrama atualizado ✔️
  - Estruturas de armazenamento eficientes; ✔️
  - Aplicativo com acesso aos requisitos principais e aos relatórios gerenciais; ✔️
  - Aplicativo com base de teste cadastrada; ✔️
  - Aplicativo com funcionamento robusto; ✔️
  - Cliente categorizado em três modalidades: horista, de turno ou mensalista. ➕➖
  - Regra de cliente horista; ⚠️
  - Regra de cliente mensalista; ⚠️
  - Regra de cliente de turno; ⚠️
  - Relatórios:
    - Veículos (data e valor de uso) ❌
    - A arrecadação total de cada um dos estacionamentos, em ordem decrescente; ⚠️
    - Quantas vezes, em média, os clientes mensalistas utilizaram o estacionamento no mês corrente; ✔️
    - Qual a arrecadação média gerada pelos clientes horistas no mês corrente; ✔️

❌
➕➖
✔️
⚠️