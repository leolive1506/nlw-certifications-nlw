package com.santam.certification_nlw.seed;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CreateSeed {
    private final JdbcTemplate jdbcTemplate;

    public CreateSeed(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public static void main(String[] args) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5434/pg_nlw");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");

        CreateSeed createSeed = new CreateSeed(dataSource);
        createSeed.run();
    }

    public void run(String ...args) {
        executeSqlFile("src/main/resources/create.sql");
    }

    private void executeSqlFile(String filePath) {
        try {
            String sqlScript = new String(Files.readAllBytes(Paths.get(filePath)));
            jdbcTemplate.execute(sqlScript);
            System.out.println("Seed executado com sucesso");
        } catch (IOException e) {
            System.out.println("Erro ao executar arquivo: " + e.getMessage());
        }

    }
}