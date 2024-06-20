# Usar uma imagem base com JDK 21
FROM openjdk:21-jdk-slim

# Instalando Maven e outras dependências necessárias para o funcionamento do firefox e geckodriver
RUN apt-get update && \
    apt-get install -y \
    maven \
    bzip2 \
    wget \
    curl \
    libgtk-3-0 \
    libdbus-glib-1-2 \
    xvfb

# Baixando e instalando o Firefox ESR versão 115
RUN wget -q "https://download-installer.cdn.mozilla.net/pub/firefox/releases/115.0esr/linux-x86_64/en-US/firefox-115.0esr.tar.bz2" -O /tmp/firefox.tar.bz2 && \
    tar -xjf /tmp/firefox.tar.bz2 -C /opt/ && \
    ln -s /opt/firefox/firefox /usr/local/bin/firefox && \
    rm /tmp/firefox.tar.bz2

# Baixando e instalando o Geckodriver versão correspondente ao firefox esr
RUN GECKODRIVER_VERSION="v0.33.0" && \
    wget -q "https://github.com/mozilla/geckodriver/releases/download/${GECKODRIVER_VERSION}/geckodriver-${GECKODRIVER_VERSION}-linux64.tar.gz" -O /tmp/geckodriver.tar.gz && \
    tar -xzf /tmp/geckodriver.tar.gz -C /usr/local/bin && \
    rm /tmp/geckodriver.tar.gz

## Adicionando o geckodriver ao PATH
ENV PATH="/usr/local/bin/geckodriver:$PATH"

## Dando permissão de execução ao geckodriver
RUN chmod +x /usr/local/bin/geckodriver

# Configurando o Xvfb (para executar o firefox sem gui)
RUN echo '#!/bin/sh\nXvfb :99 -screen 0 1920x1080x24 &\nexec "$@"' > /usr/local/bin/xvfb-run && \
    chmod +x /usr/local/bin/xvfb-run

## Xvfb escuta as conexões no display número 99.
ENV DISPLAY=:99

EXPOSE 8080

WORKDIR /app
COPY . /app

#RUN mvn clean install

# Iniciando a aplicação
CMD ["sh", "-c", "xvfb-run java -jar /app/target/furk-robot-1.0-SNAPSHOT.jar"]
