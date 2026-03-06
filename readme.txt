Collections avançadas - ConcurrentHashMap, CopyOnWriteArrayList, Tree structures


O projeto é dividido em projetos e cada um trabalho um problema x solução que pode ser corrigida com o uso de collections avançadas.

Itens abordados;

- ConcurrentHashMap

Problema
Um caso que podemos usar é quando utilizamos o HasjMap em ambiente concorrente, temos várias threads acessando um hashmap, ai pode ocorrer;
- Inconsistência de dados
- Race condition
-  Possível ConcurrentModificationException


Mesmo com 2000 incrementos esperados, o resultado pode ser diferente por causa de termos duas threads leem o mesmo valor
ambas incrementando o valor, e sobrescrevendo.

A solução

O uso do concurrentHashMap permite acesso concorrente seguro com melhor desempenho que sincronização manual.
ele foi projetado para permitir acesso simultâneo de várias threads sem corromper os dados.

Ele faz isso com algumas estratégias:

# Segmentação / locks finos
Em vez de travar o mapa inteiro, ele trava apenas partes da estrutura.
Então várias threads podem trabalhar em partes diferentes ao mesmo tempo.

Isso melhora muito a performance.

# Operações atômicas

Ele possui métodos que fazem leitura + escrita em uma única operação segura.

Internamente isso acontece como uma operação indivisível.
Nenhuma outra thread consegue interferir no meio da atualização.

Algoritmos não bloqueantes (CAS)

Nas versões modernas do Java, o ConcurrentHashMap usa técnicas de:

# CAS (Compare And Swap)

estruturas lock-free em várias operações
Isso reduz bloqueios e melhora escalabilidade.
