package xslt.transformer;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * Created by b06514a on 11/23/2016.
 */
public class Transformer {
    public static void main(String[] args) throws Exception {

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("src/xslt/transformer/response.xslt"));
        javax.xml.transform.Transformer transformer = factory.newTransformer(xslt);

        Source xml = new StreamSource(new File("src/xslt/transformer/Response.xml"));
        transformer.transform(xml, new StreamResult(new File("src/xslt/transformer/output.xml")));

//        TransformerFactory factory = TransformerFactory.newInstance();
//        Source xslt = new StreamSource(new File("transform.xslt"));
//        Transformer transformer = factory.newTransformer(xslt);
//
//        Source text = new StreamSource(new File("input.xml"));
//        transformer.transform(text, new StreamResult(new File("output.xml")));
    }
}
