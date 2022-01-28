<#-- @ftlvariable name="title" type="java.lang.String" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="article">
        <form method="post">
            <input type="hidden" name="action" value="makeArticle">
            <div class="field">
                <div class="name">
                    <label for="title">Title</label>
                </div>
                <div class="value">
                    <input id="title" name="title" value="${title!}"/>
                </div>
            </div>
            <div class="text">
                <div class="name">
                    <label for="text">Text</label>
                </div>
                <label>
                    <textarea name="text" style="resize: none; width:40rem; height:15rem;"></textarea>
                </label>
            </div>
            <div class="button-field">
                <input type="submit" value="Send">
            </div>
        </form>
    </div>

    <script>
        $(function () {
            $(".article form").submit(function () {
                const $form  = $(this);
                const title = $form.find("input[name='title']").val();
                const text = $form.find("textarea[name='text']").val();
                const data = {
                    action: "makeArticle",
                    title,
                    text
                }
                const success = function (response) {
                    if (response["error"]) {
                        notify(response["error"], 'error');
                    } else {
                        location.href = response["redirect"];
                    }
                }
                ajax(data, success);
                return false;
            });
        })
    </script>
</@c.page>