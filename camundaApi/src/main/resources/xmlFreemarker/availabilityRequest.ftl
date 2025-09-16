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
        <pos:zip>${user_info.zipCode}</pos:zip>
        <pos:email>${user_info.email}</pos:email>
      </pos:user_info>

      <!-- Dati del posting -->
      <pos:posting>
        <pos:posterFormat>${format}</pos:posterFormat>

        <#-- Converti la stringa JSON in mappa -->
        <#assign selectedZonesMap = selectedZones?eval>

        <#list selectedZonesMap?keys as cityName>
          <#list selectedZonesMap[cityName] as zone>
            <pos:zone>
              <pos:id>${zone.id}</pos:id>
              <pos:city>${zone.city}</pos:city>
            </pos:zone>
          </#list>
        </#list>

      </pos:posting>
    </pos:availabilityRequest>
  </soapenv:Body>
</soapenv:Envelope>
