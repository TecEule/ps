package ps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ParameterBase {

	protected static String Input = "";
	protected static String XmlPath = "";
	protected static int MaxAttributID = 0;

	protected void initMenueAnzeige() {

		getXmlPath();

		try {
			String auswahlMenue = "-1";

			do {

				selection();
				auswahlMenue = readSelection();

				switch (auswahlMenue) {
				case "b":
					ParameterChange.changeParameter();
					break;
				case "n":
					ParameterAdd.addParameter();
					break;
				case "np":
					ParameterAdd.addParameterParent();
					break;
				case "l":
					ParameterDelete.deleteParameter();
					break;
				case "0":
					System.out.println("Die Anwendung wird jetzt geschlossen.");
					System.exit(0);
					break;
				case "man":
					manual();
					break;
				default:
					System.out.println("Sie müssen eine Auswahl treffen.");
					break;
				}

				
			} while (auswahlMenue != "0");
		} catch (Exception e) {
			System.out.println("Es ist ein unerwarteter Fehler aufgetreten!");
			initMenueAnzeige();
		}
	}

	protected Boolean checkInput() {
		Boolean ok = true;

		return ok;
	}

	private void selection() {

		System.out.println();
		System.out.println("Anzeige Parameter");
		System.out.println("--------------------------");
		System.out.println();

		leseXML();

		System.out.println("[0] - Anwendung schließen");
		System.out.println("Kommando: ");
		Scanner scanner = new Scanner(System.in);
		Input = scanner.nextLine();
	}

	private String readSelection() {
		String selection = "";

		try {
			String[] inputDividing = new String[10];

			inputDividing = Input.split(" ");

			if (inputDividing.length > 0) {
				selection = inputDividing[0].trim();
			}
		} catch (StackOverflowError e) {
			e.printStackTrace();
		} catch (PatternSyntaxException e) {
			e.printStackTrace();
		}

		return selection;
	}

	protected void manual() {

		try {
			FileReader fr = new FileReader("Manual.txt");

			BufferedReader bf = new BufferedReader(fr);
			String ausgabezeile = "";

			while (ausgabezeile != null) {
				ausgabezeile = bf.readLine();
				if (ausgabezeile != null)
					System.out.println(ausgabezeile);
			}

			bf.close();
			fr.close();
			System.in.read();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void leseXML() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		MaxAttributID = 0;
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(XmlPath);

			NodeList parameter = doc.getElementsByTagName("Parameter");
			for (int index = 0; index < parameter.getLength(); index++) {
				Node p = parameter.item(index);
				if (p.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) p;

					NodeList paramList = e.getChildNodes();
					for (int indexChild = 0; indexChild < paramList.getLength(); indexChild++) {
						Node n = paramList.item(indexChild);
						if (n.getNodeType() == Node.ELEMENT_NODE) {
							Element ele = (Element) n;
							String id = ele.getAttribute("id");
							NodeList childList = n.getChildNodes();

							String ausgabeFormat = "ID: " + id + " <" + n.getNodeName() + "> "
									+ System.getProperty("line.separator");

							int tempID = Integer.parseInt(id);
							if (MaxAttributID < tempID)
								MaxAttributID = tempID;

							for (int j = 0; j < childList.getLength(); j++) {
								Node nkind = childList.item(j);
								if (nkind.getNodeType() == Node.ELEMENT_NODE) {
									Element kind = (Element) nkind;

									ausgabeFormat += kind.getTagName() + ": " + kind.getTextContent()
											+ System.getProperty("line.separator");
								}
							}

							System.out.println(ausgabeFormat);
						}
					}

				}
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
		}

	}

	public static String getXmlPath() {

		String tmp = "";
		try {
			String path = System.getProperty("user.dir"); //
			// ParameterBase.class.getProtectionDomain().getCodeSource().getLocation().toString(); //.getPath();
			XmlPath = path;

			XmlPath += "//ini//ks_Parameter.xml";
			tmp = XmlPath;
		} catch (Exception e) {

			e.printStackTrace();
		}
		return tmp;
	}

	@SuppressWarnings("unused")
	public static String getParameterValue(String tagName, String childNode) {
		
		getXmlPath();
		String wert = "";

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(XmlPath);

			Node parameter = doc.getElementsByTagName(tagName).item(0);

			NodeList list = parameter.getChildNodes();
			for (int index = 0; index < list.getLength(); index++) {
				Node node = list.item(index);

				if (childNode.equals(node.getNodeName())) {
					wert = node.getTextContent().trim();
				}
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
		}

		return wert;

	}

	@SuppressWarnings("unused")
	public static void deleteParameterValue(String tagName, String childNode) {
		getXmlPath();
		ParameterDelete.deleteParameterValue(tagName, childNode);
	}

	@SuppressWarnings("unused")
	public static void addParameterValue(String tagName, String childNode, String newValue) {
		getXmlPath();
		ParameterAdd.addParameterValue(tagName, childNode, newValue);
	}

	@SuppressWarnings("unused")
	public static void addParameterParent(String tagName, String parentNode) {
		getXmlPath();
		ParameterAdd.addParameterParent(tagName, parentNode);
	}

	@SuppressWarnings("unused")
	public static Boolean setParameterValue(String tagName, String childNode, String newValue) {
		getXmlPath();
		return ParameterChange.setParameterValue(tagName, childNode, newValue);
	}
}
