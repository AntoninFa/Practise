package com.acme.auto;

import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.support.TaskExecutorAdapter;
import static java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor;
import static org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME;

/**
 * Konfiguration für "Virtual Threads" ab Java 19.
 * <a href="https://spring.io/blog/2022/10/11/embracing-virtual-threads">Siehe Blog-Beitrag von Mark Paluch</a>.
 *
 * @author <a href="mailto:Juergen.Zimmermann@h-ka.de">Jürgen Zimmermann</a>
 */
interface ThreadConfig {
    /**
     * Bean-Definition, um "Virtual Threads" für die asynchrone Ausführung zu nutzen.
     *
     * @return Objekt von AsyncTaskExecutor von Spring
     */
    @Bean(APPLICATION_TASK_EXECUTOR_BEAN_NAME)
    default AsyncTaskExecutor asyncTaskExecutor() {
        return new TaskExecutorAdapter(newVirtualThreadPerTaskExecutor());
    }

    /**
     * Bean-Definition, um "Virtual Threads" innerhalb von Tomcat zu nutzen.
     *
     * @return Objekt von TomcatProtocolHandlerCustomizer von Tomcat
     */
    @Bean
    default TomcatProtocolHandlerCustomizer<?> protocolHandlerVirtualThreadExecutorCustomizer() {
        return protocolHandler -> protocolHandler.setExecutor(newVirtualThreadPerTaskExecutor());
    }
}
