## Sumário
- Instalando no Windows
	- [Baixar Firefox](#baixar-firefox)
	- [Baixar Geckodriver](#baixar-geckodriver)
	- [Instalar o Java](#instalar-o-java)
	- [Clonar o repositório](#clonar-o-repositorio)
 	- [Criando arquivo .env](#configurando-env)
   	- [Buildar o JAR](#buildar-o-jar)
	- [Executando o JAR](#executando-o-jar)
	
- Instalando no Linux
	- [Configurando a VM](#configurando-a-vm)
		-  [Passo 1: Baixar o VirtualBox](#passo-1-baixar-o-virtualbox)
		- [Passo 2: Baixar a Imagem do Ubuntu Server LTS (Sem GUI)](#passo-2-baixar-a-imagem-do-ubuntu-server-lts-sem-gui)
		- [Passo 3: Criar uma VM no VirtualBox](#passo-3-criar-uma-vm-no-virtualbox)
		- [Passo 4: Configuração de Usuário e Senha](#configura%C3%A7%C3%A3o-de-usu%C3%A1rio-e-senha)
		- [Passo 5: Criar um novo disco rígido virtual](#passo-4-criar-um-novo-disco-r%C3%ADgido-virtual)
		- [Passo 6: Selecionar Idioma e base para instalação](#passo-5-selecionar-idioma-e-base-para-instala%C3%A7%C3%A3o)
		- [Passo 6: Configuração de Rede](#passo-6-configura%C3%A7%C3%A3o-de-rede)
		- [Passo 8: Configuração de Proxy](#passo-7-configura%C3%A7%C3%A3o-de-proxy)
		- [Passo 9: Configuração de Armazenamento](#passo-8-configura%C3%A7%C3%A3o-de-armazenamento)
		- [Passo 10: Configuração de Perfil](#passo-9-configura%C3%A7%C3%A3o-de-perfil)
		- [Passo 11: Pular Upgrade para Ubuntu Pro](#passo-10-pular-upgrade-para-ubuntu-pro)
		- [Passo 12: Configuração do SSH](#passo-11-configura%C3%A7%C3%A3o-do-ssh)
		- [Passo 13: Configuração de Snaps do Servidor em Destaque](#passo-12-configura%C3%A7%C3%A3o-de-snaps-do-servidor-em-destaque)
		- [Passo 14: Aguardar a instalação finalizar e clicar em "Reiniciar Agora"](#passo-13-aguardar-a-instala%C3%A7%C3%A3o-finalizar-e-clicar-em-reiniciar-agora)
    	- [Clonar o repositório](#clonar-o-repositorio)
    	- [Criando arquivo .env](#configurando-env)
       	- [Buildar o JAR](#buildar-o-jar)
	- [Transferência de Arquivos para a VM (Sem SSH)](#transfer%C3%AAncia-de-arquivos-para-a-vm-sem-ssh)
   	- [Configuração do SSH](#configura%C3%A7%C3%A3o-do-ssh)
  	- [Transferência de Arquivos para a VM (Com SSH)](#transfer%C3%AAncia-de-arquivos-para-a-vm-com-ssh)
  	- [Instalação do Firefox e Geckodriver](#instala%C3%A7%C3%A3o-do-firefox-e-geckodriver)
	- [Instalação do Java na VM](#instala%C3%A7%C3%A3o-do-java)
	- [Execução do JAR na VM](#execu%C3%A7%C3%A3o-do-jar)
	
- Independente de plataforma
	- [Configurando .env](#configurando-env)
	- [Gerando o OTPAUTH_MIGRATION_URL](#gerando-o-otpauth_migration_url)


# Instalando no Windows
## Baixar Firefox
 - [Download Firefox](https://www.mozilla.org/pt-BR/firefox/windows/) 
-  Realizar a instalação do Firefox e adicionar ao PATH do sistema.
## Baixar Geckodriver
 - [Download Geckodriver](https://github.com/mozilla/geckodriver/releases/download/v0.34.0/geckodriver-v0.34.0-win-aarch64.zip)
-  Extrair a pasta e adicionar o executável do Geckodriver ao PATH do sistema.
## Instalar o Java
[Download Java](https://www.oracle.com/br/java/technologies/downloads/)

## Clonar o repositorio
```Git
git clone https://github.com/Furk-Tech/furk-robot.git	
```
## Criar arquivo .env
[Criando arquivo .env](#configurando-env)

## Buildar o jar
No diretório do projeto, executar:
```Maven
mvn clean package
```

## Executando o jar
```Shell
java -jar <jar_path>/<filename>.jar <AGENDA> <TIPO_PROCESSO> <IS_HEADLESS> <PATH_GECKODRIVER>
```
- **Parâmetros:**
  - `<AGENDA>`: Número da agenda.
  - `<TIPO_PROCESSO>`: Tipo do processamento.
  - `<IS_HEADLESS>`: Indica se será uma execução Headless ou não (TRUE ou FALSE).
  - `<PATH_PARA_GECKODRIVER>`: Caminho para o Geckodriver (opcional). Também pode ser setado no arquivo .env



# Instalando no Linux
## Configurando a VM
### Passo 1: Baixar o VirtualBox
[Download VirtualBox](https://www.virtualbox.org/wiki/Downloads)

### Passo 2: Baixar a Imagem do Ubuntu Server LTS (Sem GUI)
[Download Ubuntu Server](https://ubuntu.com/download/server)

### Passo 3: Criar uma VM no VirtualBox
1. Abra o VirtualBox e clique em "Novo".
2. Nomeie a VM e selecione a imagem ISO do Ubuntu Server baixada anteriormente.
3. Clique em "Próximo".

### Configuração de Usuário e Senha
- Defina o nome de usuário e senha.
- Marque a opção 'Adicionais de Convidado(E)'.
- Certifique-se de ter a ISO 'VBoxGuestAdditions.iso' no diretório de instalação do VirtualBox.

### Passo 4: Criar um novo disco rígido virtual

### Passo 5: Selecionar Idioma e base para instalação

### Passo 6: Configuração de Rede
- Mantenha as configurações padrão e clique em 'Próximo'.

### Passo 7: Configuração de Proxy
- Deixe em branco e clique em 'Próximo'.

### Passo 8: Configuração de Armazenamento
- Mantenha as configurações padrão e clique em 'Próximo'.

### Passo 9: Configuração de Perfil
- Insira o nome, nome do servidor, usuário e senha.
### Passo 10: Pular Upgrade para Ubuntu Pro

### Passo 11: Configuração do SSH
- Pule esta etapa, o SSH pode ser configurado posteriormente.

### Passo 12: Configuração de Snaps do Servidor em Destaque
- Selecione conforme necessário. *Não é obrigatório.*

### Passo 13: Aguardar a instalação finalizar e clicar em "Reiniciar Agora"


## Clonar o repositorio
Na máquina host, clone o repositório:
```Git
git clone https://github.com/Furk-Tech/furk-robot.git	
```

## Criar arquivo .env
[Criando arquivo .env](#configurando-env)

## Buildar o jar
No diretório do projeto (na máquina host), execute:
```Maven
mvn clean package
```

## Transferência de Arquivos para a VM (Sem SSH)

1. Desligue a VM.
2. Vá para as configurações da VM no VirtualBox.
3. Adicione uma nova pasta compartilhada contendo os arquivos necessários.
4. Certifique-se de ter montado a ISO do VBoxLinuxAdditions.
5. Inicialize a VM e insira a imagem das Adições de Convidado.
6. Execute os comandos necessários para montar a pasta compartilhada.
```Shell
sudo mkdir /media/cdrom
sudo mount /dev/cdrom /media/cdrom
sudo mount -t vboxsf <nome_pasta> /mnt/<nome_pasta>
sudo usermod -aG vboxusers USER
```
7. Reinicie a VM e já poderá acessar a pasta compartilhada.

## Configuração do SSH

- Verificar se há uma chave SSH criada na máquina host:

```bash
ls ~/.ssh
```

- Gerar uma nova SSH, caso não exista:

```bash
ssh-keygen -t rsa -b 4096 -C "seu_email@exemplo.com"
```

- Adicionar a chave SSH ao agente SSH:

```bash
eval `ssh-agent -s`
ssh-add ~/.ssh/id_rsa
```

### Nota

Se o `ssh-agent` não estiver instalado ou habilitado, você pode fazê-lo nas configurações do Windows. Abrindo o gerenciador de tarefas -> Serviços -> Duplo Clique em ssh-agent -> Iniciar.

- No host da VM, instalar o Open-SSH:

```bash
sudo apt update
sudo apt install openssh-server
```

- Permitir o SSH no Firewall:

```bash
sudo ufw status
sudo ufw allow ssh
sudo ufw enable
```

- Para encontrar o IP da VM:

```bash
ip addr show
```

Na máquina host, para se conectar à VM:

```bash
ssh username@ip_da_vm
```

- Para alterar as configurações do SSH na VM:

```bash
sudo nano /etc/ssh/sshd_config
```

- Autenticar com a chave SSH:

  1. Copie o conteúdo da chave SSH.
  2. Cole no arquivo `~/.ssh/authorized_keys`.
  3. No diretório onde a chave SSH foi criada, copie o conteúdo da chave. 
  4. Na VM, abra o arquivo `~/.ssh/authorized_keys` e cole o conteúdo da chave. 
  5. Salve o arquivo.
 
### Transferência de Arquivos para a VM (Com SSH):

**Usando Secure Copy**
```Shell
scp <path_arquivo> username@ip_vm:<path_destino_vm> -> para arquivos
scp -r <path_diretório> username@ip_vm:<path_destino_vm> -> para diretórios
```

## Instalação do Firefox e Geckodriver

```bash
sudo apt-get update
sudo apt-get install firefox
```
Verifique a instalação:

```bash
firefox --version
geckodriver --version
```

Não é necessário instalar o Geckodriver separadamente.

## Instalação do Java

```bash
sudo apt update
sudo apt install openjdk-17-jdk -y
```

## Execução do JAR

```bash
java -jar /media/<nome_da_pasta>/nome_do_jar.jar <AGENDA> <TIPO_PROCESSO> <IS_HEADLESS> <PATH_PARA_GECKODRIVER>
```

- **Parâmetros:**
  - `<AGENDA>`: Número da agenda.
  - `<TIPO_PROCESSO>`: Tipo do processamento.
  - `<IS_HEADLESS>`: Indica se será uma execução Headless ou não (TRUE ou FALSE).
  - `<PATH_PARA_GECKODRIVER>`: Caminho para o Geckodriver (opcional). Também pode ser setado no arquivo .env

Exemplo de execução:

```bash
java -jar /media/<nome_da_pasta>/nome_do_jar.jar 2276376 EXTRACAO_BILHETES TRUE /snap/bin/geckodriver
```


# Independente de plataforma
## Configurando .env

 Crie um arquivo .env na raiz do projeto e criar as variáveis:
 
```env
API_USERNAME=<API_USERNAME>

API_PASSWORD=<API_PASSWORD>

API_DADOS_PROCESSAMENTO_URL=<URL_CONTROLA_API_DADOS_PROCESSAMENTO_URL>

  

PATH_GECKODRIVER=<GECKODRIVER_PATH>

DEFAULT_WAIT_TIMEOUT=<TEMPO MÁXIMO (EM SEGUNDOS) QUE O WEBDRIVER IRÁ AGUARDAR CADA AÇÃO SER FINALIZADA / ELEMENTO SER ENCONTRADO>

DEFAULT_WAIT_POLLING=<INTERVALO DE TEMPO (EM MILISSEGUNDOS) EM QUE O WEBDRIVER IRÁ CONFERIR SE A AÇÃO FOI FINALIZADA / ELEMENTO FOI ENCONTRADO>

  

OTPAUTH_MIGRATION_URL=<LINK DO QR CODE GERADO PARA MIGRAR GOOGLE AUTHENTICATOR>
```

Caso o PATH_GECKODRIVER esteja vazio ou não exista no .env, serão usados os caminhos:
- /snap/bin/geckodriver caso o processo esteja sendo executado em um ambiente Linux.
- C:/Geckodriver/geckodriver.exe caso o processo esteja sendo executado em um ambiente Windows.
## Gerando o OTPAUTH_MIGRATION_URL:
1. Acesse o app do Google Authenticator
2. Menu
3. Transferir contas
4. Exportar contas
5. Selecione a conta
6. Leia o QR Code com algum leitor de QR Code para visualizar o OTPAUTH_MIGRATION_URL
