package com.tech.furk.config;

import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Substitui o executor de tarefas padrão do Spring por um executor baseado em
 * threads virtuais, permitindo a execução eficiente de métodos assíncronos.
 * 
 * Configura o Tomcat para usar um executor de threads virtuais, o que pode
 * melhorar a capacidade do servidor de lidar com um grande número de
 * requisições simultâneas, potencialmente reduzindo a sobrecarga de
 * gerenciamento de threads tradicionais.
 */

@EnableAsync
@Configuration
public class TaskExecutorConfig {

    @Bean(TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME)
    public AsyncTaskExecutor asyncTaskExecutor() {
        return new TaskExecutorAdapter(createVirtualThreadExecutor());
    }

    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> {
            protocolHandler.setExecutor(Executors.newVirtualThreadPerTaskExecutor());
        };
    }

    private ExecutorService createVirtualThreadExecutor() {
        ThreadFactory threadFactory = r -> {
            Thread thread = Thread.ofVirtual().unstarted(r);
            thread.setName("Virtual-thread-" + thread.threadId());
            return thread;
        };
        return Executors.newThreadPerTaskExecutor(threadFactory);
    }

}
