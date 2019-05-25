package faq.exception;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * I18n advice.
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
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException x, Locale locale) {
		final Map<String, String> fieldErrors = new HashMap<>();
		x.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			fieldErrors.put(fieldName, errorMessage);
		});

		String msg = this.messageSource.getMessage("error.validation", new Object[] {}, locale);
		return new ErrorResponse(0, msg, fieldErrors);
	}

	/**
	 * @see Interpolation bug <a href="https://github.com/spring-projects/spring-boot/issues/3071">#3071</a>.
	 * 
	 */
	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleConstaintViolationException(final ConstraintViolationException x, Locale locale) {
		final Map<String, String> fieldErrors = new HashMap<>();
		Set<ConstraintViolation<?>> violations = x.getConstraintViolations();
		if (violations != null) {
			violations.stream()
					.forEach(violation -> {
						String fieldName = violation.getPropertyPath().toString();
						String errorMessage = violation.getMessage();
						fieldErrors.put(fieldName, errorMessage);
					});
		}

		String msg = this.messageSource.getMessage("error.validation", new Object[] {}, locale);
		return new ErrorResponse(0, msg, fieldErrors);
	}
}