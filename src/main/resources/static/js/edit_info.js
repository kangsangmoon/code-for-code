document.addEventListener('DOMContentLoaded', function() {
    // 추가적인 JavaScript 기능이 필요하면 여기에 작성
    // 현재는 단순히 폼 전송 버튼 클릭 시 알림을 추가하는 예시

    const form = document.querySelector('form');
    form.addEventListener('submit', function(event) {
        const userId = document.getElementById('userId').value;
        const password = document.getElementById('password').value;
        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;

        if (!userId || !password || !name || !email) {
            alert('모든 필드를 채워야 합니다.');
            event.preventDefault(); // 폼 전송 방지
        }
    });
});
