package datafieldgenerator;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by b06514a on 12/8/2016.
 */
public class XPathGenerator {

	public static void main(String[] args) throws Exception{

		File responseXML = new File("C:\\Users\\b06514a\\Sandbox\\src\\datafieldgenerator\\files\\response.xml");
		Path outputPath = Paths.get("C:\\Users\\b06514a\\Sandbox\\src\\datafieldgenerator\\files\\output.txt");

		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document response = docBuilder.parse(responseXML);

		Node root = response.getDocumentElement();

		Files.deleteIfExists(outputPath);
		Files.createFile(outputPath);
		BufferedWriter writer = Files.newBufferedWriter(outputPath, Charset.defaultCharset());

		Set<String> dataFields = new HashSet<>();

		iterate(root, root.getNodeName().concat("/"), writer);
		writer.flush();
	}

	public static void iterate(Node currNode, String currPath, BufferedWriter writer) throws Exception{
		if (!isLeaf(currNode)) {
			for (int i = 0; i < currNode.getChildNodes().getLength(); i++) {
				Node child = currNode.getChildNodes().item(i);
				if (child.getNodeType() != 3) {
					iterate(child, currPath.concat(child.getNodeName().concat("/")), writer);
				}

			}
		} else {
			writer.write(currPath.replaceAll("/$", ""));
			writer.newLine();
		}
		printAttributes(currNode, currPath, writer);
	}

	public static boolean isLeaf(Node node) {

		boolean isLeaf = true;
		if (node.hasChildNodes()) {
			NodeList children = node.getChildNodes();
			for (int i = 0; i < children.getLength(); i++) {
				if (children.item(i).getNodeType() != 3) {
					isLeaf = false;
				}
			}
		}

		return isLeaf;
	}

	public static void printAttributes(Node currNode, String currPath, BufferedWriter writer) throws Exception{
		if (currNode.hasAttributes()){
			NamedNodeMap attributes = currNode.getAttributes();
			for (int i = 0; i < attributes.getLength(); i++) {
				String attribute = attributes.item(i).getNodeName();
				writer.write(currPath.concat("@").concat(attribute));
				writer.newLine();
			}
		}
	}

}
