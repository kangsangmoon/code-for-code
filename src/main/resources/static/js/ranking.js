document.addEventListener('DOMContentLoaded', function() {
    fetch('/api/v1/user/ranking')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
        .then(data => {
            const rankingTableBody = document.getElementById('rankingTableBody');
            rankingTableBody.innerHTML = '';
            data.forEach((user, index) => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${index + 1}</td>
                    <td>${user.userName}</td>
                    <td>${user.point}</td>
                `;
                rankingTableBody.appendChild(row);
            });
        })
        .catch(error => {
            document.getElementById('errorMessage').style.display = 'block';
            console.error('Error fetching ranking data:', error);
        });
});