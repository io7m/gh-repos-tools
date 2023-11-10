<?xml version="1.0" encoding="UTF-8" ?>

<stylesheet xmlns="http://www.w3.org/1999/XSL/Transform"
            xmlns:c="urn:com.io7m.changelog:4.0"
            xmlns:u="http://www.mojohaus.org/versions-maven-plugin/schema/updates/2.0"
            version="2">

  <output indent="yes"/>

  <template match="u:updates">
    <c:changes>
      <apply-templates select="u:dependencyUpdate"/>
    </c:changes>
  </template>

  <template match="u:dependencyUpdate">
    <element name="c:change">
      <attribute name="date">
        <value-of select="format-dateTime(current-dateTime(), '[Y0004]-[M02]-[D02]T[H02]:[m02]:[s02]+00:00')"/>
      </attribute>
      <attribute name="summary">
        <value-of select="concat('Update ',@groupId,':',@artifactId,' ',@oldVersion,' → ',@newVersion,'.')"/>
      </attribute>
    </element>
  </template>

</stylesheet>