//package org.example.myfood.controllers;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//@Controller
//@RequestMapping("/warehouse")
//public class WarehouseController {
//    private RestTemplate restTemplate = new RestTemplate();
//
//    @Value("${warehouse.url}")
//    private String warehouseUrl;
//    {
//        try {
//            ResponseEntity<String> responseEntity = restTemplate.postForEntity(warehouseUrl + "/clearPlace?place=b11", null, String.class);
//            System.out.println(responseEntity.getBody());
//        } catch (
//                HttpClientErrorException e) {
//            System.out.println("HTTP Error: " + e.getRawStatusCode() + " - " + e.getStatusText());
//        }
//    }
//}
