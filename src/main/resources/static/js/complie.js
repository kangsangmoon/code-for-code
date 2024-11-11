function compileCode() {
    const language = document.getElementById("language").value;
    const code = document.getElementById("code").value;

    const requestData = {
        language: language,
        code: code
    };

    fetch('/api/compile', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=utf-8'
        },
        body: JSON.stringify(requestData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('output').textContent = data.data || 'No output';
        })
        .catch(error => {
            document.getElementById('output').textContent = 'Error: ' + error.message;
        });
}