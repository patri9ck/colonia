package dev.patri9ck.colonia.connection;

import lombok.NonNull;

import java.util.Optional;

public interface ConnectionManager<A extends AutoCloseable> {

    <T> Optional<T> connection(@NonNull ConnectionConsumer<A, T> connectionConsumer);

    void connection(@NonNull ConnectionRunnable<A> connectionRunnable);
}
