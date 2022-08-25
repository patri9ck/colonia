package dev.patri9ck.colonia.connection.jedis;

import dev.patri9ck.colonia.connection.ConnectionConsumer;
import lombok.NonNull;
import redis.clients.jedis.Jedis;

public interface JedisConnectionConsumer<T> extends ConnectionConsumer<Jedis, T> {

    T consume(@NonNull Jedis jedis);
}
