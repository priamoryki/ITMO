def count(i, ndepth):
	if (n >= ndepth >= 0):
		return int(d[2 * n - i - 1][ndepth] * 2 ** ((2 * n - i - ndepth)//2))
	else:
		return 0

def f(s):
	num, depth, arr = 0, 0, []
	for i in range(2 * n):
		if (s[i] == '('):
			arr.append('(')
			depth += 1
		elif (s[i] == ')'):
			num += count(i, depth + 1) # Ставим '('
			arr.pop()
			depth -= 1
		elif (s[i] == '['):
			num += count(i, depth + 1) # Ставим '('
			if (len(arr) > 0 and arr[-1] == '('):
				num += count(i, depth - 1) # Ставим ')'
			arr.append('[')
			depth += 1
		elif (s[i] == ']'):
			num += count(i, depth + 1) # Ставим '('
			if (len(arr) > 0 and arr[-1] == '('):
				num += count(i, depth - 1) # Ставим ')'
			num += count(i, depth + 1) # Ставим '['
			arr.pop()
			depth -= 1
	return str(num)

with open('brackets2num2.in', 'r') as inp:
	s = inp.readline().split()[0]
	n = int(len(s)/2)

d = [[0 for i in range(n + 2)] for i in range(2 * n + 1)]
d[0][0] = 1
for i in range(2 * n):
	for j in range(n + 2):
		if (j + 1 <= n):
			d[i + 1][j + 1] += d[i][j]
		if (j > 0):
			d[i + 1][j - 1] += d[i][j]

with open('brackets2num2.out', 'w') as out:
	out.write(f(s))