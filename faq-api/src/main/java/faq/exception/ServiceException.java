package faq.exception;

public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = -6667500422806333126L;
	protected final String key;
	protected final Object[] params;
	
	public ServiceException(String key) {
		this(key, null);
	}
	
	public ServiceException(String key, Object[] params) {
		super(key);
		this.key = key;
		this.params = params;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public Object[] getParams() {
		return this.params;
	}
}