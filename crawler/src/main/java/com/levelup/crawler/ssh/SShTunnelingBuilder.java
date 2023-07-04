package com.levelup.crawler.ssh;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class SShTunnelingBuilder {

    @Value("${spring.ssh.tunnel.host}")
    private String host;

    @Value("${spring.ssh.tunnel.username}")
    private String username;

    @Value("${spring.ssh.tunnel.port}")
    private String port;

    @Value("${spring.ssh.tunnel.private_key}")
    private String privateKey;

    @Value("${spring.ssh.tunnel.database_port}")
    private String databasePort;

    public Integer build() {
        Integer forwardedPort = null;

        try {
            JSch jSch = new JSch();

            jSch.addIdentity(privateKey);  // 개인키
            Session session = jSch.getSession(username, host, Integer.parseInt(port));  // 세션 설정

            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();  // ssh 연결

            // 로컬pc의 남는 포트 하나와 원격 접속한 pc의 db포트 연결
            forwardedPort = session.setPortForwardingL(0, "localhost", Integer.parseInt(databasePort));

        } catch (Exception e){
            e.printStackTrace();
        }

        return forwardedPort;
    }
}
