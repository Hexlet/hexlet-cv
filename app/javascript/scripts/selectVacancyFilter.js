import 'select2/dist/js/select2.js';
import $ from 'jquery';
import * as Select2Ru from 'select2/src/js/select2/i18n/ru.js'
import * as Select2En from 'select2/src/js/select2/i18n/en.js'

const select2_langs = {
  ru: Select2Ru,
  en: Select2En
}

window.addEventListener('DOMContentLoaded', function() {
  $('.js-select').each(function() {
    const el = $(this);

    let opts = {
      theme: 'bootstrap-5',
      // language: select2_langs[$('body').data('lang')],
    }
    el.select2(opts)
  });
});
