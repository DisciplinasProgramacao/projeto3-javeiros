# Correção

  - sem cartão de javadoc no projeto
  - **GRAVE** mais de 350 linhas para criar dados de teste!!! O que aconteceu com modularização, métodos, parâmetros, laços de repetição?
  - vale a pena criar tantas classes de exceção que somente passam strings? isso não é a recomendação em termos do Java e de qualidade
  - logo no primeiro menu já temos um erro de exceção para formato incorreto!!!
  - TipoUso tem set no valor(???)
  - Conforme falamos, "SemCliente" não é uma boa ideia
  - Vários métodos e veículos sem serem usados no app
  - **App** dá erro para qualquer estacionamento que tento usar.
  - **App** mapa de estacionamento para localização. 
  - **App** salva dados, mas não lê?
  - **Estacionamento** usando entrySet em lugar de values (sem falar que podia ser stream)
  - **Veículo** não tem que receber factory como parâmetro. para isso tem o enum
  - Strategy implementado sem interface? E cuidado: _notify_ é associado a observador e não pode ser público.
  - **Uso Turno** está com turno nulo

## Nota base 14


## Colaborações
  - Alice
    - app robustez ❌
    - javadoc ✔️
  - Fernando
    - app funcional 😐
  - Johnata
    - strategy ⚠️
    - uml com padrões ✔️
  - Kimberly
    - carga final para teste ⚠️⚠️
    - salvar dados 😐
  - Pedro
    - factory em uso de vaga 😐
    - factory em veículo ⚠️
  - Talisson
    - carga final para teste ⚠️⚠️
    - salvar dados 😐


# Requisitos:
  - Gerais: 4/6 pontos
    - cliente mudar de plano
    - estacionar e cálculo de preço
    - relatórios estacionamento
    - relatórios cliente e carro
  - Padrões de projeto: 3,5/6 pontos
    - Factory 
    - Strategy
  - App: 3,5/5 pontos
    - funcional 
    - carga de teste
    - robustez
  - Documentação: 3/3 pontos
    - diagrama 
    - javadoc 


❌
😐
✔️
⚠️