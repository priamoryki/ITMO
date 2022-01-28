def get_hash(l: int, r: int) -> int:
    return (hashes[r + 1] - hashes[l] * prime_pows[r - l + 1] + (10 ** 9 + 7)) % (10 ** 9 + 7)


PRIME, hashes, prime_pows = 31, [0], [1]
s = input()
m = int(input())


for i in range(len(s)):
    hashes.append((hashes[i] * PRIME + ord(s[i])) % (10 ** 9 + 7))
    prime_pows.append((prime_pows[i] * PRIME) % (10 ** 9 + 7))


for _ in range(m):
    a, b, c, d = map(lambda x: int(x) - 1, input().split())
    print("Yes" if get_hash(a, b) == get_hash(c, d) else "No")