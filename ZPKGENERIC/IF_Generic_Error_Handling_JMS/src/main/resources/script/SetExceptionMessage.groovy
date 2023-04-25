import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
    throw new Exception(message.getHeader('ErrorMessage', String))
}