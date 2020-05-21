package org.cx.game.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"org.cx.game.zuul.filter"})
@EnableZuulProxy
@SpringBootApplication
public class GameZuulserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameZuulserverApplication.class, args);
	}
}
