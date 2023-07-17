package com.eshop.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


public class JsontoExcel {
public static void main(String[] args) {
				try {
					 System.out.println("Output from Server .... \n");
					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost post = new HttpPost("http://172.16.11.191:8081/FCG/external/fetchDetails");
					post.setHeader("apirequestId", "12345678");
					post.setHeader("Content-Type", "application/json");
					StringEntity st=new StringEntity("{\"key_type\":\"A\",\"account\":\"1113632\"}");
					post.setEntity(st);
					CloseableHttpResponse response= httpClient.execute(post);
					BufferedReader br = new BufferedReader(
		                    new InputStreamReader((((HttpResponse) response).getEntity().getContent())));
			        String output=br.readLine();
			        System.out.println("Output from Server .... \n");
			        System.out.println(output);
					
				}
				catch (Exception e) {
					// TODO: handle exception
				}
				
			}
	
}
	
