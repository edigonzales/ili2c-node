#include <stdio.h>
#include <stdlib.h>

#include "libili2c.h"

int main(int argc, char **argv) {
    fprintf(stderr, "Hallo Welt");

    graal_isolate_t *isolate = NULL;
    graal_isolatethread_t *thread = NULL;

    if (graal_create_isolate(NULL, &isolate, &thread) != 0) {
        fprintf(stderr, "initialization error\n");
        return 1;
    }

    printf("Number of entries: %d\n", ili2c(thread, argv[1], argv[2]));

    graal_tear_down_isolate(thread);
}



// if (argc != 2) {
//     fprintf(stderr, "Usage: %s <filter>\n", argv[0]);
//     exit(1);
// }
