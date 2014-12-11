package ru.ncedu.sa4ek;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sa4ek on 16.11.14.
 */
public class XMLWorker {
    String path = new String();
    String f = new String();
    public XMLWorker(String path) {
        this.path = path;
    }

    public void printXML(String f) {
        try {
            this.f = f;
            ArrayList<Employee> empl = new ArrayList<Employee>();
            File file = new File(path);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nodeLst = doc.getElementsByTagName("empl");

            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element) fstNode;

                    NodeList emplNumberElmntLst = fstElmnt.getElementsByTagName("empl_number");
                    Element emplNum = (Element) emplNumberElmntLst.item(0);
                    NodeList emplNumber = emplNum.getChildNodes();

                    NodeList emplNameElmntLst = fstElmnt.getElementsByTagName("empl_name");
                    Element emplNm = (Element) emplNameElmntLst.item(0);
                    NodeList emplName = emplNm.getChildNodes();

                    NodeList emplPositionElmntLst = fstElmnt.getElementsByTagName("empl_position");
                    Element emplPos = (Element) emplPositionElmntLst.item(0);
                    NodeList emplPosition = emplPos.getChildNodes();

                    NodeList officeElmntLst = fstElmnt.getElementsByTagName("office");
                    Element off = (Element) officeElmntLst.item(0);
                    NodeList officeEmpl = off.getChildNodes();

                    NodeList emplTelElmntLst = fstElmnt.getElementsByTagName("tel");
                    Element emplTel = (Element) emplTelElmntLst.item(0);
                    NodeList emplTelephone = emplTel.getChildNodes();

                    NodeList emplSalElmntLst = fstElmnt.getElementsByTagName("salary");
                    Element emplSal = (Element) emplSalElmntLst.item(0);
                    NodeList emplSalary = emplSal.getChildNodes();

                    empl.add(new Employee(Integer.parseInt(((Node) emplNumber.item(0)).getNodeValue()),
                            ((Node) emplName.item(0)).getNodeValue(), ((Node) emplPosition.item(0)).getNodeValue(), ((Node) officeEmpl.item(0)).getNodeValue(),
                            ((Node) emplTelephone.item(0)).getNodeValue(), Double.parseDouble(((Node) emplSalary.item(0)).getNodeValue())));
                }
            }
            System.out.println("id\tname\t\t\tposition\t\t\t\t\toffice\t\t\ttel\t\t\t\t\t\tsalary");
            for (Employee emp : empl) {
                if (emp.getFio().contains(f)) {
                    System.out.println(emp);
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void modifyXMLElement(int id, String name, String position, String office, String tel, Double salary) {
        File file = new File(path);
        if (name.length() < 3){
            System.out.println("Validation error!");
            return;
        }
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nodeLst = doc.getElementsByTagName("empl");

            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element) fstNode;

                    NodeList emplNumberElmntLst = fstElmnt.getElementsByTagName("empl_number");
                    Element emplNum = (Element) emplNumberElmntLst.item(0);
                    NodeList emplNumber = emplNum.getChildNodes();

                    if(id == Integer.parseInt(((Node) emplNumber.item(0)).getNodeValue())){
                        NodeList emplNameElmntLst = fstElmnt.getElementsByTagName("empl_name");
                        Element emplNm = (Element) emplNameElmntLst.item(0);
                        NodeList emplName = emplNm.getChildNodes();

                        NodeList emplPositionElmntLst = fstElmnt.getElementsByTagName("empl_position");
                        Element emplPos = (Element) emplPositionElmntLst.item(0);
                        NodeList emplPosition = emplPos.getChildNodes();

                        NodeList officeElmntLst = fstElmnt.getElementsByTagName("office");
                        Element off = (Element) officeElmntLst.item(0);
                        NodeList officeEmpl = off.getChildNodes();

                        NodeList emplTelElmntLst = fstElmnt.getElementsByTagName("tel");
                        Element emplTel = (Element) emplTelElmntLst.item(0);
                        NodeList emplTelephone = emplTel.getChildNodes();

                        NodeList emplSalElmntLst = fstElmnt.getElementsByTagName("salary");
                        Element emplSal = (Element) emplSalElmntLst.item(0);
                        NodeList emplSalary = emplSal.getChildNodes();

                        ((Node) emplName.item(0)).setNodeValue(name);
                        ((Node) emplPosition.item(0)).setNodeValue(position);
                        ((Node) officeEmpl.item(0)).setNodeValue(office);
                        ((Node) emplTelephone.item(0)).setNodeValue(tel);
                        ((Node) emplSalary.item(0)).setNodeValue(Double.toString(salary));
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);

            StreamResult streamResult = new StreamResult(new File(path));
            transformer.transform(domSource, streamResult);

            printXML(f);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }

    public void addXMLElement(String ename, String eposition, String eoffice, String etel, Double esalary) {
        File file = new File(path);
        int id = 0;
        if (ename.length() < 3){
            System.out.println("Validation error!");
            return;
        }
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nodeLst = doc.getElementsByTagName("empl");

            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element) fstNode;

                    NodeList emplNumberElmntLst = fstElmnt.getElementsByTagName("empl_number");
                    Element emplNum = (Element) emplNumberElmntLst.item(0);
                    NodeList emplNumber = emplNum.getChildNodes();

                    if (id < Integer.parseInt(((Node) emplNumber.item(0)).getNodeValue())) {
                        id = Integer.parseInt(((Node) emplNumber.item(0)).getNodeValue());
                    }
                }
            }

            Element empl = doc.createElement("empl");

            Element number = doc.createElement("empl_number");
            number.appendChild(doc.createTextNode(Integer.toString(id + 1)));
            empl.appendChild(number);

            Element name = doc.createElement("empl_name");
            name.appendChild(doc.createTextNode(ename));
            empl.appendChild(name);

            Element position = doc.createElement("empl_position");
            position.appendChild(doc.createTextNode(eposition));
            empl.appendChild(position);

            Element office = doc.createElement("office");
            office.appendChild(doc.createTextNode(eoffice));
            empl.appendChild(office);

            Element tel = doc.createElement("tel");
            tel.appendChild(doc.createTextNode(etel));
            empl.appendChild(tel);

            Element salary = doc.createElement("salary");
            salary.appendChild(doc.createTextNode(Double.toString(esalary)));
            empl.appendChild(salary);

            doc.getDocumentElement().appendChild(empl);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);

            StreamResult streamResult = new StreamResult(new File(path));
            transformer.transform(domSource, streamResult);

            printXML(f);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

        public void deleteXMLElement(int id){
        File file = new File(path);

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(file);

            doc.getDocumentElement().normalize();

            NodeList nodeLst = doc.getElementsByTagName("empl");

            for (int s = 0; s < nodeLst.getLength(); s++) {
                Node fstNode = nodeLst.item(s);
                if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element fstElmnt = (Element) fstNode;

                    NodeList emplNumberElmntLst = fstElmnt.getElementsByTagName("empl_number");
                    Element emplNum = (Element) emplNumberElmntLst.item(0);
                    NodeList emplNumber = emplNum.getChildNodes();

                    if (id == Integer.parseInt(((Node) emplNumber.item(0)).getNodeValue())) {
                        doc.getDocumentElement().removeChild(fstNode);
                    }
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();

            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(doc);

            StreamResult streamResult = new StreamResult(new File(path));
            transformer.transform(domSource, streamResult);

            printXML(f);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }


}