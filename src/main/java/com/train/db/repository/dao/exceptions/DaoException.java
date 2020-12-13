package com.train.db.repository.dao.exceptions;

/**
 * This exception reports about error occurs in the layer which works with the database.
 */
public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	DaoException() {
		super();
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}
}
