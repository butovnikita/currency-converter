package com.getgoodm.currencyconverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
//Сервис для парсинга XML
@Service
public class XMLService {
    private static final Logger logger = LoggerFactory.getLogger(XMLService.class);

    public static DataFromXML parseRates() {
        try {
            String URL = "http://www.cbr.ru/scripts/XML_daily.asp";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(URL);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("ValCurs");
            Node n = nList.item(0);
            LocalDate date = null;
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) n;
                //Считывание даты
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                date = LocalDate.parse((elem.getAttribute("Date")), formatter);
            }

            //Считать список валют
            NodeList nodeList = doc.getElementsByTagName("Valute");

            //Создать пустой список валют
            List<Currency> currencies = new ArrayList<>();
            List<CurrencyRate> currencyRates = new ArrayList<>();
            //Пройти в цикле по всем доступным узлам валют
            for (int i=0; i<nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    String charCode = elem.getElementsByTagName("CharCode").item(0).getTextContent();
                    Currency currency = new Currency(elem.getAttribute("ID"),
                            elem.getElementsByTagName("NumCode").item(0).getTextContent(),
                            charCode,
                            Integer.parseInt(elem.getElementsByTagName("Nominal").item(0).getTextContent()),
                            elem.getElementsByTagName("Name").item(0).getTextContent());


                    currencies.add(currency);
                    DecimalFormat df = new DecimalFormat();
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setDecimalSeparator(',');
                    symbols.setGroupingSeparator(' ');
                    df.setDecimalFormatSymbols(symbols);
                    CurrencyRate currencyRate = new CurrencyRate(elem.getAttribute("ID"),
                            date,
                            charCode,
                            (Double) df.parse((elem.getElementsByTagName("Value").item(0).getTextContent()) ));
                    currencyRates.add(currencyRate);
                    System.out.println(currency);
                    System.out.println(currencyRate);

                }
            }
            return new DataFromXML(currencies, currencyRates);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }
    public static LocalDate getActualDate() {
        try {
            String URL = "http://www.cbr.ru/scripts/XML_daily.asp";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(URL);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("ValCurs");
            Node n = nList.item(0);
            LocalDate date = null;
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) n;
                //Считывание даты
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                date = LocalDate.parse((elem.getAttribute("Date")), formatter);
                System.out.println("Актуальная дата xml:" + date);
                return date;
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
        return null;
    }
}
