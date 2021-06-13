def next(s):
    s[-1] -= 1
    s[-2] += 1
    if (s[-2] > s[-1]):
        s[-2] += s[-1]
        s.pop()
    else:
        while (s[-2] * 2 <= s[-1]):
            s.append(s[-1] - s[-2])
            s[-2] = s[-3]
    return s

inp = open('nextpartition.in', 'r')
s = inp.readline().split()[0]
n = s.split('=')[0]
s = list(map(int, s.split('=')[1].split('+')))
inp.close()

out = open('nextpartition.out', 'w')
if (len(s) != 1):
    out.write(n + '=' + '+'.join(map(str, next(s))))
else:
    out.write('No solution')
out.close()