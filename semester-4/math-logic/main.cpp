#include <iostream>
#include <tuple>

using Number = std::size_t;

struct Z {
    Number operator()(Number x) {
        return 0;
    }
};

struct N {
    Number operator()(Number x) {
        return x + 1;
    }
};

template<std::size_t I>
struct U {
    template<class... Args>
    Number operator()(Args... xs) {
        return std::get<I - 1>(std::make_tuple(xs...));
    }
};

template<class F, class... G>
struct S {
    template<class... Args>
    Number operator()(Args... xs) {
        return F{}(G{}(xs...)...);
    }
};

template<class F, class G>
struct R {
    template<class... Args>
    Number operator()(Number y, Args... xs) {
        return y == 0 ? F{}(xs...) : G{}(y - 1, R<F, G>{}(y - 1, xs...), xs...);
    }
};


int main() {
    using Add = R<U<1>, S<N, U<2>>>;
    using Mul = R<Z, S<Add, U<2>, U<3>>>;
    Mul mul;

    std::cout << mul(8, 9) << "\n";
}
