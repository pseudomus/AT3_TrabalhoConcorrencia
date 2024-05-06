# AT3_TrabalhoConcorrencia

Este é um projeto Java que simula um sistema de reserva e controle de quartos em um hotel, utilizando threads para representar hóspedes, camareiras e recepcionistas.

# Funcionalidades Principais
Alocação de hóspedes em quartos vagos.
Limpeza dos quartos após a saída dos hóspedes.
Divisão de grupos grandes de hóspedes em vários quartos.
Controle de acesso às chaves dos quartos e sincronização entre as entidades.
# Entidades do Sistema
# Quarto
Representa um quarto do hotel, com capacidade para até 4 hóspedes.

# Hóspede
Cada hóspede é representado por uma thread que solicita a reserva de um quarto e realiza outras operações.

# Camareira
Cada camareira é representada por uma thread responsável por limpar os quartos após a saída dos hóspedes.

# Recepcionista
Cada recepcionista é representado por uma thread responsável por alocar hóspedes em quartos vagos.

# Regras do Sistema
Os hóspedes devem ser alocados apenas em quartos vagos.
Os hóspedes devem deixar a chave na recepção ao saírem do hotel para passear.
As camareiras só podem entrar em quartos vazios.
Os quartos devem ser limpos após a saída dos hóspedes.
Hóspedes em espera devem ser atendidos assim que quartos vagos estiverem disponíveis.
Como Executar o Projeto
Clone o repositório para sua máquina local.
Abra o projeto em sua IDE preferida (recomendado: IntelliJ IDEA, Eclipse).
Execute a classe Main para iniciar a simulação do sistema.

# Como Executar o Projeto
Clone o repositório para sua máquina local.
Abra o projeto em sua IDE preferida (recomendado: IntelliJ IDEA, Eclipse).
Execute a classe Main para iniciar a simulação do sistema.
