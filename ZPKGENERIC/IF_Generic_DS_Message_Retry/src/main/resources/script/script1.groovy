import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import groovy.xml.*

def Message processData(Message message) {
    //Body 
       def body = message.getBody(java.lang.String) as String;
	   def data=new XmlSlurper().parseText(body)
	   def id= data.message.@id as String
	   def p=  id.indexOf(" ")
	   def url= id.substring(p+1,id.size());
	   
	   message.setHeader("URL",url);
	   
	   def payload=data.message.children()
	   payload= XmlUtil.serialize(payload)
	   
       message.setBody(payload)

       return message;
}