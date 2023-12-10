# Correção
  
  - **Histórico Cliente**: usando método que não existe
  - **Histórico Cliente**: usando informações de uso de vaga (usar método)
  - **Histórico Cliente**: três métodos iguais
  - **Histórico Cliente**: sem documentação
  - **Cliente** com get desnecessário e set desprotegido
  - **Deserializador/Serializados** desnecessário. Só chama método de outra classe, sem abstração
  - **Estacionamento** usando método sair errado
  - **UsoDeVaga** o preço deve ser cobrado por fração de hora, não de minutos
  - **UsoDeVaga** o preço não deve ser calculado/cobrado na contratação do serviço, só na saída
  - **Veículo** set desnecessário e desprotegido
  - **Veículo** usando método não existente
  - **Vaga** sem documentação
  - Testes foram mudados de lugar e não têm as importações corretas: não rodam
  - App apenas propaga exceções. Não as trata nem informa ao usuário.

## Nota base do grupo: 9

  - Contribuições
    - Alice 
      - Vaga ⚠️ incorreto e corrigido por outras pessoas (sem documentação) ⚠️
      - Teste Estacionamento ⚠️ usando muitos gets que não exisitiam
      - Histórico Cliente ⚠️⚠️⚠️ (sem documentação) ⚠️
      - Cliente: métodos x diagrama ❌
    - Fernando
      - Estacionamento p2 ➕➖
      - App inicial ✔️
      - App robusto ❌
      - Carga Dados ❌
    - Jhonata
      - Veiculo ❌ ➕➖ parcial, terminado por outra pessoa (?)
      - Teste Veiculo ➕➖ muito incompleto
      - Excecao Uso ✔️
      - Uso com servicos ⚠️
      - Enum servico ✔️
    - Kimberly
      - Cliente ⚠️
      - Teste Cliente ⚠️⚠️ quase nada
      - Serializacao ⚠️
      - Estacionamento tem código seu, a tarefa não era sua e o código está errado ❌
      - Veículo tem código seu, a tarefa não era sua e o código está errado ❌
      - App tem código seu, a tarefa não era sua e o código está errado ❌
    - Pedro
      - Estacionamento p2 ✔️
      - Teste vaga ❌ testando outras classes
      - Excecoes estacionamento ✔️
      - ~~Vaga toString~~ ❌ (jogado para o P4)
    - Talisson
      - Uso ⚠️
      - Teste Uso ✔️
      - Conformidade UML ❌

  - Tarefas nas aulas 04 e 11/10: 5 pontos;
    - Alice ➕➖ ❌
    - Fernando ➕➖ ➕➖
    - Jhonata ✔️ ➕➖
    - Kimberly ➕➖ ✔️
    - Pedro ✔️ ✔️
    - Talisson ✔️ ✔️

- Requisitos : 6/12 pontos;
- Documentação: 3/3 pontos;


## Requisitos
  - Estacionar, sair e cobrança: R$4 a cada 15 minutos, com valor limite de R$50.  
  - Serviços, tempo mínimo e cobrança 
  - Um cliente identificado tem acesso a seu histórico de uso do estacionamento.  
  - Os dados das classes existentes devem ser salvos utilizando-se serialização; 
  - App:
    - Cadastrar estacionamentos com número de vagas
    - Veículos registrados por placa e ligados a clientes. 
    - Cliente identificado com nome e com mais de um veículo. 
    - Dados de clientes e veículos salvos e carregados.
    - 3 estacionamentos
	  - Gerar aleatoriamente 50 usos de vagas
  - Relatórios:
    - Valor total arrecadado do estacionamento;
    - Valor arrecadado em determinado mês;
    - Valor médio de cada utilização do estacionamento;
    - Ranking dos clientes que mais geraram arrecadação em um determinado mês.
  - Exceções que serão tratadas no aplicativo:
    - Sair de uma vaga cujo uso já foi finalizado;
    - Estacionar em uma vaga sem haver finalizado o uso anterior;
    - Cadastrar um cliente já existente;
    - Cadastrar um veículo já existente;
  
❌
➕➖
✔️
⚠️




