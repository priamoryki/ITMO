def rec(k):
    if (k == 1):
        return '((A0|B0)|(A0|B0))'
    else:
        r = str(k - 1)
        return '((' + rec(k - 1) + '|((A' + r + '|A' + r + ')|(B' + r + '|B' + r + ')))|(A' + r + '|B' + r + '))'

n = int(input())

print(rec(n))