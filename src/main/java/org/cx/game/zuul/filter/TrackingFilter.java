package org.cx.game.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import org.cx.game.zuul.tools.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrackingFilter extends ZuulFilter{
    private static final int      FILTER_ORDER =  1;
    private static final boolean  SHOULD_FILTER=true;

    @Autowired
    FilterUtils filterUtils;

    /*
     * filterType()方法用于告诉Zuul，该过滤器是前置过滤器、路由过滤器还是后置过滤器
     */
    @Override
    public String filterType() {
        return FilterUtils.PRE_FILTER_TYPE;
    }

    /*
     * filterOrder()方法返回一个整数值，指示不同类型的过滤器的执行顺序
     */
    @Override
    public int filterOrder() {
        return FILTER_ORDER;
    }

    /*
     * shouldFilter()方法返回一个布尔值来指示该过滤器是否要执行
     */
    public boolean shouldFilter() {
        return SHOULD_FILTER;
    }

    private boolean isCorrelationIdPresent(){
      if (filterUtils.getCorrelationId() !=null){
          return true;
      }

      return false;
    }

    private String generateCorrelationId(){
        return java.util.UUID.randomUUID().toString();
    }

    /*
     * run()方法是每次服务通过过滤器时执行的代码。run()方法检查tmx-correlation-id是否存在，
     * 如果不存在，则生成一个关联值，并设置HTTP首部tmx-correlation-id
     */
    public Object run() {

        if (isCorrelationIdPresent()) {
           Logger.debug("correlation-id found in tracking filter: {}. ", filterUtils.getCorrelationId());
        }
        else{
            filterUtils.setCorrelationId(generateCorrelationId());
            Logger.debug("correlation-id generated in tracking filter: {}.", filterUtils.getCorrelationId());
        }

        RequestContext ctx = RequestContext.getCurrentContext();
        Logger.debug("Processing incoming request for {}.",  ctx.getRequest().getRequestURI());
        return null;
    }
}