def polynomial_sum(a, b):
    result = [0 for _ in range(max(len(a), len(b)))]
    for i in range(len(a)):
        result[i] += a[i]
    for i in range(len(b)):
        result[i] += b[i]
    return result[:7]


def polynomial_mul(a, b):
    result = [0 for _ in range(len(a) + len(b) - 1)]
    for i in range(len(a)):
        for j in range(len(b)):
            result[i + j] += a[i] * b[j]
    return result[:7]


def polynomial_div(a, b):
    result = [0 for _ in range(7)]
    result[0] = a[0] * b[0] ** 7
    for i in range(1, len(result)):
        if i < len(a):
            result[i] = a[i]
        result[i] -= sum(result[j] * b[i - j] for j in range(i) if 0 <= i - j < len(b))
    return result[:7]


def polynomial_pow(a, n):
    res = [1, 0, 0, 0, 0, 0, 0]
    while n != 0:
        if n % 2 == 1:
            res = polynomial_mul(res, a)
            n -= 1
        else:
            a = polynomial_mul(a, a)
            n //= 2
    return res


def my_set(a):
    res = [1, 0, 0, 0, 0, 0, 0]
    for i in range(1, 7):
        cur = polynomial_pow([0, 1, 0, 0, 0, 0, 0], i)
        res = polynomial_mul(
            res,
            polynomial_div(
                [1, 0, 0, 0, 0, 0, 0],
                polynomial_pow(polynomial_sum([1, 0, 0, 0, 0, 0, 0], polynomial_mul([-1, 0, 0, 0, 0, 0, 0], cur)), a[i])
            )
        )
    return res


def parse():
    global inp, ind
    ind += 1
    if ind < len(inp):
        if inp[ind] == "B":
            return [0, 1, 0, 0, 0, 0, 0]
        elif inp[ind] == "L":
            ind += 1
            a = parse()
            a[0] = 0
            ind += 1
            return polynomial_div(
                [1, 0, 0, 0, 0, 0, 0],
                polynomial_sum([1, 0, 0, 0, 0, 0, 0], polynomial_mul([-1, 0, 0, 0, 0, 0, 0], a))
            )
        elif inp[ind] == "S":
            ind += 1
            a = parse()
            ind += 1
            return my_set(a)
        elif inp[ind] == "P":
            ind += 1
            a = parse()
            ind += 1
            b = parse()
            ind += 1
            return polynomial_mul(a, b)


ind, inp = -1, input()
print(" ".join(map(str, parse()[:7])))