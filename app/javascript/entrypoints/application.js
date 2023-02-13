// @ts-check

// This file is automatically compiled by Webpack, along with any other files
// present in this directory. You're encouraged to place your actual application logic in
// a relevant structure within app/javascript and only use these pack files to reference
// that code so it'll be compiled.

import '../stylesheets/application.scss';
import '@nathanvda/cocoon';
import ujs from '@rails/ujs';
import editorRun from '../scripts/markdownEditor.js';
// import '../scripts/selectVacancyFilter.js'

ujs.start();
editorRun();

// Uncomment to copy all static images under ../images to the output folder and reference
// them with the image_pack_tag helper in views (e.g <%= image_pack_tag 'rails.png' %>)
// or the `imagePath` JavaScript helper below.
//

// @ts-ignore
// const images = require.context('../images', true);
// @ts-ignore
// const imagePath = (name) => images(name, true)
