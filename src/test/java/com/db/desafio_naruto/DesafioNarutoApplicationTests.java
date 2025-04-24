package com.db.desafio_naruto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.db.desafio_naruto.infrastructure.config.TestConfig;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestConfig.class)
class DesafioNarutoApplicationTests {

	@Test
	void contextLoads() {
	}

}
