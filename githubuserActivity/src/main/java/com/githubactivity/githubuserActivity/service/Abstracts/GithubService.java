package com.githubactivity.githubuserActivity.service.Abstracts;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface GithubService {

       List<String> getUserActivity(String username) throws IOException, InterruptedException;

}
