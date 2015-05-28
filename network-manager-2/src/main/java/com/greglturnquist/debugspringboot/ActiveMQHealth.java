package com.greglturnquist.debugspringboot;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQHealth implements HealthIndicator {

	private ConnectionFactory factory;

	@Autowired
	public ActiveMQHealth(ConnectionFactory factory) {
		this.factory = factory;
	}

	@Override
	public Health health() {
		try {
			factory.createConnection();
		} catch (JMSException e) {
			return new Health.Builder().down(e).build();
		}

		return new Health.Builder().up().withDetail("message", "Broker successfully reached").build();
	}
}
