<#-- // Fields -->

<#foreach field in pojo.getAllPropertiesIterator()><#if pojo.getMetaAttribAsBool(field, "gen-property", true)> <#if pojo.hasMetaAttribute(field, "field-description")>    /**
     ${pojo.getFieldJavaDoc(field, 0)}
     */
 </#if>
     <#include "GetPropertyAnnotation.ftl"/>
     var ${field.name}:${exporter.getJavaTypeName(field, jdk5)} = <#if pojo.hasFieldInitializor(field, jdk5)>${pojo.getFieldInitialization(field, jdk5)} <#else> _</#if>
</#if>
</#foreach>
