#include <iostream>
#include <omp.h>
#include <sstream>

using namespace std;

float determinant(float **matrix, int n) {
    float result = 1;
    for (int i = 0; i < n; i++) {
        int temp = i;
        for (int j = i + 1; j < n; j++) {
            if (abs(matrix[j][i]) >= abs(matrix[temp][i])) {
                temp = j;
                break;
            }
        }
        if (temp != i){
            result *= -1;
            swap(matrix[i], matrix[temp]);
        }
        if (matrix[i][i] == 0) {
            return 0;
        }
        result *= matrix[i][i];
        //#pragma omp parallel for schedule(static)
        //#pragma omp parallel for schedule(dynamic)
        #pragma omp parallel for schedule(guided)
        for (int j = i + 1; j < n; j++) {
            float b = matrix[j][i] / matrix[i][i];
            for (int k = i + 1; k < n; k++) {
                matrix[j][k] -= matrix[i][k] * b;
            }
        }
        delete matrix[i];
    }
    return result;
}

int main(int numOfArgs, char *args[]) {
    if (numOfArgs <= 2) {
        printf("Usage: hw5.exe <number of threads> <input file> [<output file>].\n");
        return 0;
    }
    int threads;
    stringstream(args[1]) >> threads;
    int maxNumberOfThreads = omp_get_max_threads();
    if (threads < 0 or maxNumberOfThreads < threads) {
        printf("Illegal number of threads.\n");
        return 0;
    }
    if (threads < maxNumberOfThreads) {
        omp_set_num_threads(threads);
    } else {
        threads = maxNumberOfThreads;
    }

    FILE *file;
    file = fopen(args[2], "r");
    if (file == NULL) {
        printf("File isn't found.\n");
        return 0;
    }
    fclose(file);

    freopen(args[2], "r", stdin);
    bool isOutputFileExists = false;
    if (numOfArgs == 4) {
        file = fopen(args[3], "w");
        if (file == NULL) {
            printf("File isn't found.\n");
            return 0;
        } else {
            isOutputFileExists = true;
        }
    }

    int n;
    cin >> n;
    float **matrix = new float *[n];
    for (int i = 0; i < n; i++) {
        matrix[i] = new float[n];
        for (int j = 0; j < n; j++) {
            cin >> matrix[i][j];
        }
    }

    auto time = omp_get_wtime();
    float result = determinant(matrix, n);
    time = omp_get_wtime() - time;

    delete matrix;
    if (isOutputFileExists) {
        fprintf(file, "Determinant: %g\n", result);
        fclose(file);
    }
    printf("Determinant: %g\nTime: %g sec\n", result, time);

    return 0;
}