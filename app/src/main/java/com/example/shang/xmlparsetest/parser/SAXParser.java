package com.example.shang.xmlparsetest.parser;

import com.example.shang.xmlparsetest.Bean.Book;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.helpers.DefaultHandler;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by shang on 2017/7/18.
 */

public class SAXParser implements BookParser {
    @Override
    public List<Book> parse(InputStream is) throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        javax.xml.parsers.SAXParser saxParser = factory.newSAXParser();
        MyHandler myHandler = new MyHandler();
        saxParser.parse(is,myHandler);
        return myHandler.getBooks();
    }

    @Override
    public String serialize(List<Book> books) throws Exception {
        SAXTransformerFactory factory = (SAXTransformerFactory) TransformerFactory.newInstance();
        TransformerHandler handler = factory.newTransformerHandler();
        Transformer transformer = handler.getTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING,"utf-8");             // 设置输出采用的编码方式  
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");                 // 是否自动添加额外的空白  
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"no");    // 是否忽略XML声明  

        StringWriter writer = new StringWriter();
        Result result = new StreamResult(writer);
        handler.setResult(result);

        String uri = "";            //代表命名空间的URI 当URI无值时 须置为空字符串  
        String localName = "";      //命名空间的本地名称(不包含前缀) 当没有进行命名空间处理时 须置为空字符串  

        handler.startDocument();  // 1
        handler.startElement(uri,localName,"books",null);

        AttributesImpl attrs = new AttributesImpl();    //负责存放元素的属性信息  
        char[] ch = null;
        for (Book book:books){
            attrs.clear();
            attrs.addAttribute(uri,localName,"id","String",String.valueOf(book.getId())); // 添加一个名为id的属性

            handler.startElement(uri,localName,"book",attrs); // 2

            handler.startElement(uri,localName,"name",null);
            ch = String.valueOf(book.getName()).toCharArray();
            handler.characters(ch,0,ch.length);
            handler.endElement(uri,localName,"name");

            handler.startElement(uri,localName,"price",null);
            ch = String.valueOf(book.getPrice()).toCharArray();
            handler.characters(ch,0,ch.length);
            handler.endElement(uri,localName,"price");

            handler.endElement(uri,localName,"book"); //2
        }
        handler.endElement(uri,localName,"books");
        handler.endDocument(); // 1

        return writer.toString();
    }



    // 定义自己的事件处理器，需要重写DefaultHandler的方法  
    private class MyHandler extends DefaultHandler{

        private Book book;
        private List<Book> books;
        private StringBuilder stringBuilder;

        //返回解析后得到的Book对象集合  
        public List<Book> getBooks(){
            return books;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();

            books = new ArrayList<Book>();
            stringBuilder = new StringBuilder();
        }

        //当执行文档时遇到起始节点，startElement方法将会被调用，我们可以获取起始节点相关信息
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);
            if (localName.equals("book")){
                book = new Book();
            }
            //将字符长度设置为0 以便重新开始读取元素内的字符节点  
            stringBuilder.setLength(0);

        }

        //然后characters方法被调用，我们可以获取节点内的文本信息
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            super.characters(ch, start, length);
            // 将读取的字符数组追加到builder中
            stringBuilder.append(ch,start,length);
        }

        //最后endElement方法被调用，我们可以做收尾的相关操作。
        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            super.endElement(uri, localName, qName);
            if (localName.equals("id")){
                book.setId(Integer.parseInt(stringBuilder.toString()));
            }else if (localName.equals("name")){
                book.setName(stringBuilder.toString());
            }else if (localName.equals("price")){
                book.setPrice(Float.parseFloat(stringBuilder.toString()));
            }else if(localName.equals("book")){
                books.add(book);
            }
        }
    }

}
