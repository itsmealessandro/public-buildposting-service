<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:pos="http://example.com/postings">
   <soapenv:Header/>
   <soapenv:Body>
      <pos:confirmationRequest>
         <pos:requestId>${requestId}</pos:requestId>
      </pos:confirmationRequest>
   </soapenv:Body>
</soapenv:Envelope>
