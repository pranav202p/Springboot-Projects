package com.githubactivity.githubuserActivity.service.Implementation;

import com.githubactivity.githubuserActivity.service.Abstracts.GithubService;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GithubServiceImpl implements GithubService {

    HttpClient client = HttpClient.newBuilder()
            .version((HttpClient.Version.HTTP_1_1))
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    ObjectMapper mapper = new ObjectMapper();
    @Override
    public List<String> getUserActivity(String username) throws IOException, InterruptedException {

        List<String> result = new ArrayList<>();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.github.com/users/" + username + "/events"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, Object>> events =
                mapper.readValue(response.body(), new TypeReference<List<Map<String, Object>>>() {});

        int limit = 5;
        int countEvents = 0;

        for (Map<String, Object> event : events) {

            if (countEvents >= limit) break;

            String type = (String) event.get("type");

            Map<String, Object> repo = (Map<String, Object>) event.get("repo");
            String repoName = (repo != null) ? (String) repo.get("name") : "unknown-repo";

            Map<String, Object> payload = (Map<String, Object>) event.get("payload");

            // 🔹 Push Event
            if ("PushEvent".equals(type)) {
                if (payload != null) {

                    List<Map<String, Object>> commits =
                            (List<Map<String, Object>>) payload.get("commits");

                    if (commits != null && !commits.isEmpty()) {
                        result.add("Pushed " + commits.size() + " commits to " + repoName);
                    } else {
                        result.add("Pushed commits to " + repoName);
                    }

                    countEvents++;
                }
            }

            // 🔹 Issues Event
            else if ("IssuesEvent".equals(type)) {
                if (payload != null) {
                    String action = (String) payload.get("action");

                    if ("opened".equals(action)) {
                        result.add("Opened a new issue in " + repoName);
                        countEvents++;
                    }
                }
            }

            // 🔹 Star Event
            else if ("WatchEvent".equals(type)) {
                result.add("Starred " + repoName);
                countEvents++;
            }

            // 🔹 Create Event (branch/repo creation)
            else if ("CreateEvent".equals(type)) {
                if (payload != null) {
                    String refType = (String) payload.get("ref_type");

                    if ("branch".equals(refType)) {
                        result.add("Created a new branch in " + repoName);
                    } else if ("repository".equals(refType)) {
                        result.add("Created a new repository " + repoName);
                    }

                    countEvents++;
                }
            }
        }

        return result;
    }
}