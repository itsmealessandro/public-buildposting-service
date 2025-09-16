<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
  xmlns:pos="http://example.com/postings">
  <soapenv:Header />
  <soapenv:Body>
    <pos:availabilityRequest>
      <!-- Dati dell'user_info -->
      <pos:user_info>
        <pos:name>${user_info.name}</pos:name>
        <pos:surname>${user_info.surname}</pos:surname>
        <pos:taxCode>${user_info.taxCode}</pos:taxCode>
        <pos:address>${user_info.address}</pos:address>
        <pos:city>${user_info.city}</pos:city>
        <pos:zip>${user_info.zip}</pos:zip>
        <pos:email>${user_info.email}</pos:email>
      </pos:user_info>

      <!-- Dati del posting -->
      <pos:posting>
        <pos:posterFormat>${posting.posterFormat}</pos:posterFormat>

        <!-- Iterazione su tutte le zone -->
        <#list posting.zones as zone>
          <pos:zone>
            <pos:id>${zone.id}</pos:id>
            <pos:city>${zone.city}</pos:city>
          </pos:zone>
        </#list>

      </pos:posting>
    </pos:availabilityRequest>
  </soapenv:Body>
</soapenv:Envelope>
