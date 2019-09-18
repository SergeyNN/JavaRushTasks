package com.javarush.task.task33.task3309;

import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;

/*
Комментарий внутри xml
*/
public class Solution {
    public static String toXmlWithComment(Object obj, String tagName, String comment) {
        String result = "";
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            marshaller.marshal(obj, document);


            Node root = document.getDocumentElement();
            NodeList alltags = root.getChildNodes();
            checkCDATA(alltags, document);
            NodeList tags = document.getElementsByTagName(tagName);
            if (tags.getLength() > 0) {
                for (int i = 0; i < tags.getLength(); i++) {
                    Node tag = tags.item(i);
                    tag.getParentNode().insertBefore(document.createComment(comment), tags.item(i));
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");  // this allows you make additional whitespace \n
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.STANDALONE, "no");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new ByteArrayOutputStream());
            transformer.transform(domSource, streamResult);
            result = streamResult.getOutputStream().toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void checkCDATA(NodeList listtag, Document document) {
        for (int i = 0; i < listtag.getLength(); i++) {
            Node tag = listtag.item(i);
            if (tag.getFirstChild().getNodeType() == Node.TEXT_NODE) {
                Node currentNode = tag.getFirstChild();
                if (currentNode.getTextContent().matches(".*[<>&\'\"].*")) {
                    String content = currentNode.getTextContent();
                    CDATASection cdataSection = document.createCDATASection(content);
                    tag.replaceChild(cdataSection, currentNode);
                }
            } else {
                if (tag.hasChildNodes()) {
                    NodeList subtags = tag.getChildNodes();
                    checkCDATA(subtags, document);
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
