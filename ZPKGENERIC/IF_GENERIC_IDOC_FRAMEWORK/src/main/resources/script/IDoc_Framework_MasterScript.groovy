/**********************************************************************************************
* Script          : IDoc_Framework_MasterScript.groovy                                        *
* Description     : This Script is Created to dynamicaly determine the receiver.Below method  *
*                   is implemented for this Interface                                         *
*                   (i)determineReceiver                                                      *
*				                        													  *
* Created By      : Amit Kundu                                                                *
* Date            : 28-FEB-2020                                                               *
***********************************************************************************************/ 

import com.sap.gateway.ip.core.customdev.util.Message;
import java.util.HashMap;
import com.sap.it.api.mapping.*;
import com.sap.it.api.ITApiFactory;
import com.sap.it.api.ITApi;
import com.sap.it.api.mapping.ValueMappingApi;

/**********************************************************************************************
* Function        : determineReceiver                                                         *
* Description     : This function/method is Created to dynamicaly determine receiver          *
*                   structure with Header, Item and Foorter which was not possiable           *
*                   for multiple IDoc                                                         *
*                                                                                             *
* Created By      : Amit Kundu                                                                *
* Date            : 28-FEB-2020                                                               *
***********************************************************************************************/  

def Message determineReceiver(Message message) {
    //Body 
       def msgBody = message.getBody(java.lang.String) as String;
       def payload = new XmlSlurper().parseText(msgBody);         // Parse input IDoc XML
       def iDocType = payload.IDOC.EDI_DC40.IDOCTYP.text();       // Retrive IDOCTYP from input IDoc XML
       def receiverPor = payload.IDOC.EDI_DC40.RCVPOR.text();     // Retrieve Receiver Partner from input IDoc XML
       def sourceSystem = iDocType + receiverPor ;                // Concatenate IDOCTYP and Receiver Partner
       def docnum = payload.IDOC.EDI_DC40.DOCNUM.text();          // Reteive idoc number from input idoc xml
       String receiverurl1 = "";                                  // Declaring receiver url
    
       def service = ITApiFactory.getApi(ValueMappingApi.class,null); // Declaring service for Value Mapping
       
       // Checking , If the service is not initial
       if( service != null) {
         String receiverurl = service.getMappedValue("S4HANA", "IDocInput", sourceSystem, "EDI3PL", "URL");  // Retriving the Value Mapping result (URL)
            if ( receiverurl.equals("") || receiverurl ==null )   // If the result is initial  populate "Default" value
                {
                    receiverurl1 = "default" ; 
                }
            else      
                receiverurl1 = receiverurl;      // If the result is not initial then populate the value (URL).
                
        }
       message.setHeader("SAP_ApplicationID",docnum);   // Setting SAP_ApplicationID with Idoc number 
       
       def prop = message.getProperties();     // Retrieve Message property
       message.setProperty("Receiverurl", receiverurl1);   // Set the Result URL in to property
       return message;    // Return modified message for further processing
}
