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


print(' '.join(map(str, prefix_function(input()))))