def next(s):
    closed, opened = 0, 0
    for i in range(len(s) - 1, -1, -1):
        if (s[i] == '('):
            opened += 1
            if (closed > opened):
                break
        else:
            closed += 1
    s = s[:len(s) - (opened + closed)]
    if (s == ''):
        return "-"
    else:
        s = s + ')' + '(' * opened + ')' * (closed - 1)
    return s

inp = open('nextbrackets.in', 'r')
s = inp.readline().split()[0]
inp.close()

out = open('nextbrackets.out', 'w')
out.write(next(s))
out.close()