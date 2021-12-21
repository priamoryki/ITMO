rules = [{} for i in range(30)]


def fun(start, index, word):
    if (index == len(word) and start == ' '):
        return True
    elif (start == ' ' or index >= len(word)):
        return False
    result = False
    if (word[index] in rules[ord(start) - 65]):
        for to in rules[ord(start) - 65][word[index]]:
            result = result or fun(to, index + 1, word)
    return result


with open('automaton.in', 'r') as inp:
    n, start = map(str, inp.readline().split())

    for i in range(int(n)):
        temp = inp.readline().split()
        if (temp[2][0] in rules[ord(temp[0][0]) - 65]):
            rules[ord(temp[0][0]) - 65][temp[2][0]].append(temp[2][1] if len(temp[2]) > 1 else ' ')
        else:
            rules[ord(temp[0][0]) - 65][temp[2][0]] = [temp[2][1] if len(temp[2]) > 1 else ' ']

    m = int(inp.readline().split()[0])
    with open('automaton.out', 'w') as out:
        for i in range(m):
            out.write('yes\n' if fun(start, 0, inp.readline().split()[0]) else 'no\n')
