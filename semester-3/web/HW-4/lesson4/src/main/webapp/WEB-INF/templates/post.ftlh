<#-- @ftlvariable name="post_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>
<link rel="stylesheet" href="../../css/posts-style.css">

<@c.page>
    <#if post_id ?? && post_id != -1>
        <#assign post = c.findBy(posts, "id", post_id)!>
        <@c.postView post true/>
    <#else>
        <h1>404</h1>
        <p>No such post</p>
    </#if>
</@c.page>