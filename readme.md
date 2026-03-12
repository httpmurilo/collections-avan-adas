valor = 10

Thread A lê -> 10
Thread B lê -> 10

Thread A grava -> 11
Thread B grava -> 11


Mesmo esperando **2 incrementos**, o resultado final será **11 em vez de 12**.

Isso acontece porque a operação **não é atômica**.

---

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

--finalizar
# Conclusão

Essas collections resolvem **problemas clássicos de sistemas modernos**:

| Problema | Solução |
|--------|--------|
| Concorrência em mapas | ConcurrentHashMap |
| Leitura concorrente em listas | CopyOnWriteArrayList |
| Ordenação automática | TreeMap |
| Ordenação + concorrência | ConcurrentSkipListMap |
