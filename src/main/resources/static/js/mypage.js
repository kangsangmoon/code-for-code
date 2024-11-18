function mypage() {
    console.info("실행됨");
    const mypageButton = document.getElementById('mypage-button');
    if (mypageButton) {
        mypageButton.addEventListener('click', async function (event) {
            event.preventDefault();

            const token = localStorage.getItem('code-for-code-auth');

            if (token) {
                try {
                    const response = await fetch('/user/info', {
                        method: 'GET',
                        headers: {
                            'code-for-code-auth': `${token}`
                        }
                    });

                    if (response.ok) {
                        window.location.href = '/user/info';
                    } else {
                        alert('마이페이지 접근 오류. 다시 시도해주세요.');
                        mypageButton.disabled = false;
                    }
                } catch (error) {
                    console.error('마이페이지 에러:', error);
                    alert('서버 오류가 발생했습니다.');
                    mypageButton.disabled = false;
                }
            } else {
                alert('마이페이지에 접근할 수 없습니다.');
                mypageButton.disabled = false;
            }
        }, { once: true });
    }
}

window.onload = function() {
    mypage();
};