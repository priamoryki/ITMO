/*⠀
⠀⠀⠀⠀⣠⣶⡾⠏⠉⠙⠳⢦⡀⠀⠀⠀⢠⠞⠉⠙⠉⠙⠲⡀
⠀⠀⠀⣴⠿⠏⠀⠀⠀⠀⠀⠀ ⢳⡀⠀⡏⠀⠀⠀⠀ ⠀   ⢷
⠀⠀⢠⣟⣋⡀⢀⣀⣀⡀⠀⣀⡀⣧⠀⢸⠀⠀⠀⠀⠀     ⡇
⠀⠀⢸⣯⡭⠁⠸⣛⣟⠆⡴⣻⡲⣿⠀⣸    OK    ⡇
⠀⠀⣟⣿⡭⠀⠀⠀⠀⠀⢱⠀⠀ ⣿⠀⢹⠀⠀⠀⠀⠀    ⡇
⠀⠀⠙⢿⣯⠄⠀⠀⠀⢀⡀⠀⠀⡿⠀⠀⡇⠀⠀⠀⠀    ⡼
⠀⠀⠀⠀⠹⣶⠆⠀⠀⠀⠀⠀⡴⠃⠀⠀⠘⠤⣄⣠⣄⣠⣄⠞
⠀⠀⠀⠀⠀⢸⣷⡦⢤⡤⢤⣞⣁
⠀⠀⢀⣤⣴⣿⣏⠁⠀⠀⠸⣏⢯⣷⣖⣦⡀
⢀⣾⣽⣿⣿⣿⣿⠛⢲⣶⣾⢉⡷⣿⣿⠵⣿
⣼⣿⠍⠉⣿⡭⠉⠙⢺⣇⣼⡏⠀⠀⠀⣄⢸
⣿⣿⣧⣀⣿.........⣀⣰⣏⣘⣆⣀
*/

#include <bits/stdc++.h>

#define M_PI        3.14159265358979323846

void fft(std::vector<std::complex<double>> &a, bool flag) {
    size_t n = a.size();
    if (n == 1) {
        return;
    }
    std::vector<std::complex<double>> a_l(n / 2), a_r(n / 2);
    for (size_t i = 0; i < n; i += 2) {
        a_l[i / 2] = a[i];
        a_r[i / 2] = a[i + 1];
    }
    fft(a_l, flag), fft(a_r, flag);
    double angle = 2 * M_PI / n;
    if (flag) {
        angle *= -1;
    }
    std::complex<double> c(1);
    for (int i = 0; i < n / 2; ++i) {
        a[i] = a_l[i] + c * a_r[i];
        a[i + n / 2] = a_l[i] - c * a_r[i];
        if (flag) {
            a[i] /= 2;
            a[i + n / 2] /= 2;
        }
        c *= std::complex<double>(cos(angle), sin(angle));
    }
}

std::vector<int> multiply(std::vector<int> &a, std::vector<int> &b) {
    std::vector<std::complex<double>> complex_a(a.begin(), a.end()), complex_b(b.begin(), b.end());
    size_t n = 1;
    while (n < std::max(a.size(), b.size())) {
        n *= 2;
    }
    n *= 2;
    complex_a.resize(n), complex_b.resize(n);
    fft(complex_a, false), fft(complex_b, false);
    for (size_t i = 0; i < n; ++i) {
        complex_a[i] *= complex_b[i];
    }
    fft(complex_a, true);
    std::vector<int> result;
    for (size_t i = 0; i < n; ++i) {
        result.push_back(complex_a[i].real() + 0.5);
    }
    return result;
}

int main() {
    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);
    int n;
    std::cin >> n;
    std::vector<int> a(n + 1), b(n + 1);
    for (int i = 0; i < n + 1; i++) {
        std::cin >> a[i];
    }
    for (int i = 0; i < n + 1; i++) {
        std::cin >> b[i];
    }
    std::vector<int> result = multiply(a, b);
    while (result.back() == 0) {
        result.pop_back();
    }
    for (int i : result) {
        std::cout << i << " ";
    }
}
