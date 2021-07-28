package kr.nzzi.msa.pmg.pomangamapimonilith.global.configuration.database;

import org.apache.commons.dbutils.QueryRunner;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class QueryRunnerConfiguration {

    @Bean
    @Primary
    QueryRunner queryRunner (DataSource dataSource) {
        return new QueryRunner(dataSource);
    }

    @Bean
    @Primary
    JpaResultMapper jpaResultMapper () {
        return new JpaResultMapper();
    }
}
