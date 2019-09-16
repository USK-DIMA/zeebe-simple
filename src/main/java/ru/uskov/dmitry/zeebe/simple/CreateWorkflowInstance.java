package ru.uskov.dmitry.zeebe.simple;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.WorkflowInstanceEvent;
import ru.uskov.dmitry.zeebe.simple.data.Order;

public class CreateWorkflowInstance {
    public static void main(String[] args) {
        ZeebeClientUtil.executeCommand(client -> {
            while (!Thread.currentThread().isInterrupted()) {
                createOrder(client);
              try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    private static void createOrder(ZeebeClient client) {
        Order order = Order.create();
        final WorkflowInstanceEvent wfInstance = client.newCreateInstanceCommand()
                .bpmnProcessId("order-process")
                .latestVersion()
                .variables(order)
                .send()
                .join();
        final long workflowInstanceKey = wfInstance.getWorkflowInstanceKey();
        System.out.println("Workflow instance created. Key: " + workflowInstanceKey + ", order: " + order);

    }
}
