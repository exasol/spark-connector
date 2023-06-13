package com.exasol.spark.s3;

/**
 * An exception for Exasol JDCB connection issues.
 */
public class ExasolConnectionException extends RuntimeException {
    private static final long serialVersionUID = 2818034094289319833L;

    /**
     * Creates an instance of a {@link ExasolConnectionException}.
     *
     * @param message error message
     * @param cause   exception cause
     */
    public ExasolConnectionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates an instance of a {@link ExasolConnectionException}.
     *
     * @param message error message
     */
    public ExasolConnectionException(final String message) {
        super(message);
    }
}
