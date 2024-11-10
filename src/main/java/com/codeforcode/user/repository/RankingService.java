package com.codeforcode.user.repository;

import com.codeforcode.user.domain.User;
import com.codeforcode.util.DateUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.codeforcode.user.domain.QUser.user;

@Repository
public class RankingService implements InitializingBean {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    private List<User> rankingList;
    private LocalDateTime lastUpdate;

    public RankingService(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
        this.lastUpdate = DateUtil.getLocalDateTime();
    }

    @Override
    @Transactional
    public void afterPropertiesSet() throws Exception {
        this.rankingList = ranking();
    }

    @Transactional(readOnly = true)
    public List<User> ranking() {
        if(isDayAfter(this.lastUpdate, DateUtil.getLocalDateTime())){
            this.rankingList = queryFactory
                    .selectFrom(user)
                    .orderBy(user.point.desc())
                    .limit(10)
                    .fetch();
        }
        return rankingList;
    }

    private boolean isDayAfter(LocalDateTime date1, LocalDateTime date2) {
        LocalDateTime dayDate1 = date1.truncatedTo(ChronoUnit.DAYS);
        LocalDateTime dayDate2 = date2.truncatedTo(ChronoUnit.DAYS);
        int compareResult = dayDate2.compareTo(dayDate1);
        return compareResult >= 1;
    }

}
