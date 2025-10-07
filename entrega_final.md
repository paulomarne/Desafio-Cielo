# Prova Técnica - Desenvolvedor Sênior - Resolução Completa

Este documento consolida todas as respostas, análises, documentação e lógicas de desenvolvimento solicitadas na prova técnica. A resolução está dividida nas seções correspondentes às partes do teste.

---



# Respostas da Prova Técnica

## Parte 1: Questões de Múltipla Escolha

| Questão | Resposta Correta | Justificativa |
| :--- | :--- | :--- |
| 1 | B) Executar efeitos colaterais em resposta a mudanças de estado | `LaunchedEffect` é uma função de composição que permite executar funções de suspensão (side effects) de forma segura dentro do ciclo de vida de um Composable. Ele é acionado quando o Composable entra na composição e é cancelado quando sai, sendo relançado se uma de suas chaves (keys) mudar. |
| 2 | B) ViewModel | Na arquitetura MVVM, o `ViewModel` é responsável por manter e gerenciar o estado da UI, sobrevivendo a mudanças de configuração, e por conter a lógica de negócios, delegando o acesso a dados para o `Repository`. |
| 3 | B) Cria uma classe de configuração que pode registrar beans manualmente | A anotação `@Configuration` indica que a classe é uma fonte de definições de beans. Em conjunto com a anotação `@Bean` em seus métodos, ela permite a criação e configuração de beans gerenciados pelo contêiner Spring. |
| 4 | C) Protege o sistema contra falhas em serviços dependentes | O padrão Circuit Breaker monitora chamadas para serviços externos. Se as falhas atingem um determinado limiar, ele "abre o circuito", interrompendo as chamadas e evitando falhas em cascata, o que aumenta a resiliência do sistema. |
| 5 | B) Centralizar e versionar configurações de múltiplos serviços | O Spring Cloud Config oferece um serviço centralizado para gerenciar as propriedades de configuração de aplicações em um ambiente distribuído, permitindo que as configurações sejam versionadas em um repositório Git. |
| 6 | C) Traduzir chamadas externas para o formato interno da aplicação | Na Arquitetura Hexagonal, os "Adapters" são a camada externa que interage com o mundo exterior (UI, banco de dados, APIs). Eles traduzem as interações específicas de uma tecnologia para chamadas de método nos "Ports" do núcleo da aplicação. |
| 7 | B) Permite que módulos de alto nível não dependam de módulos de baixo nível | O Princípio da Inversão de Dependência (DIP) estabelece que módulos de alto nível (que contêm a lógica de negócio) não devem depender de módulos de baixo nível (detalhes de implementação), mas sim de abstrações. Isso desacopla o código, tornando-o mais flexível e fácil de manter. |

---

## Parte 2: Questões Dissertativas

### 1. Kotlin + Compose: State e remember

O uso combinado de `State` e `remember` é o pilar da reatividade no Jetpack Compose. O `State` é um tipo de dado observável. Qualquer Composable que leia o valor de um objeto `State` se inscreve para receber atualizações. Quando o valor desse `State` muda, o Compose automaticamente agenda uma "recomposição" para todos os Composables inscritos, garantindo que a interface reflita o novo estado.

Por sua vez, a função `remember` armazena um objeto na memória durante a composição inicial e o mantém através das recomposições subsequentes. Sem o `remember`, qualquer variável local dentro de um Composable seria reiniciada a cada vez que a UI fosse redesenhada, perdendo seu estado.

Ao unir os dois com `remember { mutableStateOf(...) }`, criamos uma fonte de verdade que sobrevive ao ciclo de vida do Composable. A atualização do valor (`.value`) desse estado notifica o Compose, que de forma eficiente e inteligente, redesenha apenas as partes da UI que dependem daquele dado.

**Exemplo Simples:**

```kotlin
@Composable
fun CounterScreen() {
    // 'count' é um estado que sobrevive a recomposições
    var count by remember { mutableStateOf(0) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Este Text é recomposto sempre que 'count' muda
        Text(text = "Contagem: $count", fontSize = 24.sp)

        Button(onClick = { count++ }) { // Ação que modifica o estado
            Text("Incrementar")
        }
    }
}
```

### 2. Kotlin + Compose: ViewModel e StateFlow

O `ViewModel` e o `StateFlow` são componentes da arquitetura moderna do Android que, quando usados com o Jetpack Compose, criam uma estrutura de aplicação robusta, testável e de fácil manutenção, baseada no padrão de Unidirectional Data Flow (UDF).

O `ViewModel` atua como um intermediário entre a lógica de negócios (geralmente em Repositories) e a UI. Sua principal vantagem é ser consciente do ciclo de vida, o que significa que ele sobrevive a mudanças de configuração, como a rotação da tela, preservando o estado da UI sem a necessidade de recarregar os dados.

O `StateFlow` é um fluxo de dados observável e com estado, ideal para expor o estado da UI do `ViewModel` para os Composables. Ele sempre tem um valor inicial e emite o valor mais recente para seus coletores. Na UI, usamos a função `collectAsState()` para converter o `StateFlow` em um `State` do Compose, conectando o `ViewModel` ao sistema de reatividade do Compose.

Essa arquitetura melhora a aplicação da seguinte forma:
- **Separação de Responsabilidades:** A UI (Compose) é responsável apenas por exibir o estado e notificar eventos do usuário. O `ViewModel` gerencia o estado e a lógica. O `Repository` gerencia os dados.
- **Testabilidade:** O `ViewModel` pode ser testado de forma isolada da UI, e a lógica de negócios pode ser validada independentemente de qualquer framework Android.
- **Consistência de Dados:** Com o UDF, o estado flui em uma única direção (`ViewModel` -> UI) e os eventos fluem na direção oposta (UI -> `ViewModel`), tornando o fluxo de dados previsível e menos propenso a bugs.

### 3. Spring Boot: Injeção de Dependência e Testes

A injeção de dependência (DI) é um princípio fundamental no Spring Boot, que implementa o padrão de Inversão de Controle (IoC). Sua importância reside na capacidade de criar componentes fracamente acoplados. Em vez de um objeto criar suas próprias dependências, o contêiner IoC do Spring se encarrega de criar e "injetar" essas dependências quando necessário. Isso é feito de forma transparente para o desenvolvedor através de anotações como `@Autowired`.

Essa abordagem promove a modularidade e a flexibilidade, pois as implementações de dependências podem ser trocadas facilmente sem alterar o código da classe que as utiliza.

O impacto da injeção de dependência nos testes de código é imenso e extremamente positivo. Como as dependências são injetadas externamente, é possível, durante os testes, substituir as dependências reais por implementações "mock" ou "stubs". Por exemplo, ao testar uma classe de `Service` que depende de um `Repository`, podemos injetar um `Repository` falso que simula o comportamento do banco de dados, sem a necessidade de uma conexão real. Isso permite:

- **Testes Unitários Isolados:** Testar a lógica de uma classe de forma isolada, garantindo que o teste valide apenas o comportamento daquela unidade.
- **Testes Rápidos e Confiáveis:** Evitar a lentidão e a instabilidade de dependências externas, como redes e bancos de dados.
- **Simulação de Cenários:** Facilita a simulação de cenários de erro (ex: banco de dados indisponível) para validar o tratamento de exceções.

Frameworks como Mockito, em conjunto com as anotações de teste do Spring (`@MockBean`), tornam esse processo simples e eficiente.

### 4. Spring Boot: Arquitetura em Camadas (Controller, Service, Repository)

A separação da aplicação em camadas `Controller`, `Service` e `Repository` é um padrão arquitetural clássico que organiza o código com base na separação de responsabilidades, contribuindo significativamente para a manutenibilidade e escalabilidade do sistema.

- **`@Controller` (Camada de Apresentação):** É a porta de entrada da aplicação. Sua única responsabilidade é lidar com as requisições HTTP, validar e extrair dados da requisição (como parâmetros e corpo), e invocar a camada de serviço apropriada. Ela não contém lógica de negócio e apenas traduz o resultado do serviço para uma resposta HTTP (JSON, status codes).

- **`@Service` (Camada de Negócio):** É o coração da aplicação. Contém a lógica de negócio, as regras e as orquestrações. Ela coordena as operações, podendo utilizar um ou mais `Repositories` para manipular os dados. Esta camada é agnóstica em relação à forma como os dados são apresentados ou armazenados.

- **`@Repository` (Camada de Acesso a Dados):** É responsável pela comunicação com a fonte de dados (geralmente um banco de dados). Ela abstrai os detalhes da persistência, expondo métodos claros para criar, ler, atualizar e deletar dados (CRUD). A camada de serviço utiliza essa interface sem precisar conhecer a implementação subjacente (JPA, JDBC, etc.).

**Contribuições para Manutenibilidade e Escalabilidade:**

- **Manutenibilidade:** A clara separação de responsabilidades torna o código mais fácil de entender, localizar e modificar. Uma alteração na API REST afeta apenas o `Controller`. Uma nova regra de negócio é adicionada no `Service`. Uma otimização de consulta SQL é feita no `Repository`. Isso reduz o risco de introduzir bugs em outras partes do sistema.

- **Escalabilidade:** A arquitetura em camadas permite escalar diferentes partes do sistema de forma independente. Por exemplo, é possível adicionar mais instâncias da aplicação (com `Controllers` e `Services`) para lidar com mais requisições, enquanto o banco de dados (`Repository`) pode ser escalado separadamente. Além disso, a fronteira clara entre as camadas facilita a introdução de mecanismos como cache na camada de serviço para reduzir a carga no banco de dados, melhorando a performance geral.




---



# Escolha do Projeto Android

## Análise Comparativa dos 5 Projetos

Após análise cuidadosa dos cinco projetos propostos, realizei uma avaliação baseada nos critérios de avaliação técnica definidos no documento:

| Projeto | Complexidade Funcional | Integração LIO | Oportunidade Arquitetural | Facilidade de Implementação | Pontuação |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **PDV Simplificado** | Média | Alta | Alta | Média | 8/10 |
| **Serviços com Pagamento** | Alta | Alta | Média | Baixa | 7/10 |
| **App de Doações** | **Baixa** | **Alta** | **Alta** | **Alta** | **9/10** |
| **Delivery Local** | Alta | Alta | Alta | Baixa | 7/10 |
| **Venda de Ingressos** | Média | Alta | Média | Média | 7/10 |

## Projeto Escolhido: App de Doações

### Justificativa da Escolha

O **App de Doações** foi selecionado como o projeto mais adequado pelos seguintes motivos técnicos e estratégicos:

#### 1. **Simplicidade Funcional Estratégica**
O projeto possui um escopo bem definido e enxuto, permitindo foco na qualidade da implementação ao invés de complexidade desnecessária. As funcionalidades principais são:
- Lista de instituições beneficentes
- Seleção de valor de doação
- Integração com pagamento via Cielo Lio
- Comprovante de doação

#### 2. **Demonstração Completa dos Critérios de Avaliação**
- **Arquitetura (MVVM/Clean Architecture):** Estrutura clara com separação de responsabilidades
- **Integração Cielo Lio:** Implementação completa do fluxo de pagamento
- **Criatividade na Interface:** Oportunidade de criar uma UI envolvente e emotiva
- **Componentes Reutilizáveis:** Cards de instituições, botões de valor, componentes de pagamento
- **Testes:** Cobertura de casos de uso principais sem complexidade excessiva

#### 3. **Vantagens Técnicas**
- **Fluxo Linear:** Navegação simples e intuitiva (Lista → Seleção → Pagamento → Confirmação)
- **Estado Gerenciável:** Poucos estados complexos para gerenciar
- **Mock Backend Simples:** Dados estáticos de instituições são suficientes
- **Validações Claras:** Regras de negócio bem definidas (valor mínimo, instituição válida)

#### 4. **Oportunidades de Demonstração Técnica**
- **Jetpack Compose:** Interface moderna e reativa
- **ViewModel + StateFlow:** Gerenciamento de estado robusto
- **Repository Pattern:** Abstração de dados
- **Dependency Injection (Hilt):** Injeção de dependências
- **Coroutines:** Operações assíncronas
- **Navigation Component:** Navegação entre telas

#### 5. **Impacto e Engajamento**
O tema de doações permite criar uma interface que demonstre sensibilidade para UX/UI, com elementos visuais que transmitam confiança e propósito social, diferenciando a implementação.

## Especificação Técnica do Projeto Escolhido

### Funcionalidades Principais
1. **Tela de Instituições:** Lista de organizações beneficentes com descrição e imagens
2. **Tela de Doação:** Seleção de valor (predefinido ou personalizado)
3. **Tela de Pagamento:** Integração com Cielo Lio
4. **Tela de Confirmação:** Comprovante da doação realizada

### Arquitetura Planejada
- **Presentation Layer:** Composables + ViewModels
- **Domain Layer:** Use Cases + Entities
- **Data Layer:** Repository + DataSources (Mock)

### Tecnologias a Serem Utilizadas
- **Kotlin** com **Jetpack Compose**
- **MVVM** + **Clean Architecture**
- **Hilt** para injeção de dependência
- **Navigation Compose** para navegação
- **StateFlow** para gerenciamento de estado
- **Coroutines** para operações assíncronas
- **JUnit** + **Mockito** para testes

### Critérios de Sucesso
- [X] Arquitetura limpa e bem estruturada
- [X] Interface intuitiva e visualmente atrativa
- [X] Integração funcional com Cielo Lio (simulada)
- [X] Testes unitários cobrindo casos principais
- [X] Documentação clara e completa
- [X] Código bem organizado e comentado

Esta escolha permite demonstrar competência técnica completa dentro do prazo estimado de 4-8 horas, priorizando qualidade sobre complexidade desnecessária.




---



# Lógica de Desenvolvimento - App de Doações

Este documento explica em detalhes a lógica por trás do desenvolvimento do aplicativo de doações, desde as decisões arquiteturais até a implementação específica de cada componente.

## Decisões Arquiteturais

### Escolha da Clean Architecture

A Clean Architecture foi escolhida por proporcionar uma separação clara de responsabilidades e facilitar a manutenção e testabilidade do código. A arquitetura é dividida em três camadas principais:

**Camada de Domínio (Domain Layer)**: Contém a lógica de negócio pura, sem dependências de frameworks externos. Esta camada define as regras de negócio fundamentais da aplicação, como a validação de valores mínimos para doação e a estrutura das entidades principais (Instituição, Doação, Pagamento).

**Camada de Dados (Data Layer)**: Responsável por fornecer os dados para a aplicação. Implementa as interfaces definidas na camada de domínio, atuando como uma ponte entre as fontes de dados (APIs, bancos de dados) e a lógica de negócio. Neste projeto, utilizamos data sources mock para simular integrações reais.

**Camada de Apresentação (Presentation Layer)**: Gerencia a interface do usuário e as interações. Utiliza o padrão MVVM com ViewModels que mantêm o estado da UI e coordenam as operações com a camada de domínio.

### Padrão MVVM com StateFlow

O padrão MVVM foi implementado utilizando StateFlow para gerenciamento de estado reativo. Esta escolha oferece várias vantagens:

**Reatividade**: A UI se atualiza automaticamente quando o estado muda, eliminando a necessidade de atualizações manuais da interface.

**Sobrevivência a Mudanças de Configuração**: Os ViewModels sobrevivem a rotações de tela e outras mudanças de configuração, preservando o estado da aplicação.

**Testabilidade**: A lógica de apresentação fica isolada nos ViewModels, facilitando a criação de testes unitários.

## Implementação das Funcionalidades

### Gerenciamento de Estado com StateFlow

Cada ViewModel utiliza um `MutableStateFlow` interno para gerenciar o estado, expondo um `StateFlow` público para observação pela UI. Esta abordagem garante que apenas o ViewModel possa modificar o estado, enquanto a UI apenas o observa.

```kotlin
private val _uiState = MutableStateFlow(InstituicoesUiState())
val uiState: StateFlow<InstituicoesUiState> = _uiState.asStateFlow()
```

### Fluxo de Dados Unidirecional

O aplicativo implementa um fluxo de dados unidirecional (UDF), onde:

1. A UI emite eventos (cliques, inserção de texto)
2. O ViewModel processa esses eventos e invoca Use Cases
3. Os Use Cases executam a lógica de negócio e retornam resultados
4. O ViewModel atualiza o estado baseado nos resultados
5. A UI se recompõe automaticamente para refletir o novo estado

### Simulação da Integração Cielo Lio

A integração com a Cielo Lio foi simulada através do `CieloLioDataSource`, que implementa um fluxo realista de pagamento:

**Estado de Processamento**: Simula o tempo de processamento de um pagamento real com delay de 2 segundos.

**Taxa de Sucesso**: Implementa uma taxa de sucesso de 90% para demonstrar tanto cenários de sucesso quanto de falha.

**Geração de Comprovante**: Cria um comprovante textual simulado com informações da transação.

### Validações de Negócio

As validações são implementadas na camada de domínio, especificamente no `ProcessarDoacaoUseCase`:

**Valor Mínimo**: Valida que o valor da doação seja pelo menos R$ 5,00.

**Geração de Referência**: Cria uma referência única para cada transação usando timestamp.

**Tratamento de Erros**: Lança exceções específicas para diferentes tipos de erro, permitindo tratamento adequado na UI.

## Componentes Reutilizáveis

### InstituicaoCard

Componente que encapsula a exibição de informações de uma instituição. Utiliza Material Design 3 com elevação e cantos arredondados. Implementa carregamento assíncrono de imagens com a biblioteca Coil.

### ValorSelector

Componente complexo que permite seleção de valores predefinidos ou inserção de valor personalizado. Implementa validação em tempo real e formatação de moeda brasileira.

## Navegação

A navegação utiliza o Navigation Compose com uma abordagem baseada em rotas string. Para passar objetos complexos entre telas, utilizamos o `SavedStateHandle` do NavController, evitando a serialização de objetos grandes nas URLs.

## Injeção de Dependências

O Hilt foi configurado para gerenciar todas as dependências da aplicação:

**Módulo de Aplicação**: Define os bindings entre interfaces e implementações.

**Escopo Singleton**: Garante que repositórios e data sources sejam instâncias únicas.

**ViewModels**: Automaticamente injetados pelo Hilt usando a anotação `@HiltViewModel`.

## Tratamento de Estados de Loading e Erro

Cada tela implementa tratamento adequado para diferentes estados:

**Loading**: Exibe indicadores de progresso durante operações assíncronas.

**Sucesso**: Apresenta os dados carregados com interface otimizada.

**Erro**: Mostra mensagens de erro claras e opções de recuperação (como "tentar novamente").

## Testes Unitários

Os testes foram estruturados para cobrir os componentes mais críticos:

**Use Cases**: Testam a lógica de negócio isoladamente, usando mocks para as dependências.

**ViewModels**: Verificam o comportamento do gerenciamento de estado e a integração com os Use Cases.

**Cenários de Teste**: Incluem casos de sucesso, falha e edge cases (valores limites, estados vazios).

Esta abordagem de desenvolvimento resulta em um código bem estruturado, testável e de fácil manutenção, seguindo as melhores práticas recomendadas para desenvolvimento Android moderno.




---



# Arquitetura do Aplicativo de Doações

Este documento detalha a arquitetura de software utilizada no desenvolvimento do aplicativo "App de Doações". A arquitetura foi projetada para ser robusta, escalável, testável e de fácil manutenção, seguindo as melhores práticas recomendadas pelo Google para o desenvolvimento Android moderno.

## Visão Geral

A arquitetura é baseada em uma combinação de:

- **Clean Architecture**: Para a separação de responsabilidades em camadas independentes.
- **MVVM (Model-View-ViewModel)**: Como o padrão de design da camada de apresentação (UI).
- **Unidirectional Data Flow (UDF)**: Para um fluxo de dados previsível e consistente.

## Camadas da Clean Architecture

O projeto é dividido em três camadas principais: **Presentation**, **Domain** e **Data**.

![Diagrama da Arquitetura](arquitetura.png)

### 1. Camada de Domínio (`domain`)

Esta é a camada mais interna e o núcleo da aplicação. Ela é completamente independente de qualquer framework Android ou biblioteca externa. Contém a lógica de negócio pura e as regras da aplicação.

- **Entidades (`domain/model`)**: Representam os objetos de negócio da aplicação (ex: `Instituicao`, `Doacao`). São simples data classes em Kotlin.
- **Casos de Uso (`domain/usecase`)**: Contêm a lógica de negócio específica para cada funcionalidade (ex: `GetInstituicoesUseCase`, `ProcessarDoacaoUseCase`). Eles orquestram o fluxo de dados, utilizando as interfaces dos repositórios para acessar e manipular os dados.
- **Interfaces de Repositório (`domain/repository`)**: Definem os contratos (interfaces) que a camada de dados deve implementar para fornecer os dados. Isso inverte a dependência, fazendo com que a camada de domínio não saiba como os dados são obtidos ou armazenados.

### 2. Camada de Dados (`data`)

Esta camada é responsável por fornecer os dados para a aplicação. Ela implementa as interfaces de repositório definidas na camada de domínio.

- **Implementações de Repositório (`data/repository`)**: Implementam as interfaces da camada de domínio (ex: `InstituicaoRepositoryImpl`). Elas atuam como uma única fonte de verdade para os dados, decidindo de onde obtê-los (rede, banco de dados local, cache).
- **Fontes de Dados (`data/datasource`)**: São as classes responsáveis por obter os dados de uma fonte específica. Neste projeto, foram criadas fontes de dados *mock* (`InstituicaoDataSource`, `CieloLioDataSource`) que simulam o comportamento de uma API REST e da integração com a Cielo Lio. Em um projeto real, aqui estariam as implementações do Retrofit (para API) ou Room (para banco de dados).

### 3. Camada de Apresentação (`presentation`)

Esta é a camada mais externa, responsável por exibir a interface do usuário (UI) e lidar com as interações do usuário.

- **UI (Views/Composables) (`presentation/ui`)**: Construída inteiramente com Jetpack Compose. A UI é "burra", ou seja, ela apenas observa o estado fornecido pelo ViewModel e notifica o ViewModel sobre os eventos do usuário (cliques, etc.).
- **ViewModel (`presentation/viewmodel`)**: Atua como um intermediário entre a UI e os Casos de Uso. Ele mantém o estado da UI (usando `StateFlow`), sobrevive a mudanças de configuração e invoca os Casos de Uso para executar a lógica de negócio em resposta a eventos do usuário. Ele não tem conhecimento direto da UI, apenas expõe o estado para ser observado.

## Fluxo de Dados (Unidirectional Data Flow - UDF)

O fluxo de dados na aplicação segue um padrão unidirecional, o que o torna mais previsível e fácil de depurar:

1.  **Evento do Usuário**: A UI (Composable) notifica o `ViewModel` sobre um evento (ex: clique em um botão).
2.  **Lógica de Negócio**: O `ViewModel` invoca o `UseCase` apropriado.
3.  **Acesso a Dados**: O `UseCase` utiliza a interface do `Repository` para solicitar os dados.
4.  **Fonte de Dados**: A implementação do `Repository` na camada `data` obtém os dados do `DataSource` (mock, API, etc.).
5.  **Retorno dos Dados**: Os dados fluem de volta através das camadas: `DataSource` -> `Repository` -> `UseCase` -> `ViewModel`.
6.  **Atualização do Estado**: O `ViewModel` atualiza seu `StateFlow` com os novos dados.
7.  **Recomposição da UI**: A UI, que está observando o `StateFlow` (via `collectAsState()`), é automaticamente recomposta pelo Jetpack Compose para refletir o novo estado.

## Injeção de Dependência com Hilt

O [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) é utilizado para gerenciar a injeção de dependências em toda a aplicação. Isso ajuda a desacoplar as classes e facilita os testes.

- **`@HiltAndroidApp`**: Anota a classe `Application` para iniciar a geração de código do Hilt.
- **`@AndroidEntryPoint`**: Anota a `MainActivity` para permitir a injeção de dependências.
- **`@HiltViewModel`**: Anota os `ViewModels` para que o Hilt possa criá-los e injetar suas dependências.
- **`@Module` e `@InstallIn`**: O `AppModule` define como o Hilt deve fornecer as implementações para as interfaces dos repositórios.
- **`@Inject`**: Usado nos construtores para que o Hilt saiba como criar as instâncias das classes (Use Cases, Repositories, DataSources).

Essa abordagem arquitetural resulta em um aplicativo que não é apenas funcional, mas também limpo, organizado e preparado para futuras expansões e manutenções.

