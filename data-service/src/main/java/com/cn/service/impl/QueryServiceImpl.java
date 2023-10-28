package com.cn.service.impl;

import com.alicp.jetcache.Cache;
import com.alicp.jetcache.CacheConfig;
import com.alicp.jetcache.anno.*;
import com.cn.util.R;
import com.cn.dao.ScoreMapper;
import com.cn.entity.Score;
import com.cn.service.QueryService;
import com.cn.util.TransactionCompletionAfterUtil;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
// @CacheConfig:抽取类中的所有@CachePut@Cacheable@CacheEvict的公共配置
// @CacheConfig(cacheNames = "query_cache")
// CacheRefresh是jetcache定期后台刷新机制，避免缓存过期去查数据库
public class QueryServiceImpl implements QueryService {

    @Resource
    private ScoreMapper scoreMapper;


    @Override
    // @Cacheable(key = "#id")
    //https://github.com/alibaba/jetcache/blob/master/docs/CN/MethodCache.md
    //jetcache官方文档
    // @Cached注解可以为一个方法添加缓存
    // https://github.com/alibaba/jetcache/issues/817 指定个broadcastChannel和同时syncLocal = true，可以让其他本地节点缓存实时失效方案
    @Cached(name = "local", key = "#id", cacheType = CacheType.LOCAL)
    public R selectScoreById(Integer id) {
        // 缓存注解容易导致类型转换异常，背后原因使用的是默认序列化器导致
        Score score = scoreMapper.selectById(id);
        return R.success(score);
    }

    // 缓存注解容易导致类型转换异常，背后原因使用的是默认序列化器导致
    @Override
    // @Cacheable(key = "#id")
    @Cached(name = "query_cache", key = "#id", cacheType = CacheType.LOCAL)
    public R selectById(Integer id) {
        Score score = scoreMapper.selectByPrimaryKey(id);
        return R.success(score);
    }

    @Override
    // 作用于查询方法上，查询接口继续缓存。
    // 缓存注解容易导致类型转换异常，背后原因使用的是默认序列化器导致
    // @Cacheable
    @Cached(name = "query_cache", cacheType = CacheType.LOCAL)
    public R selectScoreList() {
        List<Score> scores = scoreMapper.selectList();
        return R.success(scores);
    }

    @Override
    // 修改方法添加@CachePut 保证方法被调用，又希望结果被缓存。修改完成后的结果会进行缓存到指定的cacheNames下的key值里
    // @CachePut(key = "#score.id")
    @CacheUpdate(name = "query_cache", key = "#score.id", value = "#score")
    @Transactional
    public R updateScore(Score score) {
        scoreMapper.updateById(score);
        TransactionCompletionAfterUtil.doAfterTransaction(new Runnable() {
            @Override
            public void run() {
                System.out.println("通过TransactionSynchronization事务同步器，确保该事务提交完成后，异步回调该方法成功!");
            }
        });
        return selectScoreById(score.getId());
    }

    @Override
    // 作用于查询方法上，查询接口继续缓存。
    // cacheNames/value：用来指定缓存组件的名字
    // key：缓存数据的key，同map的key。默认使用方法参数的值。
    // keyGenerator：key的生成器，key和keyGenerator二选一使用 不可同时使用，一般在key=“”里直接自己指定key就可以了。
    // condition：符合指定的条件后才进行缓存操作
    // unless：符合指定的条件后不缓存，不符合时缓存。同condition相反，也可以通过返回接口进行判断。使用#result 获取方法返回结果。
    // sync： 是否使用异步模式。默认是方法执行完，以同步的方式将方法返回的结果存到缓存中。
    // 根据id和缓存名字删除缓存
    // @CacheEvict(key = "#id")
    @CacheInvalidate(name = "query_cache", key = "#id")
    public R deleteScoreById(Integer id) {
        scoreMapper.deleteByPrimaryKey(id);
        return R.success();
    }

    // @Override
    // public Score selectScoreAndUserScore(Integer id) {
    //     return scoreMapper.selectScoreAndUserScore(id);
    // }
    //
    // @Override
    // public Score selectScoreScore(Integer id) {
    //     return scoreMapper.selectScoreScore(id);
    // }

    //
}
