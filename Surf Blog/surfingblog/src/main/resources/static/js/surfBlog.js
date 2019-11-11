const navSlide = () => {
  const burger = document.querySelector('.burger');
  const nav = document.querySelector('.navLinks');
  const navLinks = document.querySelectorAll('.navLinks li');

  burger.addEventListener('click', () => {
    // Toggle the Nav
    nav.classList.toggle('nav-active');
    // Animate the links
    navLinks.forEach((link, index) => {
      if (link.style.animation) {
        link.style.animation = '';
      } else {
        link.style.animation = `navLinkFade 0.5s ease forwards ${index / 7 + 0.75}s`;
      }
    });
  });

}

navSlide();

$('#beach-dropdown').on('change', function () {

  var beachId = $('#beach-dropdown').find('option:selected').val();

  alert(beachId + " has been selected.");

});
