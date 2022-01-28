<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>
<link rel="stylesheet" href="../../css/posts-style.css">

<@c.page>
    <#assign rposts = posts?reverse>
    <#if user_id ??>
        <#list rposts as post>
            <#if post.user_id == user_id>
                <@c.postView post false/>
            </#if>
        </#list>
    <#else>
        <#list rposts as post>
            <@c.postView post false/>
        </#list>
    </#if>
</@c.page>
