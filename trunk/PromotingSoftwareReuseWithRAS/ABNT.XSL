<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:msxsl="urn:schemas-microsoft-com:xslt" exclude-result-prefixes="msxsl"
	xmlns:b="http://schemas.openxmlformats.org/officeDocument/2006/bibliography"
>
	<xsl:output method="html" encoding="us-ascii"/>

	<xsl:template match="/">
		<xsl:apply-templates select="*"/>
	</xsl:template>
	<xsl:template match="b:StyleName">
		<xsl:text>ABNT</xsl:text>
	</xsl:template>
	<xsl:template match="b:GetImportantFields[b:SourceType = 'Book']">
		<b:ImportantFields>
			<b:ImportantField>
				<xsl:text>b:Author/b:Author/b:NameList</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Title</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Edition</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:City</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Publisher</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Year</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Pages</xsl:text>
			</b:ImportantField>
		</b:ImportantFields>
	</xsl:template>
	<xsl:template match="b:GetImportantFields[b:SourceType = 'Report']">
		<b:ImportantFields>
			<b:ImportantField>
				<xsl:text>b:Author/b:Author/b:NameList</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Title</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Year</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:ThesisType</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Department</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Institution</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:City</xsl:text>
			</b:ImportantField>
		</b:ImportantFields>
	</xsl:template>
	<xsl:template match="b:GetImportantFields[b:SourceType = 'ArticleInAPeriodical']">
		<b:ImportantFields>
			<b:ImportantField>
				<xsl:text>b:Author/b:Author/b:NameList</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Title</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:PeriodicalTitle</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:City</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Volume</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Issue</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Pages</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Month</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Year</xsl:text>
			</b:ImportantField>
		</b:ImportantFields>
	</xsl:template>
	<xsl:template match="b:GetImportantFields[b:SourceType = 'ConferenceProceedings']">
		<b:ImportantFields>
			<b:ImportantField>
				<xsl:text>b:Author/b:Author/b:NameList</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Title</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:ConferenceName</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Year</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:City</xsl:text>
			</b:ImportantField>
		</b:ImportantFields>
	</xsl:template>

	<xsl:template  match="b:GetImportantFields[b:SourceType = 'InternetSite']">
		<b:ImportantFields>
			<b:ImportantField>
				<xsl:text>b:Author/b:Author/b:NameList</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Title</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:Year</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:YearAccessed</xsl:text>
			</b:ImportantField>
			<b:ImportantField>
				<xsl:text>b:URL</xsl:text>
			</b:ImportantField>
		</b:ImportantFields>
	</xsl:template>

	<xsl:template match="b:Source[b:SourceType = 'Book']">
		<p>
			<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:Last"/>
			<xsl:if test="string-length(b:Author/b:Author/b:NameList/b:Person/b:First)>0">
				<xsl:text>, </xsl:text>
				<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:First"/>
			</xsl:if>
			<xsl:text>. </xsl:text>
			<b>
			<xsl:value-of select="b:Title"/>
			</b>
			<xsl:text>. </xsl:text>
			<xsl:if test="string-length(b:Edition)>0">
				<xsl:value-of select="b:Edition"/>
				<xsl:text>. </xsl:text>
			</xsl:if>
			<xsl:value-of select="b:City"/>
			<xsl:text>: </xsl:text>
			<xsl:value-of select="b:Publisher"/>
			<xsl:text>, </xsl:text>
			<xsl:value-of select="b:Year"/>
			<xsl:text>. </xsl:text>
			<xsl:if test="string-length(b:Pages)>0">
				<xsl:value-of select="b:Pages"/>
				<xsl:text>.</xsl:text>
			</xsl:if>
		</p>
	</xsl:template>
	<xsl:template match="b:Source[b:SourceType = 'Report']">
		<p>
			<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:Last"/>
			<xsl:if test="string-length(b:Author/b:Author/b:NameList/b:Person/b:First)>0">
				<xsl:text>, </xsl:text>
				<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:First"/>
			</xsl:if>
			<xsl:text>. </xsl:text>
			<b>
			<xsl:value-of select="b:Title"/>
			</b>
			<xsl:text>. </xsl:text>
			<xsl:value-of select="b:Year"/>
			<xsl:text>. </xsl:text>
			<xsl:if test="string-length(b:Pages)>0">
				<xsl:value-of select="b:Pages"/>
				<xsl:text> f. </xsl:text>
			</xsl:if>
			<xsl:value-of select="b:ThesisType"/>
			<xsl:text> - </xsl:text>
			<xsl:value-of select="b:Department"/>
			<xsl:text>, </xsl:text>
			<xsl:value-of select="b:Institution"/>
			<xsl:if test="string-length(b:City)>0">
				<xsl:text>, </xsl:text>
				<xsl:value-of select="b:City"/>
			</xsl:if>
			<xsl:text>.</xsl:text>
		</p>
	</xsl:template>

	<xsl:template match="b:Source[b:SourceType = 'ArticleInAPeriodical']">
		<p>
			<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:Last"/>
			<xsl:if test="string-length(b:Author/b:Author/b:NameList/b:Person/b:First)>0">
				<xsl:text>, </xsl:text>
				<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:First"/>
			</xsl:if>
			<xsl:text>. </xsl:text>
			<b>
			<xsl:value-of select="b:Title"/>
			</b>
			<xsl:text>. </xsl:text>
			<xsl:value-of select="b:PeriodicalTitle"/>
			<xsl:if test="string-length(b:City)>0">
				<xsl:text>, </xsl:text>
				<xsl:value-of select="b:City"/>
			</xsl:if>
			<xsl:if test="string-length(b:Volume)>0">
				<xsl:text>, v.</xsl:text>
				<xsl:value-of select="b:Volume"/>
			</xsl:if>
			<xsl:if test="string-length(b:Issue)>0">
				<xsl:text>, n.</xsl:text>
				<xsl:value-of select="b:Issue"/>
			</xsl:if>
			<xsl:text>, p.</xsl:text>
			<xsl:value-of select="b:Pages"/>
			<xsl:text>, </xsl:text>
			<xsl:value-of select="b:Month"/>
			<xsl:text> </xsl:text>
			<xsl:value-of select="b:Year"/>
			<xsl:text>.</xsl:text>
		</p>
	</xsl:template>

	<xsl:template match="b:Source[b:SourceType = 'ConferenceProceedings']">
		<p>
			<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:Last"/>
			<xsl:if test="string-length(b:Author/b:Author/b:NameList/b:Person/b:First)>0">
				<xsl:text>, </xsl:text>
				<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:First"/>
			</xsl:if>
			<xsl:text>. </xsl:text>
			<b>
			<xsl:value-of select="b:Title"/>
			</b>
			<xsl:text>. Palestra ministrada no </xsl:text>
			<xsl:value-of select="b:ConferenceName"/>
			<xsl:text>, </xsl:text>
			<xsl:value-of select="b:Year"/>
			<xsl:text>, </xsl:text>
			<xsl:value-of select="b:City"/>
			<xsl:text>.</xsl:text>
		</p>
	</xsl:template>

	<xsl:template match="b:Source[b:SourceType = 'InternetSite']">
		<p>
			<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:Last"/>
			<xsl:if test="string-length(b:Author/b:Author/b:NameList/b:Person/b:First)>0">
				<xsl:text>, </xsl:text>
				<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:First"/>
			</xsl:if>
			<xsl:text>. </xsl:text>
			<b>
			<xsl:value-of select="b:Title"/>
			</b>
			<xsl:text>. Acessado em (</xsl:text>
			<xsl:value-of select="b:YearAccessed"/>
			<xsl:text>). Disponível em </xsl:text>
			<xsl:value-of select="b:URL"/>
			<xsl:text>.</xsl:text>
		</p>
	</xsl:template>

	<!-- Defines the output of the entire Bibliography -->
	<xsl:template match="b:Bibliography">
		<html xmlns="http://www.w3.org/TR/REC-html40">
			<body>
				<xsl:apply-templates select ="*">
					<xsl:sort select="b:Author/b:Author/b:NameList/b:Person/b:Last" />
				</xsl:apply-templates>
			</body>
		</html>
	</xsl:template>
	<!-- Defines the output of the Citation -->
	<xsl:template match="b:Citation/b:Source[b:SourceType = 'Book']">
		<html xmlns="http://www.w3.org/TR/REC-html40">
			<body>
				<!-- Defines the output format as (Author, Year)-->
				<xsl:text>(</xsl:text>
				<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:Last"/>
				<xsl:text>, </xsl:text>
				<xsl:value-of select="b:Year"/>
				<xsl:text>)</xsl:text>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="b:Citation/b:Source[b:SourceType = 'Report']">
		<html xmlns="http://www.w3.org/TR/REC-html40">
			<body>
				<xsl:text>(</xsl:text>
				<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:Last"/>
				<xsl:text>, </xsl:text>
				<xsl:value-of select="b:Year"/>
				<xsl:text>)</xsl:text>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="b:Citation/b:Source[b:SourceType = 'ArticleInAPeriodical']">
		<html xmlns="http://www.w3.org/TR/REC-html40">
			<body>
				<xsl:text>(</xsl:text>
				<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:Last"/>
				<xsl:text>, </xsl:text>
				<xsl:value-of select="b:Year"/>
				<xsl:text>)</xsl:text>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="b:Citation/b:Source[b:SourceType = 'ConferenceProceedings']">
		<html xmlns="http://www.w3.org/TR/REC-html40">
			<body>
				<xsl:text>(</xsl:text>
				<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:Last"/>
				<xsl:text>, </xsl:text>
				<xsl:value-of select="b:Year"/>
				<xsl:text>)</xsl:text>
			</body>
		</html>
	</xsl:template>
	
	<xsl:template match="b:Citation/b:Source[b:SourceType = 'InternetSite']">
		<html xmlns="http://www.w3.org/TR/REC-html40">
			<body>
				<xsl:text>(</xsl:text>
				<xsl:value-of select="b:Author/b:Author/b:NameList/b:Person/b:Last"/>
				<xsl:text>, </xsl:text>
				<xsl:value-of select="b:Year"/>
				<xsl:text>)</xsl:text>
			</body>
		</html>
	</xsl:template>

	<xsl:template match="text()" />
</xsl:stylesheet>
