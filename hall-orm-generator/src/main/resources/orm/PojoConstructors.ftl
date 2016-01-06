
<#if pojo.needsMinimalConstructor()>	<#-- /** minimal constructor */ -->
    def this(${exporter.asParameterList(pojo.getPropertyClosureForMinimalConstructor(), jdk5, pojo)}) {
<#if pojo.isSubclass() && !pojo.getPropertyClosureForSuperclassMinimalConstructor().isEmpty()>
        super(${c2j.asArgumentList(pojo.getPropertyClosureForSuperclassMinimalConstructor())});        
</#if>
        this()
<#foreach field in pojo.getPropertiesForMinimalConstructor()>
        this.${field.name} = ${field.name}
</#foreach>
    }
</#if>    
<#if pojo.needsFullConstructor()>
<#-- /** full constructor */ -->
    def this(${exporter.asParameterList(pojo.getPropertyClosureForFullConstructor(), jdk5, pojo)}) {
<#if pojo.isSubclass() && !pojo.getPropertyClosureForSuperclassFullConstructor().isEmpty()>
        super(${c2j.asArgumentList(pojo.getPropertyClosureForSuperclassFullConstructor())});        
</#if>
       this()
<#foreach field in pojo.getPropertiesForFullConstructor()>
       this.${field.name} = ${field.name}
</#foreach>
    }
</#if>    
