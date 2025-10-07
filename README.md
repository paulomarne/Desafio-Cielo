# App de Doações - Prova Técnica

Este é um aplicativo Android nativo desenvolvido como parte de um processo seletivo. O projeto implementa um app de doações que permite aos usuários selecionar uma instituição de caridade, escolher um valor e simular uma doação através de uma integração mock com a Cielo Lio.

## ✨ Funcionalidades

- **Lista de Instituições**: Exibe instituições de caridade com imagens, nomes, descrições e categorias
- **Seleção de Valor**: Permite seleção de valores predefinidos ou inserção de valor personalizado
- **Fluxo de Pagamento Simulado**: Simula processamento completo com estados de sucesso e falha
- **Interface Reativa**: Construído inteiramente com Jetpack Compose e Material Design 3
- **Validações de Negócio**: Implementa regras como valor mínimo de R$ 5,00

## 🏗️ Arquitetura e Tecnologias

O aplicativo segue os princípios da **Clean Architecture** e padrão **MVVM**:

### Tecnologias Utilizadas
- **Linguagem**: Kotlin
- **UI**: Jetpack Compose
- **Arquitetura**: Clean Architecture + MVVM
- **Injeção de Dependência**: Hilt
- **Assincronismo**: Kotlin Coroutines e Flow
- **Navegação**: Navigation Compose
- **Carregamento de Imagens**: Coil
- **Testes**: JUnit e Mockito

### Estrutura do Projeto
```
app/src/main/java/com/doacoes/
├── data/                    # Camada de dados
│   ├── datasource/         # Fontes de dados (mock)
│   └── repository/         # Implementações dos repositórios
├── domain/                 # Camada de domínio
│   ├── model/             # Entidades de negócio
│   ├── repository/        # Interfaces dos repositórios
│   └── usecase/           # Casos de uso
├── presentation/          # Camada de apresentação
│   ├── ui/               # Composables e temas
│   └── viewmodel/        # ViewModels
└── di/                   # Módulos de injeção de dependência
```

## 🚀 Como Executar

### Pré-requisitos
- Android Studio Arctic Fox ou superior
- JDK 11 ou superior
- Android SDK (API 24+)

### Passos para Execução
1. **Clone ou baixe o projeto**
2. **Abra no Android Studio**
3. **Aguarde a sincronização do Gradle**
4. **Execute em um dispositivo ou emulador Android**

### Executar Testes
```bash
./gradlew test
```

## 🎯 Simulação de Backend

O aplicativo implementa uma simulação completa de backend através de **Data Sources Mock**:

### InstituicaoDataSource
- Simula uma API REST com dados de instituições
- Implementa delay de rede (1 segundo) para simular latência real
- Retorna lista de 3 instituições com dados completos

### CieloLioDataSource  
- Simula integração com a API da Cielo Lio
- Implementa fluxo realista de pagamento:
  - Estado de processamento (2-3 segundos)
  - Taxa de sucesso de 90%
  - Geração de comprovante textual
  - Tratamento de diferentes tipos de erro

### Validações Implementadas
- Valor mínimo de R$ 5,00 para doações
- Validação de valores positivos
- Geração de referência única por transação
- Tratamento de estados de loading, sucesso e erro

## 🧪 Testes Implementados

O projeto inclui testes unitários para os componentes críticos:

- **GetInstituicoesUseCaseTest**: Testa a lógica de busca de instituições
- **ProcessarDoacaoUseCaseTest**: Testa validações e processamento de doações
- Cobertura de cenários de sucesso, falha e edge cases

## 📱 Fluxo do Usuário

1. **Tela Inicial**: Lista de instituições com loading e tratamento de erro
2. **Seleção**: Usuário clica em uma instituição
3. **Doação**: Escolhe valor (predefinido ou personalizado)
4. **Pagamento**: Simula processamento via Cielo Lio
5. **Confirmação**: Exibe resultado (sucesso ou erro) com opções de ação

## 🎨 Design e UX

- Interface moderna com Material Design 3
- Cards com elevação e cantos arredondados
- Carregamento assíncrono de imagens
- Estados visuais claros (loading, sucesso, erro)
- Feedback imediato para ações do usuário
- Validação em tempo real de formulários

## 🔧 Próximos Passos

Para um ambiente de produção, seria necessário:

- Integração real com API da Cielo Lio
- Implementação de banco de dados local (Room)
- Autenticação e autorização de usuários
- Analytics e crash reporting
- Testes de UI automatizados
- CI/CD pipeline

---

**Desenvolvido como parte de prova técnica demonstrando conhecimentos em desenvolvimento Android moderno com Clean Architecture, Jetpack Compose e melhores práticas de desenvolvimento.**
