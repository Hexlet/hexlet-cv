import Swiper from 'swiper/bundle';

const companies = new Swiper('#companies .swiper', {
  loop: true,
  slidesPerView: 2,
  autoplay: {
    delay: 3000,
    disableOnInteraction: false,
  },
  breakpoints: {
    576: {
      slidesPerView: 4,
      spaceBetween: 10,
    },
    992: {
      slidesPerView: 6,
    },
    1200: {
      slidesPerView: 8,
    },
  },
});

const reviews = new Swiper('#reviews .swiper', {
  loop: true,
  autoplay: {
    delay: 5000,
  },
  slidesPerView: 'auto',
  spaceBetween: 30,
  pagination: {
    el: '#reviews .swiper-pagination',
    clickable: true,
  },
});
