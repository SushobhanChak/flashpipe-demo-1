import com.sap.gateway.ip.core.customdev.util.Message

Message processData(Message message) {
    // retryCount is zero based and last count is also retried
    int retryCount = (message.getHeader('SAPJMSRetries', String) ?: 0) as int
    int maxRetries = message.getProperty('MaxRetries') as int
    if(retryCount > (maxRetries - 2))
        message.setProperty('RetryLimitReached', 'X')

    return message
}