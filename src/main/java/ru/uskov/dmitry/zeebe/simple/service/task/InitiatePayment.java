package ru.uskov.dmitry.zeebe.simple.service.task;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.worker.JobClient;
import ru.uskov.dmitry.zeebe.simple.ZeebeClientUtil;
import ru.uskov.dmitry.zeebe.simple.data.Order;

public class InitiatePayment {
    public static void main(String[] args) {
        ZeebeClient client = ZeebeClientUtil.createClient();
        client.newWorker()
                .jobType("initiate-payment")
                .handler((jobClient, activatedJob) -> {
                    Order order = processOrder(activatedJob);
                    commit(jobClient, activatedJob, order);
                    sendEventMessage(client, order);
                })
                .fetchVariables("orderId", "orderValue")
                .open();
        System.out.println("Opened");
    }

    private static void sendEventMessage(ZeebeClient client, Order order) {
        client.newPublishMessageCommand()
                .messageName("payment-received")
                .correlationKey("" + order.getOrderId())
                .variables(order)
                .send();
    }

    private static Order processOrder(ActivatedJob job) {
        Order order = job.getVariablesAsType(Order.class);
        System.out.println("Process order: " + order);
        return order;
    }


    public static void commit(JobClient jobClient, ActivatedJob activatedJob, Order order) {
        jobClient.newCompleteCommand(activatedJob.getKey())
                .variables(order)
                .send()
                .join();
        System.out.println("Process committed");
    }

}
