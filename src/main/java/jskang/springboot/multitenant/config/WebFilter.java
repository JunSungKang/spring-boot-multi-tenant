package jskang.springboot.multitenant.config;

import java.util.Arrays;
import java.util.List;
import jskang.springboot.multitenant.database.DatabaseContextHolder;
import jskang.springboot.multitenant.database.DatabaseEnum;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class WebFilter implements org.springframework.web.server.WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain webFilterChain) {
        String customer = "";
        List<String> customers = exchange.getRequest().getQueryParams().get("company");
        if (customers != null) {
            customer = customers.get(0);
        }

        List<String> skipUrl = Arrays.asList("/modal/", "/js/", "/css/", "/fonts/", "/favicon.ico", "/img/", "/i18n/", "/error/");
        for (int i=0; i<skipUrl.size(); i++) {
            String url = exchange.getRequest().getPath().toString();
            if (url.startsWith(skipUrl.get(i))) {
                return webFilterChain.filter(exchange);
            }
        }

        if (DatabaseEnum.companyA.getName().equalsIgnoreCase(customer)) {
            DatabaseContextHolder.setCustomerContext(DatabaseEnum.companyA);
        } else if (DatabaseEnum.companyB.getName().equalsIgnoreCase(customer)) {
            DatabaseContextHolder.setCustomerContext(DatabaseEnum.companyB);
        } else {
            throw new RuntimeException("There are no customers added.");
        }
        return webFilterChain.filter(exchange);
    }
}
