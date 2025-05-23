import 'select2/dist/js/select2.js';
import $ from 'jquery';

window.addEventListener('DOMContentLoaded', () => {
  $('.js-select').each(function () {
    const el = $(this);

    const opts = {
      theme: 'bootstrap-5',
    };
    el.select2(opts);
  });
});
