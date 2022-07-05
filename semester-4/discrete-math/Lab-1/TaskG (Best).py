def polynomial_mul(a, b):
    result = [0 for _ in range(7)]
    for i in range(7):
        for j in range(i + 1):
            result[i] += a[j] * b[i - j]
    return result[:7]


def binomial(n, k):
    if k == 0 or k == n or (k < 0 and n > k):
        return 1
    elif k > n or k < 0:
        return 0
    else:
        p, q = 1, 1
        for i in range(min(k, n - k)):
            p *= (n - i)
            q *= (i + 1)
        return p // q


def my_set(n, k, a):
    if n == 0:
        return 1
    elif k == 0:
        return 0
    else:
        return sum(binomial(a[k] + i - 1, i) * my_set(n - i * k, k - 1, a) for i in range(n // k + 1))


def parse():
    global inp, ind
    ind += 1
    if ind < len(inp):
        if inp[ind] == "B":
            return [0, 1, 0, 0, 0, 0, 0]
        elif inp[ind] == "L":
            ind += 1
            a = parse()
            ind += 1
            result = [1, 0, 0, 0, 0, 0, 0]
            for i in range(1, 7):
                result[i] = sum(a[j] * result[i - j] for j in range(1, i + 1))
            return result
        elif inp[ind] == "S":
            ind += 1
            a = parse()
            ind += 1
            return [my_set(i, i, a) for i in range(7)]
        elif inp[ind] == "P":
            ind += 1
            a = parse()
            ind += 1
            b = parse()
            ind += 1
            return polynomial_mul(a, b)


# S(L(S(L(S(L(S(L(S(L(S(L(S(L(B)))))))))))))) -- WA
ind, inp = -1, input()
print(" ".join(map(str, parse()[:7])))
