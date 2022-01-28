def z_function(s : str):
    res, left, right = [0 for _ in range(len(s))], 0, 0
    for i in range(1, len(s)):
        res[i] = max(0, min(right - i, res[i - left]))
        while (i + res[i] < len(s) and s[res[i]] == s[i + res[i]]):
            res[i] += 1
        if (i + res[i] > right):
            left, right = i, i + res[i]
    return res


p, t = input(), input()
s = p + '#' + t
z = z_function(s)
res = [i for i in range(len(t)) if (z[len(p) + 1 + i] == len(p))]
print(len(res))
print(' '.join(map(lambda x: str(x + 1), res)))