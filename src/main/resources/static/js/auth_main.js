const heroTitle = document.querySelector('.hero h1');
heroTitle.style.opacity = 0;
heroTitle.style.transition = 'opacity 2s';
window.addEventListener('load', function () {
    heroTitle.style.opacity = 1; // 페이지 로드 후 제목을 점차 표시
});

// 각 feature 섹션에 마우스 오버 시 배경색 변경
const features = document.querySelectorAll('.feature');
features.forEach(function (feature) {
    feature.addEventListener('mouseover', function () {
        feature.style.backgroundColor = '#f0f8ff'; // 배경색 변경
    });
    feature.addEventListener('mouseout', function () {
        feature.style.backgroundColor = ''; // 원래 배경색으로 복구
    });
});


