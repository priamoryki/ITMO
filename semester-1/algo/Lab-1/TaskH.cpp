#include <bits/stdc++.h>
using namespace std;

double t(double x, double a, int p, int f) {
    return sqrt((1 - a)*(1 - a) + x*x)/p + sqrt(a*a + (1 - x)*(1 - x))/f;
}

int main() {
    int p, f;
    double a;
    cin >> p >> f >> a;
    double l = 0, r = 1, e = 1e-10;
    while (r - l > e) {
        double m1 = l + (r - l)/3;
        double m2 = r - (r - l)/3;
        if (t(m1, a, p, f) > t(m2, a, p, f)) {
            l = m1;
        } else {
            r = m2;
        }
    }
    printf("%.4f\n", r);
}