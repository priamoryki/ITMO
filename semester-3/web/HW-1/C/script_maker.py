with open('script.bat', 'w') as f:
    for i in range(1, 101):
        print(f'''curl "http://1d3p.wp.codeforces.com/new" ^
            -H "Connection: keep-alive" ^
            -H "Cache-Control: max-age=0" ^
            -H "Origin: http://1d3p.wp.codeforces.com" ^
            -H "Upgrade-Insecure-Requests: 1" ^
            -H "DNT: 1" ^
            -H "Content-Type: application/x-www-form-urlencoded" ^
            -H "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36" ^
            -H "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9" ^
            -H "Referer: http://1d3p.wp.codeforces.com/" ^
            -H "Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7" ^
            -H "Cookie: __utmc=71512449; __utmz=71512449.1630948778.1.1.utmcsr=(direct)^|utmccn=(direct)^|utmcmd=(none); evercookie_png=l1d6q2q7opwjsnjmu9; evercookie_etag=l1d6q2q7opwjsnjmu9; evercookie_cache=l1d6q2q7opwjsnjmu9; 70a7c28f3de=l1d6q2q7opwjsnjmu9; __utma=71512449.280757152.1630948778.1631287584.1631289539.7; JSESSIONID=A3CE9B6258A05F1B7F5CF8F028EDA499" ^
            --data-raw "_af=34be50b38beccce4&proof={i ** 2}&amount={i}&submit=Submit" ^
            --insecure''',
            file=f
        )