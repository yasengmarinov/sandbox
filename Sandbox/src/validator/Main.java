package validator;

/**
 * Created by b06514a on 11/25/2016.
 */
public class Main {
	public static void main(String[] args) {

		Error errors = new Error();

		FieldValidator validator = new FieldValidator(errors);
		validator.setField("asd").maxChars(2).minChars(4);
		System.out.println(errors.message);
	}
}
