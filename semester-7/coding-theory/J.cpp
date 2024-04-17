#include <iostream>
#include <vector>
#include <algorithm>
#include <random>
#include <map>

using Vector = std::vector<int>;
using Matrix = std::vector<Vector>;

const int ENCODE = 1, DECODE = 2, SIMULATE = 3;
int n, m, k, p, d, bit;
std::map<std::string, int> get_function = {
        {"Encode",   ENCODE},
        {"Decode",   DECODE},
        {"Simulate", SIMULATE}
};
std::random_device rd{};
std::mt19937 gen{rd()};
std::uniform_int_distribution<> distribBool(0, 1);
std::uniform_real_distribution<> distribReal;

std::ostream &operator<<(std::ostream &os, Vector &a) {
    for (auto &val: a) {
        os << val << " ";
    }
    os << '\n';
    return os;
}

void setup() {
    std::ios_base::sync_with_stdio(false);
    std::cin.tie(nullptr);
    std::cout.tie(nullptr);

    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
}

int get_max_bit(int num) {
    int result = 1;
    for (; num > 1; num /= 2, result *= 2) {
    }
    return result;
}

Vector get_alphas() {
    Vector result(n);
    result[0] = 1;
    for (int i = 1; i < n; i++) {
        result[i] = 2 * result[i - 1];
        if (result[i] & bit) {
            result[i] ^= p;
        }
    }
    return result;
}

Vector get_pows(Vector &alphas) {
    Vector pows(n + 1, 0);
    for (int i = 1; i < n; i++) {
        pows[alphas[i]] = i;
    }
    return pows;
}

Matrix get_cycles() {
    Matrix cycles{{0}};
    std::vector<bool> used(n, false);

    for (int i = 1; i < n; i++) {
        if (used[i]) {
            continue;
        }
        used[i] = true;

        Vector curr{i};
        for (int iter = (2 * i) % n; iter != curr[0]; iter = (2 * iter) % n) {
            used[iter] = true;
            curr.push_back(iter);
        }
        cycles.push_back(curr);
    }
    return cycles;
}

int multiply(int a, int b, Vector &alphas, Vector &pows) {
    if (a != 0 && b != 0) {
        return alphas[(pows[a] + pows[b]) % n];
    }
    return 0;
}

Vector &multiply(Vector &a, Vector &b, Vector &alphas, Vector &pows) {
    Vector res(a.size() + b.size() - 1);
    for (int i = 0; i < a.size(); i++) {
        for (int j = 0; j < b.size(); j++) {
            res[i + j] ^= multiply(a[i], b[j], alphas, pows);
        }
    }
    return (a = res);
}

Vector &get_polynom(Vector &g, Matrix &cycles, Vector &alphas, Vector &pows) {
    for (int i = 1; i < cycles.size(); i++) {
        if (cycles[i][0] > d - 1) {
            continue;
        }
        Vector mult{1};
        for (int j: cycles[i]) {
            Vector temp{alphas[j], 1};
            mult = multiply(mult, temp, alphas, pows);
        }
        g = multiply(g, mult, alphas, pows);
    }
    return g;
}

Vector encode(Vector &g, Vector &input) {
    Vector result(m, 0);
    result.insert(result.end(), input.begin(), input.end());
    Vector temp(m, 0);
    temp.insert(temp.end(), input.begin(), input.end());

    for (int i = result.size() - 1; i >= m; i--) {
        if (!temp[i]) {
            continue;
        }
        for (int j = 0; j < g.size(); j++) {
            temp[i - j] ^= g[m - j];
        }
    }
    std::copy_n(temp.begin(), m, result.begin());
    return result;
}

Vector get_syndrome(Vector &input, Vector &alphas) {
    Vector syndrome(d);
    for (int i = 1; i < d; i++) {
        int temp = 0;
        for (int j = 0; j < input.size(); j++) {
            if (input[j]) {
                temp ^= alphas[(i * j) % n];
            }
        }
        syndrome[i] = temp;
    }
    return syndrome;
}

Vector decode(Vector &input, Vector &alphas, Vector &pows) {
    Vector syndrome = get_syndrome(input, alphas);

    Vector comp{1};
    Vector loc{1};
    int L = 0;

    for (int r = 1; r < d; r++) {
        int delta = 0;
        for (int j = 0; j <= L; j++) {
            delta ^= multiply(loc[j], syndrome[r - j], alphas, pows);
        }

        comp.insert(comp.begin(), 0);

        if (delta != 0) {
            Vector temp(std::max(comp.size(), loc.size()), 0);
            std::transform(comp.begin(), comp.end(), temp.begin(), [&](int elem) {
                return multiply(elem, delta, alphas, pows);
            });
            for (int i = 0; i < loc.size(); i++) {
                temp[i] ^= loc[i];
            }

            if (2 * L <= r - 1) {
                comp.clear();
                L *= -1;
                L += r;
                int cur = alphas[(n - pows[delta]) % n];
                comp.resize(loc.size());
                std::transform(loc.begin(), loc.end(), comp.begin(), [&](int i) {
                    return multiply(cur, i, alphas, pows);
                });
            }
            loc = temp;
        }
    }

    Vector res = input;
    for (int i = 1; i <= n; i++) {
        int err = -1;
        for (int j = 0; j < loc.size(); j++) {
            err ^= multiply(loc[j], alphas[(pows[i] * j) % n], alphas, pows);
        }
        if (err == -1) {
            int index = (n - pows[i]) % n;
            res[index] = !res[index];
        }
    }
    return res;
}

int main() {
    setup();

    std::cin >> n >> p >> d;

    bit = get_max_bit(p);
    Vector alphas = get_alphas();
    Vector pows = get_pows(alphas);
    Matrix cycles = get_cycles();
    Vector g{1};
    g = get_polynom(g, cycles, alphas, pows);

    m = g.size() - 1;
    k = n - m;
    std::cout << k << '\n';
    std::cout << g;

    std::string command;
    while (std::cin >> command) {
        switch (get_function[command]) {
            case ENCODE: {
                Vector input(k);
                for (int i = 0; i < k; i++) {
                    std::cin >> input[i];
                }

                Vector res = encode(g, input);
                std::cout << res;
                break;
            }
            case DECODE: {
                Vector input(n);
                for (int i = 0; i < n; i++) {
                    std::cin >> input[i];
                }

                Vector decoded = decode(input, alphas, pows);
                std::cout << decoded;
                break;
            }
            case SIMULATE: {
                double noise;
                int iterationsNumber, maxErrorsNumber;
                std::cin >> noise >> iterationsNumber >> maxErrorsNumber;

                int mistakes = 0;
                int iter = 1;
                for (; iter < iterationsNumber && mistakes != maxErrorsNumber; iter++) {
                    Vector message = Vector(k, distribBool(gen));
                    Vector encoded = encode(g, message);
                    Vector signal(encoded.size());
                    for (int i = 0; i < signal.size(); i++) {
                        signal[i] = distribReal(gen) >= noise ? encoded[i] : !encoded[i];
                    }
                    Vector decoded = decode(signal, alphas, pows);
                    if (decoded != encoded) {
                        mistakes += 1;
                    }
                }
                std::cout << double(mistakes) / double(iter) << '\n';
                break;
            }
        }
    }
}
