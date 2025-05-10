package org.example.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisStringCommands;

import java.util.List;
import java.util.Optional;

public class RedisHelper implements AutoCloseable {

    private final RedisClient redisClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public RedisHelper(String host, int port) {
        this.redisClient = RedisClient.create(RedisURI.create(host, port));
        try (StatefulRedisConnection<String, String> c = redisClient.connect()) {
            System.out.println("[Redis] connected to " + host + ":" + port);
        }
    }

    /**
     * Список CityCountry в Redis (ключ = id, значение = JSON).
     */
    public void pushToRedis(List<CityCountry> data) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (CityCountry cc : data) {
                try {
                    sync.set(String.valueOf(cc.getId()), mapper.writeValueAsString(cc));
                } catch (JsonProcessingException e) {
                    System.err.println("[Redis] JSON-serialize error for id=" + cc.getId());
                }
            }
            System.out.println("[Redis] pushed " + data.size() + " records");
        }
    }

    public void testRedisData(List<Integer> ids) {
        try (StatefulRedisConnection<String, String> connection = redisClient.connect()) {
            RedisStringCommands<String, String> sync = connection.sync();
            for (Integer id : ids) {
                String value = sync.get(String.valueOf(id));
                try {
                    mapper.readValue(value, CityCountry.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void close() {
        redisClient.shutdown();
    }
}
