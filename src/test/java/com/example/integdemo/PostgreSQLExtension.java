package com.example.integdemo;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PostgreSQLExtension implements BeforeAllCallback, AfterAllCallback {

	private PostgreSQLContainer<?> postgres;

	@Override
	public void beforeAll(ExtensionContext context) {
		postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:11")).withDatabaseName("prop")
				.withUsername("postgres").withPassword("root").withExposedPorts(5433);

		postgres.start();
		String jdbcUrl =
				String.format("jdbc:postgresql://localhost:%d/prop", postgres.getFirstMappedPort());
		System.setProperty("spring.datasource.url", jdbcUrl);
		System.setProperty("spring.datasource.username", "postgres");
		System.setProperty("spring.datasource.password", "root");
	}

	@Override
	public void afterAll(ExtensionContext context) {
		postgres.stop();
	}
}
