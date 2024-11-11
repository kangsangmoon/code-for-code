function logout() {
    console.info("실행됨");
    document.addEventListener('click', async function (event) {
        event.preventDefault();
        const token = localStorage.getItem('code-for-code-auth');
        if (token) {
            try {
                const response = await fetch('/api/users/logout', {
                    method: 'DELETE',
                    headers: {
                        'code-for-code-auth': `${token}`
                    }
                });

                if (response.ok) {
                    localStorage.removeItem('code-for-code-auth');
                    alert('성공적으로 로그아웃되었습니다.');
                    window.location.href = '/';
                } else {
                    alert('로그아웃에 실패했습니다. 다시 시도해주세요.');
                }
            } catch (error) {
                console.error('로그아웃 에러:', error);
                alert('서버 오류가 발생했습니다.');
            }
        } else {
            alert('로그아웃할 수 있는 토큰이 없습니다.');
        }
    });
}