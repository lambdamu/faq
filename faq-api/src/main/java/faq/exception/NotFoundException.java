package faq.exception;

public class NotFoundException extends ServiceException {
	private static final long serialVersionUID = -4334875370019242776L;
	
	public NotFoundException(Long id) {
		super("error.notFound", new Object[]{id == null ? "" : Long.toString(id)});
	}
}