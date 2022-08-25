package dev.patri9ck.colonia.connection;

import lombok.NonNull;

public interface ConnectionRunnable<A extends AutoCloseable> {

    void run(@NonNull A autoCloseable) throws Exception;
}
