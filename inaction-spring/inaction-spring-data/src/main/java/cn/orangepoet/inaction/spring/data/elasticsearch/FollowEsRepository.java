package cn.orangepoet.inaction.spring.data.elasticsearch;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FollowEsRepository extends ElasticsearchRepository<Follow, Long> {
    Follow findByUserId(Long userId);

    List<Follow> findByUserIdIn(List<Long> userList);

    List<Follow> findBySource(String source);

    List<Follow> findByInteractTimeBetween(Date start, Date end);

    List<Follow> findByTagsIn(List<Integer> tags);

//    @Query
//    List<Follow> findByUserIdInAndSourceInAndInteractTimeBetweenAndTagsIn(List<Long> userIds, List<String> sourceList, Date start, Date end, List<Integer> tags);
}
