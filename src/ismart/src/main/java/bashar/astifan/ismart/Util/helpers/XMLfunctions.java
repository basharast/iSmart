package bashar.astifan.ismart.Util.helpers;

/**
 * Created by Bashar on 5/6/2016.
 */

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class XMLfunctions {
    public static DefaultHttpClient httpClient;
    public static boolean isLog=false;
    public final static Document XMLfromString(String xml){

        Document doc = null;

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {

            DocumentBuilder db = dbf.newDocumentBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);

        } catch (ParserConfigurationException e) {
            System.out.println("XML parse error: " + e.getMessage());
            return null;
        } catch (SAXException e) {
            System.out.println("Wrong XML file structure: " + e.getMessage());
            return null;
        } catch (IOException e) {
            System.out.println("I/O exeption: " + e.getMessage());
            return null;
        }

        return doc;

    }

    /** Returns element value
     * @param elem element (it is XML tag)
     * @return Element value otherwise empty String
     */
    public final static String getElementValue( Node elem ) {
        //Node kid = null;
        if(isLog) Log.d("Elm **** ", elem.getTextContent());
	     /*if( elem != null){
	         if (elem.hasChildNodes()){
	             for( kid = elem.getFirstChild(); kid != null; kid = kid.getNextSibling() ){
	                 if( kid.getNodeType() == Node.TEXT_NODE  ){
	                     return kid.getNodeValue();
	                 }
	             }
	         }
	     }*/
        return elem.getTextContent();
    }

    public static String getXML(String url){
        String line = null;
        if(isLog)Log.d("-----URL STATE -----","Start getXML");
        try {
            URL u = new URL (url);
            HttpURLConnection huc =  ( HttpURLConnection )  u.openConnection ();
            huc.setRequestMethod ("GET");  //OR  huc.setRequestMethod ("HEAD");
            huc.connect () ;
            int code = huc.getResponseCode() ;
            System.out.println(code);
            if(isLog)Log.d("-----URL STATE -----","Checking URL");
            if (code==404){
                line="Wrong URL";
                if(isLog)Log.d("-----URL STATE -----"," Wrong URL"+code);
            }else{
                //-------------------
                httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                line = EntityUtils.toString(httpEntity);
            }

        } catch (UnsupportedEncodingException e) {
            line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
        } catch (MalformedURLException e) {
            line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
        } catch (IOException e) {
            line = "<results status=\"error\"><msg>Can't connect to server</msg></results>";
        }

        return line;

    }
    public static String getXMLFromRes(InputStream inputStream){

        if(isLog)Log.d("-----URL STATE -----","Start getXML");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            if(isLog)Log.d("-----URL STATE -----"," Wrong URL");
            return "Wrong URL";
        }
        return outputStream.toString();

    }
    public static int numResults(Document doc,String countTAG){
        Node results = doc.getDocumentElement();
        int res = -1;

        try{
            res = Integer.valueOf(results.getAttributes().getNamedItem(countTAG).getNodeValue());
        }catch(Exception e ){
            res = -1;
        }

        return res;
    }

    public static String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return XMLfunctions.getElementValue(n.item(0));
    }
}
