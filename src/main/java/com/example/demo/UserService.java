package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void save(User user) throws Exception {
        String originalUsername = user.getOriginalUsername().trim();
        String originalPassword = user.getOriginalPassword().trim();
        String currentUserName = user.getCurrentUsername().trim();
        String currentPassword = user.getCurrentPassword().trim();
        String cmd = "/usr/bin/imapsync --host1 imap.mxhichina.com --ssl1 --port1 993 --user1 " + originalUsername + " --password1 " + originalPassword +
                " --host2 192.168.12.125 --ssl1 --port1 993 --tls2 --user2 " + currentUserName + " --password2 " + currentPassword + " --buffersize 2097152000 --authmech2 LOGIN --useuid --timeout 6";

        ProcessBuilder processBuilder = new ProcessBuilder(ProcessUtils.partitionCommandLine(cmd));
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();
        int success = 1;
        String s = "Exiting with return value 0";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(s)) {
                    success = 0;
                }
            }
        }
        process.waitFor();
        process.destroyForcibly();
        user.setOriginalPassword(Base64.getEncoder().encodeToString(originalPassword.getBytes()));
        user.setCurrentPassword(Base64.getEncoder().encodeToString(currentPassword.getBytes()));
        user.setSuccess(success);
        userRepository.save(user);
    }
}