<#import "commons.ftlh" as c>

<@c.page>
    <div class="user-page">
        <#if currentUser??>
            <div class="user-profile">
                <div class="container-left">
                    <h1> ${currentUser.login} </h1>
                    <ul>
                        <li>id: ${currentUser.id} </li>
                        <li>login: ${currentUser.login} </li>
                        <li>created: ${currentUser.creationTime} </li>
                    </ul>
                </div>
            </div>
        <#else>
            <h1> No such user </h1>
        </#if>
    </div>
</@c.page>