package co.com.zamora.microservice.resolveEnigmaApi.routes;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import co.com.zamora.microservice.resolveEnigmaApi.model.client.ClientJsonApiBodyResponseSuccess;

@Component
public class GetStepTwoClientRoute extends RouteBuilder{
	@Override
	public void configure()throws Exception{
		from("direct:get-step-two")
			.setHeader(Exchange.HTTP_METHOD, constant("POST"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		.to("freemarker:templates/GetStepTwoClientTemplate.ftl")
			.log("Request microservice step one ${body}")
		.hystrix()
		.hystrixConfiguration().executionTimeoutInMilliseconds(2000).end()
		.to("http4://localhost:8081/v1/getOneEnigma/getStep")
			.convertBodyTo(String.class)
			.unmarshal().json(JsonLibrary.Jackson, ClientJsonApiBodyResponseSuccess.class)
			.log("Java Response microservice step one ${body}")
		.process(new Processor() {
			@Override
			public void process(Exchange exchange)throws Exception{
				ClientJsonApiBodyResponseSuccess stepOneResponse = (ClientJsonApiBodyResponseSuccess) exchange.getIn().getBody();
				if (stepOneResponse.getData().get(0).getAnswer().equalsIgnoreCase("Paso 2: Poner la jirafa dentro")) 
				{
					exchange.setProperty("Step2", stepOneResponse.getData().get(0).getAnswer());
				}
				else
				{
					exchange.setProperty("Error", "0001");
					exchange.setProperty("descError", "Error consulting the step two");
				}
				
				
			}
			
		})
		.endHystrix()
		.onFallback()
		.process(new Processor() {
			@Override
			public void process(Exchange exchange)throws Exception {
				exchange.setProperty("Error", "0002");
				exchange.setProperty("descError", "Error consulting the step one");
			}
		})
		.end()
		.log("Response code ${exchangeProperty[Error]}")
		.log("Response description ${exchangeProperty[descError]}");
	}
}
