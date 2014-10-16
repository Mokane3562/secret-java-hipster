import java.io.File;
import java.io.IOException;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class DomUtil {
	public static Document createEmptyDoc(String top) throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation impl = builder.getDOMImplementation();
		return impl.createDocument(null, top, null);
	}

	public static void outputHTML(Node e, Writer w) throws IOException, TransformerException {
		outputHTML(e, w, 4);
	}

	public static void outputHTML(Node e, Writer w, int indent) throws IOException, TransformerException {
		TransformerFactory tf = TransformerFactory.newInstance();
		tf.setAttribute("indent-number", new Integer(4));
		Transformer xformer = tf.newTransformer();

		w.write("<!DOCTYPE html>\n");
		DOMSource source = new DOMSource(e);
		StreamResult result = new StreamResult(w);
		xformer.setOutputProperty(OutputKeys.METHOD, "html");
		xformer.setOutputProperty(OutputKeys.INDENT, "yes");
		xformer.transform(source, result);
	}

	public static void outputXML(Node e, Writer w) throws TransformerException {
		outputXML(e, w, 4);
	}

	public static void outputXML(Node e, Writer w, int indent) throws TransformerException {
		// An identity transformer.
		TransformerFactory tf = TransformerFactory.newInstance();
		tf.setAttribute("indent-number", new Integer(indent));
		Transformer xformer = tf.newTransformer();

		DOMSource source = new DOMSource(e);
		StreamResult result = new StreamResult(w);
		xformer.setOutputProperty(OutputKeys.METHOD, "xml");
		xformer.setOutputProperty(OutputKeys.INDENT, "yes");
		xformer.transform(source, result);
	}

	public static Document parseDoc(File f) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(f);
	}

	public static Document parseDoc(String uri) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new File(uri));
	}
}