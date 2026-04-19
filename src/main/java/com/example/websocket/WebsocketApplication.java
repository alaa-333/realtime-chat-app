package com.example.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class WebsocketApplication {

    public List<List<Integer>> threeSum(int[] nums) {

      ArrayList<ArrayList<Integer>> lists = new ArrayList<>();

      for (int i = 0; i < nums.length; i++)
      {
          for (int j = i+1; j < nums.length; j++)
          {
              if (nums[i] != nums[j]) {

                  for (int k = j+1 ; k < nums.length; k++)
                  {
                        if ()
                  }
              }

          }
      }



    }

	public static void main(String[] args) {
		SpringApplication.run(WebsocketApplication.class, args);
	}

}
