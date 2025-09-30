<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
  xmlns:pos="http://disim.univaq.it/services/postingservice">
  <soapenv:Header/>
  <soapenv:Body>
    <pos:availabilityRequest>
      <pos:applicant>
        <pos:name>${user_info.name}</pos:name>
        <pos:surname>${user_info.surname}</pos:surname>
        <pos:taxCode>${user_info.taxCode}</pos:taxCode>
        <pos:address>${user_info.address}</pos:address>
        <pos:city>${user_info.city}</pos:city>
        <pos:zip>${user_info.zipCode}</pos:zip>
        <pos:email>${user_info.email}</pos:email>
      </pos:applicant>
      <pos:posting>
        <pos:posterFormat>${format}</pos:posterFormat>
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