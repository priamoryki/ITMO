<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="talk" type="ru.itmo.wp.model.domain.Talk" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="targetUserId" type="long" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="table1">
        <form method="post">
            <input type="hidden" name="action" value="setTarget">
            <table>
                <tbody>
                <tr>
                    <td style="font-weight: bold;">You ${user.login}</td>
                    <td>Send Message:</td>
                    <td>
                        <label>
                            <select class="targetUsers" name="targetUserId">
                                <#list users as u>
                                    <option value="${u.id}">${u.login}</option>
                                </#list>
                            </select>
                        </label>
                    </td>
                    <td>
                        <div class="button-field">
                            <input type="submit" value="Write">
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </form>
    </div>
    <#if targetUserId??>
        <form method="post">
            <input type="hidden" name="action" value="sendMessage">
            <div class="send-message">
                <#if find(targetUserId)??>
                    Your message for user ${find(targetUserId)}:
                    </div>
                    <label>
                        <textarea name="message" cols="60" rows="5" style="resize: none"></textarea>
                    </label>
                    <div class="button-field">
                        <input type="submit" value="Send">
                    </div>
                </#if>
        </form>
    </#if>


    <#if talks?? && find(targetUserId)??>
        <div class="datatable">
            <div class="caption">User</div>
            <table>
                <thead>
                <tr>
                    <th>Source</th>
                    <th>Target</th>
                    <th>Message</th>
                </tr>
                </thead>
                <tbody>
                <#list talks as talk>
                    <tr>
                        <td>${find(talk.sourceUserId)}</td>
                        <td>${find(talk.targetUserId)}</td>
                        <td>${talk.text}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
    </#if>
</@c.page>

<#function find id>
    <#list users as u>
        <#if u.id == id>
            <#return u.login>
        </#if>
    </#list>
</#function>