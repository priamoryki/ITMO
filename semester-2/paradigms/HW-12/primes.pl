init(MAX_N) :-
    prime(10000).

prime(2) :- !.
prime(3) :- !.
prime(N) - primesList(N), !.
prime(N) :-
    N > 3, mod(N, 2) > 0, mod(N, 3) > 0,
    is_prime(N, 5),
    assert(primesList(N)).

is_prime(N, I) :- I * I > N, !.
is_prime(N, I) :-
	mod(N, I) > 0,
	NEWI is I + 2,
	is_prime(N, NEWI).

composite(N) :-
    not(prime(N)).

next_prime(P, NEWP) :-
    NEWP is P + 1,
    prime(NEWP), !.
next_prime(P, R) :-
    NEWP is P + 1,
    next_prime(NEWP, R).

find_divisors(N, D, [N], MAX) :- D * D > MAX, N > 1, !.
find_divisors(N, D, [], MAX) :- D * D > MAX, !.
find_divisors(N, D, [D | T], MAX) :-
    0 is mod(N, D),
    NEWN is N / D,
    find_divisors(NEWN, D, T, MAX), !.
find_divisors(N, D, R, MAX) :-
    next_prime(D, NEWD),
    find_divisors(N, NEWD, R, MAX).

find_number(1, _, []) :- !.
find_number(N, P, [H | T]) :-
    prime(H), P =< H,
    find_number(NEWN, H, T),
    N is NEWN * H.

prime_divisors(N, D) :-
    number(N),
    find_divisors(N, 2, D, N), !.
prime_divisors(N, D) :- find_number(N, 2, D).

gcd(A, 0, A) :- A > 0, !.
gcd(0, B, B) :- B > 0, !.
gcd(A, B, GCD) :-
    A > B,
    NEWA is mod(A, B),
    gcd(NEWA, B, GCD).
gcd(A, B, GCD) :-
    A =< B,
    NEWB is mod(B, A),
    gcd(A, NEWB, GCD).