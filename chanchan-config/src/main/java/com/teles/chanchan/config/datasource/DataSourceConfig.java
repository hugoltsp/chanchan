package com.teles.chanchan.config.datasource;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.teles.chanchan.domain.settings.DataSourceSettings;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableJpaRepositories(basePackages = { "com.teles.chanchan.data.repository" })
@EntityScan(basePackages = { "com.teles.chanchan.domain.orm" })
@SpringBootConfiguration
public class DataSourceConfig {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

	private final DataSourceSettings dataSourceSettings;

	public DataSourceConfig(DataSourceSettings settings) {
		this.dataSourceSettings = settings;
	}

	@Bean
	public DataSource dataSource() {
		logger.info("Creation connection pool.");

		String className = this.dataSourceSettings.getClassName();
		String url = this.dataSourceSettings.getUrl();
		String username = this.dataSourceSettings.getUsername();
		String password = this.dataSourceSettings.getPassword();
		boolean cachePreparedStatements = this.dataSourceSettings.getPreparedStatements().isCache();
		int preparedStatementsSize = this.dataSourceSettings.getPreparedStatements().getSize();
		int preparedStatementsSqlLimit = this.dataSourceSettings.getPreparedStatements().getSqlLimit();
		String poolName = this.dataSourceSettings.getPool().getName();

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDataSourceClassName(className);

		hikariConfig.addDataSourceProperty("url", url);
		hikariConfig.addDataSourceProperty("user", username);
		hikariConfig.addDataSourceProperty("password", password);
		hikariConfig.addDataSourceProperty("cachePrepStmts", cachePreparedStatements);
		hikariConfig.addDataSourceProperty("prepStmtCacheSize", preparedStatementsSize);
		hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", preparedStatementsSqlLimit);
		hikariConfig.setPoolName(poolName);

		HikariDataSource hikariDataSource = new HikariDataSource(hikariConfig);
		return hikariDataSource;
	}
}
