package cn.orangepoet.inaction.spring.data.elasticsearch;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.time.DateUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class Application {
    @Autowired
    private FollowEsRepository followEsRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }


    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            initFollows();

            findByUserId(123L);
            findByUserIds(Arrays.asList(123L, 456L));
            findBySource("scan_qr");
            findByTags(Arrays.asList(1, 3));
            Date now = new Date();
            findByDate(DateUtils.addDays(now, -6), DateUtils.addDays(now, -1));
            findByTpl(Arrays.asList(123L, 456L, 789L), Arrays.asList("scan_qr"), DateRange.of(DateUtils.addDays(now, -15), DateUtils.addDays(now, -1)), Arrays.asList(1, 3));
        };
    }

    private void findByUserId(Long userId) {
        Follow follow = followEsRepository.findByUserId(userId);
        assert follow != null;
    }

    private void findByUserIds(List<Long> userList) {
        List<Follow> follows = followEsRepository.findByUserIdIn(userList);
        assert follows != null && follows.size() == userList.size();
    }

    private void findBySource(String source) {
        List<Follow> follows = followEsRepository.findBySource(source);
        assert follows != null && follows.size() == 3;
    }

    private void findByTags(List<Integer> tags) {
        List<Follow> follows = followEsRepository.findByTagsIn(tags);
        assert follows != null && follows.size() == 3;
    }

    private void findByDate(Date start, Date end) {
        List<Follow> follows = followEsRepository.findByInteractTimeBetween(start, end);
        assert follows != null && follows.size() == 3;
    }

    private void findByTpl(List<Long> userIds, List<String> sourceList, DateRange dr, List<Integer> tags) {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder filter = QueryBuilders.boolQuery();
        // userId
        filter.must(QueryBuilders.termsQuery("userId", userIds));
        // source
        filter.must(QueryBuilders.termsQuery("source", sourceList));
        // interactTime
        filter.must(QueryBuilders.rangeQuery("interactTime").from(dr.start).to(dr.end));
        // orTags
        filter.must(QueryBuilders.termsQuery("tags", tags));
        // andTags
        //..
        queryBuilder.withFilter(filter);

        SearchHits<Follow> hits = elasticsearchRestTemplate.search(queryBuilder.build(), Follow.class);

        assert hits.getTotalHits() == 1;
        assert hits.getSearchHit(0).getContent() != null;
    }

    private void initFollows() {
        Date now = new Date();
        Follow f1 = new Follow(1L, 123L, "scan_qr", DateUtils.addDays(now, -35), new Integer[]{1, 2});
        Follow f2 = new Follow(2L, 456L, "scan_qr", DateUtils.addDays(now, -15), new Integer[]{2, 3});
        Follow f3 = new Follow(3L, 789L, "scan_qr", DateUtils.addDays(now, -10), new Integer[]{3});
        Follow f4 = new Follow(4L, null, "scan_qr", DateUtils.addDays(now, -5), new Integer[]{3});
        Follow f5 = new Follow(5L, null, "source_1", DateUtils.addDays(now, -5), new Integer[]{3});
        Follow f6 = new Follow(6L, null, "source_2", DateUtils.addDays(now, -5), new Integer[]{3});

        followEsRepository.save(f1);
        followEsRepository.save(f2);
        followEsRepository.save(f3);
        followEsRepository.save(f4);
        followEsRepository.save(f5);
        followEsRepository.save(f6);
    }

    @Getter
    @Setter
    @AllArgsConstructor
    static class DateRange {
        private Date start;
        private Date end;

        public static DateRange of(Date start, Date end) {
            return new DateRange(start, end);
        }
    }
}
