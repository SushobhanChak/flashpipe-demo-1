
import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.ITApi;
import com.sap.it.api.mapping.ValueMappingApi;
def Message processData(Message message) {
    //Body 
       def body = message.getBody();
       
       
     //   def msgBody = message.getBody();
       def payload = new XmlSlurper().parseText(body);
       def iDocType = payload.IDOC.EDI_DC40.IDOCTYP.text();
       def receiverPrn = payload.IDOC.EDI_DC40.RCVPRN.text();
       def sourceSystem = iDocType + receiverPrn ;
       String receiverurl1 = "";
     //  def dynamicValueMap(String S4HANA, String IDoc, String EDI, String URL, String key)
       def service = ITApiFactory.getApi(ValueMappingApi.class,null);
      
       if( service != null) {

         String receiverurl = service.getMappedValue("S4HANA", "IDoc", sourceSystem, "EDI", "URL");
            if ( receiverurl.equals("") || receiverurl ==null )
                {
                    receiverurl1 = "default" ; 
                }
            else
                receiverurl1 = receiverurl;
                
        }
        return null;
       
       def prop = message.getProperties();
       message.setProperty("Receiverurl", receiverurl1);
       return message ; 
}