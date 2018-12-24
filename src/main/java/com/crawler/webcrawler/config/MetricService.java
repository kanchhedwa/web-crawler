package com.crawler.webcrawler.config;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.ScheduledReporter;
import com.codahale.metrics.jvm.GarbageCollectorMetricSet;
import com.codahale.metrics.jvm.MemoryUsageGaugeSet;
import com.codahale.metrics.jvm.ThreadStatesGaugeSet;
import com.ryantenney.metrics.spring.config.annotation.EnableMetrics;
import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;

import metrics_influxdb.HttpInfluxdbProtocol;
import metrics_influxdb.InfluxdbReporter;

@Configuration
@EnableMetrics
public class MetricService extends MetricsConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(MetricService.class);

	@Value("${influxDB.host}")
	private String influxDBHost;

	@Value("${influxDB.host.port}")
	private String influxDBPort;

	@Value("${spring.application.name}")
	private String appName;

	@Value("${influxdb.schedule.frequency}")
	private String influxDBScheduleFrequency; 

	/**
	 * Method allows MetricsConfigurerAdapter to shutdown the reporter when spring context is closed 
	 */
	@Override
	public void configureReporters(MetricRegistry metricRegistry) {


		LOGGER.info("InfluxDB server configuration paramaters::Host:{},Port:{}, Frequency:{}, Prefix:{}" , influxDBHost, influxDBPort, influxDBScheduleFrequency, appName);

		String hostName = influxDBHost;
		int port = influxDBHost != null ? Integer.parseInt(influxDBPort):0;
		String prefix = appName;
		int scheduleFrequency = influxDBScheduleFrequency != null ? Integer.parseInt(influxDBScheduleFrequency):0;
		if (StringUtils.isBlank(hostName) || StringUtils.isBlank(influxDBPort) || StringUtils.isBlank(influxDBScheduleFrequency)) {
			LOGGER.error("***************Please configure the InfluxDB server details in applciation.properties******************");
		} else {

			String prefixHostName = null;
			try {
				prefixHostName = InetAddress.getLocalHost().getHostName().replaceAll("\\.", "-");
			} catch (UnknownHostException unknownHostException) {

				LOGGER.error("Unknown host name", unknownHostException);
			}

			LOGGER.info("influxDB UDP"); 
			
			metricRegistry.register("gc", new GarbageCollectorMetricSet());
			metricRegistry.register("memory", new MemoryUsageGaugeSet());
			metricRegistry.register("threads", new ThreadStatesGaugeSet());
			
			// TODO please uncomment below piece of code,once you have influx db installed and you
			// provided the host port in application.properties
			
			/*ScheduledReporter reporter = InfluxdbReporter
			        .forRegistry(metricRegistry)
			        .prefixedWith(prefix + "." + prefixHostName)
			        .protocol(new HttpInfluxdbProtocol(influxDBHost, Integer.parseInt(influxDBPort), "", "", "webCrawler"))				        
			        .convertRatesTo(TimeUnit.SECONDS)
			        .convertDurationsTo(TimeUnit.MILLISECONDS)
			        .filter(MetricFilter.ALL)
			        .build();
			reporter.start(scheduleFrequency, TimeUnit.SECONDS);*/
			
			LOGGER.info("Connected successfully to InfluxDB server::{}:Port::{}, reporting frequency:: {} seconds.",
					hostName, port, scheduleFrequency);
		}
	}
}
