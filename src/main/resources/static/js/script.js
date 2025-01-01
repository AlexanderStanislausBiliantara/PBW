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
      fieldArtist.value = artist.dataset.value;
      console.log(artist.dataset.value);
    });
  });

  optionShow.forEach(show => {
    show.addEventListener('click', () => {
      fieldShow.value = show.dataset.value;

      console.log(show.dataset.value);
    });
  });



});
