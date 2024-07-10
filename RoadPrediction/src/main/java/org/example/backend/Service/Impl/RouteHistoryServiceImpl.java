//package org.example.backend.Service.Impl;
//
//import org.example.backend.Entity.RouteHistory;
//import org.example.backend.Mapper.RouteHistoryMapper;
//import org.example.backend.Service.RouteHistoryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class RouteHistoryServiceImpl implements RouteHistoryService {
//
//    @Autowired
//    private RouteHistoryMapper routeHistoryMapper;
//
//    @Override
//    public List<RouteHistory> getRouteHistoryByUserId(int userId) {
//        return routeHistoryMapper.searchHistoryById(userId);
//    }
//
//    @Override
//    public int addRouteHistory(RouteHistory routeHistory) {
//        return routeHistoryMapper.insert(routeHistory);
//    }
//}
