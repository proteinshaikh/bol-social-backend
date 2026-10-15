package com.bol.post;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoData {
    @Bean
    CommandLineRunner seedPosts(PostRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Post("The more you understand people's level of intelligence, the less you argue.", "Samrah", "@Chemist1", null, null));
                repository.save(new Post("A good life is built from small habits repeated consistently.", "BOL Daily", "@boldaily", null, null));
                repository.save(new Post("Technology should make people more expressive, not more distracted. बोलो अपनी बात।", "Zeeshan Shaikh", "@proteinshaikhs", null, null));
            }
        };
    }
}
