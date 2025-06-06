package com.englishweb.h2t_backside.repository;

import com.englishweb.h2t_backside.model.features.Route;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long>, JpaSpecificationExecutor<Route> {

    @Query("SELECT r FROM Route r LEFT JOIN r.routeNodes nodes WHERE r.status = true GROUP BY r ORDER BY COUNT(nodes) DESC")
    List<Route> findTop5ByOrderByRouteNodeCountDesc(Pageable pageable);

    default List<Route> findTop3ByOrderByRouteNodeCountDesc() {
        return findTop5ByOrderByRouteNodeCountDesc(PageRequest.of(0, 3));
    }
}
