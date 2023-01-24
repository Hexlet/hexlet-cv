// @ts-check

// This file is automatically compiled by Webpack, along with any other files
// present in this directory. You're encouraged to place your actual application logic in
// a relevant structure within app/javascript and only use these pack files to reference
// that code so it'll be compiled.

import 'bootstrap-icons/font/bootstrap-icons.css';
import '../stylesheets/application.scss';
import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from "@toast-ui/editor";

import 'bootstrap-icons/font/bootstrap-icons.scss';
// import '../scripts/selectVacancyFilter.js'

import '@nathanvda/cocoon';
import ujs from '@rails/ujs';
ujs.start();

// Uncomment to copy all static images under ../images to the output folder and reference
// them with the image_pack_tag helper in views (e.g <%= image_pack_tag 'rails.png' %>)
// or the `imagePath` JavaScript helper below.
//

// @ts-ignore
// const images = require.context('../images', true);
// @ts-ignore
// const imagePath = (name) => images(name, true)

document.querySelectorAll('[data-provide="hexlet-markdown"]')
    .forEach(el => {
        const container = document.createElement('div')
        el.parentElement.appendChild(container);
        el.classList.add('d-none');
        const editor = new Editor({
            el: container,
            initialEditType: 'markdown',
            previewStyle: 'tab',
            usageStatistics: false,
            initialValue: el.value,
            events: {
                keyup(_, e) {
                    el.value = e.target.outerText
                }
            }
        });

        editor.getMarkdown();
    });
