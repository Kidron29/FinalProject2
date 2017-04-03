package configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import play.db.DB;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
public class DataConfig {

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter hibernateAdapter = new HibernateJpaVendorAdapter();
        hibernateAdapter.setShowSql(false);
        hibernateAdapter.setGenerateDdl(true);
        hibernateAdapter.setDatabase(Database.MYSQL);

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPackagesToScan("models");
        entityManagerFactory.setJpaVendorAdapter(hibernateAdapter);
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory());
    }

    @Bean
    public DataSource dataSource() {
        // Return the datasource from the play framework.
        return DB.getDataSource();
    }
    /*
    @Bean
    public DataSource dataSource() {
        // Return the datasource from the play framework.
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
    	play.Configuration conf = Play.application().configuration();
    	dataSource.setDriverClassName(conf.getString("db.default.driver"));
        dataSource.setUrl(conf.getString("db.default.url"));
        dataSource.setUsername(conf.getString("db.default.user"));
        dataSource.setPassword(conf.getString("db.default.password"));
        return dataSource();
        //https://github.com/SocialFinance/userLogin_HelloWorld
    }*/
}
