document.addEventListener("DOMContentLoaded", () => {
  const myWhiteList = $.fn.tooltip.Constructor.Default.whiteList;

  myWhiteList.a.push('data-click', 'data-click-input', 'data-share-media');
  myWhiteList.input = ['type', 'value', 'readonly'];

  $('[data-toggle="popover"]').popover();

  $('[data-toggle="popover"]').on('shown.bs.popover', function () {
    $('[data-click="copy"]').on('click', (event) => {
      event.preventDefault();

      const input = document.getElementById(event.target.dataset.clickInput);
      input.select();
      document.execCommand('copy');
    })

    const mediaButtons = document.querySelectorAll('[data-share-media="true"]');
    mediaButtons.forEach((btn) => {
      btn.addEventListener('click', (event) => {
        event.preventDefault();
        const url = btn.href;
        const options = 'toolbar=0,status=0,resizable=1,width=626,height=436';

        window.open(url, 'sharer', options);
      })
    })
  })
});

