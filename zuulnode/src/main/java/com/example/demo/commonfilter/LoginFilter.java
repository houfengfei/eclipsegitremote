package com.example.demo.commonfilter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

//////////////111111111111111
@Component
public class LoginFilter extends ZuulFilter{
	//过滤器的具体执行代码
	@Override
	public Object run() {
		//获取路径中的token参数
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        Object accessToken = request.getParameter("token");
        //如果获取的token为null,表示没有权限访问API，返回一个“token is empty”字符串
        if(accessToken == null) {
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e){}

            return null;
        }
		return null;
	}
	//指定是否要过滤
	@Override
	public boolean shouldFilter() {
		return true;
	}
	//指定过滤器的顺序
	@Override
	public int filterOrder() {
		return 0;
	}
	//指定过滤器的类型
	@Override
	public String filterType() {
		/*pre：路由之前
		  routing：路由之时
		  post： 路由之后
		  error：发送错误调用*/
		return "pre";
	}
}
