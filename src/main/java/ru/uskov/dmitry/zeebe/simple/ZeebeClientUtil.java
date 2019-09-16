package ru.uskov.dmitry.zeebe.simple;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.DeploymentEvent;

import java.util.function.Consumer;

public class ZeebeClientUtil {

    public static ZeebeClient createClient() {
        return ZeebeClient.newClientBuilder()
                .brokerContactPoint("127.0.0.1:26500")
                .build();
    }

    public static void executeCommand(Consumer<ZeebeClient> clientConsumer) {
        try (ZeebeClient client = createClient()) {
            System.out.println("Connected.");
            clientConsumer.accept(client);
        }
        System.out.println("Closed.");
    }
}
