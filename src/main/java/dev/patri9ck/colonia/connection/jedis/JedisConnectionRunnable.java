package dev.patri9ck.colonia.connection.jedis;

import dev.patri9ck.colonia.connection.ConnectionRunnable;
import lombok.NonNull;
import redis.clients.jedis.Jedis;

public interface JedisConnectionRunnable extends ConnectionRunnable<Jedis> {

    void run(@NonNull Jedis jedis);
}
