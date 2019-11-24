package com.hqw.pro.hspboot.others;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class BeanLoadCostPostProcessor implements BeanPostProcessor, ApplicationListener<ContextRefreshedEvent> {

    private Map<String, Long> cost = new HashMap<>(500);

    private static final int TOPK = 10;

    private static AtomicInteger beanCount = new AtomicInteger(0);

    private TopK<CostInfo> topK = new TopK<>(TOPK);

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        cost.put(beanName, Instant.now().toEpochMilli());
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        beanCount.incrementAndGet();

        Long startStamp = cost.get(beanName);
        if (startStamp != null) {
            Long currentCost = Instant.now().toEpochMilli() - startStamp;
            cost.put(beanName, currentCost);
            topK.add(new CostInfo(beanName, currentCost));
        }
        return bean;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        printBeanInitCostLog();
    }

    private void printBeanInitCostLog() {
        StringBuilder stringBuilder = new StringBuilder().append("\r\n");
        List<CostInfo> costInfos = Arrays.asList(topK.toArray(new CostInfo[0])).stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        for (int i = 0; i < costInfos.size(); i++) {
            stringBuilder.append(" costRank" + (i + 1) + ",beanName=" + costInfos.get(i).name + ",cost=" + costInfos.get(i).cost).append("\r\n");
        }
        System.out.println("load_spring_bean_cost_info,bean created count:{},info:{}" + beanCount.get() + stringBuilder.toString());
    }

    public static class CostInfo implements Comparable<CostInfo> {
        private String name;
        private Long cost;

        public CostInfo(String name, Long cost) {
            this.name = name;
            this.cost = cost;
        }

        public String getName() {
            return name;
        }

        public Long getCost() {
            return cost;
        }

        @Override
        public int compareTo(CostInfo o) {
            return this.cost.compareTo(o.getCost());
        }
    }
}
