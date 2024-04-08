package org.example.myfood.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.mapper.Mapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.example.myfood.DTO.ReportDTO;
import org.example.myfood.repositories.EatenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.Console;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    EatenRepository eatenRepository;
    private static JedisPool jedisPool = new JedisPool(new GenericObjectPoolConfig(), "localhost", 6379);
    public ReportDTO getCashedReport(String firstDateString, String lastDateString) {
        ObjectMapper mapper = new ObjectMapper();

        if (firstDateString == null || lastDateString == null) {
            throw new IllegalArgumentException("Date strings cannot be null");
        }

        try (Jedis jedis = jedisPool.getResource()) {
            String key = String.format("Report:%s-%s", firstDateString, lastDateString);
            String row = jedis.get(key);
            if (row != null) {
                return mapper.readValue(row, ReportDTO.class);
            }

            ReportDTO report = getReport(firstDateString, lastDateString);
            jedis.setex(key, 10, mapper.writeValueAsString(report));

            return report;

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ReportDTO getReport(String firstDateString, String lastDateString){
        String[] firstDateListString = firstDateString.split("-");
        String[] lastDateListString = lastDateString.split("-");

        ArrayList<Integer> firstDateList = new ArrayList<>();
        for(String str:firstDateListString){
            firstDateList.add(Integer.parseInt(str));
        }

        ArrayList<Integer> lastDateList = new ArrayList<>();
        for(String str:lastDateListString){
            lastDateList.add(Integer.parseInt(str));
        }

        LocalDate firstDateLocalDate = LocalDate.of(firstDateList.get(0), firstDateList.get(1), firstDateList.get(2));
        LocalDate lastDateLocalDate = LocalDate.of(lastDateList.get(0), lastDateList.get(1), lastDateList.get(2));

        String report = eatenRepository.getReport(firstDateLocalDate, lastDateLocalDate);

        String[] reportSplitedString = report.split(",");

        ArrayList<Integer> reportSplited = new ArrayList<>();
        for(String str : reportSplitedString){
            try {
                reportSplited.add(Integer.parseInt(str));
            }catch (NumberFormatException e){
                reportSplited.add(0);
            }
        }

        ReportDTO reportDTO = new ReportDTO(reportSplited.get(0), reportSplited.get(1), reportSplited.get(2), reportSplited.get(3));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return reportDTO;
    }
}
