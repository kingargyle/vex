<!--

  Stylesheet for creating the file html/toc.html based on tocconcepts.xml
  and toctasks.xml.

-->
<xsl:stylesheet version="1.0" 
                xmlns="http://www.w3.org/1999/xhtml"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	
  	
  <xsl:template match="/">
    <html>
      <head>
        <title>Vex User Guide</title>
      </head>
      <body>

    <xsl:apply-templates/>
    
      </body>
    </html>
  </xsl:template>

  <xsl:template match="toc" name="toc">
    <xsl:param name="hlevel" select="1"/>
    <xsl:element name="h{$hlevel}">
      <xsl:value-of select="@label"/>
    </xsl:element>
    <xsl:apply-templates/>
  </xsl:template>

  <xsl:template match="topic[anchor]">
    <xsl:for-each select="document(concat('toc', anchor/@id, '.xml'))/toc">
      <xsl:call-template name="toc">
        <xsl:with-param name="hlevel" select="2"/>
      </xsl:call-template>
    </xsl:for-each>
  </xsl:template>
  
  <xsl:template match="topic[topic]">
    <li><a href="{@href}"><xsl:value-of select="@label"/></a></li>
    <ul>
      <xsl:apply-templates/>
    </ul>
  </xsl:template>

  <xsl:template match="topic">
    <li><a href="{@href}"><xsl:value-of select="@label"/></a></li>
  </xsl:template>

</xsl:stylesheet>
