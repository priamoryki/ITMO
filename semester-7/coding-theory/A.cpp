#include <iostream>
#include <vector>
#include <algorithm>
#include <random>
#include <map>

using uint = unsigned int;
using Vector = std::vector<uint>;
using Matrix = std::vector<Vector>;

const uint ENCODE = 1, DECODE = 2, SIMULATE = 3;
int n, k;
std::map<std::string, uint> get_function = {
        {"Encode",   ENCODE},
        {"Decode",   DECODE},
        {"Simulate", SIMULATE}
};
std::random_device rd{};
std::mt19937 gen{rd()};
std::uniform_int_distribution<> distrib;

Vector &operator*(Vector &a, Matrix &b) {
    Vector temp(b[0].size());
    for (std::size_t i = 0; i < temp.size(); i++) {
        for (std::size_t j = 0; j < b.size(); j++) {
            temp[i] ^= a[j] * b[j][i];
            temp[i] &= 1;
        }
    }
    return (a = temp);
}

Vector &operator^(Vector &to, Vector &from) {
    for (std::size_t i = 0; i < to.size(); i++) {
        to[i] ^= from[i];
    }
    return to;
}

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

static uint count_bits(uint x) {
    return __builtin_popcount(x);
}

static uint get_complexity(uint x) {
    return 1 << count_bits(x);
}

struct Node {
    uint position;
    uint value;

    explicit Node(uint position = 0, uint value = 0) {
        this->position = position;
        this->value = value;
    }
};

struct Layer {
    uint activeBits;
    std::vector<std::vector<Node>> nodes;

    explicit Layer(uint activeBits = 0) {
        this->activeBits = activeBits;
        this->nodes = std::vector<std::vector<Node>>(get_complexity());
    }

    [[nodiscard]] uint get_complexity() const {
        return ::get_complexity(activeBits);
    }
};

static uint get_index(uint transition, uint active) {
    uint shift = 0, result = 0;
    while (active != 0) {
        if (active & 1) {
            result |= ((transition & 1) << shift);
            shift++;
        }
        transition >>= 1;
        active >>= 1;
    }
    return result;
}

static uint get_transition(uint index, uint active) {
    uint shift = 0, result = 0;
    while (active != 0) {
        if (active & 1) {
            result |= ((index & 1) << shift);
            index >>= 1;
        }
        shift++;
        active >>= 1;
    }
    return result;
}

static void top_to_bottom(Matrix &matrix) {
    std::vector<bool> free(k, true);
    int column = n;
    for (std::size_t step = 0; step < k; step++) {
        column--;
        int count = 0;
        for (std::size_t i = 0; i < k; i++) {
            if (free[i] && matrix[i][column]) {
                count++;
            }
        }
        if (count == 0) {
            step--;
            continue;
        }
        std::size_t last = k - 1;
        while (!free[last] || !matrix[last][column]) {
            last--;
        }
        free[last] = false;
        if (count == 1) {
            continue;
        }
        for (std::size_t i = 0; i < last; i++) {
            if (free[i] && matrix[i][column]) {
                matrix[i] = matrix[i] ^ matrix[last];
            }
        }
    }
}

static void bottom_to_top(Matrix &matrix) {
    int j = -1;
    for (std::size_t i = 0; i < k; i++) {
        j++;
        bool flag = true;
        if (!matrix[i][j]) {
            flag = false;
            for (std::size_t current_row = i + 1; current_row < k; current_row++) {
                if (matrix[current_row][j]) {
                    matrix[i] = matrix[i] ^ matrix[current_row];
                    flag = true;
                    break;
                }
            }
        }
        if (!flag) {
            i--;
            continue;
        }
        for (std::size_t current_row = i + 1; current_row < k; current_row++) {
            if (matrix[current_row][j]) {
                matrix[current_row] = matrix[current_row] ^ matrix[i];
            }
        }
    }
}

static Matrix minimal_span_form(Matrix &matrix) {
    Matrix new_matrix = matrix;
    bottom_to_top(new_matrix);
    top_to_bottom(new_matrix);
    std::reverse(new_matrix.begin(), new_matrix.end());
    return new_matrix;
}

static std::vector<std::pair<uint, uint>> generate_span(Matrix &matrix) {
    std::vector<std::pair<uint, uint>> result;
    for (std::size_t i = 0; i < k; i++) {
        int left = 0;
        for (int j = 0; j < n; j++) {
            if (matrix[i][j]) {
                left = j;
                break;
            }
        }
        int right = k;
        for (int j = n - 1; j >= 0; j--) {
            if (matrix[i][j]) {
                right = j;
                break;
            }
        }
        result.emplace_back(left, right);
    }
    return result;
}

static std::vector<Layer> build_graph(Matrix &matrix) {
    std::vector<std::pair<uint, uint>> heads_tails = generate_span(matrix);
    std::vector<Layer> graph{{}};
    for (std::size_t j = 0; j < n; j++) {
        uint column = 0;
        for (std::size_t i = 0; i < k; i++) {
            column |= matrix[i][j] << i;
        }
        uint nextLayerActiveBits = 0;
        for (std::size_t i = 0; i < k; i++) {
            bool flag1 = heads_tails[i].first <= j && j < heads_tails[i].second;
            bool flag2 = heads_tails[i].second == j && j == heads_tails[i].first;
            if (flag1 || flag2) {
                nextLayerActiveBits |= (1 << i);
            }
        }

        Layer &lastLayer = graph.back();
        uint shared = lastLayer.activeBits | nextLayerActiveBits;

        for (std::size_t i = 0; i < get_complexity(shared); i++) {
            uint transition = get_transition(i, shared);
            lastLayer.nodes[get_index(transition, lastLayer.activeBits)].emplace_back(
                    get_index(transition, nextLayerActiveBits),
                    count_bits(transition & column) & 1
            );
        }
        graph.emplace_back(nextLayerActiveBits);
    }
    return graph;
}

Vector decode(std::vector<Layer> &graph, std::vector<double> &signal) {
    std::vector<std::vector<double>> cost(graph.size());
    cost.front() = std::vector<double>(graph.front().get_complexity(), 0);
    std::vector<std::vector<Node>> path(graph.size());

    for (std::size_t i = 1; i < graph.size(); i++) {
        uint complexity = graph[i].get_complexity();
        cost[i] = std::vector<double>(complexity, -1000000);
        path[i] = std::vector<Node>(complexity);
    }

    for (std::size_t i = 1; i < graph.size(); i++) {
        for (std::size_t from = 0; from < graph[i - 1].get_complexity(); from++) {
            for (Node &node: graph[i - 1].nodes[from]) {
                double weight = node.value ? -signal[i - 1] : signal[i - 1];
                if (cost[i - 1][from] + weight > cost[i][node.position]) {
                    cost[i][node.position] = cost[i - 1][from] + weight;
                    path[i][node.position] = Node(from, node.value);
                }
            }
        }
    }

    Vector result{};
    uint current = 0;
    for (std::size_t i = path.size() - 1; i != 0; i--) {
        Node node = path[i][current];
        result.push_back(node.value);
        current = node.position;
    }
    reverse(result.begin(), result.end());
    return result;
}

int main() {
    setup();

    std::cin >> n >> k;
    Matrix m(k, Vector(n));
    for (std::size_t i = 0; i < k; i++) {
        for (std::size_t j = 0; j < n; j++) {
            std::cin >> m[i][j];
        }
    }

    Matrix spanForm = minimal_span_form(m);
    std::vector<Layer> graph = build_graph(spanForm);

    Vector complexities{};
    for (Layer &layer: graph) {
        complexities.push_back(layer.get_complexity());
    }
    std::cout << complexities;

    std::string command;
    while (std::cin >> command) {
        switch (get_function[command]) {
            case ENCODE: {
                Vector v(k);
                for (auto &x: v) {
                    std::cin >> x;
                }

                Vector res = v * m;
                std::cout << res;
                break;
            }
            case DECODE: {
                std::vector<double> v(n);
                for (auto &x: v) {
                    std::cin >> x;
                }

                Vector decoded = decode(graph, v);
                std::cout << decoded;
                break;
            }
            case SIMULATE: {
                double noise;
                uint iterationsNumber, maxErrorsNumber;
                std::cin >> noise >> iterationsNumber >> maxErrorsNumber;

                double sigma = sqrt(0.5 * pow(10.0, -noise / 10.0) * (double(n) / double(k)));
                std::normal_distribution<double> normalDistribution(0, sigma);
                int mistakes = 0;
                int iter = 1;
                for (; iter < iterationsNumber && mistakes != maxErrorsNumber; iter++) {
                    Vector message(k, distrib(gen) & 1);
                    Vector encoded = message * m;
                    std::vector<double> signal(encoded.size());
                    for (std::size_t j = 0; j < signal.size(); j++) {
                        signal[j] = 1 - 2 * (encoded[j] ? 1 : 0) + normalDistribution(gen);
                    }
                    Vector decoded = decode(graph, signal);
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