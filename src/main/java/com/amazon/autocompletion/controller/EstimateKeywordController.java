package com.amazon.autocompletion.controller;

import com.amazon.autocompletion.logic.KeywordVolumeCalculator;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
public class EstimateKeywordController {

    private static final String AUTOCOMPLETION_AMAZON_API_URL = "http://completion.amazon.com/search/complete?search-alias=aps&client=amazon-search-ui&mkt=1&q=";

    @GetMapping("/estimate")
    public ResponseEntity<JsonResponse> estimateKeywordVolume(@RequestParam String keyword) {

        final String url = AUTOCOMPLETION_AMAZON_API_URL + keyword;
        final RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = getHttpMessageConverters();
        restTemplate.setMessageConverters(messageConverters);

        final ResponseEntity<Object[]> response = restTemplate.exchange(url, HttpMethod.GET, null, Object[].class);
        Object[] objects = response.getBody();

        KeywordVolumeCalculator keywordVolumeCalculator = new KeywordVolumeCalculator(new ArrayList<String>((Collection<? extends String>) objects[1]) , keyword );

        return new ResponseEntity<>(new JsonResponse(keyword, keywordVolumeCalculator.getVolume()), HttpStatus.OK);
    }

    private List<HttpMessageConverter<?>> getHttpMessageConverters() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        //Add the Jackson Message converter
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        // Note: here we are making this converter to process any kind of response,
        // not only application/*json, which is the default behaviour
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);
        return messageConverters;
    }

}
