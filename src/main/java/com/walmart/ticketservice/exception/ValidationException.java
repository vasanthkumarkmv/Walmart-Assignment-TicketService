package com.walmart.ticketservice.exception;


import java.io.PrintWriter;

public class ValidationException extends RuntimeException {

    /**
     * Vendor specific error code
     */
    private String errorCode;

    /**
     * Exception reference
     */
    private volatile Throwable linkedException;

    static final long serialVersionUID = -5621384651494307979L;

    /**
     * Construct a ValidationException with the specified detail message.  The
     * errorCode and linkedException will default to null.
     *
     * @param message a description of the exception
     */
    public ValidationException(String message) {
        this(message, null, null);
    }

    /**
     * Construct a ValidationException with the specified detail message and vendor
     * specific errorCode.  The linkedException will default to null.
     *
     * @param message   a description of the exception
     * @param errorCode a string specifying the vendor specific error code
     */
    public ValidationException(String message, String errorCode) {
        this(message, errorCode, null);
    }

    /**
     * Construct a ValidationException with a linkedException.  The detail message and
     * vendor specific errorCode will default to null.
     *
     * @param exception the linked exception
     */
    public ValidationException(Throwable exception) {
        this(null, null, exception);
    }

    /**
     * Construct a ValidationException with the specified detail message and
     * linkedException.  The errorCode will default to null.
     *
     * @param message   a description of the exception
     * @param exception the linked exception
     */
    public ValidationException(String message, Throwable exception) {
        this(message, null, exception);
    }

    /**
     * Construct a ValidationException with the specified detail message, vendor
     * specific errorCode, and linkedException.
     *
     * @param message   a description of the exception
     * @param errorCode a string specifying the vendor specific error code
     * @param exception the linked exception
     */
    public ValidationException(String message, String errorCode, Throwable exception) {
        super(message);
        this.errorCode = errorCode;
        this.linkedException = exception;
    }


    /**
     * Returns a short description of this ValidationException.
     */
    public String toString() {
        return linkedException == null ?
                super.toString() :
                super.toString() + "\n - with linked exception:\n[" +
                        linkedException.toString() + "]";
    }

    /**
     * Prints this ValidationException and its stack trace (including the stack trace
     * of the linkedException if it is non-null) to the PrintStream.
     *
     * @param s PrintStream to use for output
     */
    public void printStackTrace(java.io.PrintStream s) {
        super.printStackTrace(s);
    }

    /**
     * Prints this ValidationException and its stack trace (including the stack trace
     * of the linkedException if it is non-null) to <tt>System.err</tt>.
     */
    public void printStackTrace() {
        super.printStackTrace();
    }

    /**
     * Prints this ValidationException and its stack trace (including the stack trace
     * of the linkedException if it is non-null) to the PrintWriter.
     *
     * @param s PrintWriter to use for output
     */
    public void printStackTrace(PrintWriter s) {
        super.printStackTrace(s);
    }

    @Override
    public Throwable getCause() {
        return linkedException;
    }

}

