package com.teles.chanchan.config.settings;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("datasource")
public class DataSourceSettings {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceSettings.class);

	private String url;
	private String className;
	private String username;
	private String password;
	private final Pool pool = new Pool();
	private final PreparedStatements preparedStatements = new PreparedStatements();

	@PostConstruct
	private void init() {
		logger.info(toString());
	}

	public Pool getPool() {
		return pool;
	}

	public PreparedStatements getPreparedStatements() {
		return preparedStatements;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static final class Pool {

		private String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String toString() {
			return "Pool [name=" + name + "]";
		}

	}

	public static final class PreparedStatements {

		private boolean cache;
		private int size;
		private int sqlLimit;

		public boolean isCache() {
			return cache;
		}

		public void setCache(boolean cache) {
			this.cache = cache;
		}

		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public int getSqlLimit() {
			return sqlLimit;
		}

		public void setSqlLimit(int sqlLimit) {
			this.sqlLimit = sqlLimit;
		}

		public String toString() {
			return "PreparedStatements [cache=" + cache + ", size=" + size + ", sqlLimit=" + sqlLimit + "]";
		}

	}

	@Override
	public String toString() {
		return "DataSourceSettings [url=" + url + ", className=" + className + ", username=" + username + ", password="
				+ password + ", pool=" + pool + ", preparedStatements=" + preparedStatements + "]";
	}

}
