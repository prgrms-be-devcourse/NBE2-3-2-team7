// URL 파라미터에서 토큰 확인
const token = new URLSearchParams(location.search).get('token');
if (token) {
    TokenUtil.setToken(token);
    // 토큰 파라미터 제거
    window.history.replaceState({}, document.title, window.location.pathname);
}