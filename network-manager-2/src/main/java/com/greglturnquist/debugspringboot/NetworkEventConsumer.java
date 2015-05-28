package com.greglturnquist.debugspringboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class NetworkEventConsumer {

	private static final Logger log =
			LoggerFactory.getLogger(NetworkEventConsumer.class);

	// TODO 1: Introduce CounterService
	private CounterService counterService;

	@Autowired
	public NetworkEventConsumer(CounterService counterService) {
		this.counterService = counterService;
	}

	@JmsListener(destination = Application.MAILBOX)
	public void process(Alarm event) {
		log.info("Processing " + event);

		/**
		 * More metrics to track messages as they are consumed
		 */
		counterService.increment("messages.total.consumed");
		counterService.increment("messages." + event.getHostname() + ".consumed");
	}
}
