package co.com.zamora.microservice.resolveEnigmaApi.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;
@Component
public class SaveLogsClientRoute extends RouteBuilder{
	@Override
	public void configure() throws Exception{
		from("seda:save-log")
		.routeId("SaveLog")
		.delay(3000)
		.log("Sucess Transaction Log ${body}");
		
	}
}
