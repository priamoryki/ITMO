s = input().split()[0]

cycles = []
for i in range(len(s)):
    s = s[1::] + s[0]
    cycles.append(s)
cycles.sort()

res = ''
for i in cycles:
    res += i[-1]

print(res)