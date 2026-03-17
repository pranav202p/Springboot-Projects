package com.ttracker.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication
public class TrackerApplication {



	public static void main(String[] args) {
		SpringApplication.run(TrackerApplication.class, args);

        TaskService taskService=new TaskServiceImpl();
		Scanner scanner = new Scanner(System.in);

		while(true) {
			System.out.println("....Task Tracker....");
			System.out.println("...Choose Your option...");
			System.out.println("1.Add");
			System.out.println("2.Update");
			System.out.println("3.Delete");
			System.out.println("4.Mark a task as in progress");
			System.out.println("5.Mark a task as done");
			System.out.println("6.List all tasks");
			System.out.println("7.List all tasks that are done");
			System.out.println("8.List all tasks that are not done");
			System.out.println("9.List all tasks that are in progress");
			System.out.println("10.Exit");
			int ch = scanner.nextInt();
			if(ch==10){
				System.out.println("Closing the application .......");
				break;
			}
			switch (ch) {

				case 1:
					System.out.println("Add your Task");
					String desc = scanner.next();
					int res= taskService.addTask(desc);
					System.out.println("Task added successfully(ID:" + res + ")");
					break;
				case 2:
					System.out.println("Update your Task");
					System.out.println("Enter your task Id");
					int taskId=scanner.nextInt();
					System.out.println("Enter the updated Task");
					String updesc = scanner.next();
					int res1=taskService.updateTask(taskId,updesc);
					System.out.println("Task added successfully(ID:" + res1 + ")");
					break;

				case 3:
					System.out.println("Enter the Task Id");
					int delId=scanner.nextInt();
					boolean res2=taskService.deleteTask(delId);
					if(res2) {
						System.out.println("Task Deleted Sucessfully");
					}
					break;

				case 4:
					System.out.println("Enter the Task ID to mark in progress");
					int markId=scanner.nextInt();
					taskService.mark_in_progress(markId);
					System.out.println("Marked task as in progress");
					break;

				case 5:
					System.out.println("Enter the Task ID to mark done ");
					int mrkdoneId=scanner.nextInt();
					taskService.mark_done(mrkdoneId);
					System.out.println("Marked task as done");
					break;

				case 6:
					System.out.println("List of all Taks");
					taskService.listAll().forEach(System.out::println);
					break;

				case 7:
					System.out.println("Listing completed tasks");
					taskService.listDone().forEach(System.out::println);
					break;

				case 8:
					System.out.println("Listing not completed tasks");
					taskService.listTodo().forEach(System.out::println);
					break;

				case 9:
					 System.out.println("Listing tasks in progress");
					 taskService.listInProgress().forEach(System.out::println);
					break;

				default:
					System.out.println("Invalid option");

			}
		}

	}
}
