package faq.exception;

public class FaqNotUniqueException extends ServiceException {
	private static final long serialVersionUID = -5098678157963543798L;
	
	public FaqNotUniqueException(Long id) {
		super("error.faq.notUnique", new Object[]{id == null ? "" : Long.toString(id)});
	}
}