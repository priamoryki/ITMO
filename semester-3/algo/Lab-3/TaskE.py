def prefix_function(s: str):
    res = [0 for _ in range(len(s))]
    for i in range(1, len(s)):
        j = res[i - 1]
        while (j > 0 and s[i] != s[j]):
            j = res[j - 1]
        if (s[i] == s[j]):
            j += 1
        res[i] = j
    return res


s = input()
res = len(s) - prefix_function(s)[-1]
print(res if len(s) % res == 0 else len(s))
