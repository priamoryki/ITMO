def f(depth):
    if (depth == 1):
        return [str(i) for i in range(k)]
    else:
        res, prev = [], f(depth - 1)
        for i in range(k):
            if (i % 2 == 0):
                res = [*res, *prev]
            else:
                res = [*res, *reversed(prev)]
        for i in range(k):
            for j in range(k ** (depth - 1)):
                res[i * (k ** (depth - 1)) + j] += str(i)
        return res

inp = open('telemetry.in', 'r')
n, k = map(int, inp.readline().split())
inp.close()

out = open('telemetry.out', 'w')
out.write('\n'.join(f(n)))
out.close()