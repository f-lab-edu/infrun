package com.flab.infrun.common.config.jwt;

//public class JwtFilter extends GenericFilterBean {
//
//    private final TokenProvider tokenProvider;
//
//    public JwtFilter(final TokenProvider tokenProvider) {
//        this.tokenProvider = tokenProvider;
//    }
//
//    @Override
//    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
//        final FilterChain chain)
//        throws IOException, ServletException {
//        final HttpServletRequest request = (HttpServletRequest) servletRequest;
//        final String jwt = resolveToken(request);
//
//        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
//            final Authentication authentication = tokenProvider.getAuthentication(jwt);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//
//        chain.doFilter(servletRequest, servletResponse);
//    }
//
//    private String resolveToken(HttpServletRequest request) {
//        final String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
//
//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
//            return bearerToken.substring(7);
//        }
//
//        return null;
//    }
//}
