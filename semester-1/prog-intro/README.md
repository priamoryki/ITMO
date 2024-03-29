# «Введение в программирование»

[Условия домашних заданий](http://www.kgeorgiy.info/courses/prog-intro/homeworks.html)

[Тесты к домашним заданиям](http://www.kgeorgiy.info/git/geo/prog-intro-2020)

## Домашнее задание 2. Сумма чисел

1.  Разработайте класс `Sum`, который при запуске из командной строки будет складывать переданные в качестве аргументов целые числа и выводить их сумму на консоль.
2.  Примеры запуска программы:

    <dl>

    `java Sum 1 2 3`

    <dd>Результат: 6</dd>

    `java Sum 1 2 -3`

    <dd>Результат: 0</dd>

    `java Sum "1 2 3"`

    <dd>Результат: 6</dd>

    `java Sum "1 2" " 3"`

    <dd>Результат: 6</dd>

    `java Sum " "`

    <dd>Результат: 0</dd>

    </dl>

    Аргументы могут содержать:
    *   цифры;
    *   знаки `+` и `-`;
    *   произвольные [пробельные символы](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Character.html#isWhitespace(char)).
3.  При выполнении задания можно считать, что для представления входных данных и промежуточных результатов достаточен тип `int`.
4.  Перед выполнением задания ознакомьтесь с документацией к классам [String](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html) и [Integer](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Integer.html).
5.  Для отладочного вывода используйте [System.err](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/System.html#err), тогда он будет игнорироваться проверяющей программой.

Модификация:
 * *Float*
    * Входные данные являются 32-битными числами с формате с плавающей точкой
    * Класс должен иметь имя `SumFloat`
  
[Реализация](https://github.com/priamoryki/ITMO/tree/main/semester-1/prog-intro/HW-2)

## Домашнее задание 3. Реверс

1.  Разработайте класс `Reverse`, читающий числа из [стандартного ввода](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/System.html#in), и выводящий их на [стандартный вывод](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/System.html#out) в обратном порядке.
2.  В каждой строке входа содержится некоторое количество целых чисел (может быть 0). Числа разделены пробелами. Каждое число помещается в тип `int`.
3.  Порядок строк в выходе должен быть обратным по сравнению с порядком строк во входе. Порядок чисел в каждой строке так же должен быть обратным к порядку чисел во входе.
4.  Вход содержит не более 10<sup>6</sup> чисел и строк.
5.  Для чтения чисел используйте класс [Scanner](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Scanner.html).
6.  Примеры работы программы:

    <table class="black">

    <tbody>

    <tr>

    <th>Ввод</th>

    <th>Вывод</th>

    </tr>

    <tr>

    <td>

    <pre>1 2
    3</pre>

    </td>

    <td>

    <pre>3
    2 1</pre>

    </td>

    </tr>

    <tr>

    <td>

    <pre>3
    2 1</pre>

    </td>

    <td>

    <pre>1 2
    3</pre>

    </td>

    </tr>

    <tr>

    <td>

    <pre>1

    2 -3</pre>

    </td>

    <td>

    <pre>-3 2

    1</pre>

    </td>

    </tr>

    <tr>

    <td>

    <pre>1     2
    3     4</pre>

    </td>

    <td>

    <pre>4 3
    2 1</pre>

    </td>

    </tr>

    </tbody>

    </table>

Модификация:
 * *Min*
    * Рассмотрим входные данные как (не полностью определенную) матрицу,
      вместо каждого числа выведите минимум из чисел в его столбце и строке
    * Класс должен иметь имя `ReverseMin`
  
[Реализация](https://github.com/priamoryki/ITMO/tree/main/semester-1/prog-intro/HW-3)

## Домашнее задание 4. Подсчет слов

1.  Разработайте класс `WordStat`, который будет подсчитывать статистику встречаемости слов во входном файле.
2.  Словом называется непрерывная последовательность букв, апострофов и тире (Unicode category [Punctuation, Dash](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Character.html#DASH_PUNCTUATION)). Для подсчета статистики, слова приводятся к нижнему регистру.
3.  Выходной файл должен содержать все различные слова, встречающиеся во входном файле, в порядке их появления. Для каждого слова должна быть выведена одна строка, содержащая слово и число его вхождений во входной файл.
4.  Имена входного и выходного файла задаются в качестве аргументов командной строки. Кодировка файлов: UTF-8.
5.  Примеры работы программы:

    <table class="black">

    <tbody>

    <tr>

    <th>Входной файл</th>

    <th>Выходной файл</th>

    </tr>

    <tr>

    <td style="vertical-align: top">

    <pre>To be, or not to be, that is the question:</pre>

    </td>

    <td>

    <pre>to 2
    be 2
    or 1
    not 1
    that 1
    is 1
    the 1
    question 1</pre>

    </td>

    </tr>

    <tr>

    <td style="vertical-align: top">

    <pre>Monday's child is fair of face.
    Tuesday's child is full of grace.</pre>

    </td>

    <td>

    <pre>monday's 1
    child 2
    is 2
    fair 1
    of 2
    face 1
    tuesday's 1
    full 1
    grace 1</pre>

    </td>

    </tr>

    <tr>

    <td style="vertical-align: top">

    <pre>Шалтай-Болтай
    Сидел на стене.
    Шалтай-Болтай
    Свалился во сне.</pre>

    </td>

    <td>

    <pre>шалтай-болтай 2
    сидел 1
    на 1
    стене 1
    свалился 1
    во 1
    сне 1</pre>

    </td>

    </tr>

    </tbody>

    </table>

Модификация:
 * *InputShingles*
    * Выходной файл должен содержать все различные подстроки длины 3
      слов встречающихся во входном файле, в порядке их появления.
      Слова длины меньшей 3 игнорируются.
    * Класс должен иметь имя `WordStatInputShingles`
  
[Реализация](https://github.com/priamoryki/ITMO/tree/main/semester-1/prog-intro/HW-4)

## Домашнее задание 5. Свой сканнер

1.  Реализуйте свой аналог класса `Scanner` на основе `Reader`.
2.  Примените разработанный `Scanner` для решения задания «Реверс».
3.  Примените разработанный `Scanner` для решения задания «Статистика слов».
4.  Код, управляющий чтением должен быть общим.
5.  _Сложный вариант_. Код, выделяющий числа и слова должен быть общим.
6.  При реализации блочного чтения обратите внимание на слова/числа, пересекающие границы блоков, особенно — больше одного раза.

Модификация:
 * *Abc*
    * Во вводе и выводе используются числа, записаные буквами:
      нулю соответствует буква `a`, единице – `b` и так далее
    * Класс должен иметь имя `ReverseAbc`
  
  [Реализация](https://github.com/priamoryki/ITMO/tree/main/semester-1/prog-intro/HW-5)

## Домашнее задание 6. Подсчет слов++

1.  Разработайте класс `WordStatIndex`, который будет подсчитывать статистику встречаемости слов во входном файле.
2.  Словом называется непрерывная последовательность букв, апострофов и тире (Unicode category Punctuation, Dash). Для подсчета статистики, слова приводятся к нижнему регистру.
3.  Выходной файл должен содержать все различные слова, встречающиеся во входном файле, в порядке их появления. Для каждого слова должна быть выведена одна строка, содержащая слово, число его вхождений во входной файл и номера вхождений этого слова среди всех слов во входном файле.
4.  Имена входного и выходного файла задаются в качестве аргументов командной строки. Кодировка файлов: UTF-8.
5.  Программа должна работать за линейное от размера входного файла время.
6.  Для реализации программы используйте Collections Framework.
7.  _Сложный вариант._ Реализуйте и примените класс `IntList`, компактно хранящий список целых чисел.
8.  Примеры работы программы:

    <table class="black">

    <tbody>

    <tr>

    <th>Входной файл</th>

    <th>Выходной файл</th>

    </tr>

    <tr>

    <td style="vertical-align: top">

    <pre>    To be, or not to be, that is the question:</pre>

    </td>

    <td>

    <pre>    to 2 1 5
        be 2 2 6
        or 1 3
        not 1 4
        that 1 7
        is 1 8
        the 1 9
        question 1 10</pre>

    </td>

    </tr>

    <tr>

    <td style="vertical-align: top">

    <pre>    Monday's child is fair of face.
        Tuesday's child is full of grace.</pre>

    </td>

    <td>

    <pre>    monday's 1 1
        child 2 2 8
        is 2 3 9
        fair 1 4
        of 2 5 11
        face 1 6
        tuesday's 1 7
        full 1 10
        grace 1 12</pre>

    </td>

    </tr>

    <tr>

    <td style="vertical-align: top">

    <pre>    Шалтай-Болтай
        Сидел на стене.
        Шалтай-Болтай
        Свалился во сне.</pre>

    </td>

    <td>

    <pre>    шалтай-болтай 2 1 5
        сидел 1 2
        на 1 3
        стене 1 4
        свалился 1 6
        во 1 7
        сне 1 8</pre>

    </td>

    </tr>

    </tbody>

    </table>

Модификация:
 * *SortedLineIndex*
    * В выходном файле слова должны быть упорядочены в лексикографическом порядке
    * Вместо номеров вхождений во всем файле надо указывать
      `<номер строки>:<номер в строке>`
    * Класс должен иметь имя `WordStatSortedLineIndex`
  
[Реализация](https://github.com/priamoryki/ITMO/tree/main/semester-1/prog-intro/HW-6)

## Домашнее задание 7. Разметка

1.  Разработайте набор классов для текстовой разметки.
2.  Класс <tt>Paragraph</tt> может содержать произвольное число других элементов разметки и текстовых элементов.
3.  Класс <tt>Text</tt> – текстовый элемент.
4.  Классы разметки <tt>Emphasis</tt>, <tt>Strong</tt>, <tt>Strikeout</tt> – выделение, сильное выделение и зачеркивание. Элементы разметки могут содержать произвольное число других элементов разметки и текстовых элементов.
5.  Все классы должны реализовывать метод <tt>toMarkdown([StringBuilder](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/StringBuilder.html))</tt>, которой должен генерировать [Markdown](https://ru.wikipedia.org/wiki/Markdown)-разметку по следующим правилам:
    1.  текстовые элементы выводятся как есть;
    2.  выделенный текст окружается символами '<tt>*</tt>';
    3.  сильно выделенный текст окружается символами '<tt>__</tt>';
    4.  зачеркнутый текст окружается символами '<tt>~</tt>'.
6.  Следующий код должен успешно компилироваться:

    <pre>    Paragraph paragraph = new Paragraph(List.of(
            new Strong(List.of(
                new Text("1"),
                new Strikeout(List.of(
                    new Text("2"),
                    new Emphasis(List.of(
                        new Text("3"),
                        new Text("4")
                    )),
                    new Text("5")
                )),
                new Text("6")
            ))
        ));</pre>

    Вызов <tt>paragraph.toMakdown(new StringBuilder())</tt> должен заполнять переданный <tt>StringBuilder</tt> следующим содержимым:

    <pre>    __1~2*34*5~6__</pre>

7.  Разработанные классы должны находиться в пакете <tt>markup</tt>.

Модификация:
 * *BBCode*
    * Дополнительно реализуйте метод `toBBCode`, генерирующий [BBCode](https://en.wikipedia.org/wiki/BBCode)-разметку:
      * выделеный текст окружается тегом `i`;
      * сильно выделеный текст окружается тегом `b`;
      * зачеркнутый текст окружается тегом `s`.
  
[Реализация](https://github.com/priamoryki/ITMO/tree/main/semester-1/prog-intro/HW-7)

## Домашнее задание 8. Чемпионат

1.  Решите как можно больше задач Чемпионата северо-запада России по программированию 2019.
2.  Материалы соревнования:
    *   [PCMS](https://pcms.itmo.ru/): Java. North-Western Russia Regional Contest - 2019
    *   [Условия задач](https://nerc.itmo.ru/archive/2019/northern/nwrrc-2019-statements.pdf)
    *   [Разбор задач](https://nerc.itmo.ru/archive/2019/northern/nwrrc-2019-tutorials.pdf)
3.  Задачи для решения

    <table>

    <tbody>

    <tr>

    <th colspan="2">Задача</th>

    <th>Тема</th>

    <th>Сложность</th>

    </tr>

    <tr>

    <td>A.</td>

    <td>Accurate Movement</td>

    <td>Формула</td>

    <td>5</td>

    </tr>

    <tr>

    <td>B.</td>

    <td>Bad Treap</td>

    <td>Циклы</td>

    <td>10</td>

    </tr>

    <tr>

    <td>C.</td>

    <td>Cross-Stitch</td>

    <td>Графы</td>

    <td>40</td>

    </tr>

    <tr>

    <td>D.</td>

    <td>Double Palindrome</td>

    <td>Массивы</td>

    <td>40</td>

    </tr>

    <tr>

    <td>E.</td>

    <td>Equidistant</td>

    <td>Деревья</td>

    <td>30</td>

    </tr>

    <tr>

    <td>H.</td>

    <td>High Load Database</td>

    <td>Массивы</td>

    <td>20</td>

    </tr>

    <tr>

    <td>I.</td>

    <td>Ideal Pyramid</td>

    <td>Циклы</td>

    <td>15</td>

    </tr>

    <tr>

    <td>J.</td>

    <td>Just the Last Digit</td>

    <td>Матрицы</td>

    <td>20</td>

    </tr>

    <tr>

    <td>K.</td>

    <td>King’s Children</td>

    <td>Массивы</td>

    <td>40</td>

    </tr>

    <tr>

    <td>M.</td>

    <td>Managing Difficulties</td>

    <td>Коллекции</td>

    <td>10</td>

    </tr>

    </tbody>

    </table>

[Реализация](https://github.com/priamoryki/ITMO/tree/main/semester-1/prog-intro/HW-8)

## Домашнее задание 9. Игра m,n,k

1.  Реализуйте [игру m,n,k](https://en.wikipedia.org/wiki/M,n,k-game).
2.  Добавьте обработку ошибок ввода пользователя.
3.  _Простая версия_. Проверку выигрыша можно производить за _O(nmk)_.
4.  _Сложная версия_.
    *   Проверку выигрыша нужно производить за _O(k)_.
    *   Предотвратите жульничество: у игрока не должно быть возможности достать `Board` из `Position`.
5.  _Бонусная версия_. Реализуйте `Winner` — игрок, который выигрывает всегда, когда это возможно (против любого соперника).

Модификация:
 * *Турнир*
    * Добавьте поддержку кругового турнира для нескольких участников из _c_ кругов
    * Выведите таблицу очков по схеме:
        * 3 очка за победу
        * 1 очко за ничью
        * 0 очков за поражение
  
[Реализация](https://github.com/priamoryki/ITMO/tree/main/semester-1/prog-intro/HW-9)

## Домашнее задание 10. Выражения

1.  Разработайте классы `Const`, `Variable`, `Add`, `Subtract`, `Multiply`, `Divide` для вычисления выражений с одной переменной в типе `int`.
2.  Классы должны позволять составлять выражения вида

    <pre>new Subtract(
        new Multiply(
            new Const(2),
            new Variable("x")
        ),
        new Const(3)
    ).evaluate(5)</pre>

    При вычислении такого выражения вместо каждой переменной подставляется значение, переданное в качестве параметра методу `evaluate` (на данном этапе имена переменных игнорируются). Таким образом, результатом вычисления приведенного примера должно стать число 7.
3.  Метод `toString` должен выдавать запись выражения в полноскобочной форме. Например

    <pre>new Subtract(
        new Multiply(
            new Const(2),
            new Variable("x")
        ),
        new Const(3)
    ).toString()</pre>

    должен выдавать `((2 * x) - 3)`.
4.  _Сложный вариант._ Метод `toMiniString` должен выдавать выражение с минимальным числом скобок. Например

    <pre>new Subtract(
        new Multiply(
            new Const(2),
            new Variable("x")
        ),
        new Const(3)
    ).toMiniString()</pre>

    должен выдавать `2 * x - 3`.
5.  Реализуйте метод `equals`, проверяющий, что два выражения совпадают. Например,

    <pre>new Multiply(new Const(2), new Variable("x"))
        .equals(new Multiply(new Const(2), new Variable("x")))</pre>

    должно выдавать `true`, а

    <pre>new Multiply(new Const(2), new Variable("x"))
        .equals(new Multiply(new Variable("x"), new Const(2)))</pre>

    должно выдавать `false`.
6.  Для тестирования программы должен быть создан класс `Main`, который вычисляет значение выражения `x<sup>2</sup>−2x+1`, для `x`, заданного в командной строке.
7.  При выполнении задания следует обратить внимание на:
    *   Выделение общего интерфейса создаваемых классов.
    *   Выделение абстрактного базового класса для бинарных операций.

Модификация:
 * *Triple*
    * Дополнительно реализуйте интерфейс [TripleExpression](https://github.com/priamoryki/ITMO/blob/main/semester-1/prog-intro/HW-10/expression/TripleExpression.java)
  
[Реализация](https://github.com/priamoryki/ITMO/tree/main/semester-1/prog-intro/HW-10)

## Домашнее задание 11. Разбор выражений

1.  Доработайте предыдущее домашнее задание, так что бы выражение строилось по записи вида

    <pre>x * (x - 2)*x + 1</pre>

2.  В записи выражения могут встречаться: умножение `*`, деление `/`, сложение `+`, вычитание `-`, унарный минус `-`, целочисленные константы (в десятичной системе счисления, которые помещаются в 32-битный знаковый целочисленный тип), круглые скобки, переменные (`x`) и произвольное число пробельных символов в любом месте (но не внутри констант).
3.  Приоритет операторов, начиная с наивысшего
    1.  унарный минус;
    2.  умножение и деление;
    3.  сложение и вычитание.
4.  Разбор выражений рекомендуется производить [методом рекурсивного спуска](https://ru.wikibooks.org/wiki/%D0%A0%D0%B5%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D0%B8_%D0%B0%D0%BB%D0%B3%D0%BE%D1%80%D0%B8%D1%82%D0%BC%D0%BE%D0%B2/%D0%9C%D0%B5%D1%82%D0%BE%D0%B4_%D1%80%D0%B5%D0%BA%D1%83%D1%80%D1%81%D0%B8%D0%B2%D0%BD%D0%BE%D0%B3%D0%BE_%D1%81%D0%BF%D1%83%D1%81%D0%BA%D0%B0). Алгоритм должен работать за линейное время.

Модификация:
 * *Bitwise*
    * Дополнительно реализуйте бинарные операции:
        * `&` – побитное И, приоритет меньше чем у `+` (`6 & 1 + 2` равно `6 & (1 + 2)` равно 2);
        * `^` – побитный XOR, приоритет меньше чем у `&` (`6 ^ 1 + 2` равно `6 ^ (1 + 2)` равно 5);
        * `|` – побитное ИЛИ, приоритет меньше чем у `^` (`6 | 1 + 2` равно `6 | (1 + 2)` равно 7);
  
[Реализация](https://github.com/priamoryki/ITMO/tree/main/semester-1/prog-intro/HW-11)

## Домашнее задание 12. Обработка ошибок

1.  Добавьте в программу вычисляющую выражения обработку ошибок, в том числе:
    *   ошибки разбора выражений;
    *   ошибки вычисления выражений.
2.  Для выражения `1000000*x*x*x*x*x/(x-1)` вывод программы должен иметь следующий вид:

    <pre>x       f
    0       0
    1       division by zero
    2       32000000
    3       121500000
    4       341333333
    5       overflow
    6       overflow
    7       overflow
    8       overflow
    9       overflow
    10      overflow</pre>

    Результат `division by zero` (`overflow`) означает, что в процессе вычисления произошло деление на ноль (переполнение).
3.  При выполнении задания следует обратить внимание на дизайн и обработку исключений.
4.  Человеко-читаемые сообщения об ошибках должны выводится на консоль.
5.  Программа не должна «вылетать» с исключениями (как стандартными, так и добавленными).

Модификация:
 * *AbsSqrt*
    * Дополнительно реализуйте унарные операции:
        * `abs` – модуль числа, `abs -5` равно 5;
        * `sqrt` – квадратный корень, `sqrt 24` равно 4.
  
[Реализация](https://github.com/priamoryki/ITMO/tree/main/semester-1/prog-intro/HW-12)
