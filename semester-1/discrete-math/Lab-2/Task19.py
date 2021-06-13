def f(n, k):
    depth, ans, arr = 0, '', []
    for i in range(n * 2 - 1, -1, -1):
        if (depth + 1 <= n):
            cur = int(d[i][depth + 1] * 2 ** ((i - (depth + 1))/2))
        else:
            cur = 0
        if (cur >= k):
            ans += '('
            arr.append('(')
            depth += 1
            continue
        k -= cur
        if (len(arr) > 0 and arr[-1] == '(' and depth - 1 >= 0):
            cur = int(d[i][depth - 1] * 2 ** ((i - depth + 1)/2))
        else:
            cur = 0
        if (cur >= k):
            ans += ')'
            arr.pop()
            depth -= 1
            continue
        k -= cur
        if (depth + 1 <= n):
            cur = int(d[i][depth + 1] * 2 ** ((i - (depth + 1))/2))
        else:
            cur = 0
        if (cur >= k):
            ans += '['
            arr.append('[')
            depth += 1
            continue
        k -= cur
        ans += ']'
        arr.pop()
        depth -= 1
    return ans

with open('num2brackets2.in', 'r') as inp:
    n, k = map(int, inp.readline().split())

d = [[0 for i in range(n + 1)] for i in range(2 * n + 1)]
d[0][0] = 1
for i in range(2 * n):
	for j in range(n + 1):
		if (j + 1 <= n):
			d[i + 1][j + 1] += d[i][j]
		if (j > 0):
			d[i + 1][j - 1] += d[i][j]

with open('num2brackets2.out', 'w') as out:
    out.write(f(n, k + 1))