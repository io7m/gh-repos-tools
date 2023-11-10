<?xml version="1.0" encoding="UTF-8" ?>

<stylesheet xmlns="http://www.w3.org/1999/XSL/Transform"
            xmlns:c="urn:com.io7m.changelog:4.0"
            xmlns:u="http://www.mojohaus.org/versions-maven-plugin/schema/updates/2.0"
            version="2">

  <output method="text" omit-xml-declaration="no" indent="no"/>

  <template match="u:updates">
    <c:changes>
      <apply-templates select="u:dependencyUpdate"/>
    </c:changes>
  </template>

  <template match="u:dependencyUpdate">
    <text>changelog change-add --summary </text>
    <text>'Update </text>
    <text><value-of select="@groupId"/></text>
    <text>:</text>
    <text><value-of select="@artifactId"/></text>
    <text>:</text>
    <text><value-of select="@oldVersion"/></text>
    <text> → </text>
    <text><value-of select="@newVersion"/></text>
    <text>'</text>
    <text>&#xa;</text>
  </template>

</stylesheet>
