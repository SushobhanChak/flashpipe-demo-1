import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {

    def validateHeader = { headerName ->
        def header = message.getHeader(headerName, String)
        if(!header)
            throw new Exception("Header ${headerName} not passed in by Sender IFlow")
    }
    validateHeader('RetryEndpoint')
    validateHeader('ErrorMessage')
    validateHeader('ApplicationID')

    return message
}