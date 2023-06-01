package org.example.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DBInitialization {

//    @Bean(initMethod = "createBase")
    public void createBase() {
        String create = "CREATE DATABASE \"AstonIntesive\"\n" +
                "    WITH\n" +
                "    OWNER = postgres\n" +
                "    ENCODING = 'UTF8'\n" +
                "    CONNECTION LIMIT = -1\n" +
                "    IS_TEMPLATE = False;";
    }
}
