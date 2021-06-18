#include "pi.h"

#include "random_gen.h"

double calculate_pi(unsigned long runs) {
    if (runs == 0) {
        // default return to avoid division by zero
        return 0;
    }
    unsigned long counter = 0;
    double r = 1;
    for (unsigned long i = 0; i < runs; i++) {
        double x = get_random_number();
        double y = get_random_number();
        if (x * x + y * y <= r * r) {
            // counting the number of dots in the circle
            counter++;
        }
    }
    // returning Pi
    return 4 * static_cast<long double>(counter) / runs;
}