# 🪤 TaNaOB --- Minecraft 1.8.9 (Forge)

## 📌 Sobre o Mod

Este mod para **Minecraft 1.8.9 (Forge)** cria uma **trap automática**
capaz de prender um jogador dentro de um bloco de **obsidian**, forçando
dano de sufocamento.\
A mecânica funciona cercando o alvo com blocos nas laterais e no topo, e
em seguida despejando **lava** no bloco da cabeça do jogador e **água**
ao lado, formando obsidian instantaneamente.

## 🎯 Objetivo do Mod

O objetivo é automatizar a construção de uma trap que:

-   **Imobiliza** o jogador cercando-o com blocos.
-   **Cria obsidian no bloco da cabeça** do jogador através da interação
    lava + água.
-   **Causa dano de sufocamento**, impossibilitando que ele escape.
-   **Adapta-se ao ambiente**, funcionando mesmo quando o alvo está em
    cantos ou perto de paredes.

## ⚙️ Funcionamento Geral

### 1. Cercar o Jogador

O mod deve detectar a posição do alvo e cercá-lo com blocos:

-   Blocos colocados nas **quatro laterais** ao nível do corpo.
-   Um bloco colocado **acima da cabeça**, impedindo salto.
-   **Nenhum bloco é colocado embaixo**, pois o jogador já está sobre o
    chão.
-   Se o jogador estiver **encostado em uma parede ou esquina**, o mod
    coloca blocos apenas nas laterais **acessíveis**, e sempre tampa o
    topo.

### 2. Colocar Lava e Água

Após o cerco:

1.  **Lava** é colocada **no mesmo bloco da cabeça do jogador**\
2.  **Água** é colocada em **qualquer bloco adjacente** à lava

Isso criará instantaneamente **obsidian no bloco da cabeça**, prendendo
e sufocando o alvo.

## 🧪 Requisitos da Hotbar

Antes de funcionar, o mod deve verificar se o jogador possui na hotbar:

✔ Pelo menos **1 bloco para cercar**\
✔ **1 balde de lava**\
✔ **1 balde de água**

Se qualquer um desses itens faltar → **nada deve acontecer**.

## 🧱 Prioridade de Blocos para o Cerco

A escolha dos blocos segue esta ordem:

1.  **Muro de Pedregulho (Cobblestone Wall)** -- opção ideal para o
    cerco\
2.  **Qualquer bloco de madeira** (carvalho, pinho, etc.)\
3.  **Qualquer outro bloco disponível** na hotbar

O mod deve selecionar automaticamente a melhor opção disponível.

## 🛑 Regras de Execução

O mod **somente executa** a trap quando:

-   Há blocos + lava + água na hotbar.
-   O jogador alvo pode ser cercado (laterais acessíveis ou parcialmente
    bloqueadas por paredes).

Se faltar algum requisito:

🚫 Não colocar blocos\
🚫 Não colocar lava\
🚫 Não colocar água\
🚫 Não executar nenhuma ação

## 🔁 Fluxo Completo da Trap

1.  Detectar posição do alvo\
2.  Verificar itens da hotbar\
3.  Selecionar o melhor bloco disponível\
4.  Cercar as laterais acessíveis\
5.  Tampar o topo\
6.  Colocar lava no **bloco da cabeça**\
7.  Colocar água ao lado da lava\
8.  Obsidian é formada\
9.  Jogador alvo fica preso e recebe sufocamento


