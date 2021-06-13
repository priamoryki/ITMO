def f(prefix):
    if (len(prefix) == 2 * n):
        out.write(prefix + '\n')
        return
    if (prefix.count('(') < n):
        f(prefix + '(')
    if (prefix.count('(') > prefix.count(')')):
        f(prefix + ')')

inp = open('brackets.in', 'r')
n = int(inp.readline())
inp.close()

out = open('brackets.out', 'w')
f('')
out.close()