// problem-solving.js
document.addEventListener('DOMContentLoaded', function() {
    const solutionForm = document.getElementById('solutionForm');
    const resultOutput = document.getElementById('resultOutput');

    if (solutionForm) {
        solutionForm.addEventListener('submit', function(event) {
            event.preventDefault(); // 기본 폼 제출 방지

            const formData = new FormData(solutionForm);

            fetch(solutionForm.action, {
                method: 'POST',
                body: formData
            })
                .then(response => response.json())
                .then(result => {
                    if (result.success) {
                        // 성공적으로 제출된 경우
                        resultOutput.textContent = '코드가 성공적으로 제출되었습니다!';
                    } else {
                        // 오류 처리
                        resultOutput.textContent = '코드 제출에 실패했습니다: ' + result.error;
                    }
                })
                .catch(error => {
                    console.error('서버 오류:', error);
                    resultOutput.textContent = '서버 오류가 발생했습니다.';
                });
        });
    }
});
