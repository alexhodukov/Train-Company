package com.train.db;

import java.sql.Connection;

public interface ConnectionPoolI {
	
    /** 
     * Returns ready connection from the pool.
     * @return {@link java.sql.Connection}
     */
    Connection getConnection();
    
    /** 
     * Returns used connection back in the pool.
     * @param {@link java.sql.Connection}
     * @return true if connection was removed from {@link BasicConnectionPool#usedConnections}
     */
    boolean releaseConnection(Connection connection);
    
    /** 
     * This method count total open connections.
     * @return int the total open connections 
     */
    int getSize();
    
    /** 
     * This method close all open connection with database.
     */
    void shutdown();  
}
