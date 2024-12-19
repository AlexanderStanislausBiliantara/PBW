document.addEventListener('DOMContentLoaded', function () {
  const openModalButton = document.getElementById('openModal');
  const modal = document.getElementById('popup-modal');
  const closeModalButton = document.querySelector('.close-modal');
  const searchField = document.getElementById('search-field');

  // Open the modal
  openModalButton.addEventListener('click', () => {
    modal.classList.add('visible');
    searchField.focus(); // Focus on the input field
  });

  // Close the modal
  closeModalButton.addEventListener('click', () => {
    modal.classList.remove('visible');
  });

  // Optional: Close the modal when clicking outside of it
  window.addEventListener('click', (event) => {
    if (event.target === modal) {
      modal.classList.remove('visible');
    }
  });
});
