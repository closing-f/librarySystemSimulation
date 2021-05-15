package redislock;

import java.util.Collections;

import redis.clients.jedis.Jedis;

public class RedisTool {
	
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    /*
     * Try to obtain a distributed lock
     *  jedis Redis :client
     *  lockKey Identify the lock
     *  requestId  The request id
     *  expireTime Beyond the time
     *  Success or not
     */
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {

        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }
    private static final Long RELEASE_SUCCESS = 1L;

    /*
     * Try to release a distributed lock
     *  jedis Redis :client
     *  lockKey Identify the lock
     *  requestId  The request id
     *  expireTime Beyond the time
     *  Success or not
     */
    public static boolean releaseDistributedLock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }
    public static Jedis GetJedis()
	{
    	return new Jedis("10.28.177.157",6379);
	}

}