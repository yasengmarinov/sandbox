package validator;

/**
 * Created by b06514a on 11/25/2016.
 */
public class FieldValidator {

	private Error errors;
	private String field;

	public FieldValidator(Error errors) {

		this.errors = errors;

	}

	public FieldValidator setField(String field) {
		this.field = field;
		return this;
	}

	public FieldValidator maxChars(int number) {
		if (field.length() > number) {
			errors.message+="More than " + number;
		}

		return this;
	}

	public FieldValidator minChars(int number) {
		if (field.length() < number) {
			errors.message+="Less than " + number;
		}

		return this;
	}

}
