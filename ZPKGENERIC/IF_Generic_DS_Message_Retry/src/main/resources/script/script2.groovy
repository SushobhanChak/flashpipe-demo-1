import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;

def Message processData(Message message) {

	def pmap = message.getProperties();
	String enableLogging = pmap.get("LogPayload");
		def body = message.getBody(java.lang.String) as String;
		def messageLog = messageLogFactory.getMessageLog(message);
		if(messageLog != null){
			messageLog.addAttachmentAsString("1. Before", body, "text/xml");
		}
	
       
	return message;
}