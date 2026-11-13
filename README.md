# Alaga Não

Aplicativo Android que ajuda moradores a acompanhar o risco de alagamento da sua rua
e a registrar, na hora, onde a água já subiu.

Projeto da **Global Solution 2026 (2º semestre) — Android Kotlin Developer**, desafio **CitizenTech**.

## Integrantes

| Matheus Keizo Shimomoto Hangui | 557834 |
| Bruno Hideki itikawa | 554924 |
| Gabriel Shoiti Yoshida | 558945 |

## Integrantes
Link do video: [https://www.youtube.com/watch?v=RMS3iWAFsyQ]

## Problema

Todo ano, alagamentos destroem casas e carros, deixam pessoas ilhadas e param a cidade.
Os avisos que existem hoje costumam ser genéricos ("chuva forte na Zona Leste") e o morador
não consegue saber se o problema vai chegar na **sua** rua. Quem mais sofre (moradores de
áreas de risco, idosos e pessoas sem smartphone) é quem menos recebe informação a tempo.

## Público beneficiado

- Moradores de ruas e bairros que já alagam;
- Idosos e pessoas com dificuldade de locomoção, que precisam decidir com antecedência se saem de casa;
- Motoristas e quem depende de ônibus e precisa escolher outro caminho;
- Vizinhos e comunidade, que passam a ter um registro compartilhado do que está acontecendo.

## Proposta

O Alaga Não funciona como um mural colaborativo: o morador registra que a sua rua está alagando
e o app calcula, a partir desses relatos, um **nível de risco de 0 a 100** para cada rua, com cores
de semáforo (verde, amarelo e vermelho). Os relatos ficam salvos no celular, podem ser acompanhados,
editados e marcados como resolvidos quando "a água baixou", e a rua deixa de aparecer em risco.

> Este é um **protótipo acadêmico**. O app **não** está integrado a nenhum serviço público
> (Defesa Civil, CGE, Cemaden etc.) nem envia SMS ou alertas reais.

## ODS relacionados

- **ODS 11 — Cidades e comunidades sustentáveis:** o app ajuda a cidade a ficar mais preparada para
  desastres, reduzindo o número de pessoas e bens afetados por alagamentos (meta 11.5) e dando ao
  morador informação para se proteger.
- **ODS 13 — Ação contra a mudança global do clima:** com chuvas cada vez mais intensas, o app ajuda
  as pessoas a se adaptarem a eventos extremos, apoiando a capacidade de resposta da comunidade (meta 13.1).

## Funcionalidades

- **Tela inicial** com contadores (alagando agora / água baixou), a lista de **ruas em risco**
  (calculada com os relatos ativos de cada rua) e os **últimos relatos** registrados.
- **Cadastro de relato** "Está alagando aqui": rua, bairro, gravidade (leve, moderado ou grave) e descrição.
- **Validação:** rua (mínimo de 3 letras) e bairro são obrigatórios; o app mostra o erro no campo e não salva.
- **Lista de relatos** com filtros (Todos, Alagando, Água baixou) e mensagem quando não há dados.
- **Ações sobre os relatos:** marcar como "a água baixou" (e reabrir), editar e excluir (com confirmação).
- **Risco por rua:** cada relato ativo soma pontos à rua (leve 20, moderado 40, grave 70, máximo 100).
  Acima de 60 o risco é alto (vermelho), a partir de 20 é atenção (amarelo).
- **Persistência local:** os relatos continuam salvos depois de fechar e abrir o app.

## Fluxo principal

1. Na tela **Início**, a pessoa toca em **Está alagando aqui**, informa rua, bairro e gravidade e registra o relato.
2. De volta ao **Início**, a rua aparece em **Ruas em risco** com a cor do nível e o relato entra em **Últimos relatos**.
3. Na tela **Relatos**, ela pode editar, excluir ou marcar que **a água baixou**.
4. Ao marcar que a água baixou, o contador muda e a rua deixa de aparecer em risco.

## Capturas de tela e evidências

> Capturas de tela na pasta prints.

## Tecnologias utilizadas

- Kotlin
- Jetpack Compose e Material 3
- Navigation Compose
- ViewModel com StateFlow
- Room (banco de dados local)
- Hilt (injeção de dependências)
- Coroutines e Flow
- JUnit (teste unitário do cálculo de risco)

## Organização do código

```
app/src/main/java/com/fiap/alaganao
├── data        Relato (entidade), RelatoDao, AppDatabase, RelatoRepository,
│               e cálculo de risco
├── di          DatabaseModule (Hilt + Room)
└── ui
    ├── home        tela inicial + HomeViewModel
    ├── lista       lista de relatos + ListaViewModel
    ├── formulario  cadastro/edição + FormularioViewModel
    ├── navigation  rotas e NavHost
    └── theme       cores e tema
```

## Como executar

1. Instalar o **Android Studio** (versão recente) com o SDK Android configurado.
2. Descompactar o projeto e abrir a pasta `AlagaNao` em **File > Open**.
3. Aguardar o **Gradle Sync** terminar.
4. Criar ou escolher um emulador (ou conectar um celular Android com depuração USB ativa).
5. Clicar em **Run** (▶) para instalar e abrir o app.

Requisitos: `minSdk 24`. O projeto usa as mesmas versões de Gradle, Kotlin e bibliotecas
trabalhadas em aula.
