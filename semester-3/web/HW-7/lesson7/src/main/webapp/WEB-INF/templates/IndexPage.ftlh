<#import "commons.ftlh" as c>

<link rel="stylesheet" href="/css/article.css"/>

<@c.page>
    <div class="noData">
        No data
    </div>
    <div class="articles">
        <template>
            <article>
                <div class="title"></div>
                <div class="information"></div>
                <div class="text"></div>
                <ul class="attachment"></ul>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                        2 days ago
                        <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                        <a href="#">68</a>
                    </div>
                </div>
            </article>
        </template>
    </div>

    <script>
        $(function () {
            const $noData = $(".noData");
            $noData.text("Loading...");
            const data = {
                action: "findAll"
            };
            const success = function (response) {
                if (response["size"] > 0) {
                    response["articles"].forEach(function (article) {
                        if (!article["hidden"]) {
                            $noData.hide();
                            const $tr = $($(".articles template").prop("content")).clone();
                            $tr.find(".title").text(article["title"]);
                            $tr.find(".text").text(article["text"]);
                            $tr.find(".information").text("By " + response["articleMap"][article["userId"]]);
                            $(".articles").append($tr);
                        }
                    });
                } else {
                    $noData.text("No articles");
                }
            }
            ajax(data, success);
        })
    </script>
</@c.page>
