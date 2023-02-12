import 'select2/dist/js/select2';
import $ from 'jquery';
import * as Select2Ru from 'select2/src/js/select2/i18n/ru';
import * as Select2En from 'select2/src/js/select2/i18n/en';

const select2Langs = {
  ru: Select2Ru,
  en: Select2En,
};

window.addEventListener('DOMContentLoaded', () => {
  $('.js-select').each(function () {
    const el = $(this);

    const opts = {
      theme: 'bootstrap-5',
      language: select2Langs[$('body').data('lang')],
    };
    el.select2(opts);
  });
});
