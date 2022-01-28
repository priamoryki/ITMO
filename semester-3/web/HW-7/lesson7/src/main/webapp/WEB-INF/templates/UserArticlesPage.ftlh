<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="caption">Articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hidden</th>
            </tr>
            </thead>
            <#list articles as article>
                <tbody>
                <tr>
                    <td class="article_id">${article.id}</td>
                    <td class="article_login">${article.title}</td>
                    <td class="article_hidden">
                        <#if article.hidden>
                            <input id="${article.id}" type="submit" value="Show">
                        <#else>
                            <input id="${article.id}" type="submit" value="Hide">
                        </#if>
                    </td>
                </tr>
                </tbody>
            </#list>
        </table>
    </div>

    <script>
        $(function () {
            $(".article_hidden input").click(function () {
                const $form = $(this);
                const status = ($form.attr('value') === "Hide");

                const data = {
                    action: "changeArticle",
                    id: $form.attr('id'),
                    status
                }

                const success = function (response) {
                    $form.attr('value', !status ? "Hide" : "Show")
                }

                ajax(data, success);
                return false;
            })
        })
    </script>
</@c.page>