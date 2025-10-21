<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
  xmlns:pos="http://disim.univaq.it/services/postingservice">
   <soapenv:Header/>
   <soapenv:Body>
      <pos:cancelRequest>
         <pos:requestId>${requestId}</pos:requestId>
      </pos:cancelRequest>
   </soapenv:Body>
</soapenv:Envelope>
