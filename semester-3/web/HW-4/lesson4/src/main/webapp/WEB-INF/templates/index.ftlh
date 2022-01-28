<#import "./common.ftlh" as c>
<link rel="stylesheet" href="../../css/posts-style.css">

<@c.page>
    <ul>
        <#list posts?reverse as post>
            <@c.postView post false/>
        </#list>
    </ul>
</@c.page>
