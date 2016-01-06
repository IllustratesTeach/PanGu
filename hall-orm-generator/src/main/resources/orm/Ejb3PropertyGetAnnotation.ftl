<#if ejb3>
<#if pojo.hasIdentifierProperty()>
<#if field.equals(clazz.identifierProperty)>
 ${pojo.generateAnnIdGenerator()}
</#if>
</#if>
<#if c2h.isOneToOne(field)>
${pojo.generateOneToOneAnnotation(field, cfg)}
<#elseif c2h.isManyToOne(field)>
${pojo.generateManyToOneAnnotation(field)}
${pojo.generateJoinColumnsAnnotation(field, cfg)}
<#elseif c2h.isCollection(field)>
${pojo.generateCollectionAnnotation(field, cfg)}
<#else>
${pojo.generateBasicAnnotation(field)} ${pojo.generateAnnColumnAnnotation(field)}
</#if>
</#if>