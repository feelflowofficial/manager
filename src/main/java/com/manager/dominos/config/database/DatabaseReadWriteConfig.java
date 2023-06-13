package com.manager.dominos.config.database;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource." + "databasereadwrite") // application.yml에서 어떤 properties를 읽을 지 지정
@EnableJpaRepositories(
        entityManagerFactoryRef = "databasereadwrite" + "EntityManagerFactory", // JPA에 관한 설정 및 파일의 위치는 어디에 있는 지 명시
        transactionManagerRef = "databasereadwrite" + "TransactionManager",
        basePackages = {DatabaseConfig.BASE_ENTITY_PACKAGE_PREFIX + ".databasereadwrite"}
)
@MapperScan(
        basePackages = {DatabaseConfig.BASE_MAPPER_PACKAGE_PREFIX + ".databasereadwrite"}
)

public class DatabaseReadWriteConfig extends DatabaseConfig {
    final String name = "databasereadwrite";

    @Bean(name = name + "DataSource") // 각 Method들을 빈으로 등록하며 빈의 이름을 지정하여 동일한 클래스일지라도 @Qulifier를 통한 선택적 주입이 가능
    @Primary // 첫 째 DataSource 명시
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(new HikariDataSource(this));
    }

    /* --------------------mybatis 세팅-------------------- */
    @Bean(name = name + "SessionFactory")
    @Primary
    public SqlSessionFactory sqlSessionFactory(@Qualifier(name + "DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        setConfigureSqlSessionFactory(sqlSessionFactoryBean, dataSource);

        return sqlSessionFactoryBean.getObject();
    }

    @Bean(name = name + "SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier(name + "SessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /* --------------------JPA 세팅-------------------- */
    @Bean(name = name + "EntityManagerFactory")
    @Primary
    public EntityManagerFactory entityManagerFactory(@Qualifier(name + "DataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource);
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.manager.dominos.domain.entity");
        localContainerEntityManagerFactoryBean.setPersistenceUnitName(name);
        setConfigureEntityManagerFactory(localContainerEntityManagerFactoryBean);

        return localContainerEntityManagerFactoryBean.getObject();
    }

    @Bean(name = name + "TransactionManager")
    @Primary
    public PlatformTransactionManager platformTransactionManager(@Qualifier(name + "EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);

        return jpaTransactionManager;
    }
}
