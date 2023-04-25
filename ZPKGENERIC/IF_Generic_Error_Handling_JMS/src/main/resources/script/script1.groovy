
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
def Message processData(Message message) {
    //Body 
    
    def map = message.getHeaders();
    
    def appid=map.get("ApplicationID");
    def endpt=map.get("RetryEndpoint");
    
    def EntryID=appid+" "+endpt;
    message.setHeader("EntryID",EntryID);
    
    return message;
    
}