document.addEventListener('DOMContentLoaded', function () {
    const button = document.getElementById('userId')
    const hidden = document.getElementById("hidden").value
    button.addEventListener('click', async () => {

        const headers = { 'code-for-code-auth': localStorage.getItem('code-for-code-auth') } // 토큰 가져오기
        if (headers) {
            try {
                const response = await(await fetch(`/api/user/info/edit/${hidden}`, {
                    headers
                })).json()
                console.log(hidden,headers,response)
                if (response.ok) {
                    // 성공적으로 요청이 완료된 경우
                    window.location.href = `/api/user/info/edit/${hidden}`; // 해당 페이지로 이동
                } else {
                    alert('페이지를 로드하는데 실패했습니다.');
                }
            } catch (error) {
                console.error('에러 발생:', error);
                alert('서버 오류가 발생했습니다.');
            }
        } else {
            alert('로그인을 해주세요.');
        }
    });
})