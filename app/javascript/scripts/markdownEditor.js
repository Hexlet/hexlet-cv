import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/editor';

const editorRun = () => {
  document.querySelectorAll('[data-provide="hexlet-markdown"]')
    .forEach((el) => {
      const container = document.createElement('div');
      el.parentElement.appendChild(container);
      el.classList.add('d-none');
      const editor = new Editor({
        el: container,
        initialEditType: 'markdown',
        previewStyle: 'tab',
        usageStatistics: false,
        initialValue: el.value.replace(/(\r?\n){6,}/g, '$1'),
        /* eslint-disable no-param-reassign */
        events: {
          keyup(_, e) {
            el.value = e.target.outerText;
          },
        },
        /* eslint-enable no-param-reassign */
      });

      editor.getMarkdown();
    });
};

export default editorRun;
