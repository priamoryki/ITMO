<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>
<link rel="stylesheet" href="../../css/user-page.css">

<@c.page>
    <#if handle ??>
        <#assign u = c.findBy(users, "handle", handle)!>
    <#elseif user_id ??>
        <#assign  u = c.findBy(users, "id", user_id)!/>
    </#if>

    <#if u?has_content>
        <div class="user-profile">
            <div class="info">
                <div class="left">
                    <div class="user-link">
                        <@c.userlink u false/>
                    </div>
                    <ul>
                        <li>id: ${u.id}</li>
                        <li>handle: ${u.handle}</li>
                        <li>name: ${u.name}</li>
                        <li>number of posts:
                            <a href="/posts?user_id=${u.id}">${c.countBy(posts "user_id" u.id)}</a>
                        </li>
                    </ul>
                </div>
                <div class="right">
                    <div class="photo">
                        <img src="../../img/no-title.jpg" alt="user photo">
                    </div>
                </div>
            </div>
            <div class="arrows">
                <#assign next = c.next(users, "id", u.id)!false>
                <#assign prev = c.prev(users, "id", u.id)!false>

                <#if !prev?is_boolean>
                    <a href="user?handle=${prev}">&#10229;</a>
                <#else>
                    <span>&#10229;</span>
                </#if>

                <#if !next?is_boolean>
                    <a href="user?handle=${next}">&#10230;</a>
                <#else>
                    <span>&#10230;</span>
                </#if>
            </div>
        </div>
    <#else>
        No such user
    </#if>
</@c.page>
