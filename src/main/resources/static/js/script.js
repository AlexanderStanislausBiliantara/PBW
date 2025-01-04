document.addEventListener('DOMContentLoaded', function () {
  const buttonSearchShow = document.getElementById('search-show');
  const buttonSearchArtist = document.getElementById('search-artist');
  const modalShow = document.getElementById('popup-modal-show');
  const modalArtist = document.getElementById('popup-modal-artist');
  const searchField = document.getElementById('search-field');
  const closeShow = document.querySelector('.close-modal-show');
  const closeArtist = document.querySelector('.close-modal-artist');
  const optionArtist = document.querySelectorAll('.option-artist');
  const optionShow = document.querySelectorAll('.option-show');
  const fieldArtist = document.getElementById('artist');
  const fieldShow = document.getElementById('show');
  const searchShow = document.getElementById('search-field-show');
  const searchArtist = document.getElementById('search-field-artist');
  const tempShow = document.getElementById('tempShowTitle');
  const tempArtist = document.getElementById('tempArtistName');



  buttonSearchShow.addEventListener('click', () => {
    if(modalArtist.style.display == 'flex'){
      modalArtist.style.display = 'none';
    }

    if(modalShow.style.display == 'flex'){
      modalShow.style.display = 'none';
    }
    else{
      modalShow.style.display = 'flex';
      searchField.focus();
    }
  })

  buttonSearchArtist.addEventListener('click', () => {
    if(modalShow.style.display == 'flex'){
      modalShow.style.display = 'none';
    }

    if(modalArtist.style.display == 'flex'){
      modalArtist.style.display = 'none';
    }
    else{
      modalArtist.style.display = 'flex';
      searchField.focus();
    }
  })

  closeShow.addEventListener('click', () => {
    modalShow.style.display = 'none'; // Hide the modal
  });

  closeArtist.addEventListener('click', () => {
    modalArtist.style.display = 'none';
  });

  optionArtist.forEach(artist => {
    artist.addEventListener('click', () => {
      fieldArtist.value = artist.value;
      tempArtist.value = artist.textContent;
    });
  });

  optionShow.forEach(show => {
    show.addEventListener('click', () => {
      fieldShow.value = show.value;
      tempShow.value = show.textContent;
    });
  });

  searchShow.addEventListener('input', () => {
    const searchValue = searchShow.value.toLowerCase(); // Ambil input pengguna dan ubah ke lowercase
    optionShow.forEach(show => {
        const showTitle = show.textContent.toLowerCase(); // Ambil teks dari elemen show dan ubah ke lowercase
        if (showTitle.includes(searchValue)) {
          show.style.display = ''; // Tampilkan elemen jika cocok
        } else {
          show.style.display = 'none'; // Sembunyikan elemen jika tidak cocok
        }
      });
  });

  searchArtist.addEventListener('input', () => {
    const searchValue = searchArtist.value.toLowerCase(); // Ambil input pengguna dan ubah ke lowercase
    optionArtist.forEach(artist => {
        const artistTitle = artist.textContent.toLowerCase(); // Ambil teks dari elemen artist dan ubah ke lowercase
        if (artistTitle.includes(searchValue)) {
          artist.style.display = ''; // Tampilkan elemen jika cocok
        } else {
          artist.style.display = 'none'; // Sembunyikan elemen jika tidak cocok
        }
      });
  });

});
