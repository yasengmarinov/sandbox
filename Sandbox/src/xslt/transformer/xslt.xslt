<?xml version="1.0"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fn="http://www.w3.org/" >
  <xsl:template match="/">
    <xsl:for-each select="root/test">
      <xsl:if test="position() = last()">
          <xsl:value-of select="." />
          <xsl:value-of select="position()" />
      </xsl:if>
  </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>