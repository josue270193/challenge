//package app.josue.challengecalculator.application.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RedisConfig  {
//
//    @Bean
//    public Config config() {
//        Config config = new Config();
//        config.useSingleServer().setAddress("redis://localhost:6379");
//        return config;
//    }
//
//    @Bean
//    public CacheManager cacheManager(Config config) {
//        CacheManager manager = Caching.getCachingProvider().getCacheManager();
//        cacheManager.createCache("cache", RedissonConfiguration.fromConfig(config));
//        return cacheManager;
//    }
//
//    @Bean
//    ProxyManager<String> proxyManager(CacheManager cacheManager) {
//        return new JCacheProxyManager<>(cacheManager.getCache("cache"));
//    }
//}
