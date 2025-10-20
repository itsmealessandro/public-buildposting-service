<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:pos="http://disim.univaq.it/services/postingservice">
   <soapenv:Header/>
   <soapenv:Body>
      <pos:confirmationRequest>
         <pos:requestId>${requestId}</pos:requestId>
      </pos:confirmationRequest>
   </soapenv:Body>
</soapenv:Envelope>
