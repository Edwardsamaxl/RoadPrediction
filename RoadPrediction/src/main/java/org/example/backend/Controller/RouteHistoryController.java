//package org.example.backend.Controller;
//
//import org.example.backend.Service.RouteHistoryService;
//import jakarta.servlet.http.HttpSession;
//import org.example.backend.Entity.RouteHistory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class RouteHistoryController {
//
//    @Autowired
//    private RouteHistoryService routeHistoryService;
//
//    @GetMapping("/history")
//    public ResponseEntity<List<RouteHistory>> getRouteHistory(HttpSession session) {
//        int userId = (int) session.getAttribute("userId");
//        List<RouteHistory> history = routeHistoryService.getRouteHistoryByUserId(userId);
//        return ResponseEntity.ok(history);
//    }
//
//}
