package ps;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParameterDelete extends ParameterBase {

	private static Boolean checkParameter() {
		Boolean ok = true;
		try {
			String[] commands = new String[10];
			commands = Input.split(" ");

			if (commands.length > 3 || commands.length < 3) {
				System.out.println("Die Anzahl der übergebenen Parametern stimmt nicht!");
				System.in.read();
				ok = false;
			}

		} catch (StackOverflowError e) {
			e.printStackTrace();
			ok = false;
		} catch (NullPointerException e) {
			e.printStackTrace();
			ok = false;
		} catch (IndexOutOfBoundsException e) {
			e.printStackTrace();
			ok = false;
		} catch (Exception e) {
			e.printStackTrace();
			ok = false;
		}

		return ok;
	}

	public static void deleteParameter() {

		if (checkParameter()) {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

			try {
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(XmlPath);

				String[] commands = new String[4];

				commands = Input.split(" ");

				String cmd = commands[0].trim();
				String tagName = commands[1].trim();
				String childNode = commands[2].trim();

				Node parameter = doc.getElementsByTagName(tagName).item(0);
				if (parameter != null) {
					if (cmd.equals("l")) {
						NodeList list = parameter.getChildNodes();
						for (int index = 0; index < list.getLength(); index++) {
							Node node = list.item(index);

							if (childNode.equals(node.getNodeName())) {
								parameter.removeChild(node);
							}
						}

					}

					TransformerFactory transformerfactory = TransformerFactory.newInstance();
					Transformer transformer = transformerfactory.newTransformer();
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(new File(XmlPath));
					transformer.transform(source, result);

				} else {
					System.out.println("Der tagName: " + tagName + " ist nicht vorhanden!");
				}
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DOMException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("unused")
	public static void deleteParameterValue(String tagName, String childNode) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(XmlPath);

			Node parameter = doc.getElementsByTagName(tagName).item(0);

			if (parameter != null) {
				NodeList list = parameter.getChildNodes();
				for (int index = 0; index < list.getLength(); index++) {
					Node node = list.item(index);

					if (childNode.equals(node.getNodeName())) {
						parameter.removeChild(node);
					}
				}

				TransformerFactory transformerfactory = TransformerFactory.newInstance();
				Transformer transformer = transformerfactory.newTransformer();
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(new File(XmlPath));
				transformer.transform(source, result);

			} else {
				System.out.println("Der tagName: " + tagName + " ist nicht vorhanden!");
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DOMException e) {
			e.printStackTrace();
		}
	}
}
