document.addEventListener('DOMContentLoaded', function() {
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
            alert('비밀번호가 조건에 맞지 않습니다. 8~16자, 영문, 숫자, 특수문자를 포함해야 합니다.');
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
        fetch('/api/users/signup', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.ok) {
                    localStorage.setItem('code-for-code-auth', response.headers.get("code-for-code-auth"));
                    alert('회원가입이 완료되었습니다!');
                    window.location.href = '/';
                } else {
                    response.json().then(data => {
                        alert(data.message || '회원가입 중 오류가 발생했습니다.');
                    });
                }
            })
            .catch(error => {
                console.error('서버 오류:', error);
                alert('서버 오류가 발생했습니다. 나중에 다시 시도해주세요.');
            });
    }

    if (registerForm) {
        registerForm.addEventListener('submit', function (event) {
            event.preventDefault(); // 기본 폼 제출 방지

            if (validateForm()) {
                const data = {
                    userId: document.getElementById('userId').value.trim(),
                    password: passwordInput.value.trim(),
                    repeatPassword: repeatPasswordInput.value.trim(),
                    name: document.getElementById('userName').value.trim(),
                    email: document.getElementById('email').value.trim()
                };

                submitForm(data);
            }
        });

        passwordInput.addEventListener('input', function () {
            if (PASSWORD_REGEX.test(passwordInput.value)) {
                passwordHelp.style.color = 'green';
                passwordHelp.textContent = '비밀번호 조건이 충족되었습니다.';
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