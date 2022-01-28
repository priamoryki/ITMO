package ru.itmo.web.lesson4.util;

import ru.itmo.web.lesson4.model.Color;
import ru.itmo.web.lesson4.model.Post;
import ru.itmo.web.lesson4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", Color.BLUE),
            new User(6, "pashka", "Pavel Mavrin", Color.GREEN),
            new User(9, "geranazarov555", "Georgiy Nazarov", Color.GREEN),
            new User(11, "tourist", "Gennady Korotkevich", Color.RED)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(
                    1,
                    "No sense post 1",
                    "Если программисты строили бы дом\n" +
                            "Фундамента могло б не оказаться в нем\n" +
                            "Четыре стены глухие, двери - потом\n" +
                            "Окна - тоже, понадобится - пробьем!\n" +
                            "Так, что еще нужно? А, крыша!\n" +
                            "Видишь картон? Бери его, парниша!\n" +
                            "Тащи его наверх, клади его в два слоя\n" +
                            "Чтоб ветром не срывало, к черту остальное\n" +
                            "Опять стройка встала, на стройке паралич\n" +
                            "Прораб изобретает свой собственный кирпич\n" +
                            "Пока косится лавэ, все прорабу трын-трава\n" +
                            "Он ходит-напевает эти странные слова",
                    1
            ),
            new Post(2,
                    "No sense post 2",
                    "Что были если программисты бы были врачами\n" +
                            "\"Чувак, сломал ногу\" - головами покачали\n" +
                            "\"Эй, гляди сюда, такая же нога\n" +
                            "Но у меня работает, буга-га\n" +
                            "У хирурга был бы самый неблагодарный труд\n" +
                            "Он все время что-то режет, но люди не встают\n" +
                            "Хирург пишет на форуме: \"Наверно, мой косяк\n" +
                            "Вот фотки со стола, что я делаю не так?\"",
                    1)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
