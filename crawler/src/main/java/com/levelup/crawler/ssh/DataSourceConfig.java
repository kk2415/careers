package com.levelup.crawler.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;

//@Component
public class DataSourceConfig {

    @Value("${db.driver-class-name}")
    private String driverClassName;

    @Value("${db.username}")
    private String dbUsername;

    @Value("${db.password}")
    private String dbPassword;

    @Value("${db.host}")
    private String dbHost;

    @Value("${db.port}")
    private int dbPort;

    @Value("${db.database-name}")
    private String databaseName;

    @Value("${ssh.host}")
    private String sshHost;

    @Value("${ssh.port}")
    private int sshPort;

    @Value("${ssh.username}")
    private String sshUsername;

    @Value("${ssh.password}")
    private String sshPassword;

    @Bean
    public DataSource dataSource() throws JSchException {
        // SSH 터널 설정
        JSch jsch = new JSch();
        Session session = jsch.getSession(sshUsername, sshHost, sshPort);
        session.setPassword(sshPassword);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.connect();
        int assignedPort = session.setPortForwardingL(0, dbHost, dbPort);

        // 데이터 소스 설정
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl("jdbc:mysql://localhost:" + assignedPort + "/" + databaseName);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);

        return dataSource;
    }
}
