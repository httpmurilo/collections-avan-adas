## Solução

Usar `ConcurrentHashMap`.

Ele permite **acesso concorrente seguro** sem precisar sincronizar manualmente toda a estrutura.

Foi projetado para permitir que **múltiplas threads leiam e modifiquem o mapa ao mesmo tempo** sem corromper os dados.

---

## Como funciona internamente

### Locks finos (segmentação)

Em vez de bloquear o mapa inteiro, o `ConcurrentHashMap` bloqueia **apenas partes da estrutura**.

Isso permite que várias threads operem ao mesmo tempo em regiões diferentes do mapa.

Resultado:

- mais paralelismo
- melhor performance

---

### Operações atômicas

Possui métodos que executam **leitura + escrita de forma segura**:


compute()
merge()
putIfAbsent()
replace()


Essas operações acontecem de forma **indivisível**, impedindo interferência de outras threads.

---

### CAS (Compare And Swap)

Versões modernas usam:

- **CAS (Compare And Swap)**
- estruturas **lock-free**

Isso reduz bloqueios e melhora a **escalabilidade em ambientes concorrentes**.

---

## Vantagens

- Thread-safe
- Melhor desempenho que `Collections.synchronizedMap`
- Permite alta concorrência
- Operações atômicas prontas
- Escala bem em sistemas multi-core

---

## Desvantagens

- Estrutura interna mais complexa
- Um pouco mais pesada que `HashMap` em aplicações single-thread

---

## Quando usar

Use `ConcurrentHashMap` quando:

- várias threads acessam o mesmo mapa
- existem leituras e escritas concorrentes
- é necessário alto desempenho em concorrência

---

## Casos reais

- cache concorrente
- contadores distribuídos
- session store
- monitoramento de métricas
- contagem de acessos em APIs

Exemplo:


API Gateway contando acessos por endpoint


---

# 2. CopyOnWriteArrayList

## Problema

`ArrayList` **não é thread-safe**.

Se uma thread estiver percorrendo a lista enquanto outra modifica, ocorre:


ConcurrentModificationException


Isso acontece porque:

- uma thread está iterando
- outra modifica a lista

O iterador detecta a alteração e lança exceção.

---

## Solução

Usar `CopyOnWriteArrayList`.

Ela resolve o problema copiando a lista **sempre que ocorre uma modificação**.

Estratégia:

- leituras usam uma **versão estável da lista**
- modificações criam **uma nova cópia**

---

## Como funciona

Quando ocorre um `add()`:

1. A lista atual é copiada
2. O novo elemento é adicionado na nova cópia
3. A referência da lista é atualizada

Enquanto isso, threads que estavam lendo continuam usando a versão antiga.

---

### Exemplo

Lista original:


[Ana, Carlos, Pedro]


Execução:


Thread leitura -> usa lista original

Thread escrita -> cria nova lista
[Ana, Carlos, Pedro, NovoUsuario]


Sem interferência.

---

## Vantagens

- Não gera `ConcurrentModificationException`
- Leituras extremamente rápidas
- Iteradores seguros em concorrência
- Ideal para cenários com muitas leituras

---

## Desvantagens

Cada escrita cria **uma nova cópia da lista**.

Consequências:

- maior consumo de memória
- escrita mais lenta

---

## Quando usar

Use quando houver:

- muitas leituras
- poucas modificações

---

## Casos reais

- lista de listeners
- configurações carregadas em memória
- lista de usuários online
- cache de objetos imutáveis

Exemplo comum:


lista de observers em sistemas de eventos


---

# 3. Tree Structures (TreeMap / TreeSet)

## Problema

`HashMap` **não mantém ordem**.

Imagine um sistema que precisa listar:


transações ordenadas por valor


Ou um:


ranking ordenado


Com `HashMap`, a ordem dos elementos é **aleatória**.

---

## Solução

Usar `TreeMap` ou `TreeSet`.

Essas estruturas mantêm os elementos **sempre ordenados automaticamente**.

---

## Como funciona internamente

O `TreeMap` usa uma estrutura chamada:

**Red-Black Tree**

Uma árvore binária balanceada.

Isso garante:

- inserção eficiente
- busca rápida
- manutenção automática da ordem

Complexidade média:


O(log n)


---

## Vantagens

- dados sempre ordenados
- permite buscas por intervalo
- boa performance para grandes conjuntos

---

## Desvantagens

- mais lento que `HashMap`
- não é thread-safe
- estrutura mais complexa

---

## Quando usar

Use estruturas de árvore quando precisar de:

- dados ordenados
- ranking
- intervalos de busca
- listas classificadas

---

## Casos reais

- ranking de pontuação
- ordenação de preços
- agendas e calendários
- sistemas financeiros

Exemplo:


TreeMap<timestamp, LogEvent>


---

# 4. ConcurrentSkipListMap

## Problema

Imagine um sistema de **ranking de jogadores** com várias threads atualizando pontuações ao mesmo tempo.

Se usarmos `TreeMap`, teremos problemas:

- inconsistência de dados
- race condition
- `ConcurrentModificationException`

Isso acontece porque **TreeMap não é thread-safe**.

---

## Solução

Usar `ConcurrentSkipListMap`.

Ele é:

- ordenado
- thread-safe
- altamente concorrente

---

## Como funciona internamente

Ele utiliza uma estrutura chamada:

**Skip List**

Uma lista encadeada com **múltiplos níveis de salto**.

Isso permite:

- buscas rápidas
- inserções eficientes
- concorrência sem bloquear toda a estrutura

Complexidade média:


O(log n)


---

## Vantagens

- Thread-safe
- Mantém ordenação natural das chaves
- Suporta busca por intervalo

Exemplo:


ranking.subMap(10, 20)


---

## Desvantagens

- Mais lento que `ConcurrentHashMap` para acesso simples
- Estrutura interna mais complexa

---

## Quando usar

Use `ConcurrentSkipListMap` quando precisar de:

- dados ordenados
- concorrência
- busca por intervalo

---

## Casos reais

- ranking de jogadores
- sistemas financeiros ordenados por valor
- logs ordenados por tempo
- filas de prioridade concorrentes
- sistemas de trading


---

# 5. ConcurrenteSkipListSet

## Problema

Em sistemas concorrentes, às vezes precisamos de uma **coleção de elementos únicos e ordenados**, onde **várias threads possam inserir e consultar dados ao mesmo tempo**.

Se utilizamos estruturas tradicionais como:
 
- TreeSet.
- HashSet

podemos ter problemas.

## Problemas possíveis

- Falta de thread safety
TreeSet e HashSet não são thread-safe. Se várias threads modificarem ao mesmo tempo, pode ocorrer
    - Inconsistencia de dados
    - corrupção da estrutura interna.

- Race condition
Duas threads podem tentar inserir ou remover elementos ao mesmo tempo.


|  |  |
|--------|--------|
| Thread A | verifica se elemento existe |
| Thread B | verifica se elemento existe |
| Thread A | adiciona |
| Thread B | adiciona |


- ConcurrentModificationException
Se uma thread estiver iterado enquanto outra modifica o Set, pode ocorrer uma exceção.

---

## Solução

Usar `ConcurrentSkipListSet`.

Ele é um Set que oferece:

- thread safety
- ordenação automática
- alta concorrência

Ele é baseado internamente em um `ConcurrentSkipListMap`. Cada elemento do Set é armazenado como chave no mapa.

---

## Como funciona internamente

O `ConcurrentSkipListSet` utiliza uma estrutura chamada:



**Skip List**

Uma **lista encadeada multinível**.

Isso permite que operações como:

- busca
- inserção
- remoção
tenham complexidade média:

``O(log n)``

Sem precisar travar toda a estrutura.

Isso permite **acesso concorrente eficiente**


---

## Vantagens

- Thread-safe
- Mantém os elementos **sempre ordenados**
- Permite **acesso concorrente sem bloquear toda a estrutura**
- Permite **operações de intervalo**
- Escala bem em ambientes multi-threads.


---

## Desvantagens

- Mais lento que ConcurrentHashMap ou HashSet para operações simples
- Estrutura interna mais complexa
- Consome um pouco mais de memória
- Não é ideal para cenários sem concorrência
---

## Quando usar

Use `ConcurrentSkipListMap` quando precisar de:

- coleção ordenada
- elementos únicos
- acesso concorrente
- suporte a buscas por intervalo

Exemplos de operações úteis:

`set.headSet()
set.tailSet()
set.subSet()`

---

## Casos reais

- ranking concorrente
Sistema onde vários jogadores entram no ranking simultaneamente. Por exemplo **top jogadores online**

- Controle de IDs processados
Sistema distribuídos que precisa evitar processamento duplicado. Por exemplo **Set de IDs já processados**

- Lista de tarefas ordenadas por prioridades
- Logs ordenados por tempo. Sistema que recebe logs de múltiplas threads.

---

# 6. BlockingQueue

## Problema

Em sistemas concorrentes, é comum termos o padrão:
- Producer -> gera dados
- Consumer -> processa dados

Exemplo:
- uma thread produz tarefa
- outra thread consome e processa

Sem controle adequado, podem ocorrer:
- consumo antes da produção
- perda de dados
- uso excessivo de CPU(busy waiting)
- necessidade de sincronização manual complexa

## Problemas possíveis

- Race condition -> Sem controle, múltiplas threads podem acessar a fila ao mesmo tempo e gerar inconsistência.
- Busy waiting(loop infitino) -> Consome CPU desnecessariamente e ineficiente
- Perda de dados -> Se a fila estiver cheia e não houver controle, novos dados pdem ser descartados ou gerar erro
- Complexidade de sincronização -> Usando métodos como `wait(), notify(), syncronized` o código fica dificil de manter.

---

## Solução

Usar `BlockingQueue`.

Ela resolve o problema fornecendo:
- Controle automático de concorrencia.
- bloqueio inteligente de threads
- sincronização interna segura.

Ela possui operações bloqueantes:
- Inserção -> se a fila estiver cheia -> bloqueia até ter espaço.
`put()`
- Consumo -> se a fila estiver vazia -> bloqueia ate ter elemento
`take()`

Não precisa de wait/notify, loop manual, threads ficam em eespera eficiente.

---

## Como funciona internamente

A `BlockingQueue` é uma interface que possui implementações como:
- ArrayBlockingQueue
- LinkedBlockingQueue
- PriorityBlockingQueue
- DelayQueue
---

## Vantagens

- Thread-safe
- Evita busy waiting
- Sincronização automatica
- Código mais simples e limpo
- Ideal para producer-consumer
- Bloqueio Eficiente


---

## Desvantagens

- Pode causar bloqueios indesejados se mal configurada
- Algumas implementações são limitadas
- Pode aumentar latencia se consumidores forem lentos
- ``PriorityBlockingQueue`` não é limitada(risco de memoria)
---

## Quando usar

Use `BlockingQueue` quando precisar de:

- há comunicação entre threads
- existe padrão producer-consumer
- voce precisa controlar fluxos de dados
- quer evitar sincronização manual

---

## Casos reais

- Sistema de processamento de filas
pedidos sendo processados em background
- sistemas de mensageria interna
fila de eventos dentro da aplicação
- pool de threads
interamente usa `BlockingQueue`
- processamento assincrono
upload de arquivos
- Sistemas de logging
threads geram logs -> fila -> writer
- sistema financeiro
fila de transações para processamento
---


# 7. estruturas ordenadas concorrentes

## Problema

Em sistemas modernos, muitas vezes precisamos:
- manter dados **ordenados automaticamente**
- permitir **acesso concorrente**
- garantir **consistencia e perfomance**

## Problemas possíveis

- Falta de threads safety -> Estrutura tradicionais ordenados como `TreeMap, Treeset` não são thread-safe, podendo ocasionar corrupção da arvore, dados inconsistentes.
- Race condition-> Multiplas threads alterando a estrutura ao mesmo tempo podem gerar inserções conflitantes, leituras inconsistentes.
- ``ConcurrentModificationException`` -> uma thread iterando enquanto outra modifica.
- baixa escalabilidade com sincronização manual -> se usar ``Collections.synchronizedSorteMap`` pode gerar problemas como lock global, baixa perfomance, gargalo em sistemas com muitas threads.
- perda deordenação em estruturas concorrentes simples -> strutura como ``ConcurrentHashMap`` são thread-safe mas não mantem ordenação.
---

## Solução

Usar estruturas ordennadas concorrentes;
- ``ConcurrentSkipListMap``.
-  ``ConcurrentSkipListSet``
Essas estrutura oferecem:
- ordenação automatica
- thread safety
- alta concorrencia


---

## Como funciona internamente

Essas estruturas são baseadas em:
Skip list
Uma estrutura semelhante a uma lista encadeada com multiplos niveis.
---

## Vantagens

- Thread-safe
- Mantem ordenação automatica
- Alta escabilidade
- suporte a operações de intervalo
- não bloqueia toda a estrutura
- melhor que ``treemap`` em concorrencia.


---

## Desvantagens

- Mais lenta que ``ConcurrentHashMap`` para acesso direto
- Mais consumo de memoria
- Estrutura mais complexa
- Overhead desnecessário em cenários simples
---

## Quando usar

Use estrutura ordenadas concorrentes quando precisar de:
- dados ordenados
- multiplas threads acessando simultaneamente
- consultas por intervalo
- ranking dinamico

---

## Casos reais

- Ranking em tempo real
- sistemas financeiros
- logs concorrentes
- sistemas de monitoramento
- filas de prioridade concorrentes
- sistema de tranding


# 8 comunicação entre threads

## Problema

Em sistemas concorrentes, threads precisam trocar informações e coordenar execução

Exemplo:
- uma thread produz dados
- outra consome
- outra monitora estado
Sem comunicação adequada: threads ficam descoordenadas

## Problemas possíveis

- Race condition
Thareds acessam dados compartilhados sem controle: leitura inconsistente e escrita sobrescrevendo valores.
- Falta de sincronização
Threads executam fora de ordem. Dados inválidos  ou comportamento inesperado.
- Busy waiting
Problema : Alto consumo de CPU e ineficiente
- Deadlock
Duas threads esperando uma pela outra o sistema trava
- Starvation
Uma thread nunca consegue executar
- Complexidade decontrole manual
Uso direto de:
  - wait()
  - notify()
  - synchronized
  - 
---

## Solução

Java oferece várias formas de comunicação entre threads:
- wait / notify/ notifyall
Comunicação baseada em monitores.
- BlockingQueue
comunicação via fila
- Locks (ReentrantLock + Condition)
mais controle do que synchronized
- Classes utilitarias
  - CountDownLatch
  - CyclicBarrier 
  - Semaphore
  - Exchanger

- Variaveis atomicas
AtomicInteger, AtomicBoolean

---

## Como funciona internamente

1. Monitor
Cada objeto em Java possui um monitor interno
2. BlockingQueue
Usa locks internos.
Threads são bloqueadas com eficiencia
usa fila para troca de dados
3. CAS(Compare and swap)
Usado em classe atomicas
4. LockSuporte
usado internamente por varias classes.

---

## Vantagens

- permite coordenação entre threads
- evita inconsistência de dados
- melhora performance (quando bem usado)
- evita busy waiting
- abstrações modernas simplificam muito o código


---

## Desvantagens

- complexidade alta
- difícil de debugar
- risco de deadlock
- uso incorreto pode piorar performance
- exige conhecimento profundo
---

## Quando usar

Use comunicação entre threads quando:

- múltiplas threads compartilham dados
- existe dependência entre execuções
- precisa coordenar tarefas
- sistemas assíncronos ou paralelos

---

## Casos reais

- Producer-Consumer
- Processamento em etapas
- sistemas de mensageria
- APIs assincronas
- Controle de inicializacao
- Sistemas financeiros
- Sistemas de alta perfomance

# 9 Priority Queue

## Problema

Em muitos sistemas, não basta processar dados na ordem de chegada (FIFO).

Precisamos processar com base em prioridade.

Exemplos:
- tarefas urgentes primeiro
- menor valor primeiro
- maior pontuação primeiro

Se usarmos:
- Queue (FIFO)
- ArrayList

não conseguimos garantir ordenação por prioridade de forma eficiente.

## Problemas possíveis

- Processamento fora de prioridade
- ordenação manual custosa
- baixa perfomance em inserções frequentes
- falta de estrutura adequeada
- não é thread-safe
---

## Solução

Usar ``PriorityQueue.``

Ela é uma fila que ordena automaticamente os elementos com base em:
- ordem natural (Comparable)
- ou um Comparator

---

## Como funciona internamente

1. Heap(Binary heap)
2. Offer
3. poll
4. peek

---

## Vantagens

- Ordenação automática por prioridade
- Boa performance (O(log n))
- Estrutura eficiente (heap)
- Flexível com Comparator
- Ideal para algoritmos de otimização


---

## Desvantagens

- Não é thread-safe
- Não mantém ordenação completa (apenas o topo garantido)
- Iteração não é ordenada
- Não é ideal para buscas arbitrárias
---

## Quando usar

Use ``PriorityQueue`` quando precisar de:

- processar elementos por prioridade
- sempre acessar o menor (ou maior) elemento rapidamente
- alta frequência de inserções e remoções

---

## Casos reais

- Sistema de tarefas com prioridade
- algoritmos classicos
- filas de atendimentos
- sistemas de agendamento
- processamento de eventos
- sistema financeiro

---
--finalizar
# Conclusão

Essas collections resolvem **problemas clássicos de sistemas modernos**:

| Problema | Solução |
|--------|--------|
| Concorrência em mapas | ConcurrentHashMap |
| Leitura concorrente em listas | CopyOnWriteArrayList |
| Ordenação automática | TreeMap |
| Ordenação + concorrência | ConcurrentSkipListMap |
