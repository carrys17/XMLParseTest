package com.example.shang.xmlparsetest.parser;

import com.example.shang.xmlparsetest.Bean.Book;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by shang on 2017/7/18.
 */

public class DOMParser implements BookParser {
    @Override
    public List<Book> parse(InputStream is) throws Exception {
        List<Book>books = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(is);
        Element rootElement = document.getDocumentElement();
        NodeList items = rootElement.getElementsByTagName("book");
        for (int i = 0;i<items.getLength();i++){
            Book book = new Book();
            Node item = items.item(i);
            NodeList properties = item.getChildNodes();
            for (int j = 0;j<properties.getLength();j++){
                Node property = properties.item(j);
                String nodeName = property.getNodeName();
                if (nodeName.equals("id")){
                    book.setId(Integer.parseInt(property.getFirstChild().getNodeValue()));
                }else if (nodeName.equals("name")){
                    book.setName(property.getFirstChild().getNodeValue());
                }else if (nodeName.equals("price")){
                    book.setPrice(Float.parseFloat(property.getFirstChild().getNodeValue()));
                }
            }
            books.add(book);
        }
        return books;
    }

    @Override
    public String serialize(List<Book> books) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();      //由builder创建新文档

        Element rootElement = document.createElement("books");

        for (Book book:books){
            Element bookElement = document.createElement("book");
            bookElement.setAttribute("id",book.getId()+"");

            Element nameElement = document.createElement("name");
            nameElement.setTextContent(book.getName());
            bookElement.appendChild(nameElement);

            Element priceElement = document.createElement("price");
            priceElement.setTextContent(book.getPrice()+"");
            bookElement.appendChild(priceElement);

            rootElement.appendChild(bookElement);
        }

        document.appendChild(rootElement);


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"no");
        transformer.setOutputProperty(OutputKeys.MEDIA_TYPE,"utf-8");

        StringWriter stringWriter = new StringWriter();

        Source source = new DOMSource(document);            //表明文档来源是doc  
        Result result = new StreamResult(stringWriter);     //表明目标结果为stringWriter
        transformer.transform(source,result);               //开始转换  

        return stringWriter.toString();
    }
}
