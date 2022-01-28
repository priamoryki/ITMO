#include <random>
#include <iostream>
#include <pthread.h>
#include <unistd.h>
#include <sys/mman.h>
#include <sys/wait.h>


int *createSharedArray(size_t size) {
    int protection = PROT_READ | PROT_WRITE;

    int visibility = MAP_SHARED | MAP_ANONYMOUS;

    return (int *) mmap(NULL, size * sizeof(int), protection, visibility, -1, 0);
}

struct SharedArray {
    int * data;
    pthread_mutex_t mutex;
    pthread_mutexattr_t attr;

    SharedArray(size_t n): data(createSharedArray(n)) {
        pthread_mutexattr_init(&attr);
        pthread_mutexattr_setpshared(&attr, 0);
        pthread_mutex_init(&mutex, &attr);
    }

    void decrement(size_t id) {
        pthread_mutex_lock(&mutex);
        --data[id];
        pthread_mutex_unlock(&mutex);
        // std::cout << id << ": " << data[id] << "\n";
    }

    void increment(size_t id) {
        pthread_mutex_lock(&mutex);
        ++data[id];
        pthread_mutex_unlock(&mutex);
        // std::cout << id << ": " << data[id] << "\n";
    }
};

int main() {
    size_t size = 10;
    SharedArray a = SharedArray(size);

    time_t start_time = time(nullptr);
    pid_t pid = fork();

    std::mt19937_64 rnd(std::random_device{}());
    std::uniform_int_distribution<std::size_t> dist(0, size - 1);

    if (pid == 0) {
        // if it's child
        while (time(nullptr) - start_time < 5) {
            size_t elem = dist(rnd);
            a.decrement(elem);
        }
        return 0;
    }

    while (time(nullptr) - start_time < 5) {
        size_t elem = dist(rnd);
        a.increment(elem);
    }

    int status;
    while (-1 == waitpid(pid, &status, 0));

    for (size_t i = 0; i < size; i++) {
        std::cout << a.data[i] << "\n";
    }
}