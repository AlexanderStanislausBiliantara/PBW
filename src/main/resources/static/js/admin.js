document.addEventListener('DOMContentLoaded', function () {
    const fieldUsername = document.getElementById('userName');
    const username = document.querySelectorAll('.contentUsername');

    fieldUsername.addEventListener('input', () => {
        const search = fieldUsername.value.toLowerCase(); // Ambil teks dari elemen show dan ubah ke lowercase
        username.forEach(user => {
            const name = user.textContent.toLowerCase();
            if (name.includes(search)) {
              user.parentElement.style.display = ''; // Tampilkan elemen jika cocok
            } else {
              user.parentElement.style.display = 'none'; // Sembunyikan elemen jika tidak cocok
            }
          });
    });
  
  });
  