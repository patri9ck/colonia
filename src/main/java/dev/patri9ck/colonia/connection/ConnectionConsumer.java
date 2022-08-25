package dev.patri9ck.colonia.connection;

import lombok.NonNull;

public interface ConnectionConsumer<A extends AutoCloseable, T> {

    T consume(@NonNull A connection) throws Exception;
}
