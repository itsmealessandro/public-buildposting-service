<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
  xmlns:pos="http://example.com/postings">
  <soapenv:Header />
  <soapenv:Body>
    <pos:availabilityRequest>
      <!-- Dati dell'applicant -->
      <pos:applicant>
        <pos:name>${applicant.name}</pos:name>
        <pos:surname>${applicant.surname}</pos:surname>
        <pos:taxCode>${applicant.taxCode}</pos:taxCode>
        <pos:address>${applicant.address}</pos:address>
        <pos:city>${applicant.city}</pos:city>
        <pos:zip>${applicant.zip}</pos:zip>
        <pos:email>${applicant.email}</pos:email>
      </pos:applicant>

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
