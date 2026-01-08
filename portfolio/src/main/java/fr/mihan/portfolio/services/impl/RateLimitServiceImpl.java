package fr.mihan.portfolio.services.impl;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bucket;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class RateLimitServiceImpl {
    private static final int MAX_IP_IN_MEMORY = 1000;

    private final Cache<String, Bucket> buckets = Caffeine.newBuilder()
            .expireAfterAccess(2, TimeUnit.HOURS) // Supprime l'IP si aucune requête après 2h
            .maximumSize(MAX_IP_IN_MEMORY) // max d'IPs en mémoire
            .build();

    public Bucket resolveBucket(String ip) {
        return buckets.get(ip, key -> newBucket());
    }

    private Bucket newBucket() {
        /*.
         * 3 messages max par jour
         * On récupère 1 jeton toutes les 8h
         */
        return Bucket.builder()
                .addLimit(limit -> limit
                        .capacity(3)
                        .refillIntervally(1, Duration.ofHours(8)))
                .build();
    }
}
