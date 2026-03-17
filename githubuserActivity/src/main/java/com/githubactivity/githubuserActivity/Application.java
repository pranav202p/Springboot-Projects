package com.githubactivity.githubuserActivity;

import com.githubactivity.githubuserActivity.service.Abstracts.GithubService;
import com.githubactivity.githubuserActivity.service.Implementation.GithubServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws IOException, InterruptedException {
		SpringApplication.run(Application.class, args);

		GithubService githubService=new GithubServiceImpl();

		Scanner sc=new Scanner(System.in);

		System.out.println(".....GitHub User Activity......");
		System.out.println("Enter Your Username :");
		String username=sc.next();
		List<String> res=githubService.getUserActivity(username);
		System.out.println(res);

	}

}
