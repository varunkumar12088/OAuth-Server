package com.learning.helpler;

import com.learning.constant.Provider;
import com.learning.entity.User;
import com.learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor
public class CsvUserDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        InputStream inputStream = new ClassPathResource("static/users.csv").getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        boolean skipHeader = true;

        while ((line = reader.readLine()) != null) {

            if (skipHeader) {
                skipHeader = false;
                continue;
            }

            String[] values = line.split(",");

            String username = values[0];
            String rawPassword = values[1];
            String role = values[2];

            if (userRepository.existsByUsername(username)) {
                continue;
            }

            User user = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(rawPassword))
                    .role(role)
                    .provider(Provider.LOCAL)
                    .build();

            userRepository.save(user);
        }

    }
}
