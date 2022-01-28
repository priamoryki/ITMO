<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <#assign items=[
            {"href": "/index", "name": "Home"},
            {"href": "/misc/help", "name": "Help"},
            {"href": "/contests", "name": "Contests"},
            {"href": "/users", "name": "Users"}
            ]/>
            <ul>
                <#list items as item>
                    <li>
                        <#if current_page == item.href>
                            <a class="selected" href="${item.href}">${item.name}</a>
                        <#else>
                            <a href="${item.href}">${item.name}</a>
                        </#if>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts?reverse as post>
            <section>
                <div class="header">
                    Post #${post.id}
                </div>
                <div class="body">
                    <@postText post.text false/>
                </div>
                <div class="footer">
                    <a href="/post?post_id=${post.id}">View all</a>
                </div>
            </section>
        </#list>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>

<#macro userlink user nameOnly>
    <#if nameOnly>
        ${user.name}
    <#else>
        <a href="/user?handle=${user.handle}">${user.name}</a>
        <style>
            .user-link a {
                text-decoration: none;
                font-weight: bolder;
                color: ${user.color}
            }
        </style>
    </#if>
</#macro>

<#function findBy items key value>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function countBy items key value>
    <#assign counter = 0>
    <#list items as item>
        <#if item[key] == value>
            <#assign counter += 1>
        </#if>
    </#list>
    <#return counter>
</#function>

<#function next items key value>
    <#list items as item>
        <#if item[key] == value && item_index < items?size - 1>
            <#return items[item_index + 1].handle>
        </#if>
    </#list>
</#function>

<#function prev items key value>
    <#list items as item>
        <#if item[key] == value && 0 < item_index>
            <#return items[item_index - 1].handle>
        </#if>
    </#list>
</#function>

<#macro postText text full>
    <#if full || text?length <= 250>
        <p>${text}</p>
    <#else>
        <p>${text?substring(0, 250)}...</p>
    </#if>
</#macro>

<#macro postView post full>
    <article>
        <div class="title"><a href="post?post_id=${post.id}"> ${post.title} </a></div>
        <div class="information"> By <@userlink findBy(users, "id", post.user_id) true/> </div>
        <div class="body">
            <@postText post.text full/>
        </div>
        <div class="footer">
            <div class="left">
                <img src="../../img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="../../img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="../../img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                <span class="days-ago">2 days ago</span>
                <img src="../../img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</#macro>
