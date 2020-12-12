package com.train.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class which encapsulates connections pool with database. 
 * This class implements singleton pattern.
 */
public class BasicConnectionPool implements ConnectionPoolI {

	private static final String DB_DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/train_company?serverTimezone=UTC";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "root";
	
    private static final int INITIAL_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 20;
    private static final int MAX_TIMEOUT = 5;
	
    private final List<Connection> connectionPool;
    private final List<Connection> usedConnections = new ArrayList<>();
    
    
    /** 
     * Returns ready connection from the pool.
     * @return {@link java.sql.Connection}
     */
    @Override
    public synchronized Connection getConnection() {
        if (connectionPool.isEmpty()) {
            if (usedConnections.size() < MAX_POOL_SIZE) {
                connectionPool.add(createConnection());
            } else {
                throw new RuntimeException("Maximum pool size reached, no available connections!");
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);

        try {
			if(!connection.isValid(MAX_TIMEOUT)){
			    connection = createConnection();
			}
		} catch (SQLException e) {
			throw new RuntimeException("Timeout can't be less 0");
		}

        usedConnections.add(connection);
        return connection;
    }

    /** 
     * Returns used connection back in the pool.
     * @param {@link java.sql.Connection}
     * @return true if connection was removed from {@link BasicConnectionPool#usedConnections}
     */
    @Override
    public synchronized boolean releaseConnection(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }
    
    /** 
     * This method count free connection plus busy connection.
     * @return int the total open connections 
     */
    @Override
    public synchronized int getSize() {
        return connectionPool.size() + usedConnections.size();
    }
    
    /** 
     * This method close all open connection with database.
     */
    @Override
    public synchronized void shutdown() {
        for (Connection connection : usedConnections) {
        	releaseConnection(connection);
        }
        for (Connection connection : connectionPool) {
        	if (connection != null) {
    			try {
    				connection.close();
    			} catch (SQLException e) {
    				throw new RuntimeException("Can't connection close, some occured error!");
    			}
    		} 
        }
        connectionPool.clear();
    }

    static {
		try {
			Class.forName(DB_DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Failed to find driver class " + DB_DRIVER_NAME);
		}
	}
    
    private BasicConnectionPool(List<Connection> connectionPool) {
        this.connectionPool = connectionPool;
    }
    
    public static ConnectionPoolI getPool() {
		return PoolHolder.POOL;
	}
    
    private static class PoolHolder {
		private static final ConnectionPoolI POOL = create();
	}

    private static BasicConnectionPool create() {
        List<Connection> pool = new ArrayList<>(INITIAL_POOL_SIZE);
        for (int i = 0; i < INITIAL_POOL_SIZE; i++) {
            pool.add(createConnection());
        }
        return new BasicConnectionPool(pool);
    }

    private static Connection createConnection() {
    	try {
			return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		} catch (SQLException e) {
			throw new RuntimeException("Failed getting connection!");
		}
    }

}
