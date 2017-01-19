package datafieldgenerator;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by b06514a on 12/9/2016.
 */
public class QueryGenerator {

	public static void main(String[] args) throws Exception {
		Path input = Paths.get("C:\\Users\\b06514a\\Sandbox\\src\\datafieldgenerator\\files\\output.txt");
		Path output = Paths.get("C:\\Users\\b06514a\\Sandbox\\src\\datafieldgenerator\\files\\data_fields.sql");
		Files.deleteIfExists(output);
		Files.createFile(output);

		Stream<String> stream = Files.lines(input);

		List<ImportEntry> importEntries = new ArrayList<>();

		Iterator<String> iterator = stream.iterator();
		while (iterator.hasNext()) {
			String xpath = iterator.next();
			String name = xpath.replace("Experian/FraudSolutions/Response/Products/PreciseIDServer/", "").replaceAll("@", "").replaceAll("/", ".");
			String description = name.replace("PreciseIDServer/", "").replaceAll("@", "").replaceAll("/", ".");
			importEntries.add(new ImportEntry(name, description, xpath));
		}

		BufferedWriter writer = Files.newBufferedWriter(output, Charset.defaultCharset());
		writer.write("insert into data_field(id, name, description, data_type_id, grouping_num, sort_order, customer_id, data_source_id, xml_element)");
		writer.newLine();
		writer.write("values");
		writer.newLine();
		for (ImportEntry entry: importEntries) {
			writer.write(entry.toString());
			writer.write(",");
			writer.newLine();
		}
		writer.write("");
		writer.flush();

	}

}
