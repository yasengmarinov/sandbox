package datafieldgenerator;

/**
 * Created by b06514a on 12/9/2016.
 */
public class ImportEntry {

	public static final String COMMA = ", ";
	public static final String QUOTE = "'";
	public static String NULL = "null";
	public static int dataTypeId = 1;
	public static int sortOrder = 0;
	public static int customerId = -1;
	public static int dataSource = 150;
	public static int startId = 443347000;
	public static int currId = startId;

	public int id;
	public String name;
	public String description;
	public String xmlElement;

	public ImportEntry(String name, String description, String xmlElement) {
		this.id = currId;
		currId ++;
		this.name = name;
		this.description = description;
		this.xmlElement = xmlElement;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(QUOTE).append(id).append(QUOTE);
		builder.append(COMMA);
		builder.append(QUOTE).append(name).append(QUOTE);
		builder.append(COMMA);
		builder.append(QUOTE).append(description).append(QUOTE);
		builder.append(COMMA);
		builder.append(dataTypeId);
		builder.append(COMMA);
		builder.append(NULL);
		builder.append(COMMA);
		builder.append(sortOrder);
		builder.append(COMMA);
		builder.append(customerId);
		builder.append(COMMA);
		builder.append(dataSource);
		builder.append(COMMA);
		builder.append(QUOTE).append(xmlElement).append(QUOTE);
		builder.append(")");
		return builder.toString();
	}
}
