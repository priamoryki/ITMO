def num(s):
    num = 0
    depth = 0
    for i in range(2 * n):
        if (s[i] == '('):
            depth += 1
        elif (s[i] == ')'):
            num += d[2 * n - i - 1][depth + 1]
            depth -= 1
    return str(num)

inp = open('brackets2num.in', 'r')
s = inp.readline().split()[0]
n = int(len(s)/2)
inp.close()

d = [[0 for i in range(n + 2)] for i in range(2 * n + 1)]
d[0][0] = 1
for i in range(2 * n):
	for j in range(n + 2):
		if (j + 1 <= n):
			d[i + 1][j + 1] += d[i][j]
		if (j > 0):
			d[i + 1][j - 1] += d[i][j]

out = open('brackets2num.out', 'w')
out.write(num(s))
out.close()