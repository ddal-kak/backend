package ddalkak.auth.listener;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayV2HTTPEvent;
import ddalkak.auth.AuthApplication;
import ddalkak.auth.dto.ApiGatewayLambdaResponse;
import ddalkak.auth.service.UserAuthorizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
public class LambdaEventListener implements RequestHandler<APIGatewayV2HTTPEvent, ApiGatewayLambdaResponse> {
    private final UserAuthorizer userAuthorizer;

    public LambdaEventListener() {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(AuthApplication.class)
                .web(WebApplicationType.NONE)
                .run();
        userAuthorizer = context.getBean(UserAuthorizer.class);
    }

    @Override
    public ApiGatewayLambdaResponse handleRequest(APIGatewayV2HTTPEvent event, Context context) {
        return userAuthorizer.execute(event);
    }
}
