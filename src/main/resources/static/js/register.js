document.addEventListener('DOMContentLoaded', function () {
    const registerForm = document.getElementById('registerForm');
    const passwordInput = document.getElementById('password');
    const repeatPasswordInput = document.getElementById('repeatPassword');
    const passwordHelp = document.getElementById('passwordHelp');
    const PASSWORD_REGEX = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\d~!@#$%^&*()+|=]{8,16}$/;

    function validateForm() {
        const userId = document.getElementById('userId').value.trim();
        const password = passwordInput.value.trim();
        const repeatPassword = repeatPasswordInput.value.trim();
        const name = document.getElementById('userName').value.trim();
        const email = document.getElementById('email').value.trim();
        let valid = true;

        if (userId === '') {
            alert('아이디를 입력하세요.');
            valid = false;
        }

        if (password === '') {
            alert('비밀번호를 입력하세요.');
            valid = false;
        } else if (!PASSWORD_REGEX.test(password)) {
            alert('비밀번호는 8~16자, 영문, 숫자, 특수문자(~!@#$%^&*()+|=)를 포함해야 합니다.');
            valid = false;
        }

        if (repeatPassword === '') {
            alert('비밀번호 확인을 입력하세요.');
            valid = false;
        }

        if (password !== repeatPassword) {
            alert('비밀번호와 비밀번호 확인이 일치하지 않습니다.');
            valid = false;
        }

        if (name === '') {
            alert('이름을 입력하세요.');
            valid = false;
        }

        if (email === '') {
            alert('이메일을 입력하세요.');
            valid = false;
        }

        return valid;
    }

    function submitForm(data) {
        fetch('/api/v1/user/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.text(); // JSON 대신 텍스트로 먼저 받습니다.
            })
            .then(text => {
                if (!text) {
                    throw new Error('서버로부터 빈 응답을 받았습니다.');
                }
                try {
                    return JSON.parse(text); // 텍스트를 JSON으로 파싱합니다.
                } catch (e) {
                    console.error('JSON 파싱 오류:', e);
                    console.log('받은 텍스트:', text);
                    throw new Error('서버 응답을 처리하는 중 오류가 발생했습니다.');
                }
            })
            .then(data => {
                if (data.success) {
                    alert('회원가입이 완료되었습니다.');
                    window.location.href = '/user/login';
                } else {
                    alert('회원가입에 실패했습니다: ' + (data.message || '알 수 없는 오류'));
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('회원가입 실패: ' + error.message);
            });
    }

    if (registerForm) {
        registerForm.addEventListener('submit', function (event) {
            event.preventDefault(); // 기본 폼 제출 방지

            if (validateForm()) {
                const data = {
                    userId: document.getElementById('userId').value.trim(),
                    password: passwordInput.value.trim(),
                    name: document.getElementById('userName').value.trim(),
                    email: document.getElementById('email').value.trim(),
                    userName: document.getElementById('userId').value.trim() // userName 추가
                };
                submitForm(data);
            }
        });

        passwordInput.addEventListener('input', function () {
            if (PASSWORD_REGEX.test(passwordInput.value)) {
                passwordHelp.style.color = 'green';
                passwordHelp.textContent = '비밀번호가 유효합니다.';
            } else {
                passwordHelp.style.color = 'red';
                passwordHelp.textContent = '비밀번호는 8~16자, 영문, 숫자, 특수문자(~!@#$%^&*()+|=)를 포함해야 합니다.';
            }
        });

        repeatPasswordInput.addEventListener('input', function () {
            if (passwordInput.value === repeatPasswordInput.value) {
                passwordHelp.style.color = 'green';
                passwordHelp.textContent = '비밀번호가 일치합니다.';
            } else {
                passwordHelp.style.color = 'red';
                passwordHelp.textContent = '비밀번호가 일치하지 않습니다.';
            }
        });
    }
});