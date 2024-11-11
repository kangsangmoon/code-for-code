document.addEventListener('DOMContentLoaded', function() {
    // "시작하기" 버튼을 클릭했을 때 이벤트 처리
    const startButton = document.querySelector('.btn');
    startButton.addEventListener('click', function(event) {
        event.preventDefault(); // 기본 동작 막기 (새로운 페이지로 이동하는 것을 막음)

        // 페이지 이동 전 사용자에게 확인 메시지를 띄우기
        const confirmStart = confirm('시작할려면 로그인을 해야합니다. 로그인 화면으로 이동하시겠습니까?');
        if (confirmStart) {
            // 사용자가 확인하면 해당 URL로 이동
            window.location.href = '/users/login'
        }
    });

    // 간단한 애니메이션 예시: Hero 섹션의 제목에 페이드 인 애니메이션 적용
    const heroTitle = document.querySelector('.hero h1');
    heroTitle.style.opacity = 0;
    heroTitle.style.transition = 'opacity 2s';
    window.addEventListener('load', function() {
        heroTitle.style.opacity = 1; // 페이지 로드 후 제목을 점차 표시
    });

    // 각 feature 섹션에 마우스 오버 시 배경색 변경
    const features = document.querySelectorAll('.feature');
    features.forEach(function(feature) {
        feature.addEventListener('mouseover', function() {
            feature.style.backgroundColor = '#f0f8ff'; // 배경색 변경
        });
        feature.addEventListener('mouseout', function() {
            feature.style.backgroundColor = ''; // 원래 배경색으로 복구
        });
    });
});
