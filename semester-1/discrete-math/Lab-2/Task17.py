def f(n, k):
    if (k == 1): return '(' * n + ')' * n
    depth, s = 0, ''
    for i in range(2 * n):
        if (d[2 * n - (i + 1)][depth + 1] >= k):
            s += '('
            depth += 1
        else:
            k -= d[2 * n - (i + 1)][depth + 1]
            s += ')'
            depth -= 1
    return s

inp = open('num2brackets.in', 'r')
n, k = map(int, inp.readline().split())
inp.close()

d = [[0 for i in range(n + 1)] for i in range(2 * n + 1)]
d[0][0] = 1
for i in range(2 * n):
	for j in range(n + 1):
		if (j + 1 <= n):
			d[i + 1][j + 1] += d[i][j]
		if (j > 0):
			d[i + 1][j - 1] += d[i][j]

out = open('num2brackets.out', 'w')
out.write(f(n, k + 1))
out.close()