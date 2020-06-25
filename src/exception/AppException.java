package exception;

public class AppException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4713268407511331996L;

	public AppException(String message, Exception exception) {
		super(message, exception);
	}

}
