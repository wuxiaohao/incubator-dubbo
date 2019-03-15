/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.dubbo.demo.consumer;

import com.alibaba.dubbo.demo.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Consumer {

    private Consumer(){}

    private static ClassPathXmlApplicationContext context = null;

    public static ClassPathXmlApplicationContext getContext() {
        if (context == null) {
            synchronized (Consumer.class) {
                if (context == null) {
                    context = new ClassPathXmlApplicationContext("classpath*:META-INF/spring/dubbo-demo-consumer.xml");
                    context.start();
                }
            }
        }
        return context;
    }

    public static void stop() {
        if (context != null) {
            context.stop();
        }
    }

    public static void main(String[] args) {

        DemoService demoService = getContext().getBean(DemoService.class);

        while (true) {
            try {
                Thread.sleep(1000);
                String hello = demoService.sayHello("world"); // call remote method
                System.out.println(hello); // get result

            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

    }
}
