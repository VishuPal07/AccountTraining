package com.example.demo.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.TreeMap;

public class ResponseHandler {
    public static ResponseEntity<Object> response(HttpStatus httpStatus, boolean isError, String message) {
        Map<String,Object> map = new TreeMap<String, Object>();
        map.put("Status",httpStatus.value());
        map.put("IsError", isError);
        map.put("Message", message);
        return new ResponseEntity<>(map,httpStatus);
    }

    public static ResponseEntity<Object> response(HttpStatus httpStatus, boolean isError, String message,Object object) {
        Map<String,Object> map = new TreeMap<String, Object>();
        map.put("Status",httpStatus.value());
        map.put("IsError", isError);
        map.put("Message", message);
        map.put("Response Object", object);
        return new ResponseEntity<>(map,httpStatus);
    }
}
