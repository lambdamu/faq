package faq.exception;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * I18n message.
 *
 */
@RestControllerAdvice
class ServiceAdvice {
	@Autowired
	private MessageSource messageSource;

	@ResponseBody
	@ExceptionHandler(ServiceException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleServiceException(ServiceException x, Locale locale) {
		String msg = this.messageSource.getMessage(x.getKey(), x.getParams(), locale);
		return new ErrorResponse(0, msg);
	}

	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
	public ErrorResponse handleConstaintViolationException(final ConstraintViolationException x, Locale locale) {
		StringBuilder sb = new StringBuilder();
		Set<ConstraintViolation<?>> violations = x.getConstraintViolations();
		if (violations != null) {
			sb.append(violations.stream()
					.map(violation -> violation.getMessage())
					.collect(Collectors.joining("\n")));
		}
		
		String msg = this.messageSource.getMessage("error.validation", new Object[] { sb.toString() }, locale);
		return new ErrorResponse(0, msg);
	}
}